package com.jd.help.domain;

public enum OrderType {
	YIBANDINGDAN("0", "һ�㶩��"),
	PAIMAIDINGDAN("2","��������"),
	FUWUCHANPIN("8","�����Ʒ"),
	SHOUHOUDIAOHUO("11","�ۺ����"),
	FANXIUFAHUO("15","���޷���"),
	ZHIJIEPEICHANG("16","ֱ���⳥"),
	KEFUBUJIAN("19","�ͷ�����"),
	POPFBP("21","PopFbp"),
	POPSOP("22","PopSop"),
	POPLBP("23","PopLbp"),
	POPSOPL("25","POPSOPL"),
	TUANGOUXUNI("28","�Ź�(����)"),
	DIANZILIPINKA("33","������Ʒ��"),
	YOUXIDIANKA("34","��Ϸ�㿨"),
	JIPIAO("35","��Ʊ"),
	CAIPIAO("36","��Ʊ"),
	SHOUJICHONGZHI("37","�ֻ���ֵ(��)"),
	DIANZISHUDINGDAN("38","�����鶩��"),
	JIUDIANDINGDAN("39","�Ƶ궩��"),
	TONGYONGHEYUEJIHUA("42","ͨ�ú�Լ�ƻ�"),
	DIANYINGPIAO("43","��ӰƱ"),
	JINGDIANMENPIAO("44","������Ʊ"),
	ZUCHE("45","�⳵"),
	LVYOU("47","����"),
	SHIWULIPINKA("49","ʵ����Ʒ��"),
	WUGOUQUJIANFEIDINGDAN("51","��ȡ���Ѷ���"),
	PIAOYUDINGDAN("53","Ʊ�񶩵�"),
	XIANXIALIPINKADINGDAN("54","������Ʒ������"),
	WANGYEYOUXI("62","��ҳ��Ϸ"),
	FEICHEXIANBAOXIANDINGDAN("64","�ǳ��ձ��ն���"),
	CHEXIANDINGDAN("65","���ն���"),
	JINGDONGFUWUCHANPINDINGDAN("69","���������Ʒ����"),
	LOC("75","LOC"),
	LSP("77","LSP"),
	GUOJIJIPIAODINGDAN("83","���ʻ�Ʊ����");
	
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
