package com.jd.help.dao.impl;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.help.dao.NoticeDao;
import com.jd.help.domain.Notice;
import com.jd.help.domain.NoticeBO;
import com.jd.mongodbclient2.MongoDBClient;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository("noticeDao")
public class NoticeDaoImpl implements NoticeDao{
	private final static Log logger = LogFactory.getLog(NoticeDaoImpl.class);
	@Resource(name = "helpNoticeMongoDBClient")
	private MongoDBClient helpNoticeMongoDBClient;
	
	@Override
	public long insert(Notice notice) {
		int maxSortOrder = getMaxSortOrder(notice);
		if(maxSortOrder == -1){
			return 0;
		}
		notice.setSortOrder(maxSortOrder + 1);
		List<Notice> noticeList = new ArrayList<Notice>();
		noticeList.add(notice);
		return helpNoticeMongoDBClient.insert(noticeList) == true ? 1 : 0;
	}
	
	@Override
	public List<Notice> queryForList(Notice notice, int page,
			int pageSize) {
        
        PaginatedList<Notice> pageList = 
				new PaginatedArrayList<Notice>(page, pageSize);
        List<Notice> list = null;
        try{
        	QueryBuilder  qb = new QueryBuilder();
        	/*if(notice != null && StringUtils.isNotBlank(notice.getName())){
        		Pattern pattern = Pattern.compile("^.*" + notice.getName() + ".*$", Pattern.CASE_INSENSITIVE);
        		BasicDBObject queryName = new BasicDBObject("name",pattern);
        		qb.and(queryName);
        	}*/
    		//状态
    		BasicDBObject queryStatus = new BasicDBObject();  
    		queryStatus.put("status", new BasicDBObject("$gt", 0));
    		qb.and(queryStatus);
    		//站点
    		BasicDBObject querySite = new BasicDBObject();  
    		querySite.put("siteId", notice.getSiteId());
    		qb.and(querySite);
        	
    		QueryBuilder  sortQb = new QueryBuilder();
    		sortQb.put("sortOrder").is(-1);
    		
        	int index = (page - 1) * pageSize; 
        	list = helpNoticeMongoDBClient.select(qb, sortQb,index , pageSize, Notice.class);
        	long count = helpNoticeMongoDBClient.selectCount(qb);
        	pageList.setTotalItem((int)count);
        	if(list != null){
        		pageList.addAll(list);
        	}
        }catch(Exception e){
        	logger.error("query notice list error.",e);
        }
        return pageList;
	}
	
	@Override
	public int delete(Notice notice) {
		return helpNoticeMongoDBClient.delete(new QueryBuilder().start("_id").is(new ObjectId(notice.getId()))) == true ? 1 : 0;
	}
	
    @Override
	public Notice queryForObject(Notice notice) {
		try {
			Object obj = helpNoticeMongoDBClient.selectOne(new QueryBuilder().start("_id").is(new ObjectId(notice.getId())), Notice.class);
			return  obj == null ? null : (Notice)obj;
        } catch (DataAccessException e) {
            logger.error("数据库访问出错！", e);
            return null;
        }
	}
    
    @Override
	public int update(Notice notice) {
    	try{
    		Map<String,String> optionMap = new HashMap<String,String>();
            optionMap.put("name", "$set");
            optionMap.put("content", "$set");
            optionMap.put("modifier", "$set");
            optionMap.put("modified", "$set");
    		return helpNoticeMongoDBClient.update(new QueryBuilder().start("_id").is(new ObjectId(notice.getId())), notice,optionMap) == true ? 1 : 0;
    	}catch(Exception e){
    		logger.error("update notice error", e);
    		return 0;
    	}
	}

	public int updateStatus(Notice notice) {
		try{
			Map<String,String> optionMap = new HashMap<String,String>();
			optionMap.put("status", "$set");
			optionMap.put("modifier", "$set");
			optionMap.put("modified", "$set");
			return helpNoticeMongoDBClient.update(new QueryBuilder().start("_id").is(new ObjectId(notice.getId())), notice,optionMap) == true ? 1 : 0;
		}catch(Exception e){
			logger.error("update notice error", e);
			return 0;
		}
	}

