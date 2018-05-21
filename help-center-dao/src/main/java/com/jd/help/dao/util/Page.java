package com.jd.help.dao.util;

/**
 * @param <T>
 * @author wangwenyao, laichendong
 */
public class Page<T> {

    /**
     * ��һҳ��ҳ��
     */
    public static final int FIRST_PAGE_NUM = 1;
    /**
     * Ĭ��ҳ���С
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * ��ǰҳ����һҳ��1
     */
    private int page = FIRST_PAGE_NUM;

    /**
     * ÿҳ��С
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    private T model;

    public Page() {
    }

    public Page(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page <= 0) {
            this.page = FIRST_PAGE_NUM;
        } else {
            this.page = page;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }


    public int getStartRow() {
        return (page - 1) * pageSize;
    }

    public int getEndRow() {
        return page * pageSize - 1;
    }
}