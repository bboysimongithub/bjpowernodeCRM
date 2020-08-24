package com.tankangya.crm.vo;

import java.util.List;

public class PaginationVO<T> {

    private int total;

    private List<T> datalist;

    public PaginationVO() {

    }

    public PaginationVO(int total, List<T> datalist) {
        this.total = total;
        this.datalist = datalist;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<T> datalist) {
        this.datalist = datalist;
    }

    @Override
    public String toString() {
        return "PaginationVO{" +
                "total=" + total +
                ", datalist=" + datalist +
                '}';
    }
}
