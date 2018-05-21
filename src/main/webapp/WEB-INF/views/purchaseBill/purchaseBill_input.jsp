<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>purchaseBill管理</title>
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
<script src="/js/model/purchaseBill.js"></script>
<script src="/assets/plugin/My97DatePicker/WdatePicker.js"></script>
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
					<a href="/purchaseBill.action">purchaseBill列表</a> <small> <i
						class="icon-double-angle-right"></i> <s:if test="id==null">
							新增purchaseBill信息
						</s:if> <s:else>
							purchaseBill编辑修改
						</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<!-- 					<form class="form-horizontal" role="form"> -->
					<s:form id="purchaseBillForm" action="purchaseBill_save"
						cssClass="form-horizontal" role="form">
						<s:hidden name="id" />
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 交易时间 </label>
							<div class="col-sm-9">
								<s:date name="vdate" format="yyyy-MM-dd" var="edate" />
								<s:textfield name="vdate" value="%{edate}" class="Wdate"
									onClick="WdatePicker({maxDate:new Date()})" size="12"
									style="height: 25px;" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 供应商</label>

							<div class="col-sm-9">
								<s:select list="#allSuppliers" name="supplier.id" listKey="id"
									listValue="name" 
									id="form-field-5"></s:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 采购员</label>

							<div class="col-sm-9">
								<s:select list="#allBuyers" name="buyer.id" listKey="id"
									listValue="username" 
									id="form-field-5"></s:select>
							</div>
						</div>

						<div class="space-4"></div>
						<div class="row">
							<div class="col-xs-11">
								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>产品名称</th>
												<th>产品图片</th>
												<th>产品颜色</th>
												<th>采购价格</th>
												<th>采购数量</th>
												<th>小计</th>
												<th>备注</th>
												<th><button class="btn btn-sm addItem" type="button">
														<i class="icon-time bigger-110 hidden-480"></i> 添加
													</button></th>
											</tr>
										</thead>
										<tbody id="itemTbody">
											<s:if test="id==null">
											<tr>
												<td><s:hidden name="product.id"
														code="productId" /> <s:textfield code="productName"
														disabled="true" />
													<button type="button" class="btn serachProduct"
														code="serachProduct">
														<i class="glyphicon glyphicon-search"></i>
													</button></td>
												<td code="productPic"></td>
												<td code="productColor"></td>
												<td><s:textfield name="price"
														code="price" /></td>
												<td><s:textfield name="num" code="num" /></td>
												<td code="amount"></td>
												<td><s:textfield name="descs"
														code="descs" /></td>
												<td>
													<button class="btn btn-sm" type="button" code="deleteItem">
														<i class="icon-time bigger-110 hidden-480"></i>删除
													</button>
												</td>
											</tr>
											</s:if><s:else>
											<s:iterator value="items">
											<tr>
												<td><s:hidden name="product.id"
														code="productId" /> <s:textfield code="productName" value="%{product.name}"
														disabled="true" />
													<button type="button" class="btn serachProduct"
														code="serachProduct">
														<i class="glyphicon glyphicon-search"></i>
													</button></td>
												<td code="productPic"><img alt="150x150" src="${product.smallPic}" /></td>
												<td code="productColor"><span class="btn-colorpicker"
											style="background-color:${product.color};"></span></td>
												<td><s:textfield name="price"
														code="price" /></td>
												<td><s:textfield name="num" code="num" /></td>
												<td code="amount">${amount}</td>
												<td><s:textfield name="descs"
														code="descs" /></td>
												<td>
													<button class="btn btn-sm" type="button" code="deleteItem">
														<i class="icon-time bigger-110 hidden-480"></i>删除
													</button>
												</td>
											</tr>
											</s:iterator>
											</s:else>
										</tbody>
									</table>
									<!-- /.table-responsive -->
								</div>
								<!-- /span -->
							</div>
							<!-- /row -->

							<div class="space-4"></div>

							<div class="clearfix form-actions">
								<div class="col-md-offset-3 col-md-9">
									<button id="saveBtn" class="btn btn-info" type="button">
										<i class="icon-ok bigger-110"></i> 添加
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" id="reseta">
										<i class="icon-undo bigger-110""></i> 重置
									</button>

									<!--&nbsp; &nbsp; &nbsp; -->
									<!-- <button class="btn" type="button" onclick="clearA();"> -->
									<!--<i class="icon-undo bigger-110""></i> 重置 -->
									<!--</button> -->

									&nbsp; &nbsp; &nbsp;
									<button class="btn btn-info" type="button"
										onClick="location.href='/purchaseBill.action'">
										<i class="icon-undo bigger-110""></i> 取消
									</button>
								</div>
							</div>
					</s:form>
				</div>
			</div>
		</div>
		<!-- PAGE CONTENT ENDS -->
		<!-- 模态对话框 -->
		<div id="myModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">选择商品</h4>
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