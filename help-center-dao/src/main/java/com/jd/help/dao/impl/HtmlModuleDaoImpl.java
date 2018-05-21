package com.jd.help.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.help.dao.HtmlModuleDao;
import com.jd.help.domain.HtmlModule;
import com.jd.mongodbclient2.MongoDBClient;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Repository("htmlModuleDao")
public class HtmlModuleDaoImpl implements HtmlModuleDao{
	private final static Log logger = LogFactory.getLog(HtmlModuleDaoImpl.class);
	
	@Resource(name = "helpHtmlModuleMongoDBClient")
	private MongoDBClient helpHtmlModuleMongoDBClient;
	
	@Override
	public long insert(HtmlModule htmlModule) {
		List<HtmlModule> htmlList = new ArrayList<HtmlModule>();
		htmlList.add(htmlModule);
		return helpHtmlModuleMongoDBClient.insert(htmlList) == true ? 1 : 0;
	}
	
	@Override
	public int delete(HtmlModule htmlModule) {
		return helpHtmlModuleMongoDBClient.delete(new QueryBuilder().start("_id").is(new ObjectId(htmlModule.getId()))) == true ? 1 : 0;
	}
	
	@Override
	public int update(HtmlModule htmlModule) {
		try{
    		Map<String,String> optionMap = new HashMap<String,String>();
            optionMap.put("notes", "$set");
            optionMap.put("name", "$set");
            optionMap.put("content", "$set");
            optionMap.put("modifier", "$set");
            optionMap.put("modified", "$set");
            optionMap.put("status", "$set");
            return helpHtmlModuleMongoDBClient.update(new QueryBuilder().start("_id").is(new ObjectId(htmlModule.getId())), htmlModule,optionMap) == true ? 1 : 0;
    	}catch(Exception e){
    		logger.error("update notice error", e);
    		return 0;
    	}
		
	}
	
	@Override
	public HtmlModule queryForObject(HtmlModule htmlModule) {
		try {
			Object obj = helpHtmlModuleMongoDBClient.selectOne(new QueryBuilder().start("_id").is(new ObjectId(htmlModule.getId())), HtmlModule.class);
			return  obj == null ? null : (HtmlModule)obj;
        } catch (DataAccessException e) {
            logger.error("数据库访问出错！", e);
            return null;
        }
	}
	
	@Override
	public List<HtmlModule> queryForList(HtmlModule htmlModule, int page,
			int pageSize) {
		PaginatedList<HtmlModule> pageList = 
				new PaginatedArrayList<HtmlModule>(page, pageSize);
        List<HtmlModule> list = null;
        try{
        	QueryBuilder  qb = new QueryBuilder();
        	if(htmlModule != null && StringUtils.isNotBlank(htmlModule.getName())){
        		Pattern pattern = Pattern.compile("^.*" + htmlModule.getName() + ".*$", Pattern.CASE_INSENSITIVE);
        		DBObject o = new BasicDBObject("name",pattern);
        		qb.and(o);
        	}
        	
        	//状态
    		BasicDBObject queryStatus = new BasicDBObject();  
    		queryStatus.put("status", new BasicDBObject("$gt", -1));
    		qb.and(queryStatus);
    		//站点
    		BasicDBObject querySite = new BasicDBObject();  
    		querySite.put("siteId", htmlModule.getSiteId());
    		qb.and(querySite);
        	
        	int index = (page - 1) * pageSize; 
        	list = helpHtmlModuleMongoDBClient.select(qb, index , pageSize, HtmlModule.class);
        	long count = helpHtmlModuleMongoDBClient.selectCount(qb);
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
	public List<HtmlModule> findByKeies(String[] moduleNames,int siteId) {
        List<HtmlModule> list = null;
		CallerInfo info = Profiler.registerInfo("vender.help.dao.HtmlModuleDaoImpl.findByKeies", false, true);
        try{
        	QueryBuilder  qb = new QueryBuilder();
        	BasicDBList values = new BasicDBList(); 
        	values.addAll(Arrays.asList(moduleNames));
        	BasicDBObject in = new BasicDBObject("$in", values);  
        	BasicDBObject queryKey = new BasicDBObject("key", in);
        	qb.and(queryKey);
        	qb.and(new BasicDBObject("siteId",siteId));
        	list = helpHtmlModuleMongoDBClient.select(qb, 0 , Integer.MAX_VALUE, HtmlModule.class);
        }catch(Exception e){
			logger.error("vender.help.dao.HtmlModuleDaoImpl.findByKeies",e);
        	logger.error("query notice list error.",e);
			Profiler.functionError(info);
		}finally {
			Profiler.registerInfoEnd(info);
			return list;
		}

	}
	
}
