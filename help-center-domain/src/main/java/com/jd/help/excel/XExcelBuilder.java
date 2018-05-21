package com.jd.help.excel;

/**
 * Created by lining7 on 2017/10/18.
 */
public class XExcelBuilder {
    public static <T> XExcel<T> instance(Class<T> c){
        return new XExcelImpl<T>(c);
    }

}
