package com.jd.help.dao;

import java.util.List;

import com.jd.help.domain.Notice;
import com.jd.help.domain.NoticeBO;

public interface NoticeDao {
	/**
     * ����Notice�������ݿ�
     *
     * @param Notice ������Ķ���
     * @return 
     */
    long insert(Notice notice);

    /**
     * �����ݿ�ɾ��Notice����
     * ��ɾ����ֻ��update״̬����delete������
     *
     * @param Notice ɾ����������ͨ����ָ�������ֶε�ֵ��ɾ��һ����¼����ָ�������ֶ�ɾ��һ���¼��
     * @return ɾ��������
     */
    int delete(Notice notice);

    /**
     * ����Notice�������ݿ�
     *
     * @param HtmlModule �����µĶ���ͨ������Ҫ������Щ�ֶξ�ָ����Щ�ֶε�ֵ���������ֶ�һ����Ҫָ����
     * @return ���µ�����
     */
    int update(Notice notice);

    /**
     * �����ݿ��ѯһ��Notice����
     *
     * @param Notice ��ѯ��������ͨ��ָ�������ֶε�ֵ
     * @return ���򷵻ض������򷵻�null
     */
    Notice queryForObject(Notice notice);

    /**
     * ����������ѯ����������Notice�������
     *
     * @return ��¼��
     */
    //int queryForCount(Notice notice);

    /**
     * ����������ѯ����������Notice�����б�
     *
     * @return ����������Notice�����б�û������������Notice�����򷵻�null
     */
    List<Notice> queryForList(Notice notice, int page, int pageSize);

    /**
     * ��̨��ѯ�б�
     * @param notice
     * @param page
     * @param pageSize
     * @return
     */
    public List<NoticeBO> queryForListAdmin(Notice notice, int page,int pageSize);

    /**
     * ���������ֶ�
     * @param notice
     * @return
     */
    public int updateSortOrder(Notice notice);

    /**
     * ����״̬
     * @param notice
     * @return
     */
    public int updateStatus(Notice notice);

}
