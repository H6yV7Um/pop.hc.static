package com.jd.help.dao;

import java.util.List;

import com.jd.help.domain.Notice;
import com.jd.help.domain.NoticeBO;

public interface NoticeDao {
	/**
     * 插入Notice对象到数据库
     *
     * @param Notice 待插入的对象
     * @return 
     */
    long insert(Notice notice);

    /**
     * 从数据库删除Notice对象
     * 软删除，只是update状态，不delete数据行
     *
     * @param Notice 删除条件对象，通常，指定主键字段的值来删除一条记录，或指定其他字段删除一组记录。
     * @return 删除的行数
     */
    int delete(Notice notice);

    /**
     * 更新Notice对象到数据库
     *
     * @param HtmlModule 待更新的对象，通常，需要更新那些字段就指定哪些字段的值，但主键字段一定需要指定。
     * @return 更新的行数
     */
    int update(Notice notice);

    /**
     * 从数据库查询一个Notice对象
     *
     * @param Notice 查询条件对象，通常指定主键字段的值
     * @return 有则返回对象，无则返回null
     */
    Notice queryForObject(Notice notice);

    /**
     * 根据条件查询满足条件的Notice对象个数
     *
     * @return 记录数
     */
    //int queryForCount(Notice notice);

    /**
     * 根据条件查询满足条件的Notice对象列表
     *
     * @return 满足条件的Notice对象列表，没有满足条件的Notice对象，则返回null
     */
    List<Notice> queryForList(Notice notice, int page, int pageSize);

    /**
     * 后台查询列表
     * @param notice
     * @param page
     * @param pageSize
     * @return
     */
    public List<NoticeBO> queryForListAdmin(Notice notice, int page,int pageSize);

    /**
     * 更新排序字段
     * @param notice
     * @return
     */
    public int updateSortOrder(Notice notice);

    /**
     * 更新状态
     * @param notice
     * @return
     */
    public int updateStatus(Notice notice);

}
