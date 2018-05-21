<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Department管理</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- easyui的样式主题文件 -->
<link rel="stylesheet" type="text/css"
	href="/easyui/themes/default/easyui.css">
<!-- easyui的系统图标-->
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<!-- 颜色的css -->
<link rel="stylesheet" type="text/css" href="easyui/themes/color.css">
<!-- easyui依赖的jquery库-->
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<%-- <script src="/assets/js/jquery-2.0.3.min.js"></script> --%>
<!-- easyui的插件库-->
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#tt').tree({
			//属性和事件
			animate : true,
			onClick : function(node) {
				//alert(node.text); // 在用户点击的时候提示
				//alert(node.url);
				//location.href = node.url;
// 				exists which 表明指定的面板是否存在，'which'参数可以是选项卡面板的标题或索引。 
				if($('#tb').tabs('exists',node.text)){
					//select which 选择一个选项卡面板，'which'参数可以是选项卡面板的标题或者索引。 
					$('#tb').tabs('select',node.text);
				}else{
			        $('#tb').tabs('add',{
			            title: node.text,
			            content: '<iframe width="98%" height="98%" src="'+node.url+'" frameborder="0"></iframe>',
			            closable: true
			        });
				}
			}
		});
	});
	
</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div data-options="region:'north'" style="height: 50px">北,上面,公司图片,logo</div>
		<div data-options="region:'south',split:true" style="height: 50px;">南,底部,公司信息</div>
		<div data-options="region:'west',split:true" title="West"
			style="width: 160px;">
			<ul id="tt" data-options="url:'main2_left.action'" lines="true"></ul>
		</div>
		<div id ="tb" class="easyui-tabs" data-options="region:'center'">
			<div title="主页" style="padding: 10px">欢迎进入主页</div>

		</div>
	</div>

</body>
</html>