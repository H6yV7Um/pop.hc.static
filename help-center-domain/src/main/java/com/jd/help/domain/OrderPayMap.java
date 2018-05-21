package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderPayMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(1, "��������");
		map.put(2, "�ʾֻ��");
		map.put(3, "����");
		map.put(4, "����֧��");
		map.put(5, "��˾ת��");
		map.put(6, "���п�ת��");
		map.put(7, "����-����");
		map.put(8, "���ڸ���");
		map.put(10, "��У����-�Լ�֧��");
		map.put(11, "��У����-����渶");
		map.put(12, "�½�");
		map.put(13, "��У������������");
		map.put(14, "PayPal");
		map.put(15, "��������");
		map.put(16, "У԰����");
		map.put(17, "���ھ�����");
		map.put(18, "��������");
		map.put(19, "�����");
		map.put(99, "���֧��");
		map.put(165, "�����ֻ�֧��");
	}
	
	private OrderPayMap(){
		init();
	}
	
	private static OrderPayMap singletonMap = new OrderPayMap();
	
	public static OrderPayMap getInstance(){
		return singletonMap;
	}
	
	public String getName(int code){
		return map.get(code);
	}
}
