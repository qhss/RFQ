/**
 * 
 */

$(function() {
	
	if(saleModel=="saas"){//如果是saas版本 显示进销存开关
		$("#saasStore").show();
		$("#saasDepart").show();
	}
	
	if(version=="personal"){//一号医馆个人、个人高级版可展示是否跳过支付、取药开关
		$("#personalIsSkipPay").show();
		$("#personalIsSkipTakeDrug").show();
	}
	
	$.post("setting/getallparam",null,function(result){
		$.map(result,function(param,index){
			if(param.key=="IS_PREPAY_REGFEE" && param.value.toString()=="1"){
				$("#switch_isprepay_regfee").bootstrapSwitch("state",1);
			}else if(param.key=="IS_MACHINE_DISPENSE" && param.value.toString()=="1"){
				$("#switch_ismachine_dispense").bootstrapSwitch("state",1);
			}else if(param.key=="IS_SHARE_MEDICALRECORD" && param.value.toString()=="1"){
				$("#switch_share_medicalrecord").bootstrapSwitch("state",1);
			}else if(param.key=="IS_NEEDAUTHORIZE_DISCOUNT" && param.value.toString()=="1"){
				$("#switch_needAuthorize_discount").bootstrapSwitch("state",1);
			}else if(param.key=="IS_SAAS_STORE" && param.value.toString()=="1"){
				$("#switch_saas_store").bootstrapSwitch("state",1);
			}else if(param.key=="IS_SAAS_DEPART" && param.value.toString()=="1"){
				$("#switch_saas_depart").bootstrapSwitch("state",1);
			}else if(param.key=="IS_ASSISTANT_DOCTOR" && param.value.toString()=="1"){
				$("#switch_assistant_doctor").bootstrapSwitch("state",1);
			}else if(param.key=="IS_PERSONAL_SKIP_PAY"&& param.value.toString()=="1"){
				$("#switch_personal_skip_pay").bootstrapSwitch("state",1);
			}else if(param.key=="IS_PERSONAL_SKIP_TAKEDRUG"&&param.value.toString()=="1"){
				$("#switch_personal_skip_take_drug").bootstrapSwitch("state",1);
			}else if(param.key=="IS_PAYREGFEE_RECIPETION" && param.value.toString()=="1"){
				$("#switch_ispayregfee_recipetion").bootstrapSwitch("state",1);
			}       

		});
	});
	 
	
     $('[data-am-switch]').on('switchChange.bootstrapSwitch', function (value) {  
		var $input=$(value.target);
		var ischecked=$input.prop("checked");
		var settingName=$input.data("setting");
		
		setHospital(settingName,ischecked==true?1:0);  
     }); 
});
	
function setHospital(settingName,value){
	
	if(settingName=="IS_SAAS_STORE"&&value==0){
		layer.confirm("关闭进销存后取药和开方将不校验库存！确定关闭吗?", {
			icon : 0,
			scrollbar : false,
			closeBtn: 0	,//没有叉
			btn : [ '确定', '取消' ]
		}, function(index, layero) {
			$.post("setting/setparam",{name:settingName,value:value},function(result){
				if(result.success == 1){
					openAlertTopMsg("修改成功!");
				}else{
					openAlertTopMsg("修改参数失败,请与系统管理员联系!");
				}
			});
			layer.close(index);
		}, function(index, layero) {
			$('#switch_isStore').bootstrapSwitch('readonly', false); 
			document.location.reload();//当前页面 
		});
	}else{
		$.post("setting/setparam",{name:settingName,value:value},function(result){
			if(result.success == 1){
				openAlertTopMsg("修改成功!");
			}else{
				openAlertTopMsg("修改参数失败,请与系统管理员联系!");
			}
		});
	}
	
}
