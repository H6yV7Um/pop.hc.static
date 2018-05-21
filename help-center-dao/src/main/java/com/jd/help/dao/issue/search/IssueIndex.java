package com.jd.help.dao.issue.search;

import com.jd.help.domain.Issue;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题索引bean
 *
 * @author laichendong
 * @since 2014/12/9 9:41
 */
public class IssueIndex implements Serializable {
    @Field("id")
    private Integer id;
    @Field("siteId")
    private Integer siteId;
    /**
     * 类目id  包含 1 2 3级  类目  空格分开
     */
    @Field("cataId")
    private String cataId;
    @Field("name")
    private String name;
    @Field("status")
    private Integer status;
    @Field("modified")
    private Date modified;
    @Field("sortOrder")
    private Integer sortOrder;
    @Field("orderStatus")
    private String orderStatus;
    @Field("orderShipment")
    private String orderShipment;
    @Field("orderType")
    private String orderType;
    @Field("orderPay")
    private String orderPay;
    @Field("summary")
    private String summary;
    @Field("answer")
    private String answer;
    @Field("creator")
    private String creator;
    @Field("suggestName")
    private String suggestName;

    public IssueIndex() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCataId() {
        return cataId;
    }

    public void setCataId(String cataId) {
        this.cataId = cataId;
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSuggestName() {
        return suggestName;
    }

    public void setSuggestName(String suggestName) {
        this.suggestName = suggestName;
    }

    public Issue toIssue() {
        Issue issue = new Issue();
        issue.setId(this.getId());
        String cataIdInSolr = this.getCataId();
        if (StringUtils.isNotBlank(cataIdInSolr)) {
            String[] cataIds = cataIdInSolr.split(" ");
            Integer cataId = null;
            try {
                cataId = Integer.valueOf(cataIds[cataIds.length - 1]);
            } catch (NumberFormatException e) {

            }
            issue.setCataId(cataId);
        }
        issue.setName(this.getName());
        issue.setStatus(this.getStatus());
        issue.setModified(this.getModified());
        issue.setSummary(this.getSummary());
        issue.setOrderPay(this.getOrderPay());
        issue.setOrderShipment(this.getOrderShipment());
        issue.setOrderStatus(this.getOrderStatus());
        issue.setOrderType(this.getOrderType());
        issue.setSiteId(this.getSiteId());
        issue.setCreator(this.getCreator());
        return issue;
    }

    @Override
    public String toString() {
        return "IssueIndex{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", cataId='" + cataId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", modified=" + modified +
                ", sortOrder=" + sortOrder +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderShipment='" + orderShipment + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderPay='" + orderPay + '\'' +
                ", summary='" + summary + '\'' +
                ", answer='" + answer + '\'' +
                ", creator='" + creator + '\'' +
                ", suggestName='" + suggestName + '\'' +
                '}';
    }
}
