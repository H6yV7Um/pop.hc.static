package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.order.sdk.domain.order.OrderQueryInfo;
import com.jd.order.sdk.export.vo.OrderInfoQueryVo;

public interface OrderService {
	/**
	 * 获取订单列表
	 * @param orderQueryInfo
	 * @return
	 */
	Result getOrderList(OrderQueryInfo orderQueryInfo);
	/**
	 * 获取订单详情.
	 * @param orderInfoQueryVo
	 * @return
	 */
	Result getOrderInfo(OrderInfoQueryVo orderInfoQueryVo);
}
