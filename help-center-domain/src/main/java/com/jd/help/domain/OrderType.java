package com.jd.help.domain;

public enum OrderType {
	YIBANDINGDAN("0", "一般订单"),
	PAIMAIDINGDAN("2","拍卖订单"),
	FUWUCHANPIN("8","服务产品"),
	SHOUHOUDIAOHUO("11","售后调货"),
	FANXIUFAHUO("15","返修发货"),
	ZHIJIEPEICHANG("16","直接赔偿"),
	KEFUBUJIAN("19","客服补件"),
	POPFBP("21","PopFbp"),
	POPSOP("22","PopSop"),
	POPLBP("23","PopLbp"),
	POPSOPL("25","POPSOPL"),
	TUANGOUXUNI("28","团购(虚拟)"),
	DIANZILIPINKA("33","电子礼品卡"),
	YOUXIDIANKA("34","游戏点卡"),
	JIPIAO("35","机票"),
	CAIPIAO("36","彩票"),
	SHOUJICHONGZHI("37","手机充值(新)"),
	DIANZISHUDINGDAN("38","电子书订单"),
	JIUDIANDINGDAN("39","酒店订单"),
	TONGYONGHEYUEJIHUA("42","通用合约计划"),
	DIANYINGPIAO("43","电影票"),
	JINGDIANMENPIAO("44","景点门票"),
	ZUCHE("45","租车"),
	LVYOU("47","旅游"),
	SHIWULIPINKA("49","实物礼品卡"),
	WUGOUQUJIANFEIDINGDAN("51","误购取件费订单"),
	PIAOYUDINGDAN("53","票务订单"),
	XIANXIALIPINKADINGDAN("54","线下礼品卡订单"),
	WANGYEYOUXI("62","网页游戏"),
	FEICHEXIANBAOXIANDINGDAN("64","非车险保险订单"),
	CHEXIANDINGDAN("65","车险订单"),
	JINGDONGFUWUCHANPINDINGDAN("69","京东服务产品订单"),
	LOC("75","LOC"),
	LSP("77","LSP"),
	GUOJIJIPIAODINGDAN("83","国际机票订单");
	
	private String code;
	private String name;
	
	private OrderType(String code,String name){
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
