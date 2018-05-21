package com.jd.help.center.domain.search;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: ÏÂÎç3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultHead implements Serializable {
    private SearchResultHeadError Error;
    private SearchResultHeadQuery Query;
    private SearchResultHeadSummary Summary;

    public SearchResultHeadError getError() {
        return Error;
    }

    public void setError(SearchResultHeadError error) {
        Error = error;
    }

    public SearchResultHeadQuery getQuery() {
        return Query;
    }

    public void setQuery(SearchResultHeadQuery query) {
        Query = query;
    }

    public SearchResultHeadSummary getSummary() {
        return Summary;
    }

    public void setSummary(SearchResultHeadSummary summary) {
        Summary = summary;
    }
}
