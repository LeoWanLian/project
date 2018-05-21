<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/comman.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#editBtn").on("click", function() {
			console.debug("编辑...");
		});

		$("#cutBtn").on("click", function() {
			//组件的disabled禁用属性,无法控制事件.
			if (!$(this).hasClass("l-btn-disabled")) {//判断按钮是否有禁用样式.
				console.debug("剪切...");
			}
		});
		
		$(".easyui-linkbutton").click(function() {
			//组件的disabled禁用属性,无法控制事件.
			if (!$(this).hasClass('l-btn-disabled')) {
				console.debug($(this).text());
			}
		});
	});

</script>
</head>
<body>

	<a href="#" class="easyui-linkbutton" iconCls='icon-remove' toggle="true" onclick="alert('删除....');">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls='icon-edit' plain="true" iconAlign="right" id="editBtn">编辑</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cut',disabled:true" id="cutBtn">剪切</a>
	<!--   在easyui里面不直接是button，submit按钮都是使用a标签，但是是LinkButton组件 -->
	<!-- 
		linkbutton组件:
			属性:
				iconCls:图标;
				disabled:是否禁用;
				plain:true时显示简洁效果,其实就是没有背景;
				iconAlign:按钮图标位置;可以选值为left,right;默认为:left;
				toggle: 是否可以选中效果.
				group : 分组,一般与toggle配合使用,达到单选效果;
	 -->
	<hr>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">Add</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true,size:'large',iconAlign:'top'">Add</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" disabled="true">Cut</a>
	<a href="#" class="easyui-linkbutton c2" toggle="true">Text Button</a>

	<div style="padding: 5px; border: 1px solid #ddd;">
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">Button 1</a> 
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">Button 2</a> 
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2'">Button 3</a> 
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2'">Button 4</a> 
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2'">Button 5</a>
	</div>
	
	
</body>
</html>