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
		item:{
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
		},
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
		for (var i = 0; i < 12; i++){
			data.setMonth(data.getMonth());//每次循环一次 月份值减1
			var m = data.getMonth() + 1;
			m = m < 10 ? "0" + m : m;
			var myMonth=data.getFullYear() + "-" + m;
			var obj={
				monthDate:myMonth,
				monthCount:0
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
		}
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

function AddTableRow() {
	var Table = document.getElementById("tab");
	NewRow = Table.insertRow();
	NewCell1 = NewRow.insertCell();
	NewCell2 = NewRow.insertCell();
	NewCell3 = NewRow.insertCell();
	NewCell4 = NewRow.insertCell();
	NewCell5 = NewRow.insertCell();
	NewCell6 = NewRow.insertCell();
	NewCell7 = NewRow.insertCell();
	NewCell8 = NewRow.insertCell();
	NewCell9 = NewRow.insertCell();
	NewCell10 = NewRow.insertCell();
	NewCell11 = NewRow.insertCell();
	NewCell12 = NewRow.insertCell();
	NewCell13 = NewRow.insertCell();
	NewCell14 = NewRow.insertCell();
	NewCell15 = NewRow.insertCell();
	NewCell16 = NewRow.insertCell();
	NewCell17 = NewRow.insertCell();
	NewCell18 = NewRow.insertCell();
	NewCell19 = NewRow.insertCell();
	NewCell20 = NewRow.insertCell();
	NewCell21 = NewRow.insertCell();
	NewCell22 = NewRow.insertCell();
	NewCell23 = NewRow.insertCell();
	NewCell24 = NewRow.insertCell();
	NewCell25 = NewRow.insertCell();
	NewCell26 = NewRow.insertCell();
	NewCell1.innerHTML = "" +
		"\t\t\t\t\t\t\t\t\t\t<div class=\"am-input-group\">\n" +
		"\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" style=\"width:100%\" v-model=\"item.itemCode\">\n" +
		"\t\t\t\t\t\t\t\t\t\t\t<span class=\"am-input-group-btn\">\n" +
		"\t\t\t\t\t\t\t\t\t\t<button @click=\"customerSearch\" class=\"am-btn am-btn-default\" type=\"button\">查看</button>\n" +
		"\t\t\t\t\t\t\t\t\t\t</span>\n" +
		"\t\t\t\t\t\t\t\t\t\t</div>";
	NewCell2.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell3.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell4.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell5.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell6.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell7.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell8.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell9.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell10.innerHTML = "<button type=\"button\" @click=\"save\" class=\"am-btn am-btn-primary\">查找</button>";
	NewCell11.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell12.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell13.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell14.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell15.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell16.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell17.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell18.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell19.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell20.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell21.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell22.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell23.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell24.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell25.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
	NewCell26.innerHTML = "<input type=\"text\" name=\"project.website\"/>";
}
