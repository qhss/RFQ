$(function() {
	$(".sSave").click(function(){
		update();
	});
});




/**
 * 增加或修改
 */
function update() {
	
	var id = $("#templateId").val();
	if(id=="" || id=="0")
		return;
	var exportText = CKEDITOR.instances.editorExport.getData();
	var printText = CKEDITOR.instances.editorPrint.getData();
	
	toAjaxCRUDCallBack("rpttemplate/update", {
		"exportTemplate" : exportText,
		"printTemplate" : printText,
		"id" : id
	}, function(result) {
		if(result*1>0)
			openAlertMsg("保存成功!");
		else
			openAlertMsg("保存失败!");
		
	});
}

