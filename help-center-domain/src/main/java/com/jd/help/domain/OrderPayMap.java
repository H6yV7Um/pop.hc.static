package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderPayMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(1, "货到付款");
		map.put(2, "邮局汇款");
		map.put(3, "自提");
		map.put(4, "在线支付");
		map.put(5, "公司转帐");
		map.put(6, "银行卡转帐");
		map.put(7, "分期-招行");
		map.put(8, "分期付款");
		map.put(10, "高校代理-自己支付");
		map.put(11, "高校代理-代理垫付");
		map.put(12, "月结");
		map.put(13, "高校代理－货到付款");
		map.put(14, "PayPal");
		map.put(15, "地铁自提");
		map.put(16, "校园自提");
		map.put(17, "好邻居自提");
		map.put(18, "社区自提");
		map.put(19, "自提柜");
		map.put(99, "混合支付");
		map.put(165, "银联手机支付");
	}
	
	private OrderPayMap(){
		init();
	}
	
	private static OrderPayMap singletonMap = new OrderPayMap();
	
	public static OrderPayMap getInstance(){
		return singletonMap;
	}
	
	public String getName(int code){
		return map.get(code);
	}
}
