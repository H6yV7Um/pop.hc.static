package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lining7 on 2018/1/10.
 */
public class IssueQuery implements Serializable {
    /**
     * ����
     */
    private Integer id;

    /**
     * ������ĿID
     */
    private Integer cataId;

    /**
     * վ��ID
     */
    private Integer siteId;

    /**
     * ����
     */
    private String name;

    /**
     * ״̬
     * -1 ɾ�� 0-��Ч������ 1-����
     */
    private Integer status;

    /**
     * ������
     */
    private String creator;

    /**
     * �޸���
     */
    private String modifier;

    /**
     * ������ʼʱ��
     */
    private String createdBeginTime;

    /**
     * ��������ʱ��
     */
    private String createEndTime;

    /**
     * �޸Ŀ�ʼʱ��
     */
    private String modifiedBeginTime;
    /**
     * �޸Ľ���ʱ��
     */
    private String modifiedEndTime;

    /**
     * ����״̬
     */
    private String orderStatus;

    /**
     * ����������ʽ
     */
    private String orderShipment;

    /**
     * ��������
     */
    private String orderType;

    /**
     * �������ʽ
     */
    private String orderPay;

    /**
     * ����ժҪ
     */
    private String summary;

    /**
     * ֪ʶ����
     */
    private Integer issueType;

    /**
     * ֪ʶ��ǩ����ѡ������ǩ������������ʹ��
     */
    private String issueLabelId;

    /**
     * ֪ʶ��ǩ�İ�����ѡ������ǩ��ֱ����ʾʹ�á�
     */
    private String issueLabelContent;

    /**
     * ֪ʶ�ؼ���
     */
    private String issueKeyWord;

    /**
     * ҵ��ԽӲ���
     */
    private String issueSolveDept;

    /**
     * ҵ��Խ���
     */
    private String issueSolvePin;

    /**
     * ֪ʶ����ʱ��
     * ֻ����֪ʶ�����󣬲���Ч����֪ʶ���ں�֪ʶ״̬�ӡ��ѷ�������Ϊ�������ߡ�������status ��1���0
     */
    private Date issueExpireTime;

    /**
     * ֪ʶһ����ǩIDs
     */
    private String label1Ids;

    /**
     * ֪ʶһ����ǩNames
     */
    private String label1Names;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCataId() {
        return cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getCreatedBeginTime() {
        return createdBeginTime;
    }

    public void setCreatedBeginTime(String createdBeginTime) {
        this.createdBeginTime = createdBeginTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getModifiedBeginTime() {
        return modifiedBeginTime;
    }

    public void setModifiedBeginTime(String modifiedBeginTime) {
        this.modifiedBeginTime = modifiedBeginTime;
    }

    public String getModifiedEndTime() {
        return modifiedEndTime;
    }

    public void setModifiedEndTime(String modifiedEndTime) {
        this.modifiedEndTime = modifiedEndTime;
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

    public Date getIssueExpireTime() {
        return issueExpireTime;
    }

    public void setIssueExpireTime(Date issueExpireTime) {
        this.issueExpireTime = issueExpireTime;
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
        return "IssueQuery{" +
                "id=" + id +
                ", cataId=" + cataId +
                ", siteId=" + siteId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", createdBeginTime='" + createdBeginTime + '\'' +
                ", createEndTime='" + createEndTime + '\'' +
                ", modifiedBeginTime='" + modifiedBeginTime + '\'' +
                ", modifiedEndTime='" + modifiedEndTime + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderShipment='" + orderShipment + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderPay='" + orderPay + '\'' +
                ", summary='" + summary + '\'' +
                ", issueType=" + issueType +
                ", issueLabelId='" + issueLabelId + '\'' +
                ", issueLabelContent='" + issueLabelContent + '\'' +
                ", issueKeyWord='" + issueKeyWord + '\'' +
                ", issueSolveDept='" + issueSolveDept + '\'' +
                ", issueSolvePin='" + issueSolvePin + '\'' +
                ", issueExpireTime=" + issueExpireTime +
                ", label1Ids='" + label1Ids + '\'' +
                ", label1Names='" + label1Names + '\'' +
                '}';
    }
}
