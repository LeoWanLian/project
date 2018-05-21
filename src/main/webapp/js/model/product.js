// 页面加载完成之后才执行以下代码
$(function() {
	$("#parent").change(function(){
		var pid = $(this).val();
		//清空二级类型
		$("#children").empty();
		if(pid==-1){
			$("#children").html("<option value='-1'>--请选择--</option>");
		}else{
			//先从缓存取
			var cacheData=$("#children").data(pid);
			if(cacheData){
				for (var i = 0; i < cacheData.length; i++) {
					console.debug(cacheData[i]);
					$("#children").append("<option value="+cacheData[i].id+">"+cacheData[i].name+"</option>");
				}
			}else{
//			$.get(url,data,callback,type);url:待载入页面的URL地址data:待发送 Key/value 参数。callback:载入成功时回调函数。type:返回内容格式
				$.get("product_findChildren.action?id="+pid,function(data){
					console.debug(data);
					$("#children").data(pid, data);// 放入缓存
					for (var i = 0; i < data.length; i++) {
						console.debug(data[i]);
						$("#children").append("<option value="+data[i].id+">"+data[i].name+"</option>");
					}
				});
			}
		}
	});
	
	
	$('#productForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : true
				}
			},
			costPrice : {
				validators : {
					notEmpty : true,
					numeric : true
				}
			},
			salePrice : {
				validators : {
					notEmpty : true,
					numeric : true
				}
			},
			"types.id" : {
				validators : {
					notEmpty : true
				}
			},
			"types.parent.id" : {
				validators : {
					different : {
						field : "types.id",
						message : '必须选择正确产品类型'
					}
				}
			},
			upload : {
				validators : {
					file : {
						extension : 'gif,png,jpg,jpeg',
						type : 'image/gif,image/png,image/jpeg',
						maxSize : 5 * 1024 * 1024, // 5 MB
						message : '必须上传图片而且图片不能超过5MB'
					}
				}
			}
		}
	});
});