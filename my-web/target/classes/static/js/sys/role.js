$(function() {
	// 定义列
	var aoColumns = [ {
		"sClass" : "",// 样式
		"sTitle" : "<input type=\"checkbox\" class=\"checkAll\"/>",// 表头
		"mDataProp" : "id",// 关联key
		"render" : function(oObj, sVal) {// 需要对value进行操作时用
			return '<input type="checkbox" id="' + oObj + '"  />';
		},
		"sDefaultContent" : "",// 此列默认值为""，以防数据中没有此值，DataTables加载数据的时候报错
		"bSortable" : false
	// 不进行排序
	}, {
		"mDataProp" : "name",
		"sTitle" : "角色名称",
		"sDefaultContent" : ""
	// 防止自动列宽有大有小
	}, {
		"mDataProp" : "descripe",
		"sTitle" : "说明",
		"sDefaultContent" : "",
		"render" : function(oObj, sVal) {
			if(oObj==null)
				return "";
			else{
				var reg=/\r\n/g;
				return oObj.replace(reg,'<br/>');
			}
		}
	}, {
		"mDataProp" : "canceled",
		"sTitle" : "状态",
		"sDefaultContent" : "",
		"render" : function(oObj, sVal) {
			return oObj == 0 ? EN_BADGE :DIS_BADGE;
		}
	}, {
		"mDataProp" : "id",
		"sTitle" : "操作",
		"sDefaultContent" : "",// 此列默认值为""，以防数据中没有此值，DataTables加载数据的时候报错
		"bSortable" : false,
		"render" : function(oObj, sVal, aData) {
			var content = '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs"><button onclick=\'sEdit("'
				+ aData.id
				+ '","'+aData.name+'")\' class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>'
				+(aData.type==1?"查看":"编辑")+'</button>';
			if (aData.canceled == 0 && aData.type!=1) {
			content += '<button onclick=\'sDisable("'
					+ aData.id
					+ '")\' class="am-btn am-btn-default am-btn-xs"><span class="am-icon-lock"></span> 禁用</button>';
			}
			if (aData.canceled == 1  && aData.type!=1) {
				content += '<button onclick=\'sOpen("'
						+ aData.id
						+ '")\' class="am-btn am-btn-default am-btn-xs"><span class="am-icon-unlock"></span> 启用</button>';
			}
			content += '<button onclick=\'sPermissions("'
					+ aData.id+'",'+aData.type
					+ ')\' class="am-btn am-btn-default am-btn-xs am-text-warning am-hide-sm-only"><span class="am-icon-permissions"></span>权限</button>'
					+ '';
			if(aData.type!=1){
				content += '<button onclick=\'sDelete("'
					+ aData.id
					+ '")\' class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>删除</button>'
					+ '</div></div>';
			}
		return content;
		},
		"sWidth" : "30%"// 除checkbox其余必须设定列宽
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
		});
	}
	initializeTree();
	// 初始化表格 tableID 自定义列 每行回调 url 自定义参数
	initializeTable("example", aoColumns, fnRowCallback,
			"role/page", fnServerParams);
});

// 搜索
$(".search").click(function() {
	$("#example").dataTable().fnDraw(true);
});

// 添加和修改需要执行的公共事件
function addOrUpdateInitialize() {
	$('#updateFrom')[0].reset();// 重置表单
	$("#error").hide();// 隐藏错误提示
	$("#updateFrom").validator('destroy');// 销毁表单验证
}


var fristTree;
function initializeTree(){
	toAjaxCRUDCallBack("role/allPermissions",null,function(treedata){// 获取数据
		fristTree=$('#firstTree').tree({
			dataSource : function(options, callback) {
				callback({data: options.products || treedata});
			},
			multiSelect : true,
			cacheItems : true,
			folderSelect : false
		});
		// 绑定事件
		treeHandle();
	});
}

/**
 * 权限
 * id:角色id
 * issysdef:==1表示系统预设
 */
function sPermissions(id,issysdef) {
	
	fristTree.tree('closeAll');
	fristTree.tree('discloseAll');
	colseChoose();
	toAjaxCRUDCallBack("role/queryPermissionsByRole",{"roleId":id},function(array){
		$.each(array,function(index,item){
			fristTree.tree('selectItem',$("#permissios_"+item));
			
		});
		openCustom("编辑权限", "280", "500", $("#firstTree"), 'updatePermissions("'+id+'",'+issysdef+')');
	});
}

