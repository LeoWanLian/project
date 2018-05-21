// 页面加载完成之后才执行以下代码
$(function() {
	// 弹出窗体，选择产品
	// <button type="button" class="btn serachProduct" code="serachProduct">
	// 上面的监控的button控件，后续是会动态生成的,所有监控的时候必须监控大的区域
	$("#itemTbody").on('click', 'button[code="serachProduct"]', function() {
		// alert(0);
		$("#myModal .modal-body").html("正在加载...");
		// ajax的方法加载请求
		$("#myModal .modal-body").load("product_bill.action");
		$('#myModal').modal({
			backdrop : true,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
			keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
			show : true
		});
		// 是input页面里面弹出窗体按钮
		var tr = $(this).closest("tr");

		// 弹出模态框必须先添加监控的代码，才能执行模态框上面控件的执行
		$('#myModal').on("shown.bs.modal", function() {

			// 点击弹出窗体里面的确定按钮
			$(".choose").click(function() {
				// 关闭当前的弹出窗体
				$(".close").click();
				// 从a>td->tr
				var tds = $(this).closest("tr").find("td");
				// alert(tds.length);
				// alert($(tds[0]).html());
				// 特别注意：如果是input控件，就使用val，如果是td控件就是使用html
				tr.find("input[code=productId]").val($(tds[0]).html());
				tr.find("input[code=productName]").val($(tds[1]).html());
				tr.find("td[code=productColor]").html($(tds[2]).html());
				tr.find("td[code=productPic]").html($(tds[3]).html());
				tr.find("input[code=price]").val($(tds[4]).html());
			});
		});
	});

	// 添加一行
	$(".addItem").click(function() {
		// 克隆
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

		// 附加
		// append(content) 向每个匹配的元素内部追加内容。放置到最后
		// prepend(content) 向每个匹配的元素内部追加内容。放置到第一个
		$("#itemTbody").prepend(tr);

		// 从子窗体调用父窗体的代码修改页面高度
		if (window.parent.autoHeight) {// 这里不写()，如果写了是调用函数
			window.parent.autoHeight();
		}
	});

	// 删除明细
	$("#itemTbody").on('click', 'button[code="deleteItem"]', function() {
		if ($("#itemTbody tr").size() > 1) {
			$(this).closest("tr").remove();
		}
	});
	
	//保存:修改name索引,判断非法数据
	$("#saveBtn").click(function(){
		var flag = false;//只能写在这个click的里面
		
		//each循环的，自动传入2个参数，一个是索引，一个dom对象
		$("#itemTbody tr").each(function(index,dom){
			//把普通的dom对象变成jQuery对象
			var tr = $(dom);
			//只有需要提交的控件才要修改
			tr.find("input[code=productId]").attr("name","items["+index+"].product.id");
			tr.find("input[code=price]").attr("name","items["+index+"].price");
			tr.find("input[code=num]").attr("name","items["+index+"].num");
			tr.find("input[code=descs]").attr("name","items["+index+"].descs");
			
			//判断必须选择产品id
			var productId = tr.find("input[code=productId]").val();
			if(!flag && !productId){
				alert("请选择产品");
				flag=true;
				tr.find("button[code=serachProduct]").focus();
				return false;
			}
			//判断填写采购数量
			var num = tr.find("input[code=num]").val();
			if(!flag && !num){
				alert("请填写采购数量");
				flag=true;
				tr.find("input[code=num]").focus();//焦点(光标)回到num输入框
				return false;
			}
			//判断采购数量必须为合法数据:产品:小数
			if(!flag && !/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(num)||num<0){
				alert("采购数量非法");
				flag=true;
				tr.find("input[code=num]").focus();
				return false;
			}
		});
		if(!flag){
			$("#purchaseBillForm").submit();			
		}
	});
	
	
	//自动计算小计:采购价格 	采购数量发送变化的时候自动计算小计(还是不提交到后台,只是给用户看)
	$("#itemTbody").on('blur',"input[code=num],input[code=price]",function(){
		var tr = $(this).closest("tr");
		var price = tr.find("input[code=price]").val();
		var num = tr.find("input[code=num]").val();
		var amount = (price*num).toFixed(2);//保留2个小数;
		tr.find("td[code=amount]").html(amount);
	});
});