package com.jd.help.dao;

import java.util.List;

import com.jd.help.domain.HtmlModule;

public interface HtmlModuleDao {
	/**
     * ����HtmlModule�������ݿ�
     *
     * @param HtmlModule ������Ķ���
     * @return 
     */
    long insert(HtmlModule htmlModule);

    /**
     * �����ݿ�ɾ��HtmlModule����
     * ��ɾ����ֻ��update״̬����delete������
     *
     * @param HtmlModule ɾ����������ͨ����ָ�������ֶε�ֵ��ɾ��һ����¼����ָ�������ֶ�ɾ��һ���¼��
     * @return ɾ��������
     */
    int delete(HtmlModule htmlModule);

    /**
     * ����HtmlModule�������ݿ�
     *
     * @param HtmlModule �����µĶ���ͨ������Ҫ������Щ�ֶξ�ָ����Щ�ֶε�ֵ���������ֶ�һ����Ҫָ����
     * @return ���µ�����
     */
    int update(HtmlModule htmlModule);

    /**
     * �����ݿ��ѯһ��HtmlModule����
     *
     * @param HtmlModule ��ѯ��������ͨ��ָ�������ֶε�ֵ
     * @return ���򷵻ض������򷵻�null
     */
    HtmlModule queryForObject(HtmlModule htmlModule);

    /**
     * ����������ѯ����������HtmlModule�������
     *
     * @return ��¼��
     */
    //int queryForCount(HtmlModule htmlModule);

    /**
     * ����������ѯ����������htmlModule�����б�
     *
     * @return ����������htmlModule�����б�û������������htmlModule�����򷵻�null
     */
    List<HtmlModule> queryForList(HtmlModule htmlModule, int page, int pageSize);

    List<HtmlModule> findByKeies(String[] moduleNames,int siteId);
}
