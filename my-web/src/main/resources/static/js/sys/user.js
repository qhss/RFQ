var pId = -1; //是否为根节点的标志，0为根节点，其他为非根节点
var depId = -1;
var rootOrgId;
//初始化组织机构树
$(function () {
	init();
});
function zTreeOnClick(event, treeId, treeNode) {
	if(treeNode.pid == 0){
		rootOrgId = treeNode.id;
	}
	$.post("/depart/getNodes",{"id":rootOrgId},function(result){
		vue.departs=result.data;
	});
	depId = treeNode.id;
	pId = treeNode.pid;
	$("#example").dataTable().fnDraw(true);
}
function zTreeOnAsyncSuccess(){

}


$(function() {
	// 定义列
	var aoColumns=[
					{
						"sClass" : "table-check",// 样式
						"sTitle" : "<input type=\"checkbox\" class=\"checkAll\"/>",// 表头
						"mDataProp" : "id",// 关联key
						"render" : function(oObj, sVal) {// 需要对value进行操作时用
							return '<input type="checkbox" id="'
									+ oObj + '"  />';
						},
						"sDefaultContent" : "",// 此列默认值为""，以防数据中没有此值，DataTables加载数据的时候报错
						"bSortable" : false,// 不进行排序
						"sWidth" : "5%"// 除checkbox其余必须设定列宽
					}, {
						"mDataProp" : "loginName",
						"sTitle" : "登陆账号",
						"sDefaultContent" : "",
						"sWidth" : "15%"// 除checkbox其余必须设定列宽
					// 防止自动列宽有大有小
					}, {
						"mDataProp" : "userName",
						"sTitle" : "真实姓名",
						"sDefaultContent" : "",
						"sWidth" : "15%"
					}, 
//					{
//						"mDataProp" : "departName",
//						"sTitle" : "所属部门",
//						"sDefaultContent" : "",
//						"sWidth" : "15%"
//					},
					{
						"mDataProp" : "phone",
						"sTitle" : "手机号码",
						"sDefaultContent" : "",
						"sWidth" : "10%"
					}, {
						"mDataProp" : "sex",
						"sTitle" : "性别",
						"sDefaultContent" : "",
						"render" : function(oObj, sVal) {
							return oObj == 0 ? '女' : '男';
						},
						"sWidth" : "5%"
					}, {
						"mDataProp" : "createTime",
						"sTitle" : "创建时间",
						"sDefaultContent" : "",
						"sClass" : "am-hide-sm-only",
						"render" : function(oObj, sVal) {
							return formatDate(oObj);
						},
						"sWidth" : "10%"
					}, {
						"mDataProp" : "canceled",
						"sTitle" : "状态",
						"sDefaultContent" : "",
						"sClass" : "table-title",
						"render" : function(oObj, sVal) {
							return oObj == 0 ? EN_BADGE :DIS_BADGE;
						},
						"sWidth" : "10%"
					}, {
						"mDataProp" : "id",
						"sTitle" : "操作",
						"sDefaultContent" : "",// 此列默认值为""，以防数据中没有此值，DataTables加载数据的时候报错
						"bSortable" : false,
						"render" : function(oObj, sVal, aData) {
							var content='<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs"><button onclick=\'sEdit("'
								+aData.id
								+'")\' class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>编辑</button>';
							if (aData.canceled == '0'){
								content+='<button onclick=\'sDisable("'
									+aData.id
									+'")\' class="am-btn am-btn-default am-btn-xs"><span class="am-icon-disable"></span> 禁用</button>';
							}
							if (aData.canceled == '1'){
								content+='<button onclick=\'sOpen("'
									+aData.id
									+'")\' class="am-btn am-btn-default am-btn-xs"><span class="am-icon-open"></span>启用</button>';
							}
							if(aData.isWxCreate!=1){//微信创建管理员账户不能删除
								content+='<button onclick=\'sDelete("'
									+aData.id
									+'")\' class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>删除</button>'
									+'</div></div>';
							}
							return content;
						},
						"sWidth" : "25%"
					} ];
	
	// 每行的回调 生成自定义td
	function fnRowCallback(nRow, aData, iDisplayIndex) {

		return nRow;
	}
	
	// 传递的自定义参数
	function fnServerParams(aoData) {
		aoData.push({
			"name" : "keyword",
			"value" : $("#keyword").val().trim()
		},{
			"name" : "departId",
			"value" : depId
		},{
			"name" : "pId",
			"value" : pId
		});
	}
	
	// 初始化表格 tableID 自定义列 每行回调 url 自定义参数
	initializeTable("example",aoColumns,fnRowCallback,"user/page",fnServerParams)


	toAjaxCRUDCallBack("role/all",null,function(role){
		$.each(role.data,function(index,item){
			$(".role_check").append('<label class="am-checkbox-inline"><input type="checkbox" class="userBox" id=role_'+item.id+' name="roleId" required mincheckbox="1"  value="'+item.id+'">'+item.name+'</label>');
		});
	});

}
);


