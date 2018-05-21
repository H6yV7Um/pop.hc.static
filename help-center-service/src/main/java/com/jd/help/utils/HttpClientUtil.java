package com.jd.help.utils;

import com.jd.common.util.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by yfxialiang on 2018/5/15.
 */
public class HttpClientUtil {
    private static Logger logger = LogManager.getLogger(HttpClientUtil.class);

    public static String CHARSET_UTF8 = "utf-8";

    /**
     * get utf-8
     * @param url
     * @return
     */
    public static String sendGetRequest(String url) throws Exception{
        return sendGetRequest(url,CHARSET_UTF8);
    }
    /**
     *  get any
     * @param url
     * @param charset
     * @return
     */
    public static String sendGetRequest(String url,String charset) throws Exception{
        String response = null;
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(2, false));
        client.getHttpConnectionManager().getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,charset);
        client.getHttpConnectionManager().getParams().setSoTimeout(10000);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        GetMethod method = new GetMethod(url);
        InputStream in = null;
        try {
            int status = client.executeMethod(method);
            if(status != HttpStatus.SC_OK){
                logger.error("getÊ§°Ü£¬status="+status+"url="+url);
                throw new Exception("http request response not 200");
            }else{
                in = method.getResponseBodyAsStream();
                response = IOUtils.toString(in, charset);
            }
        } catch (Exception e) {
            throw e;
        }finally{
            if(in != null)
                IOUtils.closeQuietly(in);
            if(method != null)
                method.releaseConnection();
        }
        return response;
    }

    /**
     * post utf-8
     * @param url
     * @param paras
     * @return
     */
    public static String sendPostRequest(String url,Map<String,String> paras) throws Exception{
        return sendPostRequest(url,paras,CHARSET_UTF8);
    }

    /**
     * post any
     * @param url
     * @param paras
     * @param charset
     * @return
     */
    public static String sendPostRequest(String url,Map<String,String> paras,String charset) throws Exception{
        String response = null;
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,charset);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(2,false));
        client.getHttpConnectionManager().getParams().setSoTimeout(10000);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        if(MapUtils.isNotEmpty(paras)){
            Set set = paras.entrySet();
            Iterator<Map.Entry> it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,String> entry = it.next();
                method.addParameter(entry.getKey(), entry.getValue());
            }
        }
        InputStream in = null;
        try {
            int status = client.executeMethod(method);
            if(status != HttpStatus.SC_OK){
                logger.error("postÊ§°Ü,status="+status+"url="+url+"paras="+paras);
                throw new Exception("http request response not 200");
            }else{
                in = method.getResponseBodyAsStream();
                response = IOUtils.toString(in,charset);
            }
        } catch (Exception e) {
            throw e;
        }finally {
            if(in != null)
                IOUtils.closeQuietly(in);
            if(method != null)
                method.releaseConnection();
        }
        return response;
    }

    public static Map<String, Object> json2Map(String json) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        if (StringUtils.isBlank(json)) {
            return new HashMap<String, Object>();
        }
        try {
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,true);
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            logger.error("json2Map->json="+json);
            logger.error("json´®×ªmap³ö´í",e);
            throw e;
        }
    }
}
