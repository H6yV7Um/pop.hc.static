package com.jd.help.center.domain.search;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: ÏÂÎç3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultPage implements Serializable {
    private long PageCount;
    private long PageIndex;
    private long PageSize;

    public long getPageCount() {
        return PageCount;
    }

    public void setPageCount(long pageCount) {
        PageCount = pageCount;
    }

    public long getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(long pageIndex) {
        PageIndex = pageIndex;
    }

    public long getPageSize() {
        return PageSize;
    }

    public void setPageSize(long pageSize) {
        PageSize = pageSize;
    }
}
