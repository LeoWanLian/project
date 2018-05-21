<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Department管理</title>
<!-- 必须先引入jQuery的js -->
<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		//jQuery.fn. =jQuery.prototype.
		//$.fn是指jquery的命名空间，加上fn上的方法及属性，会对jquery实例每一个有效，
		//$.fn. 相当于对jQuery的原型添加函数
		$.fn.datagrid = function(json) {
			//接收调用函数时传的json格式参数,得到url请求的aciton
			var url = json.url;
			if (url) {
				//获取到table元素
				var table = $(this);
				//获取所有的tr
				var ths = table.find("tr th");
				
				//发送ajax请求到后台,得到后台返回的json格式的数据
				$.get(url, function(data) {
					//console.debug(data.length);//10
					for (var i = 0; i < data.length; i++) {
						var tr = "<tr>";
						for (var j = 0; j < ths.length; j++) {
							//获取th里面定义的属性field
							//ths[j]是dom对象. 转换成jQuery对象,调用attr函数,获取到field 的值
							var field = $(ths[j]).attr("field");
							//获取到遍历的format属性的值
							var format = $(ths[j]).attr("format");
							if(format){
								//var format= function ageFormat(value){} 
								//window可以使用[] 来调用函数format
								tr += "<td>" + window[format](data[i][field]) + "</td>";
							}else{
								tr += "<td>" + data[i][field] + "</td>";
							}
						}
						table.append(tr);
					}
					// 从子窗体调用父窗体的代码修改页面高度
					if (window.parent.autoHeight) {// 这里不写()，如果写了是调用函数
						window.parent.autoHeight();
					}
				});
			}
		}

		//调用自定义插件,参数为json格式的url
		$("#dg").datagrid({
			url : "datagrid_json.action"
		});
	});
	
	function ageFormat(value){
		return value&&value>25?"<font color='red'>"+value+"</font>":"";
	}
	
	function departmentFormat(value){
		return value?value.name:"";
	}
	function headImageFormat(value){
		return value?"<img src='"+value+"'/>":"";
	}
</script>
</head>
<body>
	<!-- 用户名 	密码 	邮箱 	年龄 	部门 	头像 	拥有权限 	操作 -->
	<table border="1" align="center" id="dg">
		<tr>
			<th field="id">编号</th>
			<th field="username">用户名</th>
			<th field="password">密码</th>
			<th field="email">邮箱</th>
			<th field="age" format="ageFormat">年龄</th>
			<th field="department" format="departmentFormat">部门</th>
			<th field="headImage" format="headImageFormat">头像</th>
		</tr>

	</table>

</body>
</html>