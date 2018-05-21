<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product管理</title>
</head>
<body>
	<s:fielderror />
	<s:form action="product" id="domainForm">
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>
				<ul class="breadcrumb">
					<li><i class="icon-home home-icon"></i> <a href="/main.action">主页</a></li>

					<li><a href="/main.action">采购订单列表</a></li>
					<li class="active">产品列表</li>
				</ul>
				<!-- .breadcrumb -->
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<!-- 高级查询 begin -->
						名称：
						<s:textfield name="baseQuery.name" placeholder="输入名称" />
						<button class="btn btn-danger btn-sm" type="button"
							onclick="goPage(1)">
							<i class="icon-search"></i>
						</button>

						<!-- 高级查询 end -->
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
										<th>颜色</th>
										<th>缩略图</th>
										<th>成本价格</th>
										<th>产品类型</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
									<s:iterator value="pageList.data">
										<td>${id}</td>
										<td>${name}</td>
										<td><span class="btn-colorpicker"
											style="background-color:${color};"></span></td>
										<td><s:if test="smallPic!=null">
													<img alt="150x150" src="${smallPic}" />
												</s:if></td>
										<td>${costPrice}</td>
										<td>${types.name}</td>
										<td>
											<button type="button" class="btn btn-info choose">
												<i class="icon-ok bigger-110"></i> 选择
											</button>
										</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
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