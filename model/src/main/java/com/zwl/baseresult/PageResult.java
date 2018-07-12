package com.zwl.baseresult;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageResult<T> {
    private Integer currentPage;
    // 每页显示的总条数
    private Integer pageSize;
    // 总条数
    private Long totalNum;
    // 总页数
    private Integer totalPage;
    // 分页结果
    private List<T> items;

    public PageResult(List<T> items) {
        PageInfo pageInfo = new PageInfo(items);
        this.items = pageInfo.getList();
        this.pageSize = pageInfo.getPageSize();
        this.currentPage = pageInfo.getPageNum();
        this.totalNum = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
