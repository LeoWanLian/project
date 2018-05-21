<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工管理</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>

<!-- 引入bootstrapvalidator验证的内容 -->
<link rel="stylesheet"
	href="/assets/plugin/validator/css/bootstrapValidator.css" />
<!-- 核心验证的js,依赖于jQuery.js -->
<script type="text/javascript"
	src="/assets/plugin/validator/js/bootstrapValidator.js"></script>
<!-- 国际化js提示信息文件,必须在bootstrapValidator.js下面 -->
<script type="text/javascript"
	src="/assets/plugin/validator/js/language/zh_CN.js"></script>
<script src="/js/model/employee.js"></script>
</head>
<body>
<s:debug/>
	<s:fielderror />
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

				<li><a href="#">Forms</a></li>
				<li class="active">Form Elements</li>
			</ul>
			<!-- .breadcrumb -->

			<div class="nav-search" id="nav-search">
				<form class="form-search">
					<span class="input-icon"> <input type="text"
						placeholder="Search ..." class="nav-search-input"
						id="nav-search-input" autocomplete="off" /> <i
						class="icon-search nav-search-icon"></i>
					</span>
				</form>
			</div>
			<!-- #nav-search -->
		</div>

		<div class="page-content">
			<div class="page-header">
				<h1>
					<a href="/employee.action">员工列表</a> <small> <i
						class="icon-double-angle-right"></i> <s:if test="id==null">
							新增员工信息
						</s:if> <s:else>
							员工编辑修改
						</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<!-- 					<form class="form-horizontal" role="form"> -->
					<s:form id="employeeForm" action="employee_save"
						cssClass="form-horizontal" role="form">
						<s:hidden name="id" />
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<s:hidden name="baseQuery.username" />
						<s:hidden name="baseQuery.email" />
						<%-- 						<s:hidden id="id" name="password" /> --%>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 员工姓名 </label>
							<div class="col-sm-9">
								<s:textfield name="username" placeholder="员工姓名"
									class="col-xs-10 col-sm-5" id="form-field-1" />
								<!-- 验证错误信息 -->
								<span style="color: red" class="middle"><s:property
										value="fieldErrors['username'][0]" /></span>
							</div>
						</div>

						<s:if test="id==null">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-2"> 密码 </label>

								<div class="col-sm-9">
									<s:password name="password" showPassword="true"
										placeholder="密码" cssClass="col-xs-10 col-sm-5"
										id="form-field-2" />
								</div>
							</div>
						</s:if>

						<s:if test="id==null">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-2"> 确认密码 </label>

								<div class="col-sm-9">
									<s:password name="confirmPassword" showPassword="true"
										placeholder="密码" cssClass="col-xs-10 col-sm-5"
										id="form-field-2" />
								</div>
							</div>
						</s:if>


						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 年龄 </label>

							<div class="col-sm-9">
								<s:textfield name="age" placeholder="年龄"
									class="col-xs-10 col-sm-5" id="form-field-3" />

								<%--<s:property value="fieldErrors['employee.age'][0]" /> --%>
								<span style="color: red" class="middle"><s:property
										value="fieldErrors['age'][0]" /></span>

								<%--<s:fielderror style="color:red" fieldName="age" /> --%>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 邮箱地址 </label>

							<div class="col-sm-9">
								<s:textfield name="email" placeholder="邮箱地址"
									class="col-xs-10 col-sm-5" id="form-field-4" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 部门 </label>
							<div class="col-sm-9">
								<s:select list="#allDeps" name="department.id" listKey="id"
									listValue="name" headerKey="-1" headerValue="--请选择--"
									id="form-field-5"></s:select>
							</div>
						</div>
						
						<div class="space-4"></div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 角色</label>
							<div class="col-sm-9">
								<s:checkboxlist list="#allRoles" name="roles.id" listKey="id"
									listValue="name"  value="roles.{id}" id="form-field-5"/>
							</div>
						</div>
						
						<div class="space-4"></div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="submit">
									<i class="icon-ok bigger-110"></i> 添加
								</button>

								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="button" id="reseta">
									<i class="icon-undo bigger-110""></i> 重置
								</button>

								<!-- 								&nbsp; &nbsp; &nbsp; -->
								<!-- 								<button class="btn" type="button" onclick="clearA();"> -->
								<!-- 									<i class="icon-undo bigger-110""></i> 重置 -->
								<!-- 								</button> -->

								&nbsp; &nbsp; &nbsp;
								<button class="btn btn-info" type="button"
									onClick="location.href='/employee.action'">
									<i class="icon-undo bigger-110""></i> 取消
								</button>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
		<!-- PAGE CONTENT ENDS -->
</body>
</html>