package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderShipmentMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(47, "邮政包裹");
		map.put(49, "中铁快运");
		map.put(51, "邮政快包");
		map.put(52, "公路运输");
		map.put(64, "自提");
		map.put(65, "京东快递");
		map.put(67, "邮局EMS");
		map.put(68, "申通快递");
		map.put(69, "顺丰快递");
		map.put(70, "快递运输");
		map.put(71, "圆通快递");
		map.put(1001,"DHL");
		map.put(1002,"UPS");
		map.put(1015,"4PX新加坡邮政挂号小包");
		map.put(1018,"Equick专线");
		map.put(1020,"Equick小包");
		map.put(1021,"新加坡空运集货");
		map.put(1022,"EMS");
		map.put(1023,"中邮挂号小包");
		map.put(1024,"新加坡海运集货");
		map.put(1025,"ePacket US");
		map.put(1026,"ePacket AU");
		map.put(1027,"ePacket GB");
		map.put(1028,"ePacket CA");
		map.put(1029,"eParcel");
		map.put(1030,"eExpress");
		map.put(1031,"eExpress-Taiwan");
		map.put(2933,"高校代理送货");
	}
	
	private OrderShipmentMap(){
		init();
	}
	
	private static OrderShipmentMap singletonMap = new OrderShipmentMap();
	
	public static OrderShipmentMap getInstance(){
		return singletonMap;
	}
	
	public String getName(int code){
		return map.get(code);
	}
}
