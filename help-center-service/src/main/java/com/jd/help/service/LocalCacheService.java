package com.jd.help.service;

import java.util.Map;

import com.jd.help.domain.IssueOldNewMappingBO;

public interface LocalCacheService {
	
	/**
	 * ��������ӳ�仺��
	 * @return
	 */
	public Map<String,IssueOldNewMappingBO> urlMappingCache();
}
