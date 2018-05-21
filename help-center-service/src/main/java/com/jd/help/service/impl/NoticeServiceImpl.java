package com.jd.help.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jd.common.web.result.Result;
import com.jd.help.dao.NoticeDao;
import com.jd.help.domain.Notice;
import com.jd.help.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Resource
    private NoticeDao noticeDao;
	
	@Override
	public Result list(Notice notice, int page, int pageSize) {
		Result result = new Result();
        result.setSuccess(true);
       // List pageList = noticeDao.queryForList(notice, page, pageSize)
		//		new PaginatedArrayList<Notice>(page, pageSize);
       // pageList.setTotalItem(noticeDao.queryForCount(notice));
       // pageList.addAll(noticeDao.queryForList(notice, page, pageSize));
        result.addDefaultModel("noticeList", noticeDao.queryForList(notice, page, pageSize));
        return result;
	}

    @Override
    public Result listForAdmin(Notice notice, int page, int pageSize) {
        Result result = new Result();
        result.setSuccess(true);
        // List pageList = noticeDao.queryForList(notice, page, pageSize)
        //		new PaginatedArrayList<Notice>(page, pageSize);
        // pageList.setTotalItem(noticeDao.queryForCount(notice));
        // pageList.addAll(noticeDao.queryForList(notice, page, pageSize));
        result.addDefaultModel("noticeList", noticeDao.queryForListAdmin(notice, page, pageSize));
        return result;
    }

	@Override
	public Result detail(Notice notice, boolean needRemoveHttp) {
		Result result = new Result();
        if (notice == null) {
            return result;
        }
        result.setSuccess(true);
        notice = noticeDao.queryForObject(notice);
        if (notice == null) {
            return result;
        }
        if (needRemoveHttp) {
            notice.removeHttp();
        }
        result.addDefaultModel("notice", notice);

        return result;
	}

    public Result move(Notice notice,Notice noticeRef) {
        Result result = new Result();
        if (notice == null || noticeRef == null) {
            return result;
        }
        result.setSuccess(true);
        //Ωªªª≈≈–Ú÷µ
        notice = noticeDao.queryForObject(notice);
        int sortOrder = notice.getSortOrder();
        noticeRef = noticeDao.queryForObject(noticeRef);
        int sortOrderRef = noticeRef.getSortOrder();
        notice.setSortOrder(sortOrderRef);
        noticeRef.setSortOrder(sortOrder);

        noticeDao.updateSortOrder(notice);
        noticeDao.updateSortOrder(noticeRef);

        return result;
    }

	@Override
	public Result insert(Notice notice) {
		Result result = new Result();
        if (notice == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        Long id = noticeDao.insert(notice);
        if (id <= 0) {
            result.setSuccess(false);
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
	}

	@Override
	public Result update(Notice notice) {
		Result result = new Result();
        if (notice == null || notice.getId() == null ) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        boolean success = (noticeDao.update(notice) == 1);
        result.setSuccess(success);
        if(success){
            result.setResultCode("system.update.success");
        } else {
            result.setResultCode("system.update.success");
        }
        return result;
	}

	@Override
	public Result delete(Notice notice) {
		Result result = new Result();
        if (notice == null || notice.getId() == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        boolean success = (noticeDao.delete(notice) == 1);
        result.setSuccess(success);
        if(success){
            result.setResultCode("system.update.success");
        } else {
            result.setResultCode("system.update.success");
        }
        return result;
	}

    @Override
    public Result offline(Notice notice) {
        Result result = new Result();
        if (notice == null || notice.getId() == null ) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        notice.setStatus(0);
        boolean success = (noticeDao.updateStatus(notice) == 1);
        result.setSuccess(success);
        if(success){
            result.setResultCode("system.update.success");
        } else {
            result.setResultCode("system.update.success");
        }
        return result;
    }

    @Override
    public Result online(Notice notice) {
        Result result = new Result();
        if (notice == null || notice.getId() == null ) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        notice.setStatus(1);
        boolean success = (noticeDao.updateStatus(notice) == 1);
        result.setSuccess(success);
        if(success){
            result.setResultCode("system.update.success");
        } else {
            result.setResultCode("system.update.success");
        }
        return result;
    }
}
