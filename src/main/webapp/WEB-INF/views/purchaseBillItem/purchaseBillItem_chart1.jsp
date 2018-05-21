<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购报表</title>
<script type="text/javascript">
	$(function() {
		$
				.get(
						"/purchaseBillItem_data.action?${pageContext.request.queryString}",
						function(myData) {

							$('#container')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : 1,//null,
													plotShadow : false
												},
												title : {
													text : '妹妹你坐船头订单报表图'
												},
												tooltip : {
													pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
														dataLabels : {
															enabled : true,
															format : '<b>{point.name}</b>: {point.percentage:.1f} %',
															style : {
																color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																		|| 'black'
															}
														}
													}
												},
												series : [ {
													type : 'pie',
													name : '订单总价占比',
													data : myData
												} ]
											});
						});
	});
</script>
</head>
<body>
	<div id="container"
		style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
</body>
</html>