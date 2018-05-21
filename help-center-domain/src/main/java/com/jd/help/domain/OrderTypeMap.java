package com.jd.help.domain;

import java.util.HashMap;
import java.util.Map;

public class OrderTypeMap {
	
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	private void init() {
		map.put(0, "һ�㶩��");
		map.put(1, "���ֶ���");
		map.put(2, "��������");
		map.put(3, "��������");
		map.put(4, "������Ʒ");
		map.put(5, "��������");
		map.put(6, "���ڸ����");
		map.put(7, "�ڲ�����(�����˻�)");
		map.put(8, "�����Ʒ");
		map.put(9, "������-����");
		map.put(10, "������-����");
		map.put(11, "�ۺ����");
		map.put(12, "��ҵ�����");
		map.put(13, "����������");
		map.put(14, "���Ķ���");
		map.put(15, "���޷���");
		map.put(16, "ֱ���⳥");
		map.put(17, "�������߷���");
		map.put(18, "����ֱ��");
		map.put(19, "�ͷ�����");
		map.put(20, "�Ծɻ���");
		map.put(21, "PopFbp");
		map.put(22, "PopSop");
		map.put(23, "PopLbp");
		map.put(24, "Pop LBV");
		map.put(25, "POP SOPL");
		map.put(28, "�Ź�(����)");
		map.put(30, "�ֻ���ֵ");
		map.put(31, "У԰����");
		map.put(32, "IPhone��Լ");
		map.put(33, "������Ʒ��");
		map.put(34, "��Ϸ�㿨");
		map.put(35, "��Ʊ");
		map.put(36, "��Ʊ");
		map.put(37, "�ֻ���ֵ");
		map.put(38 , "�����鶩��");
		map.put(39, "�Ƶ궩��");
		map.put(40, "��������");
		map.put(41, "���ⶩ��");
		map.put(42, "ͨ�ú�Լ�ƻ�");
		map.put(43, "��ӰƱ");
		map.put(44, "������Ʊ");
		map.put(45, "�⳵");
		map.put(46, "��Ʊ");
		map.put(47, "����");
		map.put(48, "����");
		map.put(49,"ʵ����Ʒ��");
		map.put(51,"��ȡ���Ѷ���");
		map.put(52,"��������");
		map.put(53,"Ʊ�񶩵�");
		map.put(54,"������Ʒ������");
		map.put(55,"��������");
		map.put(56,"���ܲ�������");
		map.put(57,"Ӧ���̵궩��");
		map.put(58,"��������");
		map.put(60,"������ͨ��Լ����");
		map.put(61,"EPT����");
		map.put(62,"��ҳ��Ϸ");
		map.put(63,"POP����");
		map.put(64,"�ǳ��ձ��ն���");
		map.put(65,"���ն���");
		map.put(66,"��������IAP����");
		map.put(67,"������IAP����");
		map.put(68,"POP������֤�𶩵�");
		map.put(69,"���������Ʒ����");
		map.put(70,"������񶩵�");
		map.put(71,"��ѵ���񶩵�");
		map.put(72,"ģ����񶩵�");
		map.put(73,"�������");
		map.put(74,"����ɷѶ���");
		map.put(75,"LOC");
		map.put(76,"�Ʋ�Ʒ����");
		map.put(77,"LSP");
		map.put(78,"�����ƶ���");
		map.put(79,"������ƽ̨����");
		map.put(80,"�����г�����");
		map.put(81,"�����ƴ���");
		map.put(82,"����Ʊ����");
		map.put(83,"���ʻ�Ʊ����");
		map.put(84, "���ĶԽӿ��֧��ʵ�ﶩ��");
		map.put(85, "���ĶԽӿ��֧�����ⶩ��");
		map.put(86, "��Լ�����ⶩ��");
		map.put(87, "�ֻ�������ֵ");
		map.put(88, "B2B����");
		map.put(101, "ǧѰ����");
		map.put(201, "�Ƶ��Ź�");
		map.put(127, "�ݳ�Ʒ");
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
