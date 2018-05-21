package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderShipmentMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(47, "��������");
		map.put(49, "��������");
		map.put(51, "�������");
		map.put(52, "��·����");
		map.put(64, "����");
		map.put(65, "�������");
		map.put(67, "�ʾ�EMS");
		map.put(68, "��ͨ���");
		map.put(69, "˳����");
		map.put(70, "�������");
		map.put(71, "Բͨ���");
		map.put(1001,"DHL");
		map.put(1002,"UPS");
		map.put(1015,"4PX�¼��������Һ�С��");
		map.put(1018,"Equickר��");
		map.put(1020,"EquickС��");
		map.put(1021,"�¼��¿��˼���");
		map.put(1022,"EMS");
		map.put(1023,"���ʹҺ�С��");
		map.put(1024,"�¼��º��˼���");
		map.put(1025,"ePacket US");
		map.put(1026,"ePacket AU");
		map.put(1027,"ePacket GB");
		map.put(1028,"ePacket CA");
		map.put(1029,"eParcel");
		map.put(1030,"eExpress");
		map.put(1031,"eExpress-Taiwan");
		map.put(2933,"��У�����ͻ�");
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
