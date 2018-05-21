package com.jd.help.admin.web.notice;

import java.util.Date;

import javax.annotation.Resource;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.domain.HrmPurviewConstants;
import com.jd.help.domain.Notice;
import com.jd.help.domain.NoticeBO;
import com.jd.help.service.NoticeService;

public class HelpNoticeAction extends HelpBaseAction {

    @Resource
    private NoticeService noticeService;

    private Notice notice;

	/**
	 * �ƶ�˳�򽻻�����
	 */
	private Notice noticeRef;

	private NoticeBO noticeBO;

  //�Ƿ���֤Ȩ��
    private final static boolean IS_VALIDATE = HrmPurviewConstants.IS_VALIDATE;
    
    /**
     * ��������ҳ��
     *
     * @return
     */
    public String add() throws NoHrmPrivilegeException{
        initSite(IS_VALIDATE);
        return SUCCESS;
    }

    /**
     * ִ����������
     *
     * @return
     */
    public String doAdd() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
    	if (notice != null) {
        	notice.setCreator(WebHelper.getPin());
        	notice.setCreated(new Date());
        	notice.setModified(new Date());
        	notice.setModifier(WebHelper.getPin());
        	notice.setStatus(0);
        }
        Result result = noticeService.insert(notice);
        return "list";
    }

    /**
     * ֻԤ������
     *
     * @return
     */
    public String preview() throws NoHrmPrivilegeException{
    	// ��ʼ��վ��
    	initSite(IS_VALIDATE);
        notice.setModified(new Date());
        Result result  = new Result();
        result.addDefaultModel("notice",notice);
		this.toVm(result);
		return SUCCESS;
    }

	public String update() throws NoHrmPrivilegeException{
		initSite(IS_VALIDATE);
		this.toVm(noticeService.detail(notice, false));
		return SUCCESS;
	}

	public String move() throws NoHrmPrivilegeException{
		initSite(IS_VALIDATE);
		notice.setModified(new Date());
		notice.setModifier(WebHelper.getPin());
		noticeRef.setModified(new Date());
		noticeRef.setModifier(WebHelper.getPin());
		this.toVm(noticeService.move(notice,noticeRef));
		return "listPage";
	}
	
	public String doUpdate() throws NoHrmPrivilegeException{
		initSite(IS_VALIDATE);
		Result result = null;
		if(notice == null){
			result = new Result(false);
			return SUCCESS;
		}
		notice.setModifier(WebHelper.getPin());
		notice.setModified(new Date());
		result = noticeService.update(notice);
		this.toVm(result);
		return "list";
	}
	
	public String list() throws NoHrmPrivilegeException{
		initSite(IS_VALIDATE);
		if(notice == null){
			notice = new Notice();
		}
		notice.setSiteId(site != null && site.getId() != null ? site.getId() : 0);
		if(notice.getName() != null){
			notice.setName(notice.getName().trim());
		}
		page = (page <= 0 ? 1 : page);
		this.toVm(noticeService.listForAdmin(notice, page, PAGE_SIZE));
		return SUCCESS;
	}
    
    public String delete() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        this.toVm(noticeService.delete(notice));
        return "list";
    }

	public String online() throws NoHrmPrivilegeException{
		initSite(IS_VALIDATE);
		notice.setModifier(WebHelper.getPin());
		notice.setModified(new Date());
		this.toVm(noticeService.online(notice));
		return "listPage";
	}

	public String offline() throws NoHrmPrivilegeException{
		initSite(IS_VALIDATE);
		notice.setModifier(WebHelper.getPin());
		notice.setModified(new Date());
		this.toVm(noticeService.offline(notice));
		return "listPage";
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public NoticeBO getNoticeBO() {
		return noticeBO;
	}

	public void setNoticeBO(NoticeBO noticeBO) {
		this.noticeBO = noticeBO;
	}

	public Notice getNoticeRef() {
		return noticeRef;
	}

	public void setNoticeRef(Notice noticeRef) {
		this.noticeRef = noticeRef;
	}
}
