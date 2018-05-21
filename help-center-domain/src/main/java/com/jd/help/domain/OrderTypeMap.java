package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderTypeMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(0, "一般订单");
		map.put(1, "积分订单");
		map.put(2, "拍卖订单");
		map.put(3, "代购订单");
		map.put(4, "虚拟商品");
		map.put(5, "抢购订单");
		map.put(6, "分期付款订单");
		map.put(7, "内部订单(采销退货)");
		map.put(8, "服务产品");
		map.put(9, "备件库-行政");
		map.put(10, "备件库-销售");
		map.put(11, "售后调货");
		map.put(12, "大家电下乡");
		map.put(13, "企销部订单");
		map.put(14, "拍拍订单");
		map.put(15, "返修发货");
		map.put(16, "直接赔偿");
		map.put(17, "招行在线分期");
		map.put(18, "厂商直送");
		map.put(19, "客服补件");
		map.put(20, "以旧换新");
		map.put(21, "PopFbp");
		map.put(22, "PopSop");
		map.put(23, "PopLbp");
		map.put(24, "Pop LBV");
		map.put(25, "POP SOPL");
		map.put(28, "团购(虚拟)");
		map.put(30, "手机充值");
		map.put(31, "校园订单");
		map.put(32, "IPhone合约");
		map.put(33, "电子礼品卡");
		map.put(34, "游戏点卡");
		map.put(35, "机票");
		map.put(36, "彩票");
		map.put(37, "手机充值");
		map.put(38 , "电子书订单");
		map.put(39, "酒店订单");
		map.put(40, "暂无数据");
		map.put(41, "海外订单");
		map.put(42, "通用合约计划");
		map.put(43, "电影票");
		map.put(44, "景点门票");
		map.put(45, "租车");
		map.put(46, "火车票");
		map.put(47, "旅游");
		map.put(48, "保险");
		map.put(49,"实物礼品卡");
		map.put(51,"误购取件费订单");
		map.put(52,"捐赠订单");
		map.put(53,"票务订单");
		map.put(54,"线下礼品卡订单");
		map.put(55,"域名订单");
		map.put(56,"节能补贴订单");
		map.put(57,"应用商店订单");
		map.put(58,"数字音乐");
		map.put(60,"广州联通合约订单");
		map.put(61,"EPT订单");
		map.put(62,"网页游戏");
		map.put(63,"POP拍卖");
		map.put(64,"非车险保险订单");
		map.put(65,"车险订单");
		map.put(66,"数字音乐IAP订单");
		map.put(67,"电子书IAP订单");
		map.put(68,"POP拍卖保证金订单");
		map.put(69,"京东服务产品订单");
		map.put(70,"软件服务订单");
		map.put(71,"培训服务订单");
		map.put(72,"模板服务订单");
		map.put(73,"需求外包");
		map.put(74,"生活缴费订单");
		map.put(75,"LOC");
		map.put(76,"云产品订单");
		map.put(77,"LSP");
		map.put(78,"电商云订单");
		map.put(79,"电商云平台订单");
		map.put(80,"服务市场代销");
		map.put(81,"电商云代销");
		map.put(82,"汽车票订单");
		map.put(83,"国际机票订单");
		map.put(84, "拍拍对接快捷支付实物订单");
		map.put(85, "拍拍对接快捷支付虚拟订单");
		map.put(86, "合约机虚拟订单");
		map.put(87, "手机流量充值");
		map.put(88, "B2B订单");
		map.put(101, "千寻订单");
		map.put(201, "酒店团购");
		map.put(127, "奢侈品");
	}
	
	private OrderTypeMap(){
		init();
	}
	
	private static OrderTypeMap singletonMap = new OrderTypeMap();
	
	public static OrderTypeMap getInstance(){
		return singletonMap;
	}
	
	public String getName(int code){
		return map.get(code);
	}
}
