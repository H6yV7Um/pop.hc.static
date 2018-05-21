package com.jd.help.excel;

import com.jd.pop.module.utils.BeanUtils;
import com.jd.pop.module.utils.TypeArgFinder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全
 * <p>不允许空行
 * <p>excel逻辑第0行，显示的第1行，为表头信息
 * @author modi
 *
 */
@SuppressWarnings("deprecation")
class XExcelImpl<T> implements XExcel<T>{
    Logger log = LoggerFactory.getLogger(getClass());
    private Workbook workbook = new SXSSFWorkbook(); //new HSSFWorkbook();//new XSSFWorkbook();
    private Sheet sheet;//当前活跃sheet
    private final AtomicInteger rowIndex = new AtomicInteger();
    private List<ColumnParam> columns;
    private Class<T> tClass;


//	public XExcelImpl(){
////		sheet = workbook.createSheet();
//	}

    public XExcelImpl(Class<T> c) {
        tClass = c;
        initColumns();
    }

    @Override
    public XExcel<T> read(InputStream is) {
        try {
            workbook = WorkbookFactory.create(is);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            log.error("read input stream error.", e);
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public XExcel<T> write(OutputStream os) {
        try {
            workbook.write(os);
        } catch (IOException e) {
            log.error("read input stream error.", e);
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public XExcel<T> add(T t) {
        if(null == t) return this;

        Sheet sheet = getSheet();
        Row row;
        int nextRowIndex;
        synchronized (sheet) {
            nextRowIndex = nextRowIndex();
            row = sheet.createRow(nextRowIndex);
            writeRow(nextRowIndex, row, t);
        }
        return this;
    }

    private void writeRow(int nextRowIndex, Row row, T t) {
        Cell cell;
        ColumnParam cp;
        for(int i=0; i<columns.size(); i++){
            cp = columns.get(i);
            Object o = cp.invoke(t);
            cell = row.createCell(i, cp.getExcelType());
            setValue(nextRowIndex, i, cell, o, cp);
        }
    }

    private void setValue(int rowIndex, int columnIndex, Cell cell, Object o, ColumnParam cp) {
        if(!cp.writeExcel(cell, o)) {
            log.error(String.format("不支持的转换类型(row,column)(%s,%s):[%s]", rowIndex, columnIndex, o));
        }
    }


    private Sheet getSheet() {
        if(null == sheet){
            synchronized (this) {
                if(null == sheet){
                    XExcelBean ann = getTClass().getAnnotation(XExcelBean.class);
                    if(null == ann || "".equals(ann.value())){
                        sheet = workbook.createSheet(getTClass().getSimpleName());
                    }else{
                        sheet = workbook.createSheet(ann.value());
                    }
                    createHeard(sheet);
                    rowIndex.set(1);
                }
            }
        }
        return sheet;
    }
    
    public synchronized void createHeard(List<String> heardNames){
        XExcelBean ann = getTClass().getAnnotation(XExcelBean.class);
        if(null == ann || "".equals(ann.value())){
            sheet = workbook.createSheet(getTClass().getSimpleName());
        }else{
            sheet = workbook.createSheet(ann.value());
        }
        Row row = sheet.createRow(0);
        Cell cell;
        for(int i=0; i<heardNames.size(); i++){
            cell = row.createCell(i, Cell.CELL_TYPE_STRING);
            cell.setCellValue(heardNames.get(i));
        }
        rowIndex.set(1);

    }

    private int nextRowIndex(){
        return rowIndex.getAndIncrement();
    }

    private void createHeard(Sheet sheet) {
        //write
        Row row = sheet.createRow(0);
        Cell cell;
        for(int i=0; i<columns.size(); i++){
            cell = row.createCell(i, Cell.CELL_TYPE_STRING);
            cell.setCellValue(columns.get(i).getHeardStr());
        }
    }

    private void initColumns() {
        Class<T> tClass = getTClass();
        XExcelBean beanAnn = tClass.getAnnotation(XExcelBean.class);

        List<Field> allField;
        if(null == beanAnn || beanAnn.mappingSuper()){//同时映射本类和所有父类
            allField = BeanUtils.getAllField(tClass);
        }else{//只映射本类
            allField = Arrays.asList(tClass.getDeclaredFields());
        }

        boolean defaultFieldMapping = null==beanAnn?true:beanAnn.defaultFieldMapping();
        columns = new ArrayList<ColumnParam>();
        XExcelField ann;
        for(int i=0; i<allField.size(); i++){
            ann = allField.get(i).getAnnotation(XExcelField.class);
            if((null == ann && defaultFieldMapping && !Modifier.isStatic(allField.get(i).getModifiers())) || (null != ann && ann.mapping())){
                columns.add(new ColumnParam(workbook, getDateStyle(), allField.get(i), ann, i));
            }
        }
        Collections.sort(columns, new Comparator<ColumnParam>() {
            @Override
            public int compare(ColumnParam o1, ColumnParam o2) {
                return getOrder(o1) - getOrder(o2);
            }
        });
    }

    private CellStyle dateStyle;
    public CellStyle getDateStyle() {
        if(null == dateStyle){
            synchronized (this) {
                if(null == dateStyle){
                    dateStyle = workbook.createCellStyle();//cell.getCellStyle();
                    DataFormat format = workbook.createDataFormat();
                    dateStyle.setDataFormat(format.getFormat("yyyy年m月d日 HH:MM:SS"));
                }
            }
        }
        return dateStyle;
    }

    private int getOrder(ColumnParam cp){
        if(null != cp.getExcelAnn()){
            return -1 == cp.getExcelAnn().order()?cp.getFieldIndex():cp.getExcelAnn().order();
        }

        return cp.getFieldIndex();
    }

    @Override
    public XExcel<T> add(List<T> rows) {
        if(null == rows) return this;

        for(T t : rows){
            add(t);
        }
        return this;
    }

    @Override
    public XExcel<T> update(int rowIndex, T t) throws IndexOutOfBoundsException {
        Sheet sheet = getSheet();
        int rowIdx = rowIndex+1;
        Row row = sheet.getRow(rowIdx);
        if(null == row) throw new IndexOutOfBoundsException("row index error:"+rowIndex);
        writeRow(rowIdx, row, t);
        return this;
    }

    @Override
    public XExcel<T> updates(int startRowIndex, List<T> rows) throws IndexOutOfBoundsException {
        Sheet sheet = getSheet();
        int rowIdx = startRowIndex+1;
        Row row = sheet.getRow(rowIdx);
        if(null == row) throw new IndexOutOfBoundsException("row index error:"+rowIndex);

        writeRow(rowIdx, row, rows.get(0));
        for(int i=2; i<=rows.size(); i++){
            rowIdx = startRowIndex + i;
            row = sheet.getRow(startRowIndex + i);
            if(null == row){
                synchronized (sheet) {
                    row = sheet.getRow(startRowIndex + i);
                    if(null == row){
                        row = sheet.createRow(startRowIndex + i);
                    }
                }
            }
            writeRow(rowIdx, row, rows.get(i));
        }
        return this;
    }

    @Override
    public List<T> getList() {
        List<T> result = new ArrayList<T>();

        Sheet sheet = getSheet();
        Iterator<Row> iterator = sheet.iterator();

        if(iterator.hasNext()) iterator.next();//去掉头信息行
        Row row;
        while(iterator.hasNext()){
            row = iterator.next();
            try {
                result.add(readRow(row));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private T readRow(Row row) {
        T t;
        try {
            t = getTClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("请提供无参构造函数", e);
        }

        ColumnParam cp;
        for(int i=0; i<columns.size(); i++){
            cp = columns.get(i);
            cp.write(row.getCell(i), t);
        }
        return t;
    }


    @Override
    public List<T> getList(int offset, int size) throws IndexOutOfBoundsException {
        List<T> result = new ArrayList<T>();
        Sheet sheet = getSheet();
        Row row;
        for(int i=1; i<=size; i++){
            row = sheet.getRow(offset+i);
            if(null == row) break;
            result.add(readRow(row));
        }
        return result;
    }

    @Override
    public T getRow(int rowIndex) throws IndexOutOfBoundsException {
        Sheet sheet = getSheet();
        Row row = sheet.getRow(rowIndex+1);
        if(null == row) throw new IndexOutOfBoundsException("row index error:"+rowIndex);
        return readRow(row);
    }

    @Override
    public int size() {
        return getSheet().getLastRowNum();
    }

    @Override
    public XExcel<T> removeRow(int rowIndex) throws IndexOutOfBoundsException {
        getSheet().removeRowBreak(rowIndex+1);
        return this;
    }

    @Override
    public XExcel<T> removeRows(int startRowIndex, int endRowIndex) throws IndexOutOfBoundsException {
        Sheet sheet = getSheet();
        for(int i = startRowIndex+1; i<=endRowIndex; i++){
            sheet.removeRowBreak(i);
        }
        return this;
    }

    @Override
    public Iterator<T> iterable() {
        final Iterator<Row> iterator = getSheet().iterator();
        if(iterator.hasNext()) iterator.next();//去掉头信息部分

        return new Iterator<T>() {
            @Override
            public void remove() {
                iterator.remove();
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return readRow(iterator.next());
            }
        };
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getTClass(){

        if(null == tClass){
            tClass = (Class<T>) TypeArgFinder.find(getClass(), XExcelImpl.class, 0);
        }
        return tClass;
    }

}

