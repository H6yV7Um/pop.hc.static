package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderStateMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(0, "�¶���");
		map.put(1, "�ȴ�����");
		map.put(2, "�ȴ�����ȷ��");
		map.put(3, "�ӳٸ���ȷ��");
		map.put(4, "��ͣ");
		map.put(5, "�곤�������");
		map.put(6, "�ȴ���ӡ");
		map.put(7, "�ȴ�����");
		map.put(8, "�ȴ����");
		map.put(9, "�ȴ�����");
		map.put(10, "����;��");
		map.put(11, "�������");
		map.put(12, "�����˻�");
		map.put(13, "ȷ������");
		map.put(14, "�ȴ���ִ");
		map.put(15, "�ȴ�ȷ���ջ�");
		map.put(16, "�����˻�");
		map.put(17, "��������ȷ��");
		map.put(18, "���");
		map.put(19, "�ȴ����ڸ���");
		map.put(20, "�տ�ȷ��");
		map.put(21, "����");
		map.put(22, "�ȴ��˿�");
		map.put(23, "�ȴ��ͻ��ظ�");
		map.put(24, "����ȷ�����");
		map.put(25, "�ȴ������");
		map.put(26, "������");
		map.put(27, "�˿���");
		map.put(28, "�ȴ���������");
		map.put(29, "�ȴ���������");
		map.put(30, "�ȴ������������");
		map.put(31, "��ȡ��");
		map.put(32, "�����");
		map.put(1000, "δ֪״̬");
	}
	
	private OrderStateMap(){
		init();
	}
	
	private static OrderStateMap singletonMap = new OrderStateMap();
	
	public static OrderStateMap getInstance(){
		return singletonMap;
	}
	
	public String getName(int code){
		return map.get(code);
	}
}
