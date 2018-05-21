package com.jd.help.domain;

public enum OrderPay {
	HUODAOFUKUAI("1","�������� "),
	YOUJUHUIKUAN("2","�ʾֻ�� "),
	ZITI("3","����"),
	ZAIXIANZHIFU("4","����֧�� "),
	GONGSIZHUANZHANG("5","��˾ת�� "),
	FENQIFUKUAN("8","���ڸ��� "),
	YUEJIE("12","�½�"),
	PAYPAL("14","PayPal����"),
	XIAOYUANZITI("16","У԰����"),
	HAOLINJUZITI("17","���ھ�����"),
	SHEQUZITI("18","��������"),
	ZITIGUI("19","�����"),
	HUNHEZHIFU("99","���֧�� "),
	YINLIANSHOUJIZHIFU("165","�����ֻ�֧��");
	//XUNI_ID("200","����ID,��promise�ӿ�ʹ��");
	
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
