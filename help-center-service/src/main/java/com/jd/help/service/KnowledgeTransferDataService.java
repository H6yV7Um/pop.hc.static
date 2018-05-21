package com.jd.help.service;

/**
 * Created by yfxialiang on 2018/3/30.
 * 将帮助中心老表数据、规则中心数据、商家学习中心数据导入到knowledge表中
 */
public interface KnowledgeTransferDataService {


    /**
     * 将商家培训中心数据导入knowledge
     */
    public void transferXueData();

    /**
     * 将规则数据导入knowledge
     */
    public void transferSceneData();

    /**
     * 将帮助中心老表数据导入knowledge
     */
    public void transferIssueData();

    /**
     * 删除knowledge和knowledge_content表的全部数据
     */
    public void delAllData();
}
