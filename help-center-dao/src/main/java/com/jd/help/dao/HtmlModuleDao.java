package com.jd.help.dao;

import java.util.List;

import com.jd.help.domain.HtmlModule;

public interface HtmlModuleDao {
	/**
     * 插入HtmlModule对象到数据库
     *
     * @param HtmlModule 待插入的对象
     * @return 
     */
    long insert(HtmlModule htmlModule);

    /**
     * 从数据库删除HtmlModule对象
     * 软删除，只是update状态，不delete数据行
     *
     * @param HtmlModule 删除条件对象，通常，指定主键字段的值来删除一条记录，或指定其他字段删除一组记录。
     * @return 删除的行数
     */
    int delete(HtmlModule htmlModule);

    /**
     * 更新HtmlModule对象到数据库
     *
     * @param HtmlModule 待更新的对象，通常，需要更新那些字段就指定哪些字段的值，但主键字段一定需要指定。
     * @return 更新的行数
     */
    int update(HtmlModule htmlModule);

    /**
     * 从数据库查询一个HtmlModule对象
     *
     * @param HtmlModule 查询条件对象，通常指定主键字段的值
     * @return 有则返回对象，无则返回null
     */
    HtmlModule queryForObject(HtmlModule htmlModule);

    /**
     * 根据条件查询满足条件的HtmlModule对象个数
     *
     * @return 记录数
     */
    //int queryForCount(HtmlModule htmlModule);

    /**
     * 根据条件查询满足条件的htmlModule对象列表
     *
     * @return 满足条件的htmlModule对象列表，没有满足条件的htmlModule对象，则返回null
     */
    List<HtmlModule> queryForList(HtmlModule htmlModule, int page, int pageSize);

    List<HtmlModule> findByKeies(String[] moduleNames,int siteId);
}
