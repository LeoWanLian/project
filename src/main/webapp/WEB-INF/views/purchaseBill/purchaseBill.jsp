<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PurchaseBill管理</title>
<script src="/assets/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="/js/model/purchaseBill.js"></script>
</head>
<body>
	<s:fielderror />
	<s:form action="purchaseBill" id="domainForm">
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
						<h4 class="header smaller lighter blue">PurchaseBill列表</h4>
						<!-- 高级查询 begin -->
						交易时间从
						<s:date name="baseQuery.beginDate" format="yyyy-MM-dd" var="bdate" />
						<s:date name="baseQuery.endDate" format="yyyy-MM-dd" var="edate" />

						<s:textfield name="baseQuery.beginDate" value="%{bdate}"
							class="Wdate" onClick="WdatePicker({maxDate:new Date()})"
							size="12" style="height: 25px;" />
						到

						<s:textfield name="baseQuery.endDate" value="%{edate}"
							class="Wdate" onClick="WdatePicker({maxDate:new Date()})"
							size="12" style="height: 25px;" />

						审核状态：
						<s:select list="#{-2:'--请选择--',0:'待审',1:'已审',-1:'作废'}"
							name="baseQuery.status" id="form-field-5"></s:select>

						<button class="btn btn-danger btn-sm" type="button"
							onclick="goPage(1)">
							<i class="icon-search"></i>
						</button>

						<!-- 高级查询 end -->

						<span style="text-align: right; display: block;"> <a
							class="green" href="/purchaseBill_input.action"> <i
								class="icon-edit bigger-230"></i> <span
								class="badge badge-warning badge-left">新增订单</span></a>
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
										<th>总金额</th>
										<th>交易时间</th>
										<th>供应商</th>
										<th>状态</th>
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
										<td>${totalAmount}</td>
										<td>${vdate}</td>
										<td>${supplier.name}</td>
										<td code="audit"><s:if test="status==1">
												<span class="label label-success arrowed" cd="flagg">已审</span>
											</s:if> <s:elseif test="status==0">
												<span class="label label-warning"> <i
													class="icon-warning-sign bigger-120"></i> 待审
												</span>
											</s:elseif> <s:else>
												<span class="label">作废</span>
											</s:else></td>
										<td><s:if test="status==0">
												<div
													class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
													<a class="blue" href="#"> <i
														class="icon-zoom-in bigger-130"></i>
													</a> <a class="green" href="#"
														onclick="updateDomain('purchaseBill',${id});"> <i
														class="icon-pencil bigger-130"></i>
													</a> <a class="red" href="#"
														onclick="deleteDomain('/purchaseBill_delete.action?id=${id}',this);">
														<i class="icon-trash bigger-130"></i>
													</a> <a class="blue" href="#"
														onclick="auditStatus('/purchaseBill_audit.action?id=${id}',this);">
														<i class="icon-legal bigger-130"></i>
													</a>
												</div>
											</s:if>
											<s:else>
												<div>
													<i class="icon-zoom-in bigger-130 blue"></i>
													<i class="icon-pencil bigger-130 green"></i>
													<i class="icon-trash bigger-130 red"></i>
													<i class="icon-legal bigger-130 blue"></i>
												</div>
											</s:else></td>
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