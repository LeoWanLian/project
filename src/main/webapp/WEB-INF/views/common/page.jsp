<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="col-sm-6">
		<div class="dataTables_info" id="sample-table-2_info">

			显示 ${pageList.begin} 到 <span id="end">${pageList.end }</span> &nbsp;&nbsp;&nbsp; 总共<span id="totalCount">${pageList.totalCount}</span> 
			条&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; 每页显示
			<s:select list="{5,10,20}" name="baseQuery.pageSize"
				onchange='$("#domainForm").submit();' />
			条

			<!--s:textfield默认是使用name进行数据的提交和回显的，如果添加了value属性，回显就使用value -->
			<!--直接使用对象栈pageList.begin，map栈#pageList.begin，ognl表达式%{pageList.begin} -->

			&nbsp; &nbsp; &nbsp;去到第
			<s:textfield name="baseQuery.currentPage"
				value="%{pageList.currentPage}" id="pageNo" size="1"
				onkeyup="back();" />
			页
			<button class="btn btn-warning btn-xs" type="submit">
				<i class="icon-fighter-jet   bigger-110 icon-only"></i>
			</button>
		</div>

	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_bootstrap">
			<ul class="pagination">${pageList.page}
			</ul>
		</div>
	</div>
</div>
