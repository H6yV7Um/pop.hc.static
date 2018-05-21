package com.jd.help.dao.issue.search;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author laichendong
 * @since 2014/12/9 10:52
 */
public class SolrServerFactoryBean implements FactoryBean {
    private String url;
    private int connectionTimeout = 3000;
    private int readTimeout = 5000;
    private HttpSolrServer instance;

    @Override
    public Object getObject() throws Exception {
        if (instance == null && StringUtils.isNotBlank(url)) {
            instance = new HttpSolrServer(url);
            instance.setConnectionTimeout(connectionTimeout);
            instance.setSoTimeout(readTimeout);
        }
        return instance;
    }

    @Override
    public Class getObjectType() {
        return HttpSolrServer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

}
