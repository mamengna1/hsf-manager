package cn.hsf.hsfmanager.util;

import java.util.List;

/**
 * 分页
 */
public class Page {
    private Integer pageSize = 10;  //页面容量
    private Integer pageCurrentNo =1 ;  //当前页码
    private Integer totalCount;   //总数量
    private Integer totalPages;   //总页数
    private List<?> list;


    private Integer zaiXian;  //在线
    private Integer liXian;   //离线
    private Integer level;    //级别

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getZaiXian() {
        return zaiXian;
    }

    public void setZaiXian(Integer zaiXian) {
        this.zaiXian = zaiXian;
    }

    public Integer getLiXian() {
        return liXian;
    }

    public void setLiXian(Integer liXian) {
        this.liXian = liXian;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCurrentNo() {
        return pageCurrentNo;
    }

    public void setPageCurrentNo(Integer getCurrentNo) {
        this.pageCurrentNo = getCurrentNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        if (totalCount != null){
            if(totalCount%pageSize==0){
                this.totalPages=totalCount/pageSize;
            }else{
                this.totalPages=totalCount/pageSize+1;
            }
        }
        return this.totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }




    @Override
    public String toString() {
        return "Page{" +
                "pagesize=" + pageSize +
                ", pageCurrentNo=" + pageCurrentNo +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", list=" + list +
                '}';
    }
}