package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.IssueOldNewMappingDao;
import com.jd.help.domain.IssueOldNewMapping;
import com.jd.help.domain.IssueOldNewMappingBO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.util.List;

@Repository("issueOldNewMappingDao")
public class IssueOldNewMappingDaoImpl  extends BaseDao implements IssueOldNewMappingDao {
	
	/**
    * 注入数据源
    */
   @Override
   @Resource(name = "helpCenterDataSource")
   public void setDataSource(DataSource dataSource) {
       super.setDataSource(dataSource);
   }
	public void insert(List<IssueOldNewMapping> issueONMapingList){
		 insert("IssueOldNewMapping.insert", issueONMapingList);
	}
	
	@SuppressWarnings("unchecked")
	public List<IssueOldNewMapping> queryForListByIssueId(int issueId){
		try {
			return queryForList("IssueOldNewMapping.queryForListByIssueId",issueId);
        } catch (DataAccessException e) {
            logger.error("数据库访问出错！", e);
            return null;
        }
		
	}
	
	public int delete(int issueId){
		return this.delete("IssueOldNewMapping.delete", issueId);
	}
	@Override
	public List<IssueOldNewMappingBO> queryAllForList() {
		try {
			return queryForList("IssueOldNewMapping.queryAllForList");
        } catch (DataAccessException e) {
            logger.error("数据库访问出错！", e);
            return null;
        }
		
	}
}
