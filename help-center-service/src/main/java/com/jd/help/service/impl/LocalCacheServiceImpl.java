package com.jd.help.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jd.common.util.StringUtils;
import com.jd.help.dao.IssueOldNewMappingDao;
import com.jd.help.domain.IssueOldNewMappingBO;
import com.jd.help.service.LocalCacheService;

@Service("localCacheService")
public class LocalCacheServiceImpl implements LocalCacheService {

	private static final Log log = LogFactory.getLog(LocalCacheServiceImpl.class);
	
	@Resource
    private IssueOldNewMappingDao issueOldNewMappingDao;
	
	private LoadingCache<String, Map<String,IssueOldNewMappingBO>> cache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS).refreshAfterWrite(30, TimeUnit.MINUTES)
			.build(new CacheLoader<String,Map<String,IssueOldNewMappingBO>>(){
				@Override
				public Map<String, IssueOldNewMappingBO> load(String key)
						throws Exception {
					List<IssueOldNewMappingBO> ons = issueOldNewMappingDao.queryAllForList();
					Map<String,IssueOldNewMappingBO> urlMap = new HashMap<String,IssueOldNewMappingBO>();
					if(ons != null){
						for(IssueOldNewMappingBO bo : ons){
							if(bo != null && StringUtils.isNotBlank(bo.getOldUrl())){
								urlMap.put(StringUtils.trim(bo.getOldUrl()), bo);
							}
						}
					}
					log.info("--->load urlMapping data:size="+(ons == null ? "" : ons.size()));
					return urlMap;
				}
			});
	
	
	public Map<String,IssueOldNewMappingBO> urlMappingCache() {
			return cache.getUnchecked("");
	}

}
