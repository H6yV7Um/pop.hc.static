package com.jd.help.center.domain.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: ÏÂÎç3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultHeadSummary implements Serializable {
    private String ClassInfo;
    private ArrayList<HashMap> NoReasultReason;
    private long OriginalResultCount;
    private SearchResultPage Page;
    private long ResultCount;

    public String getClassInfo() {
        return ClassInfo;
    }

    public void setClassInfo(String classInfo) {
        ClassInfo = classInfo;
    }

    public ArrayList<HashMap> getNoReasultReason() {
        return NoReasultReason;
    }

    public void setNoReasultReason(ArrayList<HashMap> noReasultReason) {
        NoReasultReason = noReasultReason;
    }

    public long getOriginalResultCount() {
        return OriginalResultCount;
    }

    public void setOriginalResultCount(long originalResultCount) {
        OriginalResultCount = originalResultCount;
    }

    public SearchResultPage getPage() {
        return Page;
    }

    public void setPage(SearchResultPage page) {
        Page = page;
    }

    public long getResultCount() {
        return ResultCount;
    }

    public void setResultCount(long resultCount) {
        ResultCount = resultCount;
    }
}
