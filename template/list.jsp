<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${entityDomain}管理</title>
</head>
<body>
	<s:fielderror />
	<s:form action="${lowerEntityDomain}" id="domainForm">
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>
				<ul class="breadcrumb">
					<li><i class="icon-home home-icon"></i> <a href="#">Home</a></li>

					<li><a href="#">Tables</a></li>
					<li class="active">Simple &amp; Dynamic</li>
				</ul>
				<!-- .breadcrumb -->
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<h4 class="header smaller lighter blue">${entityDomain}列表</h4>
						<!-- 高级查询 begin -->
						名称：
						<s:textfield name="baseQuery.name" placeholder="输入名称" />
						<button class="btn btn-danger btn-sm" type="button"
							onclick="goPage(1)">
							<i class="icon-search"></i>
						</button>

						<!-- 高级查询 end -->

						<span style="text-align: right; display: block;"> <a
							class="green" href="/${lowerEntityDomain}_input.action"> <i
								class="icon-edit bigger-230"></i> <span
								class="badge badge-warning badge-left">新增用户</span></a>
						</span>
						<!-- <i class="icon-edit bigger-230"></i> -->
						<!-- 新增用户 -->
						<%-- <span class="badge badge-warning badge-left"></span> --%>

						<div class="table-responsive">
							<table id="sample-table-2"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center"><label> <input type="checkbox"
												class="ace" /> <span class="lbl"></span>
										</label></th>
										<th>名称</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody id="itemTbody">
									<s:iterator value="pageList.data">
										<s:if test="id==#parameters.id[0]">
											<tr style="color: red">
										</s:if>
										<s:else>
											<tr>
										</s:else>
										<td class="center"><input type="checkbox" name="ids"
											value="${id}"></td>
										<td>${name}</td>
 
										<td>
											<div
												class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
												<a class="blue" href="#"> <i
													class="icon-zoom-in bigger-130"></i>
												</a> <a class="green" href="#" onclick="updateDomain('${lowerEntityDomain}',${id});"> <i
													class="icon-pencil bigger-130"></i>
												</a> <a class="red" href="#" onclick="deleteDomain('/${lowerEntityDomain}_delete.action?id=${id}',this);">
													<i class="icon-trash bigger-130"></i>
												</a>
											</div>

										</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<%@include file="/WEB-INF/views/common/page.jsp"%>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</s:form>
	<!-- 模态对话框 -->
	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">错误信息</h4>
				</div>
				<div class="modal-body" style="color: red;"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>