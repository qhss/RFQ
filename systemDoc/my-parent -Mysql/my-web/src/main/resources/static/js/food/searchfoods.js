var ajaxpath="food/";
var vue = new Vue({
	el:"#editorPanel",
	data:{
		obj:{},// 分拣设备
	},
	created:function(){
		var that = this;
		
	},
	methods:{
//		add:function(){
//			
//			openCustom("添加", "500", "400", $("#editorPanel"),'vue.create()');
//		},
//		create:function(){
//			var that = this;
//			
//			var validator = validators("updateFrom");// 验证表单
//			if (!validator) {
//				return false;
//			}
//			
//			var data={
//				"foodType":that.obj.foodType,
//				"foodName":that.obj.foodName,
//				"foodPrice":that.obj.foodPrice
//			};
//			toAjaxDetail(ajaxpath+"save",data,"添加","example");
//		},
//		//编辑
//		edit:function(id){
//			var that = this;
//			$('#updateFrom')[0].reset();
//			$("#updateFrom").validator('destroy');
//			
//			$.post(ajaxpath+"one",{"id":id},function(result){
//				if(result.success==1){
//					that.obj = result.data;
//					openCustom("修改", "500", "400", $("#editorPanel"),'vue.update()');
//				}
//				else
//					openAlertMsg(result.errorMessage);
//			});
//		},
//		// 编辑保存
//		update:function(){
//			var validator = validators("updateFrom");// 验证表单
//			if (!validator) {
//				return false;
//			}
//			
//			var that = this;
//			openConfirm(0,"操作确认","确认修改数据吗?",function(){
//				toAjaxDetail(ajaxpath+"save",{
//					"id":that.obj.id,
//					"foodType":that.obj.foodType,
//					"foodName":that.obj.foodName,
//					"foodPrice":that.obj.foodPrice
//				},"修改","example");
//			});
//			
//		},
//		// 删除
//		del:function(id){
//			openConfirm(0,"操作确认","确认删除吗?",function(){
//				toAjaxCRUDCallBack(ajaxpath+"del",{
//					"id":id,
//				},function(result){
//					if(result.success==1){
//						openAlertMsg("删除成功");
//						$("#example").dataTable().fnDraw(true);
//					}else{
//						openAlertMsg(result.errorMessage);
//					}
//				});
//			});
//			
//		},
//		delBatch:function(ids){
//			
//		}
		
	}
});




$(function() {
	// 定义列
	var aoColumns = [{
				"sClass" : "table-check",
				"sTitle" : "<input type=\"checkbox\" class=\"checkAll\"/>",
				"mDataProp" : "id",
				"render" : function(oObj, sVal) {
					return '<input type="checkbox" id="' + oObj + '"  data-id="'+oObj+'" />';
				},
				"sDefaultContent" : "",
				"bSortable" : false
			},{
				"mDataProp" : "foodType",
				"sTitle" : "餐饮类型",
				"sDefaultContent" : "",
				"render" : function(oObj, sVal) {
					if(oObj==0)
						return '特色小吃';
					if(oObj==1)
						return '精品套餐';
					else
						return '酷爽饮品';
				},

				"sWidth" : "20%",
				"bSortable" : false
			},
			{
				"mDataProp" : "foodName",
				"sTitle" : "餐饮名称",
				"sDefaultContent" : "",
				"sWidth" : "40%",
				"bSortable" : false
			}
			,{
				"mDataProp" : "foodPrice",
				"sTitle" : "餐饮价格",
				"sDefaultContent" : "",
				"sWidth" : "20%",
				"bSortable" : false

			},
			{
				"mDataProp" : "createTime",
				"sTitle" : "推荐度",
				"sDefaultContent" : "",
				"sWidth" : "20%",
				"render":function(oObj,sVal){
					return "五星推荐";
				},
				"bSortable" : false
			}
//			,{
//				"mDataProp" : "id",
//				"sTitle" : "操作",
//				"sDefaultContent" : "",// 此列默认值为""，以防报错
//				"bSortable" : false,
//				"render" : function(oObj, sVal, aData) {
//					var buttons = [];
//					buttons.push({
//						name : '编辑',
//						icon : 'am-icon-pencil',
//						class : 'am-btn am-btn-default am-btn-xs am-text-primary',
//						clickEvent : "vue.edit('" + aData.id + "')"
//					})
//					buttons.push({
//						name : '删除',
//						icon : 'am-icon-remove',
//						class : 'am-btn am-btn-default am-btn-xs am-text-danger',
//						clickEvent : "vue.del('" + aData.id + "')"
//					})
//					
//
//					content = getButtonBar(buttons);
//					return content;
//				},
//				"sWidth" : "20%"
//			}
			];
	function getButtonBar(buttons) {
		var content = '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
		for (i = 0; i < buttons.length; i++) {
			content += '<button onclick="'
					+ buttons[i].clickEvent
					+ '" class="' +buttons[i].class+ '">'
					+ '<span class="' + buttons[i].icon + '"></span>'
					+ buttons[i].name + '</button>';
		}
		content += '</div></div>';
		return content;
	}
	// 每行的回调 生成自定义td
	function fnRowCallback(nRow, aData, iDisplayIndex) {
		return nRow;
	}
	// 传递的自定义参数
	//由于JavaScript没有提供原生的trim()函数。
	///$.trim()是jQuery提供的函数，用于去掉字符串首尾的空白字符。
	function fnServerParams(aoData) {

	}
	
	initializeTable("example", aoColumns, fnRowCallback, ajaxpath+"page",fnServerParams);

});

// 搜索
$(".search").click(function() {
	$("#example").dataTable().fnDraw(true);
});

//// 键盘回车键更新
//$("#searchParam input").keypress(function(event) {
//	if (event.charCode == 13) {
//		$("#example").dataTable().fnDraw(true);
//	}
//});
//
//// 新增
//$(".sAdd").click(function() {
//	if(($('#updateFrom')[0] || '')!='')
//		$('#updateFrom')[0].reset();
//	$("#updateFrom").validator('destroy');
//	vue.add();
//});
//
//// 批量删除
//$(".sdeleteAll").click(function() {
//	var array = getCheck();
//	if (null == array || array.length < 1) {
//		openAlertMsg("请至少选择一项");
//		return false;
//	}
//	openConfirm(0, "操作确认", "确认删除所有选中的数据吗？", function() {
//		toAjaxDetail(ajaxpath+"batchdel", {
//			"ids" : array
//		}, "删除", "example");
//	});
//});
//
//
//// 获得所有选中的check
//function getCheck(){
//	var array=new Array();
//	$("#example input[type=checkbox]:checked").each(function(){
//		var idval=$(this).data("id") || "";
//		if(idval!="")
//			array.push(idval);
//	});
//	return array;
//}

