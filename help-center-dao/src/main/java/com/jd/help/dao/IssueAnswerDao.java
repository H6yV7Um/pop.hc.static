package com.jd.help.dao;

import com.jd.help.domain.IssueAnswer;

import java.util.List;

/**
 * dao 接口
 * generated by bud
 *
 * @author @laichendong
 */
public interface IssueAnswerDao {

    /**
     * 插入issueAnswer对象到数据库
     *
     * @param issueAnswer 待插入的对象
     * @return 是否插入成功
     */
    void insert(IssueAnswer issueAnswer);

    /**
     * 从数据库删除issueAnswer对象
     * 软删除，只是update状态，不delete数据行
     *
     * @param issueAnswer 删除条件对象，通常，指定主键字段的值来删除一条记录，或指定其他字段删除一组记录。
     * @return 删除的行数
     */
    int delete(IssueAnswer issueAnswer);

    /**
     * 更新issueAnswer对象到数据库
     *
     * @param issueAnswer 待更新的对象，通常，需要更新那些字段就指定哪些字段的值，但主键字段一定需要指定。
     * @return 更新的行数
     */
    int update(IssueAnswer issueAnswer);

    /**
     * 从数据库查询一个issueAnswer对象
     *
     * @param issueAnswer 查询条件对象，通常指定主键字段的值
     * @return 有则返回对象，无则返回null
     */
    IssueAnswer queryForObject(IssueAnswer issueAnswer);

    /**
     * 根据条件查询满足条件的issueAnswer对象个数
     *
     * @return 记录数
     */
    int queryForCount(IssueAnswer issueAnswer);

    /**
     * 根据条件查询满足条件的issueAnswer对象列表
     *
     * @return 满足条件的issueAnswer对象列表，没有满足条件的issueAnswer对象，则返回null
     */
    List<IssueAnswer> queryForList(IssueAnswer issueAnswer, int page, int pageSize);
    
    /**
     * 通过IssueId查询
     * @param issueId
     * @return
     */
    IssueAnswer queryOneByIssueId(int issueId);
}