	public int updateSortOrder(Notice notice){
		try{
			Map<String,String> optionMap = new HashMap<String,String>();
			optionMap.put("sortOrder", "$set");
			optionMap.put("modifier", "$set");
			optionMap.put("modified", "$set");
			return helpNoticeMongoDBClient.update(new QueryBuilder().start("_id").is(new ObjectId(notice.getId())), notice,optionMap) == true ? 1 : 0;
		}catch(Exception e){
			logger.error("update notice error", e);
			return 0;
		}
	}

	private int getMaxSortOrder(Notice notice){
		QueryBuilder  qb = new QueryBuilder();
		qb.put("siteId").is(notice.getSiteId());

		QueryBuilder  sortQb = new QueryBuilder();
		sortQb.put("sortOrder").is(-1);

		try{
			List<Notice> list = helpNoticeMongoDBClient.select(qb, sortQb,0 , 1, Notice.class);
			if(list == null || list.size() < 1){
				return 1;
			}
			Notice n = list.get(0);
			if(n == null || n.getSortOrder() == null){
				return -1;
			}
			return n.getSortOrder();
		}catch (Exception e){
			return -1;
		}

	}



	public List<NoticeBO> queryForListAdmin(Notice notice, int page,int pageSize){
		PaginatedList<NoticeBO> pageList =
				new PaginatedArrayList<NoticeBO>(page, pageSize);
		List<Notice> list = null;
		try{
			QueryBuilder  qb = new QueryBuilder();
			if(notice != null && StringUtils.isNotBlank(notice.getName())){
				Pattern pattern = Pattern.compile("^.*" + notice.getName() + ".*$", Pattern.CASE_INSENSITIVE);
				BasicDBObject queryName = new BasicDBObject("name",pattern);
				qb.and(queryName);
			}
			//状态
			BasicDBObject queryStatus = new BasicDBObject();
			queryStatus.put("status", new BasicDBObject("$gt", -1));
			qb.and(queryStatus);
			//站点
			BasicDBObject querySite = new BasicDBObject();
			querySite.put("siteId", notice.getSiteId());
			qb.and(querySite);

			QueryBuilder  sortQb = new QueryBuilder();
			sortQb.put("sortOrder").is(-1);

			long count = helpNoticeMongoDBClient.selectCount(qb);
			int index = (page - 1) * pageSize;
			boolean hasNext = false;
			boolean hasPre = false;
			//因为要支持页面移动顺序，所以要得到该页内第一条记录的上一条和最后一条记录的下一条
			if(page * pageSize < count){ //如果当前页后还有数据
				hasNext = true;
				pageSize = pageSize + 1;
			}
			if(page > 1){
				hasPre = true;
				index = index - 1;
				pageSize = pageSize + 1; //查询当前页前一条
			}
			list = helpNoticeMongoDBClient.select(qb, sortQb,index , pageSize, Notice.class);

			pageList.setTotalItem((int)count);
			if(list != null){

				pageList.addAll(convertList(list,hasPre,hasNext));
			}
		}catch(Exception e){
			logger.error("query notice list error.",e);
		}
		return pageList;
	}

	private List<NoticeBO> convertList(List<Notice> list,boolean hasPre,boolean hasNext){
		List<NoticeBO> newList = new ArrayList<NoticeBO>();
		int startIndex = 0;
		if(hasPre && list.size() > 1){
			startIndex = 1;
		}
		int endIndex = list.size() - 1;
		if(hasNext){
			endIndex = list.size() - 2;
		}
		for(int i = startIndex; i <= endIndex; i ++){
			NoticeBO n = new NoticeBO();
			n.setId(list.get(i).getId());
			n.setContent(list.get(i).getContent());
			n.setCreated(list.get(i).getCreated());
			n.setCreator(list.get(i).getCreator());
			n.setModified(list.get(i).getModified());
			n.setModifier(list.get(i).getModifier());
			n.setName(list.get(i).getName());
			n.setSiteId(list.get(i).getSiteId());
			n.setSortOrder(list.get(i).getSortOrder());
			n.setStatus(list.get(i).getStatus());
			if(hasPre || i > startIndex){
				n.setPreId(list.get(i - 1).getId());
			}
			if(hasNext || i < endIndex){
				n.setNextId(list.get(i + 1).getId());
			}
			newList.add(n);
		}
		return newList;
	}

}
