var ajaxpath="price/";
var vue = new Vue({
	el:"#panel",
	data:{
		obj:{
			id:'',
			hotelRoomId:'',
			date:'',
			planCount:0,
			price:0,
			memberPrice:0,
		},
		//默认价格
		roomprice:{
			planCount:0,
			price:0,
			memberPrice:0,
			id:'',
		},
		rooms:[],
		editmode:0
	},
	created:function(){
		var that = this;

		$.post(ajaxpath+"allroom","",function(result){
			if(result.success==1){
				that.rooms = result.data;
			}
		});
	},
	methods:{
		//加载默认价格
		loadprice:function(){
			var that=this;
			var postdata={"id":this.roomprice.id};
			$.post(ajaxpath+"loadnormal",postdata,function(result){
				if(result.success==1){
					that.roomprice = result.data;
				}
				else
					openAlertMsg(result.errorMessage || "");
			});
		},
		//保存默认价格
		saveprice:function(){
			var that=this;
			var postdata=this.roomprice;
			if((postdata.id || '')==''){
				openAlertMsg("请选择房型");
				return;
			}
			$.post(ajaxpath+"savenormal",postdata,function(result){
				if(result.success==1){
					//that.loadprice();
				}
				else{
					openAlertMsg(result.errorMessage || "");
					that.loadprice();
				}
			});
			//
		},
		
		add:function(){
			if((this.obj.hotelRoomId || '')==''){
				openAlertMsg("请选择房型");
				return;
			}
			openCustom("添加", "600", "500", $("#editorPanel"),'vue.create()');
		},
		create:function(){
			var that = this;
			
			var validator = validators("updateFrom");// 验证表单
			if (!validator) {
				return false;
			}
			
			var data=that.obj;
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
					openCustom("修改", "600", "500", $("#editorPanel"),'vue.update()');
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
			openConfirm(0,"操作确认","确认修改数据吗?",function(index){
				var postdata=that.obj;
				$.post(ajaxpath+"save",postdata,function(result){
					layer.close(index);
					if(result.success==1){
						$("#example").dataTable().fnDraw(true);
					}
					else
						openAlertMsg(result.errorMessage);
				});
			});
			
		},
		// 修改启用、禁用状态
		updateCanceled:function(id,canceled,text){
			openConfirm(0,"启用/禁用","确认"+text+"吗?",function(){
				toAjaxDetail(ajaxpath+"cancel",{
					"id":id,
					"canceled":canceled
				},text,"example");
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
			}
//	,{
//				"mDataProp" : "date",
//				"sTitle" : "周",
//				"sDefaultContent" : "",
//				"sWidth" : "10%",
//				"bSortable" : false,
//				"render" : function(oObj, sVal, aData) {
//					if((aData.coverUrl || '')=='')
//						return '';
//					else
//						return "<img style='width:60px;' src='"+aData.coverUrl+"' />";
//				}
//
//			}
	,{
				"mDataProp" : "date",
				"sTitle" : "日期",
				"sDefaultContent" : "",
				"sWidth" : "30%",
				"bSortable" : false,
				"render" : function(oObj, sVal, aData) {
					return formatDateByYYYYMMD(oObj);
				}
				

			},{
				"mDataProp" : "planCount",
				"sTitle" : "房间数",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false

			},{
				"mDataProp" : "price",
				"sTitle" : "门市价格",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false

			},{
				"mDataProp" : "memberPrice",
				"sTitle" : "会员价格",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false

			},{
				"mDataProp" : "canceled",
				"sTitle" : "启用/禁用状态",
				"sDefaultContent" : "",
				
				"render" : function(oObj, sVal) {
					if(oObj==0)
						return '启用';
					else
						return '禁用';
				},
				"sWidth" : "10%",
				"bSortable" : false
			},{
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

					if (aData.canceled == 0) // 0=正常
						buttons.push({
							name : '禁用',
							class : 'am-btn am-btn-default am-btn-xs am-text-warning',
							icon : 'am-icon-ban',
							clickEvent : "vue.updateCanceled('" + aData.id
								+ "',1,'禁用')"
						})
					if (aData.canceled == 1) // 1=禁用
						buttons.push({
							name : '启用',
							class : 'am-btn am-btn-default am-btn-xs am-text-success',
							icon : 'am-icon-circle-o',
							clickEvent : "vue.updateCanceled('" + aData.id
								+ "',0,'启用')"
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
				"sWidth" : "30%"
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
	function fnServerParams(aoData) {
		aoData.push({
			"name" : "startDate",
			"value" : $("#startDate").val().trim()
		},{
			"name" : "endDate",
			"value" : $("#endDate").val()
		},{
			"name":"roomId",
			"value":vue.obj.hotelRoomId
		}
		);
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
	vue.equipment = {};
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



vue.$nextTick(function(){
	var timer=setInterval(function(){
		if(bindOptionEvent())
			clearInterval(timer);
	}, 600);
	
});


function bindOptionEvent(){
	var $radios = $('[name="optTypeName"]');
	$radios.on('change',function() {
		var roomid= $radios.filter(':checked').attr("raw");
		vue.roomprice.id=roomid;
		vue.obj.hotelRoomId=roomid;
		//加载默认价格
		vue.loadprice();
		//加载房型价格
		$("#example").dataTable().fnDraw(true);
	});
	return true;
}

$(function(){
	bindOptionEvent();
})
