package com.jd.help.excel;

import com.jd.pop.module.utils.JsonUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lining7 on 2017/10/18.
 */
public class ColumnParam {
    Logger log = LoggerFactory.getLogger(getClass());

    private final Workbook workbook;
    private final CellStyle dateStyle;
    private final Field field;
    private XExcel.XExcelField excelAnn;
    private Integer type;
    private int fieldIndex;
    public ColumnParam(Workbook workbook, CellStyle dateStyle, Field field2, XExcel.XExcelField ann, int fieldIndex) {
        this.workbook = workbook;
        this.dateStyle = dateStyle;
        this.field = field2;
        this.field.setAccessible(true);
        this.excelAnn = ann;
        this.fieldIndex = fieldIndex;
    }
    private void write(Object cellValue, Object t) {
        try {
            field.set(t, cellValue);
        } catch (Exception e) {
            log.info(field.getName()+",反射写入属性值失败", e);
        }
    }
    public void write(Cell cell, Object t) {
        try {
            field.set(t, getCellValue(cell));
        } catch (Exception e) {
            log.info(field.getName()+",反射写入属性值失败", e);
        }
    }
    public Object invoke(Object t) {
        try {
            return field.get(t);
        } catch (Exception e) {
            log.info(field.getName()+",反射获取属性值失败", e);
            return null;
        }
    }
    public int getType() {
        if(null == type){
            type = getExcelTypePri();
        }

        return type;
    }
    @SuppressWarnings("deprecation")
    public int getExcelType() {
        int excelTypePri = getExcelTypePri();
        if(XExcel.CELL_TYPE_DATE == excelTypePri) {
            excelTypePri = Cell.CELL_TYPE_NUMERIC;
        }
        return excelTypePri;
    }
    private int getExcelTypePri() {
        if(null == excelAnn || -1 == excelAnn.excelType()){
            return getExcelTypeByFieldType();
        }
        return excelAnn.excelType();
    }
    @SuppressWarnings("deprecation")
    private int getExcelTypeByFieldType() {
        Class<?> type = field.getType();
        if(type.isPrimitive()){
            if(type.equals(boolean.class)){
                return Cell.CELL_TYPE_BOOLEAN;
            }else if(type.equals(Void.TYPE)){
                //null
            }else{
                return Cell.CELL_TYPE_NUMERIC;
            }
        }else if(Number.class.isAssignableFrom(type)){
            return Cell.CELL_TYPE_NUMERIC;
        }else if(String.class.isAssignableFrom(type)){
            return Cell.CELL_TYPE_STRING;
        }else if(Date.class.isAssignableFrom(type)){
            return Cell.CELL_TYPE_NUMERIC;
        }else if(Calendar.class.isAssignableFrom(type)){
            return Cell.CELL_TYPE_NUMERIC;
        }else if(Boolean.class.isAssignableFrom(type)){
            return Cell.CELL_TYPE_BOOLEAN;
        }else{
            log.info(String.format("未知对应类型class:%s", type.getName()));
        }
        return Cell.CELL_TYPE_BLANK;
    }
    public String getHeardStr() {
        if(null == excelAnn || "".equals(excelAnn.value())){
            return field.getName();
        }
        return excelAnn.value();
    }
    public Field getField() {
        return field;
    }
    public XExcel.XExcelField getExcelAnn() {
        return excelAnn;
    }
    public int getFieldIndex() {
        return fieldIndex;
    }
    ///////////////////
    public boolean writeExcel(Cell cell, Object o) {
        if(null == o){
            return true;//
        }
        if(o.getClass().isPrimitive()){
            if(o.getClass().equals(boolean.class)){
                cell.setCellValue((Boolean)o);

            }else if(o.getClass().equals(Void.TYPE)){
                //null
            }else if(XExcel.CELL_TYPE_DATE==getType() && o.getClass().equals(Long.TYPE)){
                setDateStyle(cell);
                cell.setCellValue(new Date((Long)o));
            }else{
                cell.setCellValue(Double.valueOf(o+""));
            }
        }else if(XExcel.CELL_TYPE_DATE==getType() && o instanceof Long){
            setDateStyle(cell);
            cell.setCellValue(new Date((Long)o));
        }else if(o instanceof Number){
            cell.setCellValue(Double.valueOf(o+""));
        }else if(o instanceof String){
            try {
                cell.setCellValue((String)o);
            } catch (Exception e) {
                System.err.println(o);
                e.printStackTrace();
            }
        }else if(o instanceof Date){
            setDateStyle(cell);
            cell.setCellValue((Date)o);
        }else if(o instanceof Calendar){
            setDateStyle(cell);
            cell.setCellValue((Calendar)o);
        }else if(o instanceof Boolean){
            cell.setCellValue((Boolean)o);
        }else {
            try {
                cell.setCellValue(JsonUtils.toJson(o));
            } catch (Exception e) {
                log.error("write error:", e);
                return false;
            }
        }
        return true;
    }


    private void setDateStyle(Cell cell){

        cell.setCellStyle(dateStyle);
    }

    private Object getCellValue(Cell cell) {
        if(null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK){
            return null;
        }

        Class<?> type = getField().getType();
        if(type.isPrimitive()){
            if(type.equals(boolean.class)){
                return cell.getBooleanCellValue();
            }else if(type.equals(Void.TYPE)){
            }else{
                return format(type, cell.getNumericCellValue(), cell);
            }
        }else if(Number.class.isAssignableFrom(type)){
            return format(type, cell.getNumericCellValue(), cell);
        }else if(String.class.isAssignableFrom(type)){
            return cell.getStringCellValue();
        }else if(Date.class.isAssignableFrom(type)){
            return cell.getDateCellValue();
        }else if(Calendar.class.isAssignableFrom(type)){
            Calendar c = Calendar.getInstance();
            c.setTime(cell.getDateCellValue());
            return c;
        }else if(Boolean.class.isAssignableFrom(type)){
            return cell.getBooleanCellValue();
        }else{
            log.error(String.format("不支持的转换类型(row,column)(%s,%s):[%s]", cell.getRowIndex(), cell.getColumnIndex(), cell));
        }
        return null;
    }

    private Object format(Class<?> type, Double numericCellValue, Cell cell) {
        if(isIn(type, Integer.class, int.class)){
            return numericCellValue.intValue();
        }else if(isIn(type, Long.class, long.class)){
            if(XExcel.CELL_TYPE_DATE == getType()) {
                Date dateCellValue = cell.getDateCellValue();
                return dateCellValue==null?0:dateCellValue.getTime();
            }
            return numericCellValue.longValue();
        }else if(isIn(type, Double.class, double.class)){
            return numericCellValue.doubleValue();
        }else if(isIn(type, Character.class, char.class)){
            return (char)numericCellValue.intValue();
        }else if(isIn(type, Byte.class, byte.class)){
            return numericCellValue.byteValue();
        }else if(isIn(type, Short.class, short.class)){
            return numericCellValue.shortValue();
        }else if(isIn(type, Float.class, float.class)){
            return numericCellValue.floatValue();
        }else{
            return numericCellValue;
        }
    }

    private boolean isIn(Class<?> type, Class<?>... cs) {
        if(null == type || null == cs) return false;

        for(Class<?> c : cs){
            if(type.equals(c)){
                return true;
            }
        }
        return false;
    }
}
