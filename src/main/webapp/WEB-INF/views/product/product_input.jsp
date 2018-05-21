<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>product管理</title>
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
<script src="/js/model/product.js"></script>

<script src="assets/js/ace-elements.min.js"></script>
<script>
	$(function() {
		//处理颜色选择框
		$('#simple-colorpicker-1').ace_colorpicker();
	});

	//处理图片上传控件
	$('#id-input-file-2').ace_file_input({
		no_file : '选择图片 ...',
		btn_choose : '选择',
		btn_change : '改变',
		droppable : false,
		onchange : null,
		thumbnail : false
	});
	
	$("#parent").bind("change",function() {
		$("#children").empty();
		var pid = $(this).val();
		if (pid == -1) {
			$("#children").html("<option value='-1'>--请选择--</option>");
		} else {
			// 先从缓存取
			var cacheData = $("#children").data(pid);
			if (cacheData) {
				for (var i = 0; i < cacheData.length; i++) {
					$("#children").append(
							"<option value='" + cacheData[i].id + "'>"
									+ cacheData[i].name + "</option>");
				}
			} else {// 从后台取
				$.get("product_findChildren.action", {
					id : pid
				}, function(data) {
					// alert(data.length);
					$("#children").data(pid, data)// 放入缓存
					for (var i = 0; i < data.length; i++) {
						$("#children").append(
								"<option value='" + data[i].id + "'>"
										+ data[i].name + "</option>");
					}
				});
			}
		}
	});
</script>
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
					<a href="/product.action">product列表</a> <small> <i
						class="icon-double-angle-right"></i> <s:if test="id==null">
							新增product信息
						</s:if> <s:else>
							product编辑修改
						</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<!-- 					<form class="form-horizontal" role="form"> -->
					<s:form id="productForm" action="product_save"
						enctype="multipart/form-data" cssClass="form-horizontal"
						role="form">
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

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">颜色</label>
							<div class="col-sm-9">
								<s:select id="simple-colorpicker-1" name="color"
									list="{'#ac725e','#d06b64','#f83a22','#fa573c','#ff7537','#ffad46','#42d692','#16a765','#7bd148','#b3dc6c','#fbe983','#fad165','#92e1c0','#9fe1e7','#9fc6e7','#4986e7','#9a9cff','#b99aff','#c2c2c2','#cabdbf','#cca6ac','#f691b2','#cd74e6','#a47ae2','#555'}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 成本价格 </label>
							<div class="col-sm-9">
								<s:textfield name="costPrice" placeholder="成本价格"
									class="col-xs-10 col-sm-5" id="form-field-1" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 销售价格 </label>
							<div class="col-sm-9">
								<s:textfield name="salePrice" placeholder="销售价格"
									class="col-xs-10 col-sm-5" id="form-field-1" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">品牌</label>
							<div class="col-sm-9">
								<s:select list="#allBrands" name="brand.id" listKey="id"
									listValue="name" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">单位</label>
							<div class="col-sm-9">
								<s:select list="#allUnits" name="unit.id" listKey="id"
									listValue="name" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">产品类型</label>
							<div class="col-sm-9">
								<s:select id="parent" list="#allParents" name="types.parent.id"
									headerKey="-1" headerValue="--请选择--" listValue="name"
									listKey="id" />
								<s:select id="children" list="#allChildrens" name="types.id"
									listValue="name" listKey="id" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">图片</label>
							<div class="col-sm-9" style="width: 35%">
								<input id="id-input-file-2" name="upload" type="file"> <label
									class="file-label" data-title="Choose"> <span
									class="file-name" data-title="No File ..."> </span>
								</label>
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
									onClick="location.href='/product.action'">
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