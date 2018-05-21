package com.jd.help.customer.web.utils;

import com.jd.common.util.StringUtils;

/**
 * Created by lipengfei5 on 2017/9/21.
 */
public class VenderStringUtils {
    public static String[] split(String str,String separatorChars){
        if(StringUtils.isBlank(str)){
            return new String[0];
        }
        if("\\n".equals(separatorChars)){
            String[] split = StringUtils.split(str, "\n");
            return StringUtils.split(str,"\n");
        }
        return StringUtils.split(str,separatorChars);
    }
    public static String subStringStr(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        return StringUtils.substringBefore(str,".");
    }
    public static String subStringAppend(String str,String separator){
        if(StringUtils.isBlank(str)){
            return str;
        }
        int i = StringUtils.indexOf(str, separator);
        String temStart = StringUtils.substringBefore(str, separator);
        String temEnd = StringUtils.substring(str,i+1,i+3);
        return temStart+"."+temEnd;
    }
    public static String subStringByLength(String str,int length,String endStr){
        if(StringUtils.isBlank(str)){
            return str;
        }
        if(str.length()<=length){
            return str;
        }
        String temStr = StringUtils.substring(str,0,length);
        return temStr + endStr;
    }
//    public static void main(String[] args) {
//        String s = subStringByLength("1231.2323232", 3,"...");
//        System.out.println(s);
//    }
}
