package com.jd.help.service;

/**
 * Created by yfxialiang on 2018/3/30.
 * �����������ϱ����ݡ������������ݡ��̼�ѧϰ�������ݵ��뵽knowledge����
 */
public interface KnowledgeTransferDataService {


    /**
     * ���̼���ѵ�������ݵ���knowledge
     */
    public void transferXueData();

    /**
     * ���������ݵ���knowledge
     */
    public void transferSceneData();

    /**
     * �����������ϱ����ݵ���knowledge
     */
    public void transferIssueData();

    /**
     * ɾ��knowledge��knowledge_content���ȫ������
     */
    public void delAllData();
}
