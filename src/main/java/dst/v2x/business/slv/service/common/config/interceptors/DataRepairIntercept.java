package dst.v2x.business.slv.service.common.config.interceptors;

import cn.hutool.core.util.StrUtil;
import com.dst.steed.vds.common.domain.response.Response;
import com.dst.steed.vds.common.util.DstJsonUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 数据修复接口认证拦截器
 *
 */
@Slf4j
public class DataRepairIntercept implements HandlerInterceptor {
	private static String OK_DATA_REPAIR_CODE = "202506241529pyl";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if(!OK_DATA_REPAIR_CODE.equals(getDataRepairCode(request))){
			return setResponse(Response.error("dataRepairCode错误"), response);
		}
		return true;
	}

	public static String getDataRepairCode(HttpServletRequest request){
		String dataRepairCode = request.getHeader("dataRepairCode");
		if (StrUtil.isBlank(dataRepairCode)){
			dataRepairCode = request.getParameter("dataRepairCode");
		}
		return dataRepairCode;
	}

	private boolean setResponse(Response response, HttpServletResponse httpResponse) {
		httpResponse.setHeader("Content-type", "application/json;charset=UTF-8");
		try {
			ServletOutputStream outputStream = httpResponse.getOutputStream();
			outputStream.write(DstJsonUtil.toString(response).getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			log.error("setResponse方法报错", e);
		}
		return false;
	}
}
