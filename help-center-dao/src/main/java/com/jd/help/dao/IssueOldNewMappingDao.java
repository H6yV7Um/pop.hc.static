package com.jd.help.dao;

import java.util.List;

import com.jd.help.domain.IssueOldNewMapping;
import com.jd.help.domain.IssueOldNewMappingBO;

public interface IssueOldNewMappingDao {
	/**
	 * 插入列表
	 * @param issueONMapingList
	 */
	void insert(List<IssueOldNewMapping> issueONMapingList);
	/**
	 * 通过issueID查询列表
	 * @param issueId
	 * @return
	 */
	List<IssueOldNewMapping> queryForListByIssueId(int issueId);
	/**
	 * 通过issueID删除列表
	 * @param issueId
	 * @return
	 */
	int delete(int issueId);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<IssueOldNewMappingBO> queryAllForList();
}
