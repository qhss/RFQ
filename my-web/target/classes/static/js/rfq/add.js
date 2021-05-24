var ajaxpath="rfq/";
var vue = new Vue({
	el:"#panel",
	data:{
		dataId:'',
		customer:{
			cardCode:'',
			cardName:'',
			cardEname:'',
			currency:'',
			fax:'',
			contact:'',
			cusClass:''

		},
		project:{
			projectName:'',
			business:'',
			location1:'',
			location2:'',
			endCustomer:'',
			lifeTime:'',
			endProduct:'',
			mpDate:'',
			website:'',
			remark:''

		},
		items:[{
			xxx:'',
			itemCode:'',
			brand:'',
			pName:'',
			series:'',
			zzCountry:'',
			cardItemCode:'',
			EAU:'',
			quantity:'',
			notaxPrice:'',
			ltbPrice:'',
			taxPrice:'',
			infoPrice:'',
			checker:'',
			effectivedate:'',
			SD:'',
			PCN:'',
			moq:'',
			units:'',
			lt:'',
			subtotal:'',
			remark:'',
			rfqId:'',
			dataSourceId:''
		}],
		spaNames:[],
		dateArr:[
			{
				monthDate:'',
				monthCount:''
			}
		],
		monthTotal:0,
		editmode:0
	},
	created:function(){
		var that=this;
		//获取当前及接下来12个月的日期
		var dataArr = [];
		var data = new Date();
		var year = data.getFullYear();
		data.setMonth(data.getMonth()-1);
		for (var i = 0; i < 12; i++){
			data.setMonth(data.getMonth()+1);//每次循环一次 月份值加1
			var m = data.getMonth() + 1;
			m = m < 10 ? "0" + m : m;
			var myMonth=data.getFullYear() + "-" + m;
			var obj={
				monthDate:myMonth,
				monthCount:''
			}
			dataArr.push(obj);
		}
		that.dateArr=dataArr;

		//获取到所有的endCustomer代码
		$.post(ajaxpath+"allApplys","",function(result){
			if(result.success==1){
				that.spaNames = result.data;
			}
		});
	},
	methods:{
		add:function(){
			if((this.dataId|| '')==''){
				openAlertMsg("请选择要报价的公司");
				return;
			}
			this.editmode=1;
			// openCustom("添加", "600", "500", $("#editorPanel"),'vue.create()');
		},
		create:function(){

		},
		back:function(){
			this.editmode=0;
			this.customer={};
			this.project={};
			this.item={};
		},
		save:function(){

		},

		customerSearch:function(){
			var that = this;
			var data={
				"cardCode":that.customer.cardCode,
                "dataId":that.dataId,
			};
			$.post(ajaxpath+"checkCustomer",data,function(result){
				if(result.success==1){
					that.customer = result.data;
				}
				else{
					that.customer={}
					openAlertMsg(result.errorMessage);
				}

			});
		},
		itemCodeSearch:function(){

			openAlertMsg("暂未开发");
		},
		total: function(){
			var that =this;
			var  valueArr = document.getElementsByName("monthCount") ;
			var sumValue=0;
			for (var i=0;i<valueArr.length;i++ )
			{
				if (valueArr[i].value == "" || valueArr[i].value == null || valueArr[i].value == "null" || valueArr[i].value == undefined){
					valueArr[i].value=0;
				}
				sumValue +=parseInt(valueArr[i].value);
			}
			that.monthTotal=sumValue;
			console.log(that.dateArr);
		},
		addTableRow: function () {
			var that =this;
			var obj={
				xxx:'',
				itemCode:'',
				brand:'',
				pName:'',
				series:'',
				zzCountry:'',
				cardItemCode:'',
				EAU:'',
				quantity:'',
				notaxPrice:'',
				ltbPrice:'',
				taxPrice:'',
				infoPrice:'',
				checker:'',
				effectivedate:'',
				SD:'',
				PCN:'',
				moq:'',
				units:'',
				lt:'',
				subtotal:'',
				remark:'',
				rfqId:'',
				dataSourceId:''
			}
			that.items.push(obj);
		},
		deleteChecked: function(index){
			var that =this;
			that.items.splice(index,1)
		}
	}
});

// 新增
// $(".sAdd").click(function() {
// 	if(($('#updateFrom')[0] || '')!='')
// 		$('#updateFrom')[0].reset();
// 	$("#updateFrom").validator('destroy');
// 	vue.equipment = {};
// 	vue.add();
// });
function bindOptionEvent(){
	var $radios = $('[name="optTypeName"]');
	$radios.on('change',function() {
		var dataId= $radios.filter(':checked').attr("raw");
		vue.dataId=dataId;
	});
	return true;
}

$(function(){
	bindOptionEvent();
})

$(function() {
	//搜索按钮
	$("#customerSearch").keypress(function (e) {
		var key = window.event ? e.keyCode : e.which;
		if (key.toString() == "13") {
			vue.customerSearch()
			return false;
		}
	});
})
// $('.js-typeahead').typeahead({
// 	order:"asc",
// 	source: {
// 		groupName: {
// 			// Array of Objects / Strings
// 			data: this.spaNames
// 		}
// 	},
// 	callback: {
// 		onInit:function() { }
// 	}
// });