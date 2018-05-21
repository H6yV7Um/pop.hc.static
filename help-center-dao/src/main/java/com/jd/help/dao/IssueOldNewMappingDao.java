package com.jd.help.dao;

import java.util.List;

import com.jd.help.domain.IssueOldNewMapping;
import com.jd.help.domain.IssueOldNewMappingBO;

public interface IssueOldNewMappingDao {
	/**
	 * �����б�
	 * @param issueONMapingList
	 */
	void insert(List<IssueOldNewMapping> issueONMapingList);
	/**
	 * ͨ��issueID��ѯ�б�
	 * @param issueId
	 * @return
	 */
	List<IssueOldNewMapping> queryForListByIssueId(int issueId);
	/**
	 * ͨ��issueIDɾ���б�
	 * @param issueId
	 * @return
	 */
	int delete(int issueId);
	
	/**
	 * ��ѯ����
	 * @return
	 */
	List<IssueOldNewMappingBO> queryAllForList();
}
