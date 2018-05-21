//处理分页
function goPage(no) {
	// 把点击后的页码赋值给隐藏域里面的baseQuery.currentPage
	$("#pageNo").val(no);
	// 提交表单
	$("#domainForm").submit();
	// window.location.href = "/employee.action?baseQuery.currentPage="+no;
}

// 处理高级修改,跳回到修改时的list页面
function updateDomain(actionName, id) {
	// 设置表单的action属性值为页面传的url
	$("#domainForm").attr("action", actionName + "_input.action?id=" + id);
	// post提交表单
	$("#domainForm").submit();
	// 提交表单后,修改表单的地址为查询的地址
	$("#domainForm").attr("action", actionName + ".action");
}



// 处理下载
function downloadDomain(actionName) {
	// 设置表单的action属性值为页面传的url
	$("#domainForm").attr("action", actionName + "_download.action");
	// post提交表单
	$("#domainForm").submit();
	// 提交表单后,修改表单的地址为查询的地址
	$("#domainForm").attr("action", actionName + ".action");
}

// 处理上传导入文件
function uploadDomain() {
	// 设置表单的action属性值为页面传的url
	$("#domainForm").attr("action", actionName + "_download.action");
	// post提交表单
	$("#domainForm").submit();
	// 提交表单后,修改表单的地址为查询的地址
	$("#domainForm").attr("action", actionName + ".action");
}

// 处理批量删除
function deleteAll(actionName) {
	// 设置表单的action属性值为页面传的url
	$("#domainForm").attr("action", actionName + "_deleteAll.action");
	// post提交表单
	$("#domainForm").submit();
	// 提交表单后,修改表单的地址为查询的地址
	$("#domainForm").attr("action", actionName + ".action");
}

$(function() {
	// prop(name属性名称|properties作为属性的“名/值对”对象|key,value|属性名称，属性值fn)
	// 获取在匹配的元素集中的第一个元素的属性值。
	// 全选
	$("#checkAll").click(function() {
		$("input[name=ids]").prop("checked", true);
	});
	// 不全选
	$("#checkNotAll").click(function() {
		$("input[name=ids]").prop("checked", false);
	});
	// 反选
	$("#checkUnAll").click(function() {
		// 遍历
		$("input[name=ids]").each(function(index, dom) {
			this.checked = !this.checked;
		});
	});

});

function checkChange(src) {
	console.debug(src.checked);
	console.debug($("input[name=ids]"));
	// attr单个 prop 多个对象
	$("input[name=ids]").prop("checked", src.checked);
}
// //处理高级删除,跳回到修改时的list页面
// function deleteDomain(url) {
// // 设置表单的action属性值为页面传的url
// $("#domainForm").attr("action", url);
// // post提交表单
// $("#domainForm").submit();
// }


// 高级删除 ajax方案
/**
 * 通过远程 HTTP POST 请求载入信息。 jQuery.post(url, [data], [callback], [type]) 这是一个简单的
 * POST 请求功能以取代复杂 $.ajax 。请求成功时可调用回调函数。如果需要在出错时执行函数，请使用 $.ajax。 url:发送请求地址。
 * data:待发送 Key/value 参数。 callback:发送成功时回调函数。 type:返回内容格式，xml, html, script,
 * json, text, _default。
 * 
 */
// ajax的高级删除(后台不能返回结果视图,只能返回json字符串)
function deleteDomain(url, src) {
	$.post(url, function(data) {
		if (data.success) {
			// 提交表单
			// $("#domainForm").submit();
			// 这里的this指的是post对象
			// console.debug($(this));
			// console.debug($(src));
			// 追溯到tr的父级元素删除tr.
			$(src).closest("tr").remove();
			// 如果当前页面的tr都被删除, 就提交表单
			if ($("#itemTbody tr").size == 0) {
				$("#domainForm").submit();
			} else {
				// 修改下面的条数展示
				$("#end").html($("#end").html() - 1);
				// 修改下面的条数展示
				$("#totalCount").html($("#totalCount").html() - 1);
			}
		} else {
			$("#myModal .modal-body").html(data.msg);
			$('#myModal').modal({
				backdrop : true,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
				keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
				show : true
			});

			// alert("失败了！原因:" + data.msg);
		}
	}, 'json');
}

// localhost/main_changePwd.action?newPwd=5&oldPwd=6
function changePwd(url,newPwd,oldPwd) {
	var oldPwd = $("#oldpwd").val();
	var newPwd1 = $("#newpwd1").val();
	var newPwd2 = $("#newpwd2").val();
	if(newPwd1!=newPwd2){
		alert("两次密码输入不一致");
	}
	
	$.get(url+"_changePwd.action?newPwd="+newPwd1+"&oldPwd="+oldPwd, function(data) {
//		$("#pwdModal .modal-body").html(data.msg);
//		$('#pwdModal').modal({
//			backdrop : true,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
//			keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
//			show : true
//		})
		if(data.success){
			alert(data.msg);
			location.href='/login_logout.action';
		}else{
			alert(data.msg);
		}

	}, 'json');
}

$(function() {
	// employee.jsp页面 的页面输入框回删功能
	$("#pageNo").keyup(function() {
		var c = $(this);
		// 如果含有非数字
		if (/[\D]/.test(c.val())) {
			// 将非数字替换成空串
			var temp = c.val().replace(/[\D]/g, '');
			$(this).val(temp);
		}
	})
	// // function clearA() {
	// $("#form-field-5").val(-1);
	// $(".col-xs-10").val("");
	// }

	$(function() {
		$("#reseta").click(function() {
			$("#form-field-5").val(-1);
			$(".col-xs-10").val("");
		});
	});

});
