$(function() {
	// 定义列
	var aoColumns = [ {
		"sClass" : "table-check",// 样式
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
		"sTitle" : "模板名称",
		"sDefaultContent" : "",
		"sWidth" : "25%"// 除checkbox其余必须设定列宽
	// 防止自动列宽有大有小
	},{
		"mDataProp" : "code",
		"sTitle" : "模板编码",
		"sDefaultContent" : "",
		"sClass" : "am-hide-sm-only",
		"sWidth" : "10%"
	}, {
		"mDataProp" : "id",
		"sTitle" : "导出模板",
		"sDefaultContent" : "",
		"render" : function(oObj, sVal) {// 需要对value进行操作时用
			if((oObj || "").length>0)
				return "已设置";
			else
				return "";
		},
		"sClass" : "am-hide-sm-only",
		"sWidth" : "15%"
	},{
		"mDataProp" : "id",
		"sTitle" : "打印模板",
		"sDefaultContent" : "",
		"render" : function(oObj, sVal) {// 需要对value进行操作时用
			if((oObj || "").length>0)
				return "已设置";
			else
				return "";
		},
		"sClass" : "am-hide-sm-only",
		"sWidth" : "10%"
	},{
		"mDataProp" : "memo",
		"sTitle" : "备注",
		"sDefaultContent" : "",
		"sClass" : "am-hide-sm-only",
		"sWidth" : "10%"
	},{
		"mDataProp" : "id",
		"sTitle" : "操作",
		"sDefaultContent" : "",// 此列默认值为""，以防数据中没有此值，DataTables加载数据的时候报错
		"bSortable" : false,
		"render" : function(oObj, sVal, aData) {
			var content = '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
			content +='<button onclick=\'sEdit("'+ aData.id + '")\' class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>编辑名称</button>';
			content +='<button onclick=\'editTemplate("'+ aData.id + '")\' class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改模板</button>';
			content += '</div></div>';
			return content;
		},
		"sWidth" : "30%"
	} ];
	// 每行的回调 生成自定义td
	function fnRowCallback(nRow, aData, iDisplayIndex) {
		/*var content = '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">';
		content +='<button onclick=\'sEdit("'+ aData.id + '")\' class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>编辑名称</button>';
		content +='<button onclick=\'editTemplate("'+ aData.id + '")\' class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改模板</button>';
		
		content += '</div></div>';
		$('td:eq(6)', nRow).html(content);
		return nRow;*/
	}

	// 传递的自定义参数
	function fnServerParams(aoData) {
		aoData.push({
			"name" : "name",
			"value" : $("#txtTemplateName").val().trim()
		});
	}
	// 初始化表格 tableID 自定义列 每行回调 url 自定义参数
	initializeTable("example", aoColumns, fnRowCallback,"rpttemplate/queryOnePage", fnServerParams)
});

// 搜索
$(".search").click(function() {
	$("#example").dataTable().fnDraw(true);
});

// 添加和修改需要执行的公共事件
function addOrUpdateInitialize() {
	$('#updateFrom')[0].reset();// 重置表单
	$("#updateFrom").validator('destroy');// 销毁表单验证
}

/**
 * 修改
 */
function sEdit(id) {

	addOrUpdateInitialize();
	$("#templateId").val(id);
	toAjaxCRUDCallBack("rpttemplate/getOne", {
		"id" : id
	}, function(data) {
		$("#name").val(data.name);
		$("#memo").val(data.memo);
		
		openCustom("编辑模板", "500", "400", $("#alert"), 'addOrUpdate("修改")');
	});
}

/**
 * 新增
 */
$(".sAdd").click(function() {

	addOrUpdateInitialize();

	$("#templateId").val('');
	$("#typeName").attr("required", "required");
	openCustom("新增业务类型", "500", "400", $("#alert"), 'addOrUpdate("新增")');
});

/**
 * 增加或修改
 */
function addOrUpdate(str) {
	var validator = validators("updateFrom");// 验证表单
	if (!validator) {
		return false;
	}
	var id = $("#templateId").val();
	toAjaxCRUDCallBack("rpttemplate/checkName", {
		"name" : $("#name").val().trim(),
		"id" : id
	}, function(count) {
		if (count > 0) {
			validat("name");
			openAlertMsg("名称重复！");
			return false;
		} else {
			if (null == id || "" == id) {
				toAjaxDetail("rpttemplate/add", $("#updateFrom").serialize(), str, "example");
			} else {
				toAjaxDetail("rpttemplate/update", $("#updateFrom").serialize(), str, "example");
			}
		}
	});
}

function editTemplate(id){
	if(id=="" || id==0 ||id==null)
		return;
	window.location.href="rpttemplate/editor?id="+id;
}



// 获得所有选中的check
function getCheck() {
	var array = new Array();
	$("#example input[type=checkbox]:checked").each(function() {
		array.push(this.id);
	});
	return array;
}
