package dst.v2x.business.slv.service.module.business.archive.service;

import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.redis.RedisKeyConstant;
import dst.v2x.business.slv.service.common.utils.DstRedisUtil;
import dst.v2x.business.slv.service.common.utils.RedisUtil;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosGetVehicleListReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosPageRes;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse2;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosTokenRes;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.service.ZelosAutoApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-车辆信息-九识相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class VehicleInfoZelosBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;
    @Autowired
    private ZelosAutoApiService zelosAutoApiService;

    /**
     * 拉取九识车辆信息
     *
     * @author 江民来
     * @date 2025/2/10 18:33
     */
    public void updateZelosVehicleInfo() {
        try {
            log.info("开始拉取九识车辆信息");
            // 初始页码
            Integer pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            // 每次最大100条
            ZelosGetVehicleListReq req = new ZelosGetVehicleListReq();
            req.setPageSize(100);
            while (true) {
                req.setPageNo(pageNo);
                // 调用九识的接口获取车辆数据-车辆编号
                ZelosResponse2<ZelosPageRes> response = zelosAutoCarFeignApi.getAutoVehicleList(req);
                if (response == null || response.getData() == null) {
                    return;
                }
                ZelosPageRes page = response.getData();
                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = page.getTotalPages();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                //通过车辆编号获取车辆信息并入库
                vehicleInfoService.batchAddOrUpdate(zelosAutoApiService.getAutoVehicleInfo(page.getVehicles()));

                // 页码+1
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("结束拉取九识车辆信息");
        } catch (Exception e) {
            log.error("拉取九识车辆信息异常", e);
        }
    }

    /**
     * 清理九识无效车辆信息
     */
    public void clearZelosInvalidVehicleInfo() {
        try {
            log.info("开始清理九识无效车辆信息");
            // 创建查询对象
            VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
            pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            //初始化页码
            int pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            //设置每页查询数量，每页最大100条
            pageDTO.setPageSize(100);

            //按照页码循环查询
            while (true) {
                pageDTO.setPageNum(pageNo);
                //分页请求九识获取车辆信息
                PageDTO<String> pageRes = vehicleInfoService.queryNoPage(pageDTO);

                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = pageRes.getTotalPage();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                //获取已失效的车辆编号
                List<String> invalidVehicleNos = zelosAutoApiService.getInvalidVehicleNo(pageRes.getList());

                log.info("已失效的车辆编号:{}", invalidVehicleNos);
                //批量删除
                vehicleInfoService.batchRemove(invalidVehicleNos);

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("结束清理九识无效车辆信息");
        } catch (Exception e) {
            log.error("清理九识无效车辆信息异常", e);
        }
    }

    /**
     * 获取九识无人车的token
     *
     * @return ZelosResponse<ZelosTokenDTO>
     * @author 江民来
     * @date 2025/2/10 16:10
     */
    public String getZelosToken() {
        String tokenKey = DstRedisUtil.toKey(RedisKeyConstant.ZELOS_ACCESS_TOKEN_KEY);
        // 先去redis中拿取token
        String token = (String) redisUtil.get(tokenKey);
        // 如果redis里面有，直接返回
        if (StringUtils.isNotBlank(token)) {
            log.debug("从Redis中获取到九识无人车的TOKEN->{}", token);
            return token;
        }
        return flushToken();
    }

    /**
     * 刷新token
     */
    public String flushToken(){
        log.info("九识刷新token-开始");
        String tokenKey = DstRedisUtil.toKey(RedisKeyConstant.ZELOS_ACCESS_TOKEN_KEY);
        ZelosTokenRes res = zelosAutoApiService.getAccessToken();
        log.info("请求九识无人车的Token->{}", JSON.toJSONString(res));

        // 拿到过期时间 接口文档给的时间单位是分钟,过期时间是1440分钟 ，24小时
        Integer expiresTime = res.getExpiresAfter();
        // 如果接口没有给过期时间，就给个默认值 10秒
        if (expiresTime == null) {
            expiresTime = 10;
        } else {
            // 如果给了过期时间，就在Redis中早1分钟过期,防止有临界值；再转换成秒
            expiresTime = (expiresTime - 1) * 60;
        }
        // 保存到Redis
        redisUtil.set(tokenKey, res.getToken(), expiresTime);

        log.info("九识刷新token-结束");
        return res.getToken();
    }
}
