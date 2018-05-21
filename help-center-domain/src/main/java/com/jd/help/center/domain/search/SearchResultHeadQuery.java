package com.jd.help.center.domain.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: ÏÂÎç3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultHeadQuery implements Serializable {
    private String Category;
    private String CategoryLevel;
    private  long Cost;
    private String SortType;
    private String Time;
    private ArrayList<SearchResultHeadQueryFiltType> FiltType;
    private ArrayList<SearchResultHeadQueryItem> items;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategoryLevel() {
        return CategoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        CategoryLevel = categoryLevel;
    }

    public long getCost() {
        return Cost;
    }

    public void setCost(long cost) {
        Cost = cost;
    }

    public String getSortType() {
        return SortType;
    }

    public void setSortType(String sortType) {
        SortType = sortType;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public ArrayList<SearchResultHeadQueryFiltType> getFiltType() {
        return FiltType;
    }

    public void setFiltType(ArrayList<SearchResultHeadQueryFiltType> filtType) {
        FiltType = filtType;
    }

    public ArrayList<SearchResultHeadQueryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<SearchResultHeadQueryItem> items) {
        this.items = items;
    }
}
