$(function(){
	vue.getAllSetting();
});


var vue=new Vue({
	el:"#scanVue",
	data:{
		wxSettingMap:{"state":""},
		aliSettingMap:{"state":""}
	},
	methods:{
		getAllSetting:function(){
			that=this;
			$.post("scanToPay/getAllSetting",{},function(result){
				if(result!=null){
					that.wxSettingMap=result.wxSettingMap;
					that.aliSettingMap=result.aliSettingMap;
				}
			});
		},
		wxChangeState:function(state){
			var msg="";
			if(state==0){
				msg="确定关闭微信扫码支付功能吗？";
			}else{
				msg="确定打开微信扫码支付功能吗？";
			}
			openConfirm(0, "修改", msg, function() {
				toAjaxCRUDCallBack("scanToPay/updateWxSetting",{
					"state":state
				},function(result){
					if(result.success==1){
						openAlertMsg("修改成功!");
						setTimeout(function() {
							window.location.reload();
						}, 800);
					}else{
						openAlertMsg(result.errorMessage);
					}
				});
			});
		},
		AliChangeState:function(state){
			var msg="";
			if(state==0){
				msg="确定关闭支付宝扫码支付功能吗？";
			}else{
				msg="确定打开支付宝扫码支付功能吗？";
			}
			openConfirm(0, "修改", msg, function() {
				toAjaxCRUDCallBack("scanToPay/updateAliSetting",{
					"state":state
				},function(result){
					if(result.success==1){
						openAlertMsg("修改成功!");
						setTimeout(function() {
							window.location.reload();
						}, 800);
					}else{
						openAlertMsg(result.errorMessage);
					}
				});
			});
		},
		wxCommit:function(){
			that=this;
			openConfirm(0, "修改", "确认修改微信扫码支付设置吗？", function() {
				toAjaxCRUDCallBack("scanToPay/updateWxSetting",{
					"appid" : that.wxSettingMap.appid,
					"mchid" : that.wxSettingMap.mchid,
					"apikey" : that.wxSettingMap.apikey,
					"queryDuration":that.wxSettingMap.queryDuration,
					"maxQueryRetry":that.wxSettingMap.maxQueryRetry
				},function(result){
					if(result.success==1){
						openAlertMsg("修改成功!");
						setTimeout(function() {
							window.location.reload();
						}, 800);
					}else{
						openAlertMsg(result.errorMessage);
					}
				});
			});
		},
		aliCommit:function(){
			that=this;
			openConfirm(0, "修改", "确认修改支付宝扫码支付设置吗？", function() {
				toAjaxCRUDCallBack("scanToPay/updateAliSetting",{
					"appid":that.aliSettingMap.appid,
					"pid":that.aliSettingMap.pid,
					"appAuthToken":that.aliSettingMap.appAuthToken,
					"privateKey":that.aliSettingMap.privateKey,
					"publicKey":that.aliSettingMap.publicKey,
					"alipayPublicKey":that.aliSettingMap.alipayPublicKey,
					"queryDuration":that.aliSettingMap.queryDuration,
					"maxQueryRetry":that.aliSettingMap.maxQueryRetry
				},function(result){
					if(result.success==1){
						openAlertMsg("修改成功!");
						setTimeout(function() {
							window.location.reload();
						}, 800);
					}else{
						openAlertMsg(result.errorMessage);
					}
				});
			});
		},
	}
})