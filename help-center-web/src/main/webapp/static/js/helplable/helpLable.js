/**
 * Created by zhaojianhong on 2018/3/28.
 */
$(function () {
    /*默认加载标签列表*/
    getHelpLableList();

    /*查询标签列表*/
    $("#query").click(function () {
        getHelpLableList();
    });
    /*加载标签列表〃*/
    function getHelpLableList() {
        var name = $("#name").val();
        var createdPin = $("#createdPin").val();
        var level = $("#level").find("option:selected").attr("name");
        $.ajax({
            url: "/lable/getHelpLableList.action",
            type: "POST",
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            data: {
                "helpLable.name": name,
                "helpLable.level": level,
                "helpLable.createdPin": createdPin
            },
            success: function (res) {
                console.log("i'm comming");
                console.log(res);
                var lableTable = $("#lableTable");
                lableTable.empty();
                var thead = "<tr><th>标签名称</th><th style='width: 8%'>标签级别</th><th style='width: 14%'>标签分类</th><th style='width: 8%'>状态</th><th>创建时间</th><th>创建人erp</th><th style='width: 14%'> 备注</th><th style='width: 8%'>操作</th></tr>"
                lableTable.append(thead);
                var resultList = res.resultList;
                for (var i = 0; i < resultList.length; i++) {
                    var tr = "<tr><td title='" + resultList[i].name + "' id='name" + i + "'>" + resultList[i].name + "</td><td title='" + resultList[i].level + "' id='level" + i + "'>" + resultList[i].level + "</td><td title='" + resultList[i].classify + "' id='classify" + i + "'>" + resultList[i].classify + "</td><td title='" + resultList[i].status + "' id='status" + i + "'>" + resultList[i].status + "</td><td title='" + resultList[i].createdTime + "' id='createdTime" + i + "'>" + resultList[i].createdTime + "</td><td title='" + resultList[i].createdPin + "' id='createdPin" + i + "'>" + resultList[i].createdPin + "</td><td title='" + resultList[i].content + "' id='content" + i + "'>" + resultList[i].content + "</td><td><button class='delete' id='delete" + i + "'>删除</button><input type ='hidden' id='id" + i + "'value='" + resultList[i].id + "'></td></tr>"
                    lableTable.append(tr);
                }
            }
        });
    }

    /*删除*/
    $("#lableTable").on("click", ".delete", function () {
        var id = $(this).attr("id");
        id = $("#id" + id.substring(id.length - 1)).val();
        $.ajax({
            url: "/lable/deleteLable.action",
            contentType: 'application/json',
            dataType: 'json',
            data: {
                "helpLable.id": id
            },
            success: function (res) {
                if (res.token == "success") {
                    alert("删除成功");
                    getHelpLableList();
                } else {

                }
            }
        });
    });

    /*新增*/
    $("#insert").click(function () {
        $('#main').css("background-color", "#F0F0F0");
        $(".outDiv").show();

    });

    /*选择标签级别*/
    $("#newLevel").change(function () {
        var level = $("#newLevel").find("option:selected").attr("name");
        if (level == 2) {
            $("#newClassifyDiv").css('display', 'block');
            var newClassify = $("#newClassify");
            newClassify.empty();
            newClassify.append("<option name='' id='1'>请选择</option>");
            $.ajax({
                url: "/lable/getFirstLevelLable.action",
                contentType: 'application/json',
                dataType: 'json',
                data: {
                    "helpLable.level": "1"
                },
                success: function (res) {
                    if (res.token == "success") {
                        var data = res.data;
                        for (var i = 0; i < data.length; i++) {
                            var item = "<option id='" + data[i].id + "'>" + data[i].name + "</option>"
                            newClassify.append(item);
                        }
                    } else {

                    }
                }
            });
        }
        if (level == 1) {
            $("#newClassifyDiv").css('display', 'none');
        }
    });

    /*提交*/
    $("#confirm").click(function () {
        $("#confirm").attr("disabled", true);
        setTimeout("$('#confirm').removeAttr('disabled')",2000); //设置三秒后提交按钮可用
        var flag = confirmCheck();
        if (flag == false) {
            return;
        }
        var name = $("#newName").val();
        var status = $("#newStatus").find("option:selected").attr("name");
        var level = $("#newLevel").find("option:selected").attr("name");
        var parentId = $("#newClassify").find("option:selected").attr("id") == undefined ? '0' : $("#newClassify").find("option:selected").attr("id");
        var classify = $("#newClassify").val() == "请选择" || $("#newClassify").val() == null ? $("#newName").val() : $("#newClassify").val() + "- >" + $("#newName").val();
        var content = $("#newContent").val();
        //var parentId = $("").v();
        $.ajax({
                url: "/lable/insertLable.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpLable.name": name,
                    "helpLable.status": status,
                    "helpLable.level": level,
                    "helpLable.classify": classify,
                    "helpLable.content": content,
                    "helpLable.parentId": parentId
                },
                success: function (res) {
                    if (res.token == "success") {
                        alert("添加成功");
                        initDialog();
                        getHelpLableList();
                    } else {

                    }
                }
            }
        );
    });

    $("#cancle").click(function () {
        initDialog();
    });

    $("#x").click(function () {
        initDialog();
    });

    function initDialog() {
        $("#newName").val("");
        $("#newStatus").val("请选择");
        $("#newLevel").val("请选择");
        var newClassify = $("#newClassify");
        newClassify.empty();
        $("#newClassifyDiv").css('display', 'none');
        $("#newContent").val("");
        $(".frm_msg").css('display', 'none');
        $(".newName").text("标签名称不能为空");
        $("#textCount").text("0/40");
        $(".outDiv").hide();
    }

    function confirmCheck() {
        var name = $("#newName").val();
        var status = $("#newStatus").val();
        var level = $("#newLevel").val();
        var classify = $("#newClassify").val();
        var content = $("#newContent").val();
        if (name == "") {
            $(".newName").css("display", "block");
            return false;
        }
        if (status == "请选择") {
            $(".newStatus").css("display", "block");
            return false;
        }
        if (level == "请选择") {
            $(".newLevel").css("display", "block");
            return false;
        }
        if ($("#newLevel").find("option:selected").attr("name") == '2' && classify == "请选择") {
            $(".newClassify").css("display", "block");
            return false;
        }
        if (content.length >= 40) {
            $(".newContent").css("display", "block");
            return false;
        }
        return true;
    }

    $("#newName").blur(function () {
        nameRepeatCheck();
    })
    function nameRepeatCheck() {
        var name = $("#newName").val().trim();
        $.ajax({
                url: "/lable/nameRepeatCheck.action",
                type: "POST",
                ansync: false,
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpLable.name": name
                },
                success: function (res) {
                    if (res.token == "error") {
                        $(".newName").text("标签名称重复,请重新填写");
                        $(".newName").css("display", "block");
                        $("#confirm").attr("disabled", true);
                    } else {
                        $(".newName").text("标签名称不能为空");
                        $(".newName").css("display", "none");
                        $("#confirm").attr("disabled", false);
                    }
                }
            }
        )
    }
    
    $("#newContent").blur(function () {
        var len = $("#newContent").val().length;
        $("#textCount").text(len+"/40");
        if(len>40){
            $(".newContent").css("display", "block");
        }else{
            $(".newContent").css("display", "none");
        }
    });
});
