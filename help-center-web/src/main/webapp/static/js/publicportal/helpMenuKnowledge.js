/**
 * Created by zhaojianhong on 2018/4/24.
 */
$(function () {
    //点击radio
    $('#tbody').on('click', 'input[name="radio"]', function (e) {
        $("#confirmNew").attr("disabled",false);
    });
    /**
     * 点击替换
     */
    $(".update").click(function () {
        $(".outDiv").show();
        getKnowledgeType();
        var knowledgeId = $(this).attr("thisId");
        $("#activeId").val(knowledgeId);
        var menuId = $(this).parent().parent().children("td").eq(0).text();
        $("#activeMenuId").val(menuId);
        var knowledgeIdQuery = $("#knowledgeIdQuery").val();
        var knowledgeNameQuery = $("#knowledgeNameQuery").val();
        //默认不展示数据，通过查询加载数据
  /*      $.ajax({
                url: "/publicportal/k_getNewKnowledgeList.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuKnowledge.changeKnowledgeId": knowledgeId,
                    "helpMenuKnowledge.menuId": menuId,
                    "knowledgeEsBean.knowledgeId": knowledgeIdQuery,
                    "knowledgeEsBean.name": knowledgeNameQuery
                },
                success: function (res) {
                    if (res.token == "success") {
                        var list = res.data;
                        var tbody = $("#tbody");
                        tbody.empty();
                        for (var i = 0; i < list.length; i++) {
                            var tr = "<tr><td><input type='radio' name='radio' value='" + list[i].knowledgeId + "'></td><td title='" + list[i].name + "' id='name" + i + "'>" + list[i].name + "</td><td title='" + list[i].knowledgeId + "' id='knowledgeId" + i + "'>" + list[i].knowledgeId + "</td><td title='" + list[i].bizTypeName + "' id='bizTypeName" + i + "'>" + list[i].bizTypeName + "</td><td title='" + list[i].label2Names + "' id='label2Names" + i + "'>" + list[i].label2Names + "</td><td title='" + list[i].createTime + "' id='createTime" + i + "'>" + list[i].createTime + "</td></tr>"
                            tbody.append(tr);
                        }
                    } else {

                    }
                }
            }
        );*/
    });

    /**
     * 点击查询
     */
    $("#queryNew").click(function () {
        var tbody = $("#tbody");
        tbody.empty();
        var knowledgeId = $("#activeId").val();
        var menuId = $("#activeMenuId").val();
        var knowledgeIdQuery = $("#knowledgeIdQuery").val();
        var knowledgeNameQuery = $("#knowledgeNameQuery").val();
        var type = $("#issueType").find("option:selected").attr("id");
        //展示搜索替换页面
        $.ajax({
                url: "/publicportal/k_getNewKnowledgeList.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuKnowledge.changeKnowledgeId": knowledgeId,
                    "helpMenuKnowledge.menuId": menuId,
                    "knowledgeEsBean.knowledgeId": knowledgeIdQuery,
                    "knowledgeEsBean.name": knowledgeNameQuery,
                    "knowledgeEsBean.bizTypeId": type
                },
                success: function (res) {
                    if (res.token == "success") {
                        var list = res.data;
                        var tbody = $("#tbody");
                        tbody.empty();
                        for (var i = 0; i < list.length; i++) {
                            var tr = "<tr><td><input type='radio' name='radio' value='" + list[i].knowledgeId + "'></td><td title='" + list[i].name + "' id='name" + i + "'>" + list[i].name + "</td><td title='" + list[i].knowledgeId + "' id='knowledgeId" + i + "'>" + list[i].knowledgeId + "</td><td title='" + list[i].content + "' id='content" + i + "'>" + list[i].content + "</td><td title='" + list[i].label2Names + "' id='label2Names" + i + "'>" + list[i].label2Names + "</td><td title='" + list[i].summary + "' id='summary" + i + "'>" + list[i].summary + "</td></tr>"
                            tbody.append(tr);
                        }
                    } else {

                    }
                }
            }
        );
    });

    /**
     * 点击确定
     */
    $("#confirmNew").click(function () {
        var knowledgeId = $('input[name="radio"]:checked ').val();
        var ids = $("#stable tbody tr").find("td:nth-child(3)").map(function (index, elem) {
            return $(elem).html();
        }).get().join(" ");
        var arr = ids.split(" ");
        for (var n in arr) {
            if (knowledgeId == arr[n]) {
                alert("该知识已存在");
                return;
            }
        }
        initDialog();
        $.ajax({
                url: "/publicportal/k_update.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuKnowledge.menuId": $("#activeMenuId").val(),
                    "helpMenuKnowledge.changeKnowledgeId": $("#activeId").val(),
                    "helpMenuKnowledge.newKnowledgeId": knowledgeId
                },
                success: function (res) {
                    if (res.token == "success") {
                        alert("替换成功");
                        $("#form").submit();
                    } else {

                    }
                }
            }
        );

    });

    /**
     * 删除
     */
    $(".delete").click(function () {
        var knowledgeId = $(this).attr("thisId");
        var menuId = $(this).parent().parent().children("td").eq(0).text();
        $.ajax({
                url: "/publicportal/k_update.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuKnowledge.changeKnowledgeId": knowledgeId,
                    "helpMenuKnowledge.menuId": menuId,
                },
                success: function (res) {
                    if (res.token == "success") {
                        alert("删除成功");
                        $("#form").submit();
                    } else {

                    }
                }
            }
        );
    });

    $("#cancleNew").click(function () {
        initDialog();
    });

    $("#x").click(function () {
        initDialog();
    });
});
function checkForQueryMsg() {
    var reg = /^[0-9]*$/;
    $(".failMessage").text("请输入查询条件");
    var menuId = $(".menuId").val();
    var menuDesc = $(".menuDesc").val();
    if (menuId == "" && menuDesc == "") {
        $(".failMessage").css("display", "block");
        return false;
    } else if (!reg.test(menuId)) {
        $(".failMessage").text("SHOP端页面Id只能是数字哦");
        $(".failMessage").css("display", "block");
        return false;
    }
}
function initDialog() {
    $(".outDiv").hide();
    $("#knowledgeIdQuery").val("");
    $("#knowledgeNameQuery").val("");
    var tbody = $("#tbody");
    tbody.empty();
    var item = $("#issueType");
    item.empty();
}

function getKnowledgeType() {
    $.ajax({
            url: "/publicportal/k_getKnowledgeType.action",
            type: "POST",
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            success: function (res) {
                if (res.token == "success") {
                    var types = res.data;
                    var item = $("#issueType");
                    item.append("<option name=''>请选择</option>");
                    for (var n in types) {
                        item.append("<option id='"+(n+1)+"'>"+types[n]+"</option>");
                    }
                } else {

                }
            }
        }
    );
}