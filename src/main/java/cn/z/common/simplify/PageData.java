package cn.z.common.simplify;

import java.util.List;

public class PageData {
  private int pageNum;  //页号
  private int pageSize;  //页大小
  private Long count;  //总记录数
  
  private List<? extends Object> data;  //查询sql的结果

  public static PageData build(int pageNum, int pageSize, Long count, List<? extends Object> data) {
    return new PageData(pageNum, pageSize, count, data);
  }

  public PageData(int pageNum, int pageSize, Long count, List<? extends Object> data) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.count = count;
    this.data = data;
  }

  public PageData(int pageNum, int pageSize) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  public PageData() {
  }

  @Override
  public String toString() {
    return "PageData{" +
            "pageNum=" + pageNum +
            ", pageSize=" + pageSize +
            ", count=" + count +
            ", data=" + data +
            '}';
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public List<? extends Object> getData() {
    return data;
  }

  public void setData(List<? extends Object> data) {
    this.data = data;
  }
}
