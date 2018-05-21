package com.jd.help.utils;

import com.jd.common.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by yfxialiang on 2018/4/11.
 */
public class HtmlUtil {
    public static String Html2Text(String inputString) {
        if(StringUtils.isEmpty(inputString)){
            return "";
        }
        String textStr = inputString;
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>}
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>}
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(textStr);
            textStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(textStr);
            textStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(textStr);
            textStr = m_html.replaceAll(""); // 过滤html标签

            return textStr.replaceAll("&nbsp;","");

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
            return "";
        }

    }
}
