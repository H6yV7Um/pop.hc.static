/**
 * Created by zhaojianhong on 2018/3/28.
 */
$(function () {
    /*Ĭ�ϼ��ر�ǩ�б�*/
    getHelpLableList();

    /*��ѯ��ǩ�б�*/
    $("#query").click(function () {
        getHelpLableList();
    });
    /*���ر�ǩ�б�*/
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
                var thead = "<tr><th>��ǩ����</th><th style='width: 8%'>��ǩ����</th><th style='width: 14%'>��ǩ����</th><th style='width: 8%'>״̬</th><th>����ʱ��</th><th>������erp</th><th style='width: 14%'> ��ע</th><th style='width: 8%'>����</th></tr>"
                lableTable.append(thead);
                var resultList = res.resultList;
                for (var i = 0; i < resultList.length; i++) {
                    var tr = "<tr><td title='" + resultList[i].name + "' id='name" + i + "'>" + resultList[i].name + "</td><td title='" + resultList[i].level + "' id='level" + i + "'>" + resultList[i].level + "</td><td title='" + resultList[i].classify + "' id='classify" + i + "'>" + resultList[i].classify + "</td><td title='" + resultList[i].status + "' id='status" + i + "'>" + resultList[i].status + "</td><td title='" + resultList[i].createdTime + "' id='createdTime" + i + "'>" + resultList[i].createdTime + "</td><td title='" + resultList[i].createdPin + "' id='createdPin" + i + "'>" + resultList[i].createdPin + "</td><td title='" + resultList[i].content + "' id='content" + i + "'>" + resultList[i].content + "</td><td><button class='delete' id='delete" + i + "'>ɾ��</button><input type ='hidden' id='id" + i + "'value='" + resultList[i].id + "'></td></tr>"
                    lableTable.append(tr);
                }
            }
        });
    }

    /*ɾ��*/
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
                    alert("ɾ���ɹ�");
                    getHelpLableList();
                } else {

                }
            }
        });
    });

    /*����*/
    $("#insert").click(function () {
        $('#main').css("background-color", "#F0F0F0");
        $(".outDiv").show();

    });

    /*ѡ���ǩ����*/
    $("#newLevel").change(function () {
        var level = $("#newLevel").find("option:selected").attr("name");
        if (level == 2) {
            $("#newClassifyDiv").css('display', 'block');
            var newClassify = $("#newClassify");
            newClassify.empty();
            newClassify.append("<option name='' id='1'>��ѡ��</option>");
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

    /*�ύ*/
    $("#confirm").click(function () {
        $("#confirm").attr("disabled", true);
        setTimeout("$('#confirm').removeAttr('disabled')",2000); //����������ύ��ť����
        var flag = confirmCheck();
        if (flag == false) {
            return;
        }
        var name = $("#newName").val();
        var status = $("#newStatus").find("option:selected").attr("name");
        var level = $("#newLevel").find("option:selected").attr("name");
        var parentId = $("#newClassify").find("option:selected").attr("id") == undefined ? '0' : $("#newClassify").find("option:selected").attr("id");
        var classify = $("#newClassify").val() == "��ѡ��" || $("#newClassify").val() == null ? $("#newName").val() : $("#newClassify").val() + "- >" + $("#newName").val();
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
                        alert("��ӳɹ�");
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
        $("#newStatus").val("��ѡ��");
        $("#newLevel").val("��ѡ��");
        var newClassify = $("#newClassify");
        newClassify.empty();
        $("#newClassifyDiv").css('display', 'none');
        $("#newContent").val("");
        $(".frm_msg").css('display', 'none');
        $(".newName").text("��ǩ���Ʋ���Ϊ��");
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
        if (status == "��ѡ��") {
            $(".newStatus").css("display", "block");
            return false;
        }
        if (level == "��ѡ��") {
            $(".newLevel").css("display", "block");
            return false;
        }
        if ($("#newLevel").find("option:selected").attr("name") == '2' && classify == "��ѡ��") {
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
                        $(".newName").text("��ǩ�����ظ�,��������д");
                        $(".newName").css("display", "block");
                        $("#confirm").attr("disabled", true);
                    } else {
                        $(".newName").text("��ǩ���Ʋ���Ϊ��");
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
