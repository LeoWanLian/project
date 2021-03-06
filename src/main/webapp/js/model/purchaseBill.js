// 页面加载完成之后才执行以下代码
function auditStatus(url, src) {
	$.post(url, function(data) {
		if (data.success) {
			$(src).closest("tr").find("td[code=audit]").html(
					"<span class='label label-success arrowed'>已审</span>");
			$(src).closest("td").find("a").removeAttr('onclick');
			$(src).closest("td").find("a").removeAttr('href');
		} else {
			$("#myModal .modal-body").html(data.msg);
			$('#myModal').modal({
				backdrop : true,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
				keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
				show : true
			})
			// alert("失败了！原因:" + data.msg);
		}
	}, 'json');
}
$(function() {
	// 必须对tbody添加事件监控
	// $("#itemTbody button[code=serachProduct]").click(function() {
	$("#itemTbody").on('click', 'button[code="serachProduct"]', function() {
		// alert(0);
		// $(".modal-body").html("xxxx");
		$("#myModal .modal-body").load("product_bill.action");
		$('#myModal').modal({
			backdrop : true,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
			keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
			show : true
		})

		var tr = $(this).closest("tr");
		$('#myModal').on("shown.bs.modal", function() {
			// 弹出模态对话框
			$(".choose").click(function() {
				// 关闭模态对话框
				$(".close").click();
				// 获取到td里面的值
				var tds = $(this).closest("tr").find("td");
				// alert(tds.length);
				// alert($(tds[3]).html());
				tr.find("input[code=productId]").val($(tds[0]).html());
				tr.find("input[code=productName]").val($(tds[1]).html());
				tr.find("td[code=productColor]").html($(tds[2]).html());
				tr.find("td[code=productPic]").html($(tds[3]).html());
				tr.find("input[code=price]").val($(tds[4]).html());
			});
		});
	});
	// 添加一行明细
	$(".addItem").click(function() {
		// 克隆一行
		var tr = $("#itemTbody tr:last").clone();
		// 附加之前清空原来的数据
		tr.find("input[code=productId]").val("");
		tr.find("input[code=productName]").val("");
		tr.find("input[code=price]").val("");
		tr.find("input[code=num]").val("");
		tr.find("input[code=descs]").val("");
		tr.find("td[code=productColor]").html("");
		tr.find("td[code=productPic]").html("");
		tr.find("td[code=amount]").html("");
		// 附加一行
		$("#itemTbody").prepend(tr);

		// 从子窗体调用父窗体的代码修改页面高度
		if (window.parent.autoHeight) {// 这里不写()，如果写了是调用函数
			window.parent.autoHeight();
		}
	});

	// 删除一行
	$("#itemTbody").on('click', 'button[code="deleteItem"]', function() {
		if ($("#itemTbody tr").size() > 1) {
			$(this).closest("tr").remove();
		}
	});

	// 保存:修改name索引,判断非法数据
	$("#saveBtn").click(function() {
		var flag = false;// 只能写在这个click的里面
		$("#itemTbody tr").each(function(index, item) {
			var tr = $(item);
			tr.find("input[code=productId]").prop("name","items["+ index+ "].product.id");
											tr.find("input[code=price]").prop(
													"name",
													"items[" + index
															+ "].price");
											tr.find("input[code=num]").prop(
													"name",
													"items[" + index + "].num");
											tr.find("input[code=descs]").prop(
													"name",
													"items[" + index
															+ "].descs");
											// 判断必须选择产品id
											var productId = tr.find(
													"input[code=productId]")
													.val();
											if (!flag && !productId) {
												alert("请选择产品");
												flag = true;
												tr
														.find(
																"button[code=serachProduct]")
														.focus();
												return false;
											}
											// 判断填写采购数量
											var num = tr
													.find("input[code=num]")
													.val();
											if (!flag && !num) {
												alert("请填写采购数量");
												flag = true;
												tr.find("input[code=num]")
														.focus();// 焦点(光标)回到num输入框
												return false;
											}
											// 判断采购数量必须为合法数据:产品:小数
											if (!flag
													&& !/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/
															.test(num)
													|| num < 0) {
												alert("采购数量非法");
												flag = true;
												tr.find("input[code=num]")
														.focus();
												return false;
											}
										});
						// 循环外面手动提交表单
						if (!flag) {
							$("#purchaseBillForm").submit();
						}
					});
	// 自动计算小计:采购价格 采购数量发送变化的时候自动计算小计(还是不提交到后台,只是给用户看)
	$("#itemTbody").on('blur', "input[code=num],input[code=price]", function() {
		var tr = $(this).closest("tr");
		var price = tr.find("input[code=price]").val();
		var num = tr.find("input[code=num]").val();
		var amount = (price * num).toFixed(2);// 保留2个小数;
		tr.find("td[code=amount]").html(amount);
	});
});
