package com.jd.help.domain;

public enum OrderStatus {
	
    XIN_DING_DAN("0","新订单"),
    DENGDAI_FUKUAN("1","等待付款"),
    DENGDAI_FUKUAN_QUEREN("2","等待付款确认"),
    YANCHI_FUKUAN_QUEREN("3","延迟付款确认"),
    ZANTING("4","暂停"),
    DIANZHANG_ZUIZHONG_SHENHE("5","店长最终审核"),
    DENGDAI_DAYIN("6","等待打印"),
    DENGDAI_CHUKU("7","等待出库"),
    DENGDAI_DABAO("8","等待打包"),
    DENGDAI_FAHUO("9","等待发货"),
    ZITI_TUZHONG("10","自提途中"),
    SHANGMEN_TIHUO("11","上门提货"),
    ZITI_TUIHUO("12","自提退货"),
    QUEREN_ZITI("13","确认自提"),
    DENGDAI_HUIZHI("14","等待回执"),
    DENGDAI_QUEREN_SHOUHUO("15","等待确认收货"),
    PEISONG_TUIHUO("16","配送退货"),
    HUODAOFUKUAN_QUEREN("17","货到付款确认"),
    WANCHENG("18","完成"),
    DENGDAI_FENQI_FUKUAN("19","等待分期付款"),
    SERVICE_FINISHED("20","收款确认"),
    SUODING("21","锁定"),
    DENGDAI_TUIKUAN("22","等待退款"),
    DENGDAI_KEHU_HUIFU("23","等待客户回复"),
    CHANGSHANG_WANCHENG("24","厂商确认完成"),
    DENGDAI_ZAISHENHE("25","等待再审核"),
    DUIZHANGZHONG("26","对账中"),
    TUIKUANZHONG("27","退款中"),
    DENGDAI_SANFANG_CHUKU("28","等待三方出库"),
    DENGDAI_SANFANG_FAHUO("29","等待三方发货"),
    DENGDAI_SANFANGFAHUO_WANCHENG("30","等待三方发货完成"),
    YIQUXIAO("31","已取消"),
    CHAIFENZHONG("32","拆分中"),
    UNKNOW("1000","未知状态");
	
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
