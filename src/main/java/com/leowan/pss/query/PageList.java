package com.leowan.pss.query;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.json.annotations.JSON;

/**
 * 封装查询后的结果
 */
public class PageList<T> {
	// 当前页码
	private int currentPage;
	// 一页的条数
	private int pageSize;
	// 记录总数
	private int totalCount;
	// 总页数 = 总记录数/一页的条数
	private int totalPage;
	// 当前页码的数据
	private List<T> data;

	public PageList() {
	}

	public PageList(int currentPage, int pageSize, int totalCount) {
		// 1.判断传入参数不能为负数
		this.currentPage = currentPage < 1 ? 1 : currentPage;
		this.pageSize = pageSize < 1 ? 1 : pageSize;

		this.totalCount = totalCount;
		// 2.计算总的页数
		this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize
				: this.totalCount / this.pageSize + 1;
		// 3.判断当前页码不能大于总的页数
		this.currentPage = currentPage > this.totalPage ? this.totalPage : currentPage;
	}
	@JSON(serialize = false)
	public int getBegin() {
		return (currentPage - 1) * pageSize + 1;
	}
	@JSON(serialize = false)
	public int getEnd() {
		int end = currentPage * pageSize;
		return end > totalCount ? totalCount : end;
	}
	@JSON(serialize = false)
	public String getPage() {
		 if (this.totalCount <= this.pageSize) {
			    return "";
			  }
			StringBuilder builder = new StringBuilder();
			if (currentPage == 1) {
				builder.append("<li class='prev disabled'><a href='#'>首页</a></li>");
				builder.append("<li class='prev disabled'><a href='#'>上一页</a></li>");
			} else {
				builder.append("<li class='prev'><a href='#' onclick='goPage(1);'>首页</a></li>");
				builder.append("<li class='prev'><a href='#' onclick='goPage(" + (currentPage - 1) + ");'>上一页</a></li>");
			}

			for (int i = 1; i <= totalPage; i++) {
				if (this.currentPage == i) {
					builder.append("<li class='prev disabled'><li class='active'><a href='#'>" + i + "</a></li></li>");
				} else {
					builder.append("<li class='prev'><a href='#' onclick='goPage(" + i + ");'>" + i + "</a></li>");
				}
			}

			if (currentPage == totalPage) {
				builder.append("<li class='next disabled'><a href='#'>下一页</a></li>");
				builder.append("<li class='next disabled'><a href='#'>末页</a></li>");
			} else {
				builder.append("<li class='prev'><a href='#' onclick='goPage(" + (currentPage + 1) + ");'>下一页</a></li>");
				builder.append("<li class='prev'><a href='#' onclick='goPage(" + totalPage + ");'>末页</a></li>");
			}

			return builder.toString();
	}

	public int getPageCount() {
		return totalCount;
	}

	public void setPageCount(int pageCount) {
		this.totalCount = pageCount;
	}
	@JSON(serialize = false)
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	@JSON(serialize = false) 
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	@JSON(serialize = false)
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@JSON(name = "rows")  
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@JSON(name = "total")
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "PageList [currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", data=" + data.size() + "]";
	}

}
