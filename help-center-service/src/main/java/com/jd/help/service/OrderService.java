package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.order.sdk.domain.order.OrderQueryInfo;
import com.jd.order.sdk.export.vo.OrderInfoQueryVo;

public interface OrderService {
	/**
	 * ��ȡ�����б�
	 * @param orderQueryInfo
	 * @return
	 */
	Result getOrderList(OrderQueryInfo orderQueryInfo);
	/**
	 * ��ȡ��������.
	 * @param orderInfoQueryVo
	 * @return
	 */
	Result getOrderInfo(OrderInfoQueryVo orderInfoQueryVo);
}
