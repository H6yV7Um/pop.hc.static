package com.jd.help.customer.web;

import com.jd.common.util.PaginatedList;
import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.center.web.util.ShopWebHelper;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.dao.issue.search.IssueSearcher;
import com.jd.help.domain.*;
import com.jd.help.service.OrderService;
import com.jd.order.sdk.domain.order.OrderInfo;
import com.jd.order.sdk.domain.order.OrderQueryInfo;
import com.jd.order.sdk.export.vo.OrderInfoQueryVo;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;

import javax.annotation.Resource;

import java.net.URLDecoder;
import java.util.Date;

public class OrderAction extends HelpBaseAction {
	
	private static final Log log = LogFactory.getLog(OrderAction.class);
	
	@Resource
	private OrderService orderService;
	
	@Resource
    private IssueSearcher issueSearcher;
	
	private long orderId;
	
	//订单功能类别
	//订单列表
	private static final int CODE_ORDER_LIST = 1;
	//订单详情
	private static final int CODE_ORDER_DETAIL = 2;
	
	//每页显示条数
	private static final int ORDER_PAGE_SIZE = 5;
	//匹配问题权重
	private static final int BOOST_ORDER_STATE = 48;
	private static final int BOOST_ORDER_TYPE = 34;
	private static final int BOOST_ORDER_PAY = 10;
	private static final int BOOST_ORDER_SHIPMENT = 8;
	
	private OrderStateMap orderStateMap = OrderStateMap.getInstance();
	private OrderTypeMap orderTypeMap = OrderTypeMap.getInstance();
	private OrderPayMap orderPayMap = OrderPayMap.getInstance();
	private OrderShipmentMap orderShipmentMap = OrderShipmentMap.getInstance();
	
	public String list(){
		// 初始化站点
        initSite();
		if (response != null) {
			response.setHeader("Cache-Control", "no-cache");
			response.addDateHeader("Last-Modified", System.currentTimeMillis());
		}
		String userPin = null; 
		try{
			userPin = ShopWebHelper.getPin();
			userPin = URLDecoder.decode(userPin, "gbk");
		}catch(Exception e){
			Result result  = new Result(false);
			result.setResultCode("not login");
			return "login";
		}

		
		OrderQueryInfo orderQueryInfo = new OrderQueryInfo();
		orderQueryInfo.setSize(ORDER_PAGE_SIZE);
		page = (page <= 0 ? 1 : page);
		orderQueryInfo.setPage(page);
		orderQueryInfo.setPin(userPin);
		orderQueryInfo.setState(4096);
		//List<Integer> typeList = new ArrayList<Integer>();
		//typeList.add(new Integer(0));
		//orderQueryInfo.setTypeList(typeList);
		Date endDate = new Date(System.currentTimeMillis());
		orderQueryInfo.setEndDate(endDate);
		Date startDate = DateUtils.addDays(endDate, -90);
		orderQueryInfo.setStartDate(startDate);
		//log.debug("-------orderQueryInfo-->" + JSONObject.fromObject(orderQueryInfo));
		Result result  = orderService.getOrderList(orderQueryInfo);
		
		this.toVm(result);
		return SUCCESS;
	}
	public String detail(){
		// 初始化站点
        initSite();
		if (response != null) {
			response.setHeader("Cache-Control", "no-cache");
			response.addDateHeader("Last-Modified", System.currentTimeMillis());
		}
        String userPin = null; 
		try{
			userPin = ShopWebHelper.getPin();
			userPin = URLDecoder.decode(userPin, "gbk");
		}catch(Exception e){
			Result result  = new Result(false);
			result.setResultCode("not login");
			return "login";
		}
		
		OrderInfoQueryVo orderInfoQueryVo = new OrderInfoQueryVo();
		orderInfoQueryVo.setPin(userPin);
		orderInfoQueryVo.setOrderId(orderId);
		
		Result result = orderService.getOrderInfo(orderInfoQueryVo);
		
		CallerInfo info = Profiler.registerInfo("vender.help.customer.orderAction.detail.getIssue", false, true);
		if(result.get("orderInfo") != null){
			OrderInfo orderInfo = (OrderInfo)result.get("orderInfo");
			
			StringBuilder queryString = new StringBuilder();
			queryString.append("siteId:").append(site.getId()).append(IssueSearcher.AND);
			queryString.append("status:(0 1)").append(IssueSearcher.AND);
			queryString.append("(");
			//if()
			queryString.append("orderStatus:(").append(orderInfo.getOrderStatusInfo().getOrderState())
				.append(")^").append(BOOST_ORDER_STATE).append(IssueSearcher.AND);
			queryString.append("orderType:(").append(orderInfo.getOrderType())
				.append(")^").append(BOOST_ORDER_TYPE).append(IssueSearcher.AND);
            queryString.append("orderPay:(").append(orderInfo.getIdPaymentType())
                    .append(")^").append(BOOST_ORDER_PAY).append(IssueSearcher.AND);
            queryString.append("orderShipment:(").append(orderInfo.getIdShipmentType())
                    .append(")^").append(BOOST_ORDER_SHIPMENT);
			queryString.append(")");
			
			SolrQuery solrQuery = new SolrQuery(queryString.toString().trim());
	        page = (page <= 0 ? 1 : page);
			PaginatedList<Issue> issueList = issueSearcher.search(solrQuery, page, PAGE_SIZE);
			result.addDefaultModel("issueList", issueList);
		}
		this.toVm(result);
		Profiler.registerInfoEnd(info);
		return SUCCESS;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public OrderStateMap getOrderStateMap() {
		return orderStateMap;
	}

	public OrderTypeMap getOrderTypeMap() {
		return orderTypeMap;
	}

	public OrderPayMap getOrderPayMap() {
		return orderPayMap;
	}

	public OrderShipmentMap getOrderShipmentMap() {
		return orderShipmentMap;
	}
}