/**
 * 修改权限
 */
function updatePermissions(id,issysdef){
	
	if(issysdef==1){
		openAlertMsg("系统预设角色,无法修改权限,请联系系统管理员!");
		return false;
	}
	var array=getChoose();
	if(array.length<=0){
		openAlertMsg("请至少选择一项权限!");
		return false;
	}
	openConfirm(0, "修改", "确认要修改此角色所对于的权限吗？", function() {
		toAjaxDetail("role/updatePermissions", {
			"roleId":id,
			"menuIds" : array
		}, "修改", "example");
	});
}



/**
 * 修改
 */
function sEdit(id,name) {

	addOrUpdateInitialize();
	
	toAjaxCRUDCallBack("role/id", {
		"roleId" : id
	},function(data){
		$("#id").val(data.id);

		$("#updateName").val(data.name);
		$("#updateDescription").val(data.descripe);
		
		if(data.type==1){
			$("#updateName").attr("readOnly","readOnly");
			$("#updateDescription").attr("readOnly","readOnly");
		}else
		{
			$("#updateName").removeAttr("readOnly");
			$("#updateDescription").removeAttr("readOnly");
		}
		

		openCustom("编辑角色", "450", "300", $("#alert"), 'addOrUpdate("修改")');
	});// 查询修改用户信息

}
/**
 * 新增
 */
$(".sAdd").click(function() {
	addOrUpdateInitialize();
	$("#id").val('');
	openCustom("添加角色", "450", "300", $("#alert"), 'addOrUpdate("新增")');
});

/**
 * 批量删除
 */
$(".sdeleteAll").click(function() {
	isShouhou=0;
	var array = getCheck();
	if (null == array || array.length < 1) {
		openAlertMsg("请选择一个角色");
		return false;
	}
	if(isShouhou>0){
		openAlertMsg("售后角色不可被删除");
		return false;
	}
	openConfirm(0, "删除", "确认删除此角色吗？删除后可能导致部分账号不能正常使用！", function() {
		toAjaxDetail("role/batchdel", {
			"ids" : array
		}, "删除", "example");
	});
});

/**
 * 删除
 */
function sDelete(id) {
	openConfirm(0, "提示", "确认删除该角色吗？删除后可能导致部分账号不能正常使用！", function() {
		toAjaxDetail("role/del", {
			"id" : id
		}, "删除", "example");
	});
}

/**
 * 增加或修改
 */
function addOrUpdate(str) {
	var validator = validators("updateFrom");// 验证表单
	if (!validator) {
		return false;
	}
	//检查名称是否重复
	toAjaxCRUDCallBack("role/exist", {
		"roleId" : $("#id").val(),
		"roleName" : $("#updateName").val().trim()
	},function(result){
		if (result.success==1) {
			if(result.data==true){
				validat("updateName");
				openAlertMsg("角色已存在！");
				return false;
			}
		}
		//提交数据
		toAjaxDetail("role/save", $("#updateFrom").serialize(), str,"example",false);
	});
}

/**
 * 禁用
 */
function sDisable(id) {
	openConfirm(0, "提示", "确认禁用该角色吗？禁用后可能导致部分账号不能正常使用！", function() {
		toAjaxDetail("role/state", {
			"id" : id,
			"state" : 1
		}, "禁用", "example");
	});
}

/**
 * 启用
 */
function sOpen(id) {
	openConfirm(0, "提示", "确认启用该角色吗？", function() {
		toAjaxDetail("role/state", {
			"id" : id,
			"state" : 0
		}, "启用", "example");
	});
}



var isShouhou=0;
// 获得所有选中的check
function getCheck() {
	var array = new Array();
	$("#example input[type=checkbox]:checked").each(function() {
		if($(this).parent().next().text().trim().indexOf("售后")<0){
			array.push(this.id);
		}else{
			isShouhou++;
		}
	});
	return array;
}

/**
 * 关闭tree
 */
function colseChoose(){
	$(".am-tree-selected").each(function(index,item){
		fristTree.tree('selectItem',this);
	});
}


// 获得所有选中的树节点
function getChoose() {
	var array = new Array();
	$(".am-tree-selected").each(function() {
		var id=$(this).attr("permissionsid");
		if(id){
			array.push(id);
		}
	});
	return array;
}