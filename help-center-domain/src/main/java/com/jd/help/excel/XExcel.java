package com.jd.help.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.util.Iterator;
import java.util.List;

/**
 * excel工具类(对象化工具类)
 * @author modi
 *
 */
public interface XExcel<T>{
    /**
     * 从文件流中读取excel函数
     * @param is
     * @return
     */
    public XExcel<T> read(InputStream is);
    /**
     * 将文件写入到指定流文件当中
     * @param os
     * @return
     */
    public XExcel<T> write(OutputStream os);

    /**
     * 添加一行数据
     * @param t
     * @return
     */
    public XExcel<T> add(T t);
    /**
     * 批量添加数据
     * @param rows
     * @return
     */
    public XExcel<T> add(List<T> rows);

    /**
     * 修改指定行数据，指定行号超过界线抛出异常
     * @param rowIndex 从0开始
     * @param t
     * @throws IndexOutOfBoundsException
     */
    public XExcel<T> update(int rowIndex, T t) throws IndexOutOfBoundsException;
    /**
     * 批量修改数据，从指定行号开始，后续迭代修改
     * @param startRowIndex
     * @param rows
     * @throws IndexOutOfBoundsException 其实行号超过现有界线时抛出异常
     */
    public XExcel<T> updates(int startRowIndex, List<T> rows) throws IndexOutOfBoundsException;

    /**
     * 获取对象集合
     * @return
     */
    public List<T> getList();
    /**
     * 分页获取对象集合
     * @param offset
     * @param size
     * @return
     * @throws IndexOutOfBoundsException
     */
    public List<T> getList(int offset, int size) throws IndexOutOfBoundsException;
    /**
     * 获取指定行
     * @param rowIndex
     * @return
     * @throws IndexOutOfBoundsException
     */
    public T getRow(int rowIndex) throws IndexOutOfBoundsException;

    /**
     * 获取总行数
     * @return
     */
    public int size();

    /**
     * 删除指定行数据
     * @param rowIndex
     * @return
     * @throws IndexOutOfBoundsException
     */
    public XExcel<T> removeRow(int rowIndex) throws IndexOutOfBoundsException;
    /**
     * 批量删除指定行数据
     * @param startRowIndex
     * @param endRowIndex
     * @return
     * @throws IndexOutOfBoundsException 只在startRowIndex超出时抛出异常
     */
    public XExcel<T> removeRows(int startRowIndex, int endRowIndex) throws IndexOutOfBoundsException;

    /**
     * 返回可遍历Iterable<T>
     * @return
     */
    public Iterator<T> iterable();

    /**
     * 自定义表头
     * @param headerNames
     */
    public void createHeard(List<String> headerNames);


    ////////////////////
    /**
     * 功能说明: 用来在对象的属性上加入的annotation，通过该annotation说明某个属性所对应的标题<br/>
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface XExcelField {
        /**
         * 属性的标题名称(默认值""，此时取属性名为列明)
         * @return
         */
        String value() default "";

        /**
         * 是否映射，默认true，映射
         * @return
         */
        boolean mapping() default true;

        /**
         * 在excel的顺序(默认-1，此时按字段先后顺序自动排序)
         * @return
         */
        int order() default -1;

        /**
         * 字段对应excel 类型
         * <p>具体excel类型值参见 poi Cell对象常量，如CELL_TYPE_NUMERIC等
         * <p>默认值-1，此时采用内置对应模板
         * <p>2017-8-21扩展 100对应日期格式
         * @return
         */
        int excelType() default -1;
    }

    /**
     * 功能说明: 添加在映射对象上的标签<br/>
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface XExcelBean {
        /**
         * 属性的标题名称(默认值""，默认去对象simpleName)
         * @return
         */
        String value() default "";

//	    /**
//	     * 是否映射，默认true，映射
//	     * @return
//	     */
//	    boolean mapping() default true;
        /**
         * 未XExcelField标注的标签是否映射
         * @return 默认true，映射
         */
        boolean defaultFieldMapping() default true;

        /**
         * 是否映射父类字段
         * @return
         */
        boolean mappingSuper() default true;
    }

    /**
     * 时间类型字段
     */
    public final static int CELL_TYPE_DATE = 100;
	
}
