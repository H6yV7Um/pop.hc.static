package com.jd.help.domain;

public enum OrderStatus {
	
    XIN_DING_DAN("0","�¶���"),
    DENGDAI_FUKUAN("1","�ȴ�����"),
    DENGDAI_FUKUAN_QUEREN("2","�ȴ�����ȷ��"),
    YANCHI_FUKUAN_QUEREN("3","�ӳٸ���ȷ��"),
    ZANTING("4","��ͣ"),
    DIANZHANG_ZUIZHONG_SHENHE("5","�곤�������"),
    DENGDAI_DAYIN("6","�ȴ���ӡ"),
    DENGDAI_CHUKU("7","�ȴ�����"),
    DENGDAI_DABAO("8","�ȴ����"),
    DENGDAI_FAHUO("9","�ȴ�����"),
    ZITI_TUZHONG("10","����;��"),
    SHANGMEN_TIHUO("11","�������"),
    ZITI_TUIHUO("12","�����˻�"),
    QUEREN_ZITI("13","ȷ������"),
    DENGDAI_HUIZHI("14","�ȴ���ִ"),
    DENGDAI_QUEREN_SHOUHUO("15","�ȴ�ȷ���ջ�"),
    PEISONG_TUIHUO("16","�����˻�"),
    HUODAOFUKUAN_QUEREN("17","��������ȷ��"),
    WANCHENG("18","���"),
    DENGDAI_FENQI_FUKUAN("19","�ȴ����ڸ���"),
    SERVICE_FINISHED("20","�տ�ȷ��"),
    SUODING("21","����"),
    DENGDAI_TUIKUAN("22","�ȴ��˿�"),
    DENGDAI_KEHU_HUIFU("23","�ȴ��ͻ��ظ�"),
    CHANGSHANG_WANCHENG("24","����ȷ�����"),
    DENGDAI_ZAISHENHE("25","�ȴ������"),
    DUIZHANGZHONG("26","������"),
    TUIKUANZHONG("27","�˿���"),
    DENGDAI_SANFANG_CHUKU("28","�ȴ���������"),
    DENGDAI_SANFANG_FAHUO("29","�ȴ���������"),
    DENGDAI_SANFANGFAHUO_WANCHENG("30","�ȴ������������"),
    YIQUXIAO("31","��ȡ��"),
    CHAIFENZHONG("32","�����"),
    UNKNOW("1000","δ֪״̬");
	
	private String code;
	private String name;
	
	private OrderStatus(String code,String name){
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
