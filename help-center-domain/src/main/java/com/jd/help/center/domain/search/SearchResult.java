package com.jd.help.center.domain.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: ÏÂÎç3:50
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {
    private SearchResultHead Head;
    private Object ObjCollection;
    private Object ObjExtAttrCollection;
    private ArrayList<SearchResultParagraph> Paragraph;

    public SearchResultHead getHead() {
        return Head;
    }

    public void setHead(SearchResultHead head) {
        Head = head;
    }

    public Object getObjCollection() {
        return ObjCollection;
    }

    public void setObjCollection(Object objCollection) {
        ObjCollection = objCollection;
    }

    public Object getObjExtAttrCollection() {
        return ObjExtAttrCollection;
    }

    public void setObjExtAttrCollection(Object objExtAttrCollection) {
        ObjExtAttrCollection = objExtAttrCollection;
    }

    public ArrayList<SearchResultParagraph> getParagraph() {
        return Paragraph;
    }

    public void setParagraph(ArrayList<SearchResultParagraph> paragraph) {
        Paragraph = paragraph;
    }
}
