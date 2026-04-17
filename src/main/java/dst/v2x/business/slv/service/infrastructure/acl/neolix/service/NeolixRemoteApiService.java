package dst.v2x.business.slv.service.infrastructure.acl.neolix.service;

import cn.hutool.core.collection.CollectionUtil;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.redis.RedisKeyConstant;
import dst.v2x.business.slv.service.common.utils.DstRedisUtil;
import dst.v2x.business.slv.service.common.utils.RedisUtil;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteAuthApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixStationInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixTaskInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixVehicleBaseListReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.*;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新石器无人车远程接口服务
 * @author: zy
 * @date: 2024/11/8 15:57
 */
@Slf4j
@Service
public class NeolixRemoteApiService {
    @Resource
    private NeolixRemoteApi neolixRemoteApi;
    @Resource
    private NeolixRemoteAuthApi neutronRemoteAuthApi;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private NeolixConvertService neolixConvertService;

    @Value("${neolix.clientId}")
    private String clientId;

    @Value("${neolix.clientSecret}")
    private String clientSecret;

    @Value("${neolix.enterpriseCode}")
    private String enterpriseCode;

    /**
     * 获取新石器无人车的任务信息列表
     */
    public List<TaskInfo> getTaskList(String vin) {
        NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin(vin);
        NeolixResponse<List<NeolixTaskInfoRes>> response = neolixRemoteApi.getTaskList(req);
        log.debug("response: {}", response);
        if(CollectionUtil.isEmpty(response.getData())){
            return new ArrayList<>();
        }
        return response.getData().stream().map(d->neolixConvertService.convertTaskInfo(d)).collect(Collectors.toList());
    }

    /**
     * 获取新石器无人车的站点信息列表
     */
    public List<StationInfo> getStationList(String vin) {
        NeolixStationInfoReq req = new NeolixStationInfoReq();
        req.setVin(vin);
        NeolixResponse<List<NeolixStationInfoRes>> response = neolixRemoteApi.getStationList(req);
        log.debug("response: {}", response);
        if(CollectionUtil.isEmpty(response.getData())){
            return new ArrayList<>();
        }
        return response.getData().stream().map(d->neolixConvertService.convertStationInfo(d)).collect(Collectors.toList());
    }

    /**
     * 获取新石器无人车的车辆信息
     * @return
     */
    public List<NeolixVehicleBaseListRes> getVehicleBaseList() {
        try {
            NeolixVehicleBaseListReq req = new NeolixVehicleBaseListReq();
            req.setEnterpriseCode(enterpriseCode);

            NeolixResponse<List<NeolixVehicleBaseListRes>> response = neolixRemoteApi.getVehicleBaseList(req);
            log.info("拉取新石器无人车的车辆信息，响应：{}", DstJsonUtil.toString(response));
            if (response == null || response.getData() == null) {
                return new ArrayList<>();
            }
            return response.getData();
        } catch(Exception e){
            log.error("拉取新石器无人车的车辆信息失败", e);
        }
        return new ArrayList<>();
    }

    /**
     * 刷新token
     */
    public String flushToken(){
        log.info("新石器刷新token-开始");
        NeolixTokenRes data = neutronRemoteAuthApi.getToken("client_credentials", clientId, clientSecret);
        if(data == null){
            return null;
        }
        String accessToken = data.getAccessToken();
        if(StringUtils.isBlank(accessToken)){
            return null;
        }

        // 拿到过期时间 接口文档给的时间单位是秒
        Integer expiresTime = data.getExpiresIn();
        // 如果接口没有给过期时间，就给个默认值 10秒
        if (expiresTime == null) {
            expiresTime = 10;
        } else {
            // 如果给了过期时间，就在Redis中早1分钟过期,防止有临界值
            expiresTime = expiresTime - 60;
            log.info("新石器的TOKEN过期时间,单位秒->{}", expiresTime);
        }
        // 保存到Redis
        redisUtil.set(DstRedisUtil.toKey(RedisKeyConstant.NEOLIX_ACCESS_TOKEN_KEY), accessToken, expiresTime);
        log.info("新石器刷新token-结束，新token->{}", accessToken);
        return accessToken;
    }

    /**
     * 获取token
     * @return
     */
    public String getNeolixToken() {
        String tokenKey = DstRedisUtil.toKey(RedisKeyConstant.NEOLIX_ACCESS_TOKEN_KEY);

        // 先去redis中拿取token
        String token = (String) redisUtil.get(tokenKey);
        // 如果redis里面有，直接返回
        if (StringUtils.isNotBlank(token)) {
            log.debug("从Redis中获取到新石器无人车的TOKEN->{}", token);
            return token;
        }
        return flushToken();
    }
}
