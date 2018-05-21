<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PurchaseBillItem管理</title>
<script src="/assets/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="/js/highcharts/highcharts.js"></script>
<script src="/js/highcharts/highcharts-3d.js"></script>
<script src="/js/highcharts/modules/exporting.js"></script>
<script type="text/javascript">
$(function(){
	$(".btn").click(function(){
// 		<button data-url="purchaseBillItem_chart1.action"/>
		//给button上面加上一个data-url属性, 如果有就在模态框加载url的action
		var url = $(this).data("url");
		if(url){
			$("#myModal .modal-body").html("正在加载");
			//跳转到purchaseBillItem_chart1.action
			//拿到#domainForm表单里面的所有参数,也就是报表的查询条件
			$("#myModal .modal-body").load(url+"?"+$('#domainForm').serialize());
			$('#myModal').modal({
				backdrop : true,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
				keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
				show : true
			});
		}
	});
});
function downloadSheet(actionName) {
	// 设置表单的action属性值为页面传的url
	$("#domainForm").attr("action", actionName + "_download.action");
	// post提交表单
	$("#domainForm").submit();
	// 提交表单后,修改表单的地址为查询的地址
	$("#domainForm").attr("action", actionName + ".action");
}

</script>
</head>
<body>
	<s:fielderror />
	<s:form action="purchaseBillItem" id="domainForm">
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
						<h4 class="header smaller lighter blue">PurchaseBillItem列表</h4>
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
							name="baseQuery.status" id="form-field-5" onchange="goPage(1)"></s:select>
						<s:select
							list="#{' o.bill.supplier.name ':'供应商分组',' o.bill.buyer.username ':'采购员分组',' month(o.bill.vdate) ':'月份分组'}"
							name="baseQuery.groupBy" id="form-field-5" onchange="goPage(1)"></s:select>


						<button class="btn btn-danger btn-sm" type="button"
							onclick="goPage(1)">
							<i class="icon-search">搜索</i>
						</button>

						<button id="2d" class="btn btn-primary btn-sm " type="button"
							data-url="purchaseBillItem_chart1.action">
							<i class="icon-search">2D饼图</i>
						</button>

						<button id="3d" class="btn  btn-pink btn-sm" type="button"
							data-url="purchaseBillItem_chart2.action">
							<i class="icon-search">3D饼图</i>
						</button>
						
							<button class="btn btn-info icon-download-alt btn-sm" onclick="downloadSheet('purchaseBillItem');" type="button">导出列表</button>

						<!-- 高级查询 end -->

						<!-- <i class="icon-edit bigger-230"></i> -->
						<!-- 新增用户 -->
						<%-- <span class="badge badge-warning badge-left"></span> --%>

						<div class="table-responsive">
							<table id="sample-table-2"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>明细编号</th>
										<th>供应商名称</th>
										<th>采购员</th>
										<th>产品名称</th>
										<th>交易时间</th>
										<th>采购数量</th>
										<th>采购价格</th>
										<th>小计</th>
										<th>产品类别</th>
										<th>状态</th>
									</tr>
								</thead>

								<tbody id="itemTbody">
									<s:iterator value="#list" var="objects">
										<tr style="color: blue">
											<td colspan="10">${objects[0]}-记录数<s:property
													value="#objects[1]" />条
											</td>
										</tr>
										<!-- 定义变量,用来累加 -->
										<s:set var="totalAmount" value="0" />
										<s:set var="totalNum" value="0" />
										<s:set var="avgPrice" value="0" />
										<!--iterator迭代器,因为action在栈顶.直接可以调用action里面的方法 -->
										<s:iterator value="findItems(#objects[0])">
											<tr>
												<td>${id}</td>
												<td>${bill.supplier.name}</td>
												<td>${bill.buyer.username}</td>
												<td>${product.name}</td>
												<td>${bill.vdate}</td>
												<td>${num}</td>
												<td>${price}</td>
												<td>${amount}</td>
												<td>${product.types.name}</td>
												<td><s:if test="bill.status==1">
														<span class="label label-success arrowed" cd="flagg">已审</span>
													</s:if> <s:elseif test="bill.status==0">
														<span class="label label-warning"> <i
															class="icon-warning-sign bigger-120"></i> 待审
														</span>
													</s:elseif> <s:else>
														<span class="label">作废</span>
													</s:else></td>
											</tr>
											<s:set var="totalAmount" value="#totalAmount+amount" />
											<s:set var="totalNum" value="#totalNum+num" />
											<s:set var="avgPrice" value="#totalAmount/#totalNum" />
										</s:iterator>
										<tr style="color: red">
											<th colspan="5">总计</th>
											<th>${totalNum}</th>
											<th>${avgPrice}</th>
											<th>${totalAmount}</th>
											<th></th>
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
					<h4 class="modal-title">采购报表图</h4>
				</div>
				<div class="modal-body" style="color: red;"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>