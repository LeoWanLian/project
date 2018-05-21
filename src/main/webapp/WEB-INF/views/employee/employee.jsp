<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/views/common/head.jsp"%>

<!-- 滑块 -->
<link rel="stylesheet" type="text/css"
	href="/assets/plugin/slider/css/bootstrap-slider.min.css">
<script type="text/javascript"
	src="/assets/plugin/slider/js/bootstrap-slider.min.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工管理</title>

<script type="text/javascript">
$(function(){
	  $("#ex2").slider({
		  min: 18,
	      max: 130
	  });
});
</script>
</head>
<body>
	<s:fielderror />
	<s:form action="employee" id="domainForm">
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
						<h4 class="header smaller lighter blue">员工列表</h4>
						<!-- 高级查询 begin -->
						用户名：
						<s:textfield name="baseQuery.username" placeholder="输入用户名" />
						邮箱：
						<s:textfield name="baseQuery.email" placeholder="输入邮箱" />
						部门
						<s:select list="#allDeps" name="baseQuery.deptId" listKey="id"
							listValue="name" headerKey="-1" headerValue="--请选择--"></s:select>
						年龄 <b>${baseQuery.ages[0]}</b>&nbsp;&nbsp;
						<s:textfield id="ex2" name="baseQuery.age" cssClass="span2"
							data-slider-min="18" data-slider-max="130" data-slider-step="1"
							data-slider-value="[%{baseQuery.ages[0]},%{baseQuery.ages[1]}]" />
						&nbsp;&nbsp;<b>到${baseQuery.ages[1]}</b>


						<button class="btn btn-danger btn-sm" type="button"
							onclick="goPage(1)">
							<i class="icon-search"></i>
						</button>

						<a href="#" onclick="downloadDomain('employee');" class="green">
							<button class="btn btn-info icon-download-alt  bigger-100" type="button">导出列表</button>
						</a> <a href="/import.action" class="red"> <i
							class=" icon-download-alt  bigger-130">导入列表</i>
						</a>
						<!-- 高级查询 end -->



						<span style="text-align: right; display: block;"> <a
							class="green" href="/employee_input.action"> <i
								class="icon-edit bigger-230"></i> <span
								class="badge badge-warning badge-left">新增用户</span></a>

						</span>

						<div>
							<!-- 							<input type="checkbox" onchange="checkChange(this)" />全选/全不选<br /> -->
							<!-- 							<input type="button" id="checkAll" value="全选" />  -->
							<!-- 							<input type="button" id="checkNotAll" value="全不选" />  -->
							<input type="button" id="checkUnAll" class="btn btn-info"
								value="反选" /> <span> <a class="green" href="#"
								onclick="deleteAll('employee');"> <i
									class="icon-trash bigger-200"></i> <span
									class="badge badge-warning badge-left">批量删除</span></a>

							</span>
						</div>



						<!-- <i class="icon-edit bigger-230"></i> -->
						<!-- 新增用户 -->
						<%-- <span class="badge badge-warning badge-left"></span> --%>

						<div class="table-responsive">
							<table id="sample-table-2"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center"><label> <input type="checkbox"
												name="del" class="ace" onchange="checkChange(this)" />全选/全不选
												<span class="lbl"></span>
										</label></th>
										<th>用户名</th>
										<th>密码</th>
										<th>邮箱</th>
										<th>年龄</th>
										<th>部门</th>
										<th>头像</th>
										<th>拥有权限</th>
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
										<td>${username}</td>
										<td>${password}</td>
										<td>${email}</td>
										<td>${age}</td>
										<!-- 年龄 -->
										<td>${department.name}</td>
										<!-- 部门 -->
										<td><img alt="没有头像" src="${headImage}"></td>

										<td>
											<!--去掉空的[]括号  --> <%-- <s:if test="roles.size()>0"> --%> <%-- 	${roles}   --%>
											<%-- </s:if> --%> <!-- 去掉[]和, --> <s:iterator value="roles"
												status="sta">
												<s:if test="#sta.last">
													${name}
												</s:if>
												<s:else>
										 			${name},
										 		</s:else>
											</s:iterator>
										</td>

										<td>
											<div
												class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
												<a class="blue" href="#"> <i
													class="icon-zoom-in bigger-130"></i>
												</a> <a class="green" href="#"
													onclick="updateDomain('employee',${id});"> <i
													class="icon-pencil bigger-130"></i>
												</a> <a class="red" href="#"
													onclick="deleteDomain('/employee_delete.action?id=${id}',this);">
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