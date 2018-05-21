package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderStateMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(0, "新订单");
		map.put(1, "等待付款");
		map.put(2, "等待付款确认");
		map.put(3, "延迟付款确认");
		map.put(4, "暂停");
		map.put(5, "店长最终审核");
		map.put(6, "等待打印");
		map.put(7, "等待出库");
		map.put(8, "等待打包");
		map.put(9, "等待发货");
		map.put(10, "自提途中");
		map.put(11, "上门提货");
		map.put(12, "自提退货");
		map.put(13, "确认自提");
		map.put(14, "等待回执");
		map.put(15, "等待确认收货");
		map.put(16, "配送退货");
		map.put(17, "货到付款确认");
		map.put(18, "完成");
		map.put(19, "等待分期付款");
		map.put(20, "收款确认");
		map.put(21, "锁定");
		map.put(22, "等待退款");
		map.put(23, "等待客户回复");
		map.put(24, "厂商确认完成");
		map.put(25, "等待再审核");
		map.put(26, "对账中");
		map.put(27, "退款中");
		map.put(28, "等待三方出库");
		map.put(29, "等待三方发货");
		map.put(30, "等待三方发货完成");
		map.put(31, "已取消");
		map.put(32, "拆分中");
		map.put(1000, "未知状态");
	}
	
	private OrderStateMap(){
		init();
	}
	
	private static OrderStateMap singletonMap = new OrderStateMap();
	
	public static OrderStateMap getInstance(){
		return singletonMap;
	}
	
	public String getName(int code){
		return map.get(code);
	}
}
