package com.jd.help.domain;

public enum OrderShipment {
	YOUZHENGBAOGUO("47","��������"),
	ZHONGTIEKUAIYUN("49","��������"),
	YOUZHENGKUAIBAO("51","�������"),
	GONGLUYUNSHU("52","��·����"),
	ZITI("64","����"), 
	JINGDONGKUAIDI("65","�������"),
	YOUJUEMS("67","�ʾ�EMS"), 
	SHENTONGKUAIDI("68","��ͨ���"),
	SHUNFENGKUAIDI("69","˳����"),
	KUAIDIYUNSHU("70","�������"),
	YUANTONGKUAIDI("71","Բͨ���"),
	DHL("1001","DHL"), 
	UPS("1002","UPS"), 
	EMS("1022","EMS"), 
	ZHONGYOUGUAHAOXIAOBAO("1023","���ʹҺ�С��");

	
	private String code;
	private String name;
	
	private OrderShipment(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
