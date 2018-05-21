package com.jd.help.domain;

import com.jd.common.util.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ϊ����https׼���Ĺ��߷���
 * Created by laichendong on 4/10/16.
 */
public class HttpsUtil {
    private static final Pattern HREF_PATTERN = Pattern.compile("((href|src|data-lazyload)\\s*=\\s*(?:\"|'))\\s*([^\"'>]*)\\s*((?:\"|'))", Pattern.CASE_INSENSITIVE);

    private static  final String  DOCUMENT="document.write";

    /**
     * ��html���hrefָ������ӱ��"Э������Ӧ��"
     * �������href��src��ֵ ����http:// ��ͷ��,  ���� // ��ͷ
     *
     * @param html Ҫ�����html
     * @return ������html
     */
    public static String removeHttp(String html) {
        if (html == null || html.trim().isEmpty()) {
            return html;
        }

        StringBuffer buf = new StringBuffer();
        Matcher matcher = HREF_PATTERN.matcher(html);
        while (matcher.find()) {
            String href = matcher.group(3);
            if (href.toLowerCase().startsWith("http:")){
                href = href.replaceFirst("http:", "");
            }
            matcher.appendReplacement(buf, "$1" + Matcher.quoteReplacement(href) + "$4");
        }
        matcher.appendTail(buf);
        return buf.toString();

    }

    /**
     * ��html���hrefָ������ӱ��"Э������Ӧ��"
     * �������href��src��ֵ ����http:// ��ͷ��,  ���� // ��ͷ
     *
     * @param html Ҫ�����html
     * @return ������html
     */
    public static String removeHttp2(String html) {
        if (html == null || html.trim().isEmpty()) {
            return html;
        }

        StringBuffer buf = new StringBuffer();
        Matcher matcher = HREF_PATTERN.matcher(html);
        while (matcher.find()) {
            String href = matcher.group(3);
            if (href.toLowerCase().startsWith("http:") &&
                    (href.toLowerCase().indexOf("mjbbs.jd.com") < 0)){
                href = href.replaceFirst("http:", "");
            }
            matcher.appendReplacement(buf, "$1" + Matcher.quoteReplacement(href) + "$4");
        }
        matcher.appendTail(buf);
        return buf.toString();
    }

    /**
     * http�����ȡ��������
     * @param serviceURL
     * @return
     */
    public static String doGetRequest(String serviceURL) {
        String resultStr = "";
        System.out.println("serviceURL:"+serviceURL);
        GetMethod get = new GetMethod(serviceURL);
        get.addRequestHeader("Accept" ,  "8859_1");
        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(get);
            if(result!=200){
                return "";
            }
            resultStr  = get.getResponseBodyAsString();
        }catch(Exception ex){
            return "-1";
        }

        return resultStr;
    }
}
