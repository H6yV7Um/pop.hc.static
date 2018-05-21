package com.jd.help.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.util.Iterator;
import java.util.List;

/**
 * excel������(���󻯹�����)
 * @author modi
 *
 */
public interface XExcel<T>{
    /**
     * ���ļ����ж�ȡexcel����
     * @param is
     * @return
     */
    public XExcel<T> read(InputStream is);
    /**
     * ���ļ�д�뵽ָ�����ļ�����
     * @param os
     * @return
     */
    public XExcel<T> write(OutputStream os);

    /**
     * ���һ������
     * @param t
     * @return
     */
    public XExcel<T> add(T t);
    /**
     * �����������
     * @param rows
     * @return
     */
    public XExcel<T> add(List<T> rows);

    /**
     * �޸�ָ�������ݣ�ָ���кų��������׳��쳣
     * @param rowIndex ��0��ʼ
     * @param t
     * @throws IndexOutOfBoundsException
     */
    public XExcel<T> update(int rowIndex, T t) throws IndexOutOfBoundsException;
    /**
     * �����޸����ݣ���ָ���кſ�ʼ�����������޸�
     * @param startRowIndex
     * @param rows
     * @throws IndexOutOfBoundsException ��ʵ�кų������н���ʱ�׳��쳣
     */
    public XExcel<T> updates(int startRowIndex, List<T> rows) throws IndexOutOfBoundsException;

    /**
     * ��ȡ���󼯺�
     * @return
     */
    public List<T> getList();
    /**
     * ��ҳ��ȡ���󼯺�
     * @param offset
     * @param size
     * @return
     * @throws IndexOutOfBoundsException
     */
    public List<T> getList(int offset, int size) throws IndexOutOfBoundsException;
    /**
     * ��ȡָ����
     * @param rowIndex
     * @return
     * @throws IndexOutOfBoundsException
     */
    public T getRow(int rowIndex) throws IndexOutOfBoundsException;

    /**
     * ��ȡ������
     * @return
     */
    public int size();

    /**
     * ɾ��ָ��������
     * @param rowIndex
     * @return
     * @throws IndexOutOfBoundsException
     */
    public XExcel<T> removeRow(int rowIndex) throws IndexOutOfBoundsException;
    /**
     * ����ɾ��ָ��������
     * @param startRowIndex
     * @param endRowIndex
     * @return
     * @throws IndexOutOfBoundsException ֻ��startRowIndex����ʱ�׳��쳣
     */
    public XExcel<T> removeRows(int startRowIndex, int endRowIndex) throws IndexOutOfBoundsException;

    /**
     * ���ؿɱ���Iterable<T>
     * @return
     */
    public Iterator<T> iterable();

    /**
     * �Զ����ͷ
     * @param headerNames
     */
    public void createHeard(List<String> headerNames);


    ////////////////////
    /**
     * ����˵��: �����ڶ���������ϼ����annotation��ͨ����annotation˵��ĳ����������Ӧ�ı���<br/>
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface XExcelField {
        /**
         * ���Եı�������(Ĭ��ֵ""����ʱȡ������Ϊ����)
         * @return
         */
        String value() default "";

        /**
         * �Ƿ�ӳ�䣬Ĭ��true��ӳ��
         * @return
         */
        boolean mapping() default true;

        /**
         * ��excel��˳��(Ĭ��-1����ʱ���ֶ��Ⱥ�˳���Զ�����)
         * @return
         */
        int order() default -1;

        /**
         * �ֶζ�Ӧexcel ����
         * <p>����excel����ֵ�μ� poi Cell����������CELL_TYPE_NUMERIC��
         * <p>Ĭ��ֵ-1����ʱ�������ö�Ӧģ��
         * <p>2017-8-21��չ 100��Ӧ���ڸ�ʽ
         * @return
         */
        int excelType() default -1;
    }

    /**
     * ����˵��: �����ӳ������ϵı�ǩ<br/>
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface XExcelBean {
        /**
         * ���Եı�������(Ĭ��ֵ""��Ĭ��ȥ����simpleName)
         * @return
         */
        String value() default "";

//	    /**
//	     * �Ƿ�ӳ�䣬Ĭ��true��ӳ��
//	     * @return
//	     */
//	    boolean mapping() default true;
        /**
         * δXExcelField��ע�ı�ǩ�Ƿ�ӳ��
         * @return Ĭ��true��ӳ��
         */
        boolean defaultFieldMapping() default true;

        /**
         * �Ƿ�ӳ�丸���ֶ�
         * @return
         */
        boolean mappingSuper() default true;
    }

    /**
     * ʱ�������ֶ�
     */
    public final static int CELL_TYPE_DATE = 100;
	
}
