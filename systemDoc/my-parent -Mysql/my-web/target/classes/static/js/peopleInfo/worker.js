var ajaxpath="worker/";
var vue = new Vue({
	el:"#editorPanel",
	data:{
		obj:{},// 分拣设备
	},
	created:function(){
		var that = this;
		
	},
	methods:{
		add:function(){
			
			openCustom("添加", "500", "400", $("#editorPanel"),'vue.create()');
		},
		create:function(){
			var that = this;
			
			var validator = validators("updateFrom");// 验证表单
			if (!validator) {
				return false;
			}
			
			var data={
				"workerType":that.obj.workerType,
				"workerName":that.obj.workerName,
				"workerPrice":that.obj.workerPrice,
				"workerAge":that.obj.workerAge,
				"workerPhone":that.obj.workerPhone
			};
			toAjaxDetail(ajaxpath+"save",data,"添加","example");
		},
		//编辑
		edit:function(id){
			var that = this;
			$('#updateFrom')[0].reset();
			$("#updateFrom").validator('destroy');
			
			$.post(ajaxpath+"one",{"id":id},function(result){
				if(result.success==1){
					that.obj = result.data;
					openCustom("修改", "500", "400", $("#editorPanel"),'vue.update()');
				}
				else
					openAlertMsg(result.errorMessage);
			});
		},
		// 编辑保存
		update:function(){
			var validator = validators("updateFrom");// 验证表单
			if (!validator) {
				return false;
			}
			
			var that = this;
			openConfirm(0,"操作确认","确认修改数据吗?",function(){
				toAjaxDetail(ajaxpath+"save",{
					"id":that.obj.id,
					"workerType":that.obj.workerType,
					"workerName":that.obj.workerName,
					"workerPrice":that.obj.workerPrice,
					"workerAge":that.obj.workerAge,
					"workerPhone":that.obj.workerPhone
				},"修改","example");
			});
			
		},
		// 删除
		del:function(id){
			openConfirm(0,"操作确认","确认删除吗?",function(){
				toAjaxCRUDCallBack(ajaxpath+"del",{
					"id":id,
				},function(result){
					if(result.success==1){
						openAlertMsg("删除成功");
						$("#example").dataTable().fnDraw(true);
					}else{
						openAlertMsg(result.errorMessage);
					}
				});
			});
			
		},
		delBatch:function(ids){
			
		}
		
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
			},
			{
				"mDataProp" : "workerName",
				"sTitle" : "员工姓名",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false
			},{
				"mDataProp" : "workerType",
				"sTitle" : "员工职别",
				"sDefaultContent" : "",
				"render" : function(oObj, sVal) {
					if(oObj==0)
						return '酒店高管';
					if(oObj==1)
						return '部门组长';
					else
						return '普通职工';
				},

				"sWidth" : "10%",
				"bSortable" : false
			},
			{
				"mDataProp" : "workerAge",
				"sTitle" : "年龄",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false
			}
			,{
				"mDataProp" : "workerPrice",
				"sTitle" : "工资",
				"sDefaultContent" : "",
				"sWidth" : "15%",
				"bSortable" : false

			},{
				"mDataProp" : "workerPhone",
				"sTitle" : "联系电话",
				"sDefaultContent" : "",
				"sWidth" : "20%",
				"bSortable" : false

			},{
				"mDataProp" : "createTime",
				"sTitle" : "更新时间",
				"sDefaultContent" : "",
				"sWidth" : "20%",
				"render":function(oObj,sVal){
					return formatDate(oObj/1000);
				},
				"bSortable" : false
			},
			{
				"mDataProp" : "id",
				"sTitle" : "操作",
				"sDefaultContent" : "",// 此列默认值为""，以防报错
				"bSortable" : false,
				"render" : function(oObj, sVal, aData) {
					var buttons = [];
					buttons.push({
						name : '编辑',
						icon : 'am-icon-pencil',
						class : 'am-btn am-btn-default am-btn-xs am-text-primary',
						clickEvent : "vue.edit('" + aData.id + "')"
					})
					buttons.push({
						name : '删除',
						icon : 'am-icon-remove',
						class : 'am-btn am-btn-default am-btn-xs am-text-danger',
						clickEvent : "vue.del('" + aData.id + "')"
					})
					

					content = getButtonBar(buttons);
					return content;
				},
				"sWidth" : "20%"
			}];
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

// 键盘回车键更新
$("#searchParam input").keypress(function(event) {
	if (event.charCode == 13) {
		$("#example").dataTable().fnDraw(true);
	}
});

// 新增
$(".sAdd").click(function() {
	if(($('#updateFrom')[0] || '')!='')
		$('#updateFrom')[0].reset();
	$("#updateFrom").validator('destroy');
	vue.add();
});

// 批量删除
$(".sdeleteAll").click(function() {
	var array = getCheck();
	if (null == array || array.length < 1) {
		openAlertMsg("请至少选择一项");
		return false;
	}
	openConfirm(0, "操作确认", "确认删除所有选中的数据吗？", function() {
		toAjaxDetail(ajaxpath+"batchdel", {
			"ids" : array
		}, "删除", "example");
	});
});


// 获得所有选中的check
function getCheck(){
	var array=new Array();
	$("#example input[type=checkbox]:checked").each(function(){
		var idval=$(this).data("id") || "";
		if(idval!="")
			array.push(idval);
	});
	return array;
}

