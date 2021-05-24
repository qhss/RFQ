$(document).ready(function(){
    $("#btnSave").click(save);

    $("#btnAdd").click(add);

    $("#btnDelete").click(function () {
        layer.confirm('确定要删除吗？', {
            btn : [ '是的', '取消' ]
            // 按钮
        }, function() {
            del();
        });
    });
});
//初始化组织机构树
$(function () {
    init();
});

function zTreeOnAsyncSuccess() {
}

function zTreeOnClick(event, treeId, treeNode) {
    $("#deptId").val(treeNode.id);
    $("#deptName").val(treeNode.name); //部门名称
    $("#deptNo").val(treeNode.code);
    $("#sort").val(treeNode.sort);
    $("#remarks").val(treeNode.memo);
    $("#pid").val(treeNode.pid);
    $("#parentDept").val(treeNode.name); //父级
    $("#ppath").val(treeNode.status);
    $("#deptStatus").val(treeNode.canceled);
    vue.oType = treeNode.type;
    vue.selectCode = treeNode.code;
    console.log(treeNode.type);
   if(treeNode.type == 2){
        $("#erpPlaceDiv").removeClass('am-hide');
        $("#btnAdd").addClass('am-hide');
   }else {
       $("#erpPlaceDiv").addClass('am-hide');
       $("#btnAdd").removeClass('am-hide');
   }
    $("#deptInfo").removeClass('am-hide');
    $("#btnEdit").removeClass('am-hide');
    $("#btnSave").removeClass('am-hide');
    $("#btnDelete").removeClass('am-hide');
    $(".for-add").addClass('am-hide');

}


function add() {
    $("#pid").val("");
    $("#deptId").val("");
    $("#deptName").val("");
    $("#deptNo").val("");
    $("#sort").val("");
    $("#remarks").val("");
    $(".for-add").removeClass('am-hide');
    $("#admins").hide();
}

function del() {
    $.get("/depart/del",{id:$("#deptId").val()},function (data) {
        if (data.success == 1){
            delNode();
            layer.msg("删除成功");
        } else{
            layer.msg("删除失败");
        }
    })
}

function save() {
    var zTree = $.fn.zTree.getZTreeObj("deptTree"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode) {
        var param = $("#deptForm").serializeArray();
        if (param[0].value){
            $.post("/depart/update",param,function (data) {
                if (data.success==1){
                    eidtNode(data.data);
                    layer.msg("修改成功");
                }else if(data.success == 0){
                    layer.msg(data.errorMessage);
                } else{
                    layer.msg("修改失败");
                }
            });
        }else {
            $("#pid").val(treeNode.id);
            param = $("#deptForm").serializeArray();
            $.post("/depart/update",param,function (data) {
                if (data.success){
                    addNodes(data.data);
                    layer.msg("新增成功");
                } else if(data.success == 0){
                    layer.msg(data.errorMessage);
                }else{
                    layer.msg("新增失败");
                }
            });
        }
    }else{
        layer.msg("请先选择父级部门");
    }

}

function addNodes(obj) {
    var zTree = $.fn.zTree.getZTreeObj("deptTree"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode) {
        treeNode = zTree.addNodes(treeNode, obj);
    } else {
        treeNode = zTree.addNodes(null, obj);
    }
    if(obj.type == 2){
        $("#btnAdd").addClass('am-hide');
    }else {
        $("#btnAdd").removeClass('am-hide');
    }
}

function eidtNode(obj) {
    var zTree = $.fn.zTree.getZTreeObj("deptTree");
    var nodes = zTree.getSelectedNodes(), treeNode = nodes[0];
    if (nodes.length == 0) {
        alert("请先选择一个节点");
        return;
    }
    if(obj.type == 2){
        $("#btnAdd").addClass('am-hide');
    }else {
        $("#btnAdd").removeClass('am-hide');
    }

    treeNode.name=obj.name;
    treeNode.type = obj.type;
    treeNode.code = obj.code;
    treeNode.sort = obj.sort;
    treeNode.memo = obj.memo;
    treeNode.canceled = obj.canceled;
    zTree.updateNode(treeNode);
}

function delNode(){
    var treeObj = $.fn.zTree.getZTreeObj("deptTree");
    var nodes = treeObj.getSelectedNodes();
    for (var i=0, l=nodes.length; i < l; i++) {
        treeObj.removeNode(nodes[i]);
    }
}

var vue = new Vue({
    el : '#deptInfo',
    data : {
        oType : '',
        selectCode : ''
    },
    methods: {

    }
})
