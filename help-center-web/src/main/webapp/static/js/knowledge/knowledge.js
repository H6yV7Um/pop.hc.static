
$(function () {

    // 关联知识显示页点击确定
    $('.btn-confirm').click(function () {
        // 标记已经操作过关联知识
        var status = document.getElementById("relationKnowLedgeStatus");
        status.value = 1;
        // alert(status.value);
        $('#knowledge1').modal('hide');
    });

    /**
     * 关联知识选择按钮点击
     */
    $("#chooseRelation").click(function () {
        // 验证是否选择了知识标签，关联知识是根据标签推荐的
        // alert("111");
        var lableIds = $('.lableIds');
        var issueId = $("input[name=issueBO\\[\\'issue\\'\\]\\.id]").val();
        if (lableIds.val().length == 0) {
            var wrongSpan = document.getElementById("wrongId");
            wrongSpan.style.cssText = "color: #e15f63; overflow: hidden; *zoom: 1";
            return;
        }
        // alert("2222");
        $('#knowledge1').modal('show');
        if (!isRelation()) {
            // alert(" search ");
            // 清空隐藏的匹配知识
            var tbody = $("#tbody");
            tbody.empty();
            var labelIdsValue = $('.lableIds').val();
            var knowledgeIdValue = $("input[name=issueBO\\[\\'issue\\'\\]\\.id]").val();
            knowledgeIdValue = (typeof(knowledgeIdValue) == 'undefined' ? 0 : knowledgeIdValue);
            // alert(labelIdsValue);
            // alert(knowledgeIdValue);

            // 数据获取
            $.ajax({
                    url: "/help/issue/issue_relationKnowledge.action",
                    type: "POST",
                    contentType: 'application/x-www-form-urlencoded',
                    dataType: 'json',
                    data: {
                        "label": labelIdsValue,
                        "knowledgeId": knowledgeIdValue
                    },
                    success: function (res) {
                        if (res.token == "success") {
                            var list = res.data;
                            var tbody = $("#tbody");
                            tbody.empty();
                            for (var i = 0; i < list.length; i++) {
                                // 给子页面(弹窗)赋值
                                var tr = "<tr id='tr"+i+"'>" +
                                    "<td title='" + list[i].id + "' id='id" + i + "'>" + list[i].id + "</td>" +
                                    "<td title='" + list[i].name + "' id='name" + i + "'>" + list[i].name + "</td>" +
                                    "<td title='" + list[i].contentTypeStr + "' id='contentTypeStr" + i + "'>" + list[i].contentTypeStr + "</td>" +
                                    "<td title='" + list[i].label2Names + "' id='label2Names" + i + "'>" + list[i].label2Names + "</td>" +
                                    "<td title='" + list[i].createTime + "' id='createTime" + i + "'>" + list[i].createTime + "</td>" +
                                    "<td title='" + list[i].isStrongStr + "' id='isStrongStr" + i + "'>" + list[i].isStrongStr + "</td>" +
                                    "<td  style=\"display:none;\" title='" + list[i].status + "' id='status" + i + "'>" + list[i].status + "</td>" +
                                    "<td>" +
                                    "     <input type=\"button\" class=\"btn-info btn-sm\" value=\"替换\" onclick='replace(" + i + "," + list[i].id + ")'>" +
                                    "     <input type=\"button\" class=\"btn-danger btn-sm\" value=\"删除\" onclick='clickRemove(" + i + ")'>" +
                                    "</td>" +
                                    "</tr>";
                                tbody.append(tr);

                                // 给父页面对应的数据框赋值
                                fillParentData(i, list[i]);
                            }
                        } else {
                            alert("获取关联知识数据失败");
                        }
                    }
                }
            );
        }
    });

    /**
     * 知识替换页点击查询
     */
    $("#queryNew").click(function () {
        var tbody1 = $("#tbody1");
        tbody1.empty();
        var knowledgeId = $("#chooseKnowledge").val();

        var knowledgeIdQuery = $("#knowledgeIdQuery").val();
        var knowledgeNameQuery = $("#knowledgeNameQuery").val();
        var type = $("#issueType").find("option:selected").attr("id");
        // alert("1 " + knowledgeIdQuery + "  " + knowledgeNameQuery + "  " + type);
        //展示搜索替换页面
        $.ajax({
                url: "/help/issue/issue_allKnowledge.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "knowledgeId": knowledgeId,
                    "knowledgeQueryDTO.knowledgeId": knowledgeIdQuery,
                    "knowledgeQueryDTO.name": knowledgeNameQuery,
                    "knowledgeQueryDTO.typeId": type
                },
            success: function (res) {
                if (res.token == "success") {
                    var list = res.data;
                    for (var i = 0; i < list.length; i++) {
                        var tr = "<tr>" +
                            "<td><input type='radio' name='radio' value='" + list[i].id + "'></td>" +
                            "<td title='" + list[i].name + "' id='r_name" + list[i].id + "'>" + list[i].name + "</td>" +
                            "<td title='" + list[i].id + "' id='r_knowledgeId" + list[i].id + "'>" + list[i].id + "</td>" +
                            "<td title='" + list[i].contentTypeStr + "' id='r_contentTypeStr" + list[i].id + "'>" + list[i].contentTypeStr + "</td>" +
                            "<td title='" + list[i].label2Names + "' id='r_label2Names" + list[i].id + "'>" + list[i].label2Names + "</td>" +
                            "<td title='" + list[i].createTime + "' id='r_createTime" + list[i].id + "'>" + list[i].createTime + "</td>" +
                            "</tr>";
                        tbody1.append(tr);
                    }
                } else {
                    alert("查询知识失败");
                }
            }
            }
        );
    });


    /**
     * 知识替换页点击确定
     */
    $("#confirmNew").click(function () {
        // 校验：替换页的知识不能与推荐页的知识重复
        var knowledgeId = $('input[name="radio"]:checked ').val();
        // alert("选择了 " + knowledgeId);
        var ids = $(".ftable tbody tr").find("td:nth-child(1)").map(function (index, elem) {
            return $(elem).html();
        }).get().join(" ");
        // alert("ids " + ids);
        var arr = ids.split(" ");
        for (var n in arr) {
            if (knowledgeId == arr[n]) {
                alert("不能重复关联同一个知识");
                return;
            }
        }

        // 校验通过，
        var beReplaceKnowledge = document.getElementById("beReplaceKnowledge").value;
        // alert("after pass id : " + beReplaceKnowledge);
        var index = document.getElementById("beReplaceKnowledgeIndex").value;
        // alert("after pass index : " + index);

        // 关联页数据替换
        document.getElementById("id" + index + "").innerHTML = document.getElementById("r_knowledgeId" + knowledgeId + "").innerHTML;
        document.getElementById("name" + index + "").innerHTML = document.getElementById("r_name" + knowledgeId + "").innerHTML;
        document.getElementById("contentTypeStr" + index + "").innerHTML = document.getElementById("r_contentTypeStr" + knowledgeId + "").innerHTML;
        document.getElementById("label2Names" + index + "").innerHTML = document.getElementById("r_label2Names" + knowledgeId + "").innerHTML;
        document.getElementById("createTime" + index + "").innerHTML = document.getElementById("r_createTime" + knowledgeId + "").innerHTML;
        document.getElementById("isStrongStr" + index + "").innerHTML = "人工匹配";
        // 父页面数据替换操作
        var parentData = document.getElementById("rel-form-group-" + index + "");
        // dom对象转换jQuery对象
        var $parentData = $(parentData);
        $parentData.find('input').each(function () {
            if (this.name == 'relationKnowledgeId') {
                this.value = knowledgeId;
            } else if (this.name == 'replaceKnowledgeId') {
                this.value = beReplaceKnowledge;
            } else if (this.name == 'status') {
                this.value = 0;
            } else if (this.name == 'isStrong') {
                this.value = 1;
            }
        });
        // 替换操作完成，关闭替换页弹窗
        $("#replaceModal").modal("hide");
    });

    /**
     * 替换页点取消
     */
    $("#cancleNew").click(function () {
        $("#replaceModal").hide();
        emptyDialog();
    });

    /**
     * 替换页点x
     */
    $("#xx").click(function () {
        $("#replaceModal").hide();
        emptyDialog();
    });
});

