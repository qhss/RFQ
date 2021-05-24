var ajaxpath="photo/";
var vue = new Vue({
	el:"#infopanel",
	data:{
		obj:{
			id:'',
			mainpic:'',
			pics:[],
		},//编辑对象
		
	},
	created:function(){
		var that = this;
		//加载数据
		that.load();
	},
	methods:{
		load:function(id){
			var that = this;
			
			$.post(ajaxpath+"one",{"id":id},function(result){
				if(result.success==1){
					that.obj = result.data;
				}
				else
					openAlertMsg(result.errorMessage);
			});
		},
		//保存
		save:function(){
			
			var that = this;
			openConfirm(0,"操作确认","确认修改数据吗?",function(index){
				layer.close(index);
				toAjaxCRUDCallBack(ajaxpath+"save",that.obj,function(){
					that.load();
				});
			});
			
		},
		setpics:function(imgurl){
			this.obj.pics.push(imgurl);
		},
		remove:function(index){
			this.obj.pics.splice(index,1);
		}
		
		
	}
   
});

vue.$nextTick(function(){
	var timer=setInterval(function(){
		if(bindFileEvent())
			clearInterval(timer);
	}, 500);
	
});







