package com.jd.help.domain;

public enum OrderPay {
	HUODAOFUKUAI("1","货到付款 "),
	YOUJUHUIKUAN("2","邮局汇款 "),
	ZITI("3","自提"),
	ZAIXIANZHIFU("4","在线支付 "),
	GONGSIZHUANZHANG("5","公司转帐 "),
	FENQIFUKUAN("8","分期付款 "),
	YUEJIE("12","月结"),
	PAYPAL("14","PayPal贝宝"),
	XIAOYUANZITI("16","校园自提"),
	HAOLINJUZITI("17","好邻居自提"),
	SHEQUZITI("18","社区自提"),
	ZITIGUI("19","自提柜"),
	HUNHEZHIFU("99","混合支付 "),
	YINLIANSHOUJIZHIFU("165","银联手机支付");
	//XUNI_ID("200","虚拟ID,供promise接口使用");
	
	private String code;
	private String name;
	
	private OrderPay(String code,String name){
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