// function initDialog() {
//     $("#replaceModal").hide();
//     $("#knowledgeIdQuery").val("");
//     $("#knowledgeNameQuery").val("");
//     var item = $("#issueType");
//     item.empty();
// }

function emptyDialog() {
    $(".replaceModal").hide();
    $("#knowledgeIdQuery").val("");
    $("#knowledgeNameQuery").val("");
    var item = $("#issueType");
    item.empty();
}

/**
 * 获取知识类型
 */
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
                        item.append("<option id='" + (n + 1) + "'>" + types[n] + "</option>");
                    }
                } else {
                    alert("获取知识类型失败");
                }
            }
        }
    );
}

/**
 * 填充父页面隐藏域div的值
 * @param index
 * @param data
 */
function fillParentData(index, data) {
    $('#rel-form-group-' + index + '').find('input').each(function () {
        if (this.name == 'relationKnowledgeId') {
            this.value = data.id;
        } else if (this.name == 'replaceKnowledgeId') {
            this.value = data.replaceKnowledgeId;
        } else if (this.name == 'status') {
            this.value = data.status;
        } else if (this.name == 'isStrong') {
            this.value = data.isStrong;
        }
    });
}

/**
 * 点击替换按钮
 * @param index 被替换数据的下标
 * @param id 要被替换的知识id
 */
function replace(index, id) {
    // alert("" + id);
    var issueId = document.getElementById("knowledgeId") == null ? 0 : document.getElementById("knowledgeId").value;
    // alert(" issueId " + issueId);
    document.getElementById("chooseKnowledge").value = issueId;
    document.getElementById("beReplaceKnowledge").value = id;
    document.getElementById("beReplaceKnowledgeIndex").value = index;
    var item = $("#issueType");
    item.empty();
    getKnowledgeType();
    $("#replaceModal").modal("show");

}

/**
 * 确认删除弹出询问弹窗
 */
function clickRemove(index) {
    $("#modal").find(".modal-title").html("删除关联知识").end()
        .find(".modal-body").html("<p>你确定删除？</p>").end()
        .find(".btn-do-update-status").attr("onclick", "remove(" + index + " )").end()
        .modal("show");
}

/**
 * 删除弹窗点击确认按钮
 * @param index 数据下标，从0开始
 */
function remove(index) {
    // 删除弹窗页数据
    var tr = document.getElementById("tr" + index);
    if (tr != null) {
        tr.parentNode.removeChild(tr);
        // 删除父页面隐藏域内的数据
        $('#rel-form-group-' + index + '').find('input').each(function () {
            if (this.name == 'status') {
                this.value = 1
            }
        });
        $("#modal").modal("hide");
    }
}

/**
 * 关联知识弹窗内是否已经有数据
 * @returns {boolean}
 */
function isRelation() {
    var status = document.getElementById("relationKnowLedgeStatus");
    // alert(status.value);
    return (status.value == 1);
}
