package com.jd.help.service.impl;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.common.web.result.Result;
import com.jd.help.service.OrderService;
import com.jd.order.sdk.domain.order.OrderInfo;
import com.jd.order.sdk.domain.order.OrderListInfo;
import com.jd.order.sdk.domain.order.OrderQueryInfo;
import com.jd.order.sdk.domain.order.OrderWareInfo;
import com.jd.order.sdk.domain.result.OrderListResultInfo;
import com.jd.order.sdk.export.result.OrderInfoResult;
import com.jd.order.sdk.export.result.OrderListInfoResult;
import com.jd.order.sdk.export.vo.ClientInfo;
import com.jd.order.sdk.export.vo.OrderInfoQueryVo;
import com.jd.order.sdk.export.vo.OrderInfoQueryVoParam;
import com.jd.order.sdk.export.vo.OrderQueryInfoParam;
import com.jd.order.sdk.jsf.export.OrderCenterJsfExport;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("orderService")
public class OrderServiceImpl implements OrderService{

	private static final Log log = LogFactory.getLog(OrderServiceImpl.class);

	private static final String SYS_NAME = "pop.hc.man";
	private static final String TOKEN = "C5458F53A2AC481DB0D7ADA348FAB09A";

	//@Resource(name="orderCenterExportSaf")
	//private OrderCenterExport orderCenterExport;

	@Resource
	private OrderCenterJsfExport orderCenterJsfExport;

	private static final String IMAGE_HOST = "//img10.360buyimg.com/n4/";
	
	//缓存用户pin的key的前缀
	private static final String CACHE_KEY_PREFIX_LIST = "HELP_ORDER_PIN_";
	
	@Override
	public Result getOrderList(OrderQueryInfo orderQueryInfo) {
		CallerInfo info = Profiler.registerInfo("vender.help.customer.orderService.getOrderList", false, true);
		Result result = new Result();
		OrderListResultInfo orderResult = null;
		OrderListInfoResult infResult = null;
		try{
			OrderQueryInfoParam param = new OrderQueryInfoParam();
			param.setOrderQueryInfo(orderQueryInfo);
			ClientInfo clientInfo = new ClientInfo(SYS_NAME,TOKEN);
			param.setClientInfo(clientInfo);
			infResult = orderCenterJsfExport.getOrderList(param);
			if(infResult != null && infResult.isSuccess()){
				orderResult = infResult.getOrderListResultInfo();
			}

		}catch(Exception e){
			log.error("vender.help.customer.orderService.getOrderList",e);
			log.error("call orderCenterExport error.",e);
			result.setSuccess(false);
			result.addDefaultModel("hasError",new Boolean(true));
		}
		if(orderResult == null || orderResult.getOrderListInfos() == null 
				|| orderResult.getOrderListInfos().size() < 1){
			result.setSuccess(true);
			result.setResultCode("no entity");
			return result;
		}
		log.debug("call orderList------->" + JSONObject.fromObject(orderResult) );
		log.debug("call order-sdk orderList:" + orderResult.getNum() + ",order size" + orderResult.getOrderListInfos().size());
		PaginatedList<OrderListInfo> orderList =
				new PaginatedArrayList<OrderListInfo>(orderQueryInfo.getPage(), orderQueryInfo.getSize());
		result.setSuccess(true);
		orderList.addAll(orderResult.getOrderListInfos());
		orderList.setTotalItem(orderResult.getNum());
		result.addDefaultModel("orderList", orderList);
		Profiler.registerInfoEnd(info);
		return result;
		
	}
	
	public Result getOrderInfo(OrderInfoQueryVo orderInfoQueryVo){
		CallerInfo info = Profiler.registerInfo("vender.help.customer.orderService.getOrderInfo", false, true);
		Result result = new Result();
		OrderInfo orderInfo = null;
		OrderInfoResult orderInfoResult = null;
		try{
			OrderInfoQueryVoParam param = new OrderInfoQueryVoParam();
			ClientInfo clientInfo = new ClientInfo(SYS_NAME,TOKEN);
			param.setClientInfo(clientInfo);
			param.setOrderId(String.valueOf(orderInfoQueryVo.getOrderId()));
			param.setPin(orderInfoQueryVo.getPin());
			orderInfoResult = orderCenterJsfExport.getOrderInfo(param);
			if(orderInfoResult != null && orderInfoResult.isSuccess()){
				orderInfo = orderInfoResult.getOrderInfo();
			}
		}catch(Exception e){
			log.error("vender.help.customer.orderService.getOrderInfo",e);
			log.error("call getOrderInfo error.",e);
			result.setSuccess(false);
			result.addDefaultModel("hasError",new Boolean(true));
		}
		if(orderInfo != null && orderInfo.getOrderWareInfos() != null && orderInfo.getOrderWareInfos().size() > 0){
			for(OrderWareInfo ware : orderInfo.getOrderWareInfos()){
				ware.setImgPath(IMAGE_HOST + ware.getImgPath());
			}
		}
//		try{
//			JsonConfig jc = new JsonConfig();
//			jc.setExcludes(new String[]{"insSinglePay"});
//			log.info("call orderInfo------------->" + JSONObject.fromObject(orderInfo,jc).toString());
//		}catch(Exception e){
//			log.error("----",e);
//		}
		result.setSuccess(true);
		result.addDefaultModel("orderInfo", orderInfo);
		Profiler.registerInfoEnd(info);
		return result;
	}

}
