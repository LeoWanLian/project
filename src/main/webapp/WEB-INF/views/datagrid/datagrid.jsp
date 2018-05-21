<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Department管理</title>
<!-- easyui的样式主题文件 -->
<link rel="stylesheet" type="text/css"
	href="/easyui/themes/default/easyui.css">
<!-- easyui的系统图标-->
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<!-- 颜色的css -->
<link rel="stylesheet" type="text/css" href="easyui/themes/color.css">
<!-- easyui依赖的jquery库-->
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<!-- easyui的插件库-->
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<!-- easyui的汉化包 -->
<script type="text/javascript" src="/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 必须先引入jQuery的js -->
<script type="text/javascript">
	function deleteEmployee() {
		// 		console.debug("delete");
		//调用datagrid组件的getSelected方法当前选中的只是一行
		//getSelections none 返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。 
		//getSelected none 返回第一个被选中的行或如果没有选中的行则返回null。

		var row = $("#dg").datagrid('getSelected');//  获取选择一行的数据
		if (!row) {
			$.messager.alert('提示信息', '必须选中一行!', 'info');
			return;
		}
		//row代表选中行的json数据
		$.messager.confirm('操作提示', '你真的要删除吗？老板同意了没有?', function(r) {
			$.get("datagrid_delete.action", {
				id : row.id
			}, function(data) {
				if (data.success) {
					$.messager.alert('提示信息', '删除成功!');
					//重新加载datagrid:调用datagrid组件的reload方法		
					//reload param 重载行。等同于'load'方法，但是它将保持在当前页。 
					$('#dg').datagrid('reload');//类似于原来的$("#domainFrom").submit()
				} else {
					// 					alert(data.msg);
					$.messager.show({
						title : '错误信息',
						msg : data.msg,
						showType : 'fade',
						style : {
							right : '',
							bottom : ''
						}
					});
				}
			});
		});
	}

	function addEmployee() {
		// 		console.debug("add");
		//弹出对话框,居中,设置模态框的标题
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '新增员工');
		//清空新增模态框里面的内容
		$('#fm').form('clear');
		id = null;
	}

	var id = null;
	var index = null;
	function editEmployee() {
		// 		console.debug("edit");
		var row = $("#dg").datagrid('getSelected');//  获取选择一行的数据
		if (!row) {
			$.messager.alert('提示信息', '修改前必须选中一行!', 'info');
			return;
		}

		// 		调用对话框组件的open打开、conter居中、setTitle设置标题方法
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改员工');
		// 		console.debug(row.id);
		// 		模拟回显的代码***************************
		// 				for(var p in row){
		// 					console.debug(p+"="+row[p]);
		// 					$("#fm input[name="+p+"]").val(row[p]);
		// 				}
		// 				因为row有department对象，在department对象有id==部门id
		// 				但是表单里面部门name属性是department.id
		// 				row["department.id"]!=row.department.id;
		if (row.department) {//部门不为null
			//<input id="cc" class="easyui-combobox" name="department.id"
			// 			row["department.id"] = row.department.id;
			$('#cc').combobox('setValue', row.department.id);
		}
		// 		回显数据***************************
		$('#fm').form('load', row);
		id = row.id;
		// 		getRowIndex row 返回指定行的索引号，该行的参数可以是一行记录或一个ID字段值。 
		index = $('#dg').datagrid('getRowIndex', row);
		console.debug(index);
	}

	// 	执行提交操作，该选项的参数是一个对象，它包含以下属性：
	// 	url：请求的URL地址。
	// 	onSubmit: 提交之前的回调函数。
	// 	success: 提交成功后的回调函数。
	function saveEmployee() {
		//调用form组件的submit方法
		$("#fm").form('submit', {
			//请求URL地址
			url : "datagrid_save.action",
			//在提交之前触发，返回false可以终止提交。
			onSubmit : function() {
				//调用验证的方法，自动根据验证的规则处理验证
				return $(this).form('validate');
			},
			//success并不是表示后台保存成功，只是在表单提交成功以后触发，属于前台验证通过了，点击保存按钮提交到了后台
			success : function(result) {
				console.debug(result);
				//easyui form表单提交之后就算后台返回的json字符串，都必须自己写代码进行转换
				var result = JSON.parse(result);
				if (result.success) {//如果保存成功
					//关闭对话框
					$('#dlg').dialog('close'); // close the dialog
					if (id) {//如果有id,说明是修改,重新加载数据
						$("#dg").datagrid("reload");
					} else {//新增后的保存,跳到末页
						$("#dg").datagrid('gotoPage', 30000);
					}
				} else {//如果保存失败
					$.messager.show({
						title : '错误信息',
						msg : result.msg,
					});
				}

			}

		});

	}

	// 单元格formatter(格式化器)函数，带3个参数：
	// value：字段值。
	// row：行记录数据。
	// index: 行索引。
	function ageFormat(value, row, index) {
		// 		console.debug(value);
		// 		console.debug(row);
		// 		console.debug(index);
		return value > 25 ? "<font color='red'>" + value + "</font>" : value;
	}

	function departmentFormat(value) {
		return value ? value.name : "";
	}
	function headImageFormat(value) {
		return value ? "<img src='"+value+"'/>" : "";
	}
</script>
</head>
<body>

	<table id="dg" title="员工列表" class="easyui-datagrid"
		style="width: 700px; heght: 250px" url="datagrid_json.action"
		toolbar="#toolbar" pagination="true" rownumbers="true"
		fitColumns="true" singleSelect="true" fit="true">
		<thead>
			<tr>
				<th field="id" width="50" hidden="true">编号</th>
				<th field="username" width="50">用户名</th>
				<th field="password" width="50">密码</th>
				<th field="email" width="50">邮箱</th>
				<th field="age" width="20" formatter="ageFormat">年龄</th>
				<th field="department" width="50" formatter="departmentFormat">部门</th>
				<th field="headImage" width="50" formatter="headImageFormat">头像</th>
			</tr>
		</thead>
	</table>

	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="addEmployee()">新增</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editEmployee()">编辑</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="deleteEmployee()">删除</a>
	</div>

	<!-- input框 -->
	<div id="dlg" class="easyui-dialog" style="width: 400px" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate style="margin: 0; padding: 20px 50px">
			<input type="hidden" name="id">
			<div style="margin-bottom: 20px; font-size: 14px; border-bottom: 1px solid #ccc">员工信息</div>
			<div style="margin-bottom: 10px">
				<input name="username" class="easyui-textbox" required="true" label="用户名:" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="password" class="easyui-textbox" required="true" label="密码:" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="age" class="easyui-textbox" required="true" label="年龄" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="email" class="easyui-textbox" validType="email" label="邮箱:" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<!-- name="department.id"提交的名称，同原来employee_input.jsp一样 -->
				<!--url:'datagrid_dept.action'"获取部门列表的json：[{"id":1,"name":"部门1"}]   -->
				<!--valueField:'id提交到后台的值',textField:'name在页面上面看到了'  -->

				<input id="cc" class="easyui-combobox" name="department.id"
					data-options="valueField:'id',textField:'name',url:'datagrid_dept.action'"
					label="部门" style="width: 100%" />
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveEmployee()" style="width: 90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width: 90px">取消</a>
	</div>

</body>
</html>