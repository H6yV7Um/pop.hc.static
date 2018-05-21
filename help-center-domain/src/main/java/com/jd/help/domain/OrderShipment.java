package com.jd.help.domain;

public enum OrderShipment {
	YOUZHENGBAOGUO("47","邮政包裹"),
	ZHONGTIEKUAIYUN("49","中铁快运"),
	YOUZHENGKUAIBAO("51","邮政快包"),
	GONGLUYUNSHU("52","公路运输"),
	ZITI("64","自提"), 
	JINGDONGKUAIDI("65","京东快递"),
	YOUJUEMS("67","邮局EMS"), 
	SHENTONGKUAIDI("68","申通快递"),
	SHUNFENGKUAIDI("69","顺丰快递"),
	KUAIDIYUNSHU("70","快递运输"),
	YUANTONGKUAIDI("71","圆通快递"),
	DHL("1001","DHL"), 
	UPS("1002","UPS"), 
	EMS("1022","EMS"), 
	ZHONGYOUGUAHAOXIAOBAO("1023","中邮挂号小包");

	
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
