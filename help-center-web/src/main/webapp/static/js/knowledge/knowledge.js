
$(function () {

    // ����֪ʶ��ʾҳ���ȷ��
    $('.btn-confirm').click(function () {
        // ����Ѿ�����������֪ʶ
        var status = document.getElementById("relationKnowLedgeStatus");
        status.value = 1;
        // alert(status.value);
        $('#knowledge1').modal('hide');
    });

    /**
     * ����֪ʶѡ��ť���
     */
    $("#chooseRelation").click(function () {
        // ��֤�Ƿ�ѡ����֪ʶ��ǩ������֪ʶ�Ǹ��ݱ�ǩ�Ƽ���
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
            // ������ص�ƥ��֪ʶ
            var tbody = $("#tbody");
            tbody.empty();
            var labelIdsValue = $('.lableIds').val();
            var knowledgeIdValue = $("input[name=issueBO\\[\\'issue\\'\\]\\.id]").val();
            knowledgeIdValue = (typeof(knowledgeIdValue) == 'undefined' ? 0 : knowledgeIdValue);
            // alert(labelIdsValue);
            // alert(knowledgeIdValue);

            // ���ݻ�ȡ
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
                                // ����ҳ��(����)��ֵ
                                var tr = "<tr id='tr"+i+"'>" +
                                    "<td title='" + list[i].id + "' id='id" + i + "'>" + list[i].id + "</td>" +
                                    "<td title='" + list[i].name + "' id='name" + i + "'>" + list[i].name + "</td>" +
                                    "<td title='" + list[i].contentTypeStr + "' id='contentTypeStr" + i + "'>" + list[i].contentTypeStr + "</td>" +
                                    "<td title='" + list[i].label2Names + "' id='label2Names" + i + "'>" + list[i].label2Names + "</td>" +
                                    "<td title='" + list[i].createTime + "' id='createTime" + i + "'>" + list[i].createTime + "</td>" +
                                    "<td title='" + list[i].isStrongStr + "' id='isStrongStr" + i + "'>" + list[i].isStrongStr + "</td>" +
                                    "<td  style=\"display:none;\" title='" + list[i].status + "' id='status" + i + "'>" + list[i].status + "</td>" +
                                    "<td>" +
                                    "     <input type=\"button\" class=\"btn-info btn-sm\" value=\"�滻\" onclick='replace(" + i + "," + list[i].id + ")'>" +
                                    "     <input type=\"button\" class=\"btn-danger btn-sm\" value=\"ɾ��\" onclick='clickRemove(" + i + ")'>" +
                                    "</td>" +
                                    "</tr>";
                                tbody.append(tr);

                                // ����ҳ���Ӧ�����ݿ�ֵ
                                fillParentData(i, list[i]);
                            }
                        } else {
                            alert("��ȡ����֪ʶ����ʧ��");
                        }
                    }
                }
            );
        }
    });

    /**
     * ֪ʶ�滻ҳ�����ѯ
     */
    $("#queryNew").click(function () {
        var tbody1 = $("#tbody1");
        tbody1.empty();
        var knowledgeId = $("#chooseKnowledge").val();

        var knowledgeIdQuery = $("#knowledgeIdQuery").val();
        var knowledgeNameQuery = $("#knowledgeNameQuery").val();
        var type = $("#issueType").find("option:selected").attr("id");
        // alert("1 " + knowledgeIdQuery + "  " + knowledgeNameQuery + "  " + type);
        //չʾ�����滻ҳ��
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
                    alert("��ѯ֪ʶʧ��");
                }
            }
            }
        );
    });


    /**
     * ֪ʶ�滻ҳ���ȷ��
     */
    $("#confirmNew").click(function () {
        // У�飺�滻ҳ��֪ʶ�������Ƽ�ҳ��֪ʶ�ظ�
        var knowledgeId = $('input[name="radio"]:checked ').val();
        // alert("ѡ���� " + knowledgeId);
        var ids = $(".ftable tbody tr").find("td:nth-child(1)").map(function (index, elem) {
            return $(elem).html();
        }).get().join(" ");
        // alert("ids " + ids);
        var arr = ids.split(" ");
        for (var n in arr) {
            if (knowledgeId == arr[n]) {
                alert("�����ظ�����ͬһ��֪ʶ");
                return;
            }
        }

        // У��ͨ����
        var beReplaceKnowledge = document.getElementById("beReplaceKnowledge").value;
        // alert("after pass id : " + beReplaceKnowledge);
        var index = document.getElementById("beReplaceKnowledgeIndex").value;
        // alert("after pass index : " + index);

        // ����ҳ�����滻
        document.getElementById("id" + index + "").innerHTML = document.getElementById("r_knowledgeId" + knowledgeId + "").innerHTML;
        document.getElementById("name" + index + "").innerHTML = document.getElementById("r_name" + knowledgeId + "").innerHTML;
        document.getElementById("contentTypeStr" + index + "").innerHTML = document.getElementById("r_contentTypeStr" + knowledgeId + "").innerHTML;
        document.getElementById("label2Names" + index + "").innerHTML = document.getElementById("r_label2Names" + knowledgeId + "").innerHTML;
        document.getElementById("createTime" + index + "").innerHTML = document.getElementById("r_createTime" + knowledgeId + "").innerHTML;
        document.getElementById("isStrongStr" + index + "").innerHTML = "�˹�ƥ��";
        // ��ҳ�������滻����
        var parentData = document.getElementById("rel-form-group-" + index + "");
        // dom����ת��jQuery����
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
        // �滻������ɣ��ر��滻ҳ����
        $("#replaceModal").modal("hide");
    });

    /**
     * �滻ҳ��ȡ��
     */
    $("#cancleNew").click(function () {
        $("#replaceModal").hide();
        emptyDialog();
    });

    /**
     * �滻ҳ��x
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
 * ��ȡ֪ʶ����
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
                    item.append("<option name=''>��ѡ��</option>");
                    for (var n in types) {
                        item.append("<option id='" + (n + 1) + "'>" + types[n] + "</option>");
                    }
                } else {
                    alert("��ȡ֪ʶ����ʧ��");
                }
            }
        }
    );
}

/**
 * ��丸ҳ��������div��ֵ
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
 * ����滻��ť
 * @param index ���滻���ݵ��±�
 * @param id Ҫ���滻��֪ʶid
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
 * ȷ��ɾ������ѯ�ʵ���
 */
function clickRemove(index) {
    $("#modal").find(".modal-title").html("ɾ������֪ʶ").end()
        .find(".modal-body").html("<p>��ȷ��ɾ����</p>").end()
        .find(".btn-do-update-status").attr("onclick", "remove(" + index + " )").end()
        .modal("show");
}

/**
 * ɾ���������ȷ�ϰ�ť
 * @param index �����±꣬��0��ʼ
 */
function remove(index) {
    // ɾ������ҳ����
    var tr = document.getElementById("tr" + index);
    if (tr != null) {
        tr.parentNode.removeChild(tr);
        // ɾ����ҳ���������ڵ�����
        $('#rel-form-group-' + index + '').find('input').each(function () {
            if (this.name == 'status') {
                this.value = 1
            }
        });
        $("#modal").modal("hide");
    }
}

/**
 * ����֪ʶ�������Ƿ��Ѿ�������
 * @returns {boolean}
 */
function isRelation() {
    var status = document.getElementById("relationKnowLedgeStatus");
    // alert(status.value);
    return (status.value == 1);
}
