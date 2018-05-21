// 页面加载完成之后才执行以下代码
$(function() {
	$("#${lowerEntityDomain}Form").bootstrapValidator({
		message : 'This value is not valid',
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
			}
		}
	});
});