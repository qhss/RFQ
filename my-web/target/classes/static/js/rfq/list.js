var ajaxpath="rfq/";
var vue = new Vue({
	el:"#panel",
	data:{
		obj:{

		},
		editmode:0
	},
	created:function(){
		var that = this;
	},
	methods:{
		detail:function(id){
			this.editmode=1
		},
		back:function(){
			this.editmode=0
		}
	}
});


$(function() {
	// 定义列
	var aoColumns = [
			// {
			// 	"sClass" : "table-check",
			// 	"sTitle" : "<input type=\"checkbox\" class=\"checkAll\"/>",
			// 	"mDataProp" : "id",
			// 	"render" : function(oObj, sVal) {
			// 		return '<input type="checkbox" id="' + oObj + '"  data-id="'+oObj+'" />';
			// 	},
			// 	"sDefaultContent" : "",
			// 	"sWidth" : "3%",
			// 	"bSortable" : false
			// },
	{
				"mDataProp" : "type",
				"sTitle" : "发送PM审核",
				"sDefaultContent" : "",

				"render" : function(oObj, sVal) {
					if(oObj==0)
						return '是';
					else
						return '否';
				},
				"sWidth" : "5%",
				"bSortable" : false

			},{
				"mDataProp" : "id",
				"sTitle" : "单号",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false

			},{
				"mDataProp" : "cardCode",
				"sTitle" : "客户代码",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false

			},
			{
				"mDataProp" : "cardName",
				"sTitle" : "客户名称",
				"sDefaultContent" : "",
				"sWidth" : "8%",
				"bSortable" : false

			},
			{
				"mDataProp" : "docdate",
				"sTitle" : "日期",
				"sDefaultContent" : "",
				"sWidth" : "10%",
				"bSortable" : false

			},{
			"mDataProp" : "currency",
			"sTitle" : "币别",
			"sDefaultContent" : "",
			"sWidth" : "5%",
			"bSortable" : false
			},
		{
			"mDataProp" : "total",
			"sTitle" : "金额",
			"sDefaultContent" : "",
			"sWidth" : "8%",
			"bSortable" : false
		},
		{
			"mDataProp" : "checkStatus",
			"sTitle" : "状态",
			"sDefaultContent" : "",
			"sWidth" : "5%",
			"bSortable" : false
		},
		{
			"mDataProp" : "priceType",
			"sTitle" : "类型",
			"sDefaultContent" : "",
			"sWidth" : "5%",
			"bSortable" : false
		},
		// {
		// 	"mDataProp" : "remark",
		// 	"sTitle" : "备注",
		// 	"sDefaultContent" : "",
		// 	"sWidth" : "10%",
		// 	"bSortable" : false
		// },

		{
				"mDataProp" : "id",
				"sTitle" : "操作",
				"sDefaultContent" : "",// 此列默认值为""，以防报错
				"bSortable" : false,
				"render" : function(oObj, sVal, aData) {
					var buttons = [];
					buttons.push({
						name : '详情',
						icon : 'am-icon-pencil',
						class : 'am-btn am-btn-default am-btn-xs am-text-primary',
						clickEvent : "vue.detail('" + aData.id + "')"
					})
					content = getButtonBar(buttons);
					return content;
				},
				"sWidth" : "5%"
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
			"name" : "kId",
			"value" : $("#kId").val().trim()
		},{
			"name" : "kCardCode",
			"value" : $("#kCardCode").val().trim()
		},{
			"name" : "kItemcode",
			"value" : $("#kItemcode").val().trim()
		});
	}
	
	initializeTable("example", aoColumns, fnRowCallback, ajaxpath+"page",fnServerParams);

});
// 搜索
$(".search").click(function() {
	$("#example").dataTable().fnDraw(true);
});
