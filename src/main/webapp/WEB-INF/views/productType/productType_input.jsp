<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>productType管理</title>
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
<script src="/js/model/productType.js"></script>
</head>
<body>
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
					<a href="/productType.action">productType列表</a> <small> <i
						class="icon-double-angle-right"></i> <s:if test="id==null">
							新增productType信息
						</s:if> <s:else>
							productType编辑修改
						</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<!-- 					<form class="form-horizontal" role="form"> -->
					<s:form id="productTypeForm" action="productType_save"
						cssClass="form-horizontal" role="form">
						<s:hidden name="id" />
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 名称 </label>
							<div class="col-sm-9">
								<s:textfield name="name" placeholder="名称"
									class="col-xs-10 col-sm-5" id="form-field-1" />
								<!-- 验证错误信息 -->
								<span style="color: red" class="middle"><s:property
										value="fieldErrors['name'][0]" /></span>
							</div>
						</div>

<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-3 control-label no-padding-right" -->
<!-- 								for="form-field-1"> 部门 </label> -->
								
<!-- 							<div class="col-sm-9"> -->
<%-- 								<s:select list="#allDeps" name="department.id" listKey="id" --%>
<%-- 									listValue="name" headerKey="-1" headerValue="--请选择--" --%>
<%-- 									id="form-field-5"></s:select> --%>
<!-- 							</div> -->
<!-- 						</div> -->

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
									onClick="location.href='/productType.action'">
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