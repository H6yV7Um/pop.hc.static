package com.jd.help.rpc;

import com.jd.ump.annotation.JProfiler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用用户标签接口的代理
 *
 * @author laichendong
 * @since 2015/1/3 14:14
 */
public class UserTagProxy {
    private String url;
    private String passkey;
    private int readTimeout = 1000;
    private int connectTimeout = 1000;


    /**
     * @param pin 用户名
     * @return A 或 D 或 null
     */
    @JProfiler(jKey = "vender.help.customer.method.outside.tagOfUser", jAppName = "vender_help_center")
    public String tagOfUser(String pin) {
        try {
            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(readTimeout);
            s.setConnectTimeout(connectTimeout);
            RestTemplate restTemplate = new RestTemplate(s);
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            messageConverters.add(new MappingJacksonHttpMessageConverter());
            restTemplate.setMessageConverters(messageConverters);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("passkey", passkey);
            paramMap.put("appCode", "helpcenter.jd.com");
            paramMap.put("customerId", pin);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, paramMap, Map.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                Map body = responseEntity.getBody();
                if (body.get("responseStatus") != null && (Boolean) body.get("responseStatus")) {
                    Integer customerGrade = Integer.valueOf(String.valueOf(body.get("customerGrade")));
                    if (customerGrade == 10) {
                        return "A";
                    } else if (customerGrade == 20) {
                        return "D";
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

}
