package com.jd.help.dao;

import com.jd.help.domain.Keyword;
import com.jd.help.domain.KeywordBO;

import java.util.List;

/**
 * @author haoming1
 * @Description: �ؼ��ʶ���־ò�
 * @Date Created in 20:42 2018/1/8
 * @Modified By:
 */

public interface KeywordDao {

    /**
     * ����ؼ���
     * @param keyword
     */
    public void insert(Keyword keyword);

    /**
     * ��ѯ�ؼ����б�
     * @param bo
     * @return
     */
    public List listKeyword(KeywordBO bo);


    /**
     * ��ѯ���ղ���ؼ�������
     * @param bo
     * @return
     */
    Long countTodayKeyword(KeywordBO bo);
}
