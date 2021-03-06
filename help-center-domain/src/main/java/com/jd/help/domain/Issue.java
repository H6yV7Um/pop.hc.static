package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Issue domain类
 * <p/>
 * generated by bud
 *
 * @author @laichendong
 */
@SuppressWarnings("unused")
public class Issue implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 类目ID
     */
    private Integer cataId;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 问题
     */
    private String name;

    /**
     * 状态
     * -1 删除 0-有效，下线 1-发布
     */
    private Integer status;


    /**
     * 排序字段
     */
    private Integer sortOrder;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 订单发货方式
     */
    private String orderShipment;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单付款方式
     */
    private String orderPay;

    /**
     * 问题摘要
     */
    private String summary;

    /**
     * 知识类型
     */
    private Integer issueType;

    //所属的标签的一级IDs，以空格分隔
    private String label1Ids;

    //所属的标签的一级名称s，以空格分隔
    private String label1Names;


    /**
     * 知识二级标签，可选择多个标签，搜索场景下使用
     */
    private String issueLabelId;

    /**
     * 知识二级标签文案，可选择多个标签，直接显示使用。
     */
    private String issueLabelContent;

    /**
     * 知识关键字
     */
    private String issueKeyWord;

    /**
     * 业务对接部门
     */
    private String issueSolveDept;

    /**
     * 业务对接人
     */
    private String issueSolvePin;

    /**
     * 知识到期时间
     * 只有在知识发布后，才有效；在知识到期后，知识状态从“已发布”变为“已下线”，即将status 由1变成0
     */
    private Date issueExpireTime;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCataId() {
        return this.cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return this.modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderShipment() {
        return orderShipment;
    }

    public void setOrderShipment(String orderShipment) {
        this.orderShipment = orderShipment;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(String orderPay) {
        this.orderPay = orderPay;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Date getIssueExpireTime() {
        return issueExpireTime;
    }

    public void setIssueExpireTime(Date issueExpireTime) {
        this.issueExpireTime = issueExpireTime;
    }

    public Integer getIssueType() {
        return issueType;
    }

    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }

    public String getIssueLabelId() {
        return issueLabelId;
    }

    public void setIssueLabelId(String issueLabelId) {
        this.issueLabelId = issueLabelId;
    }

    public String getIssueLabelContent() {
        return issueLabelContent;
    }

    public void setIssueLabelContent(String issueLabelContent) {
        this.issueLabelContent = issueLabelContent;
    }

    public String getIssueKeyWord() {
        return issueKeyWord;
    }

    public void setIssueKeyWord(String issueKeyWord) {
        this.issueKeyWord = issueKeyWord;
    }

    public String getIssueSolveDept() {
        return issueSolveDept;
    }

    public void setIssueSolveDept(String issueSolveDept) {
        this.issueSolveDept = issueSolveDept;
    }

    public String getIssueSolvePin() {
        return issueSolvePin;
    }

    public void setIssueSolvePin(String issueSolvePin) {
        this.issueSolvePin = issueSolvePin;
    }

    public String getLabel1Ids() {
        return label1Ids;
    }

    public void setLabel1Ids(String label1Ids) {
        this.label1Ids = label1Ids;
    }

    public String getLabel1Names() {
        return label1Names;
    }

    public void setLabel1Names(String label1Names) {
        this.label1Names = label1Names;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", cataId=" + cataId +
                ", siteId=" + siteId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", sortOrder=" + sortOrder +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderShipment='" + orderShipment + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderPay='" + orderPay + '\'' +
                ", summary='" + summary + '\'' +
                ", issueType=" + issueType +
                ", label1Ids='" + label1Ids + '\'' +
                ", label1Names='" + label1Names + '\'' +
                ", issueLabelId='" + issueLabelId + '\'' +
                ", issueLabelContent='" + issueLabelContent + '\'' +
                ", issueKeyWord='" + issueKeyWord + '\'' +
                ", issueSolveDept='" + issueSolveDept + '\'' +
                ", issueSolvePin='" + issueSolvePin + '\'' +
                ", issueExpireTime=" + issueExpireTime +
                '}';
    }
}