// 搜索
$(".search").click(function() {
	$("#example").dataTable().fnDraw(true);
});

var vue = new Vue({
	el:"#alert",
	data:{
		user:{},
		departs:[] //所有可选部门
	},
	created:function(){
	/*	var that=this;
		$.post("depart/all",null,function(result){
			  that.departs=result.data;
		});*/
	},
	methods:{
		//新增用户
		addUser:function(){
			addOrUpdateInitialize();
			
			$("#updateLoginName").attr("disabled",false);// 设置用户名为可编辑
			openCustom("添加用户","500","550",$("#alert"),'vue.addOrUpdate("新增")');
		},
		//编辑用户资料前
		editUser:function(userId){
			addOrUpdateInitialize();
			//$("#password").removeAttr("required");// 移除密码验证
			$("#updateLoginName").attr("disabled",true);// 设置用户名为不可编辑
			var that = this;
			/**/
			toAjaxCRUDCallBack("user/queryUserById",{"userId":userId},function(data){
				that.user = data;
				that.user.password = '';
				
				$.each(data.roles,function(index,item){

					$("#role_"+item.id).prop('checked',true);// 选中角色
				});
				openCustom("编辑用户","500","550",$("#alert"),'vue.addOrUpdate("修改")');
			});
		},
		//添加或编辑用户
		addOrUpdate:function(str){
			
			var that = this;
			var roleIds = getAllCheckRole();
			var depart=($("#depart").val()+"").trim();
			
			var validator=validators("updateFrom");// 验证表单
			if(!validator){
				return false;
			}
			
			var id=$("#id").val() || '';
			if(id==''){
				var rePassword=$("#repassword").val();
				if(rePassword!=this.user.password)
				{
					openAlertMsg("两次密码输入不一致！");
					$("repassword").focus();
					return;
				}
			}
			
			toAjaxCRUDCallBack("user/loginNameExist",{"userId":id,"loginName":$("#updateLoginName").val().trim()},function(result){
				if(result.data){
					validat("updateLoginName");
					openAlertMsg("账号已存在！");
					return false;
				}else {
					
					var postData={
						"id":that.user.id,
						"loginName":that.user.loginName,
						"password":that.user.password,
						"sex":that.user.sex,
						"userName":that.user.userName,
						"phone":that.user.phone,
						"departId":that.user.departId,
						
						"roleId":roleIds
					};
					toAjaxDetail("user/addOrUpdateUser",postData, str+"用户", "example");
										
				}
			});
		},
		//数据置空
		blankData:function(){
			this.user = {};
		}
	}
});


function fleshTable () { // 刷新表格
	$("#example").dataTable().fnDraw(true);
}

// 添加和修改需要执行的公共事件
function addOrUpdateInitialize(){
	vue.blankData();

	$('#updateFrom')[0].reset();// 重置表单
	$("#loginNameError").hide();// 隐藏错误提示
	$("#updateFrom").validator('destroy');// 销毁表单验证
}

/**
 * 修改
 */
function sEdit(id) {
	vue.editUser(id);
}

/**
 * 新增
 */
$(".sAdd").click(function(){
	vue.addUser();
});


//获取所有选中角色
function getAllCheckRole(){
	var array=new Array();
	$(".role_check input[type=checkbox]:checked").each(function(){
		array.push(this.value);
	});
	return array;
}

/**
 * 禁用
 */
function sDisable(id) {
	openConfirm(0, "提示", "确认禁用该用户吗？", function() {
		var postData= {
			"id" : id,
			"state" : 1
		};
		toAjaxDetail("user/state",postData, "禁用", "example");
	});
}

/**
 * 启用
 */
function sOpen(id) {
	openConfirm(0, "提示", "确认启用该用户吗？", function() {
		var postData= {
			"id" : id,
			"state" : 0
		};
		toAjaxDetail("user/state", postData, "启用", "example");
	});
}


/**
 * 批量删除
 */
$(".sdeleteAll").click(function() {
	var array=getCheck();
	if(null==array||array.length<1){
		openAlertMsg("请选择一个用户");
		return false;
	}
	openConfirm(0,"删除","确认删除此用户吗？",function(){
		toAjaxDetail("user/batchdel",{"ids":array},"删除",
		"example");
	});
});


/**
 * 删除
 */
function sDelete(id) {
	openConfirm(0, "提示", "确认删除该用户吗？", function() {
		toAjaxDetail("user/del", {
			"id" : id
		}, "删除", "example");
	});
}


// 获得所有选中的check
function getCheck(){
	var array=new Array();
	$("#example input[type=checkbox]:checked").each(function(){
		array.push(this.id);
	});
	return array;
}

