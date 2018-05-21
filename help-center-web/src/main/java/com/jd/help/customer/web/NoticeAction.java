package com.jd.help.customer.web;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.domain.HttpsUtil;
import com.jd.help.domain.Notice;
import com.jd.help.service.NoticeService;
import com.jd.help.service.PopWareShopService;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeAction extends HelpBaseAction {
	@Resource
	private NoticeService noticeService;
	@Resource
	private PopWareShopService popWareShopService;

	private static String replaceStrOne ="document.write";
	private String noticeId;

	private String mod;
	private String bid;

	private Map<String, Object> jsonRoot = new HashMap<String, Object>();

	public String list(){
		// 初始化站点
		initSite();
		Notice notice = new Notice();
		notice.setSiteId(site.getId());
		if (page <= 0) {
			page = 1;
		}
		Result result  = noticeService.list(notice, page, PAGE_SIZE);
		this.toVm(result);
		return SUCCESS;
	}



	public String detail(){
		// 初始化站点
		initSite();
		Notice notice = new Notice();
		notice.setId(noticeId);
		Result result  = noticeService.detail(notice, true);
		this.toVm(result);
		return SUCCESS;
	}

	/**
	 * 落地页展示
	 * @return http://help.jd.com/page/landing.action?siteEnName=venderportal
	 */
	public String landing() {
		return "landing";
	}

	public String listTop3(){
		CallerInfo info = Profiler.registerInfo("vender.help.action.NoticeAction.listTop3", false, true);
		// 初始化站点
		initSite();
		Notice notice = new Notice();
		notice.setSiteId(site.getId());
		Result result  = noticeService.list(notice, 1, 3);
		jsonRoot.put("noticeList", result.get("noticeList"));
		Profiler.registerInfoEnd(info);
		return "json";
	}
	public String listTop6(){
		CallerInfo info = Profiler.registerInfo("vender.help.action.NoticeAction.listTop6", false, true);
		// 初始化站点
		initSite();
		Notice notice = new Notice();
		notice.setSiteId(site.getId());
		Result result  = noticeService.list(notice, 1, 6);
		List<Notice> noticeList = (List)result.get("noticeList");
		jsonRoot.put("noticeList", noticeList);
		Profiler.registerInfoEnd(info);
		return "json";
	}

	/**
	 * 查询公告信息
	 * @return
	 */
	public String getNoticeInfo(){
		CallerInfo info = Profiler.registerInfo("vender.help.action.NoticeAction.getNoticeInfo", false, true);
		// 初始化站点
		initSite();
		Notice notice = new Notice();
		notice.setSiteId(site.getId());
		//设置获取的公告信息
		jsonRoot.put("noticeInfo",convertMessage());
		jsonRoot.put("isSuccess",true);
		Profiler.registerInfoEnd(info);
		return "json";
	}

	/**
	 * 转换获取的请求内容
	 * @return
	 */
	private String convertMessage(){
		//获取公告信息
		String noticeInfo = HttpsUtil.doGetRequest(getNoticeRequestUrl());
		if(StringUtils.isNotEmpty(noticeInfo)){
			int len = noticeInfo.indexOf(replaceStrOne) + replaceStrOne.length()+ 2;
			noticeInfo = noticeInfo.substring(len,noticeInfo.length()-3);
			noticeInfo = noticeInfo.replaceAll("\\\\n","");
			return noticeInfo;
		}
		return "";
	}

	/**
	 * 拼装调用链接请求
	 * @return
	 */
	private String getNoticeRequestUrl(){
		StringBuffer strUrl = new StringBuffer();
		strUrl.append("http://mjbbs.jd.com/api.php");
		strUrl.append("?mod=").append(mod);
		strUrl.append("&bid=").append(bid);
		return strUrl.toString();
	}
	public String 	queryImportNotice()  {
		jsonRoot.put("isSuccess",true);
		String content=popWareShopService.queryForumThreadList(7);
//		content=new String(content.getBytes("ISO-8859-1"),"UTF-8");
		jsonRoot.put("content",content);
		return "json";
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public Map<String, Object> getJsonRoot() {
		return jsonRoot;
	}

	public void setJsonRoot(Map<String, Object> jsonRoot) {
		this.jsonRoot = jsonRoot;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
}
