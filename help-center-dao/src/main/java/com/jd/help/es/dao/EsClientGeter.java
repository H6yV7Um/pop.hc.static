package com.jd.help.es.dao;


import com.jd.help.es.dao.impl.IssueEsDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * es
 *
 */
public  class EsClientGeter {


    private static final Log log = LogFactory.getLog(EsClientGeter.class);

    private TransportClient client = null;

    private String clusterName;
    private String ip;
    private int port;


    @PostConstruct
    private void init() throws UnknownHostException {
        if(null==clusterName){
            return;
        }
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();// 集群名
        //创建client
        try {
            client  = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
        } catch (UnknownHostException e) {
            log.error("【IssueEsDaoImpl.searchIssueByKeyword】获取客户端失败");
        }
    }

    //获取客户端
    public TransportClient getClient(){
        return client;
    }

    public String getClusterName() {
        return clusterName;
    }
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

}

