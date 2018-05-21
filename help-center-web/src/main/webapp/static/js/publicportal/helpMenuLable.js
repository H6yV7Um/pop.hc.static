/**
 * Created by zhaojianhong on 2018/4/12.
 */
$(function () {
    var labelConfirmCode = "0"; //新增:0  修改:1   默认是0
    console.log("hello");
    if ($('.label_list').find('label.selected').length == 0) {
        $('#labelConfirm').attr("disabled", true);
    }
    if ($('.label_listBack').find('label.selected').length == 0) {
        $('#labelConfirmBack').attr("disabled", true);
    }

//添加标签按钮，获取所有标签
    $('#getAllLabel').on('click', function () {
        openLabelModal(1);
    });

    //添加标签按钮，获取所有标签 back
    $('#getAllLabelBack').on('click', function () {
        openLabelModalBack(1);
    });

//打开标签对话框
    function openLabelModal(index) {
        //初始化模态框
        var $modal = $('#labelModal');
        $modal.attr('data-index', index);
        $modal.find('.img_item label').removeClass('selected');
        //$('.lableNames').removeAttr('readonly');
        $('#labelConfirm').removeClass('btn_primary').addClass('btn_disabled');
        //document.getElementById('lableNames').removeAttribute("readonly");
        initAllLabel();
        $modal.modal('show');
    }

    //选择单个label
    $('.label_list').on('click', 'li', function (e) {
        var $label = $(this).find('label'), $input = $(this).find('input'), $btn = $('#labelConfirm');
        e.preventDefault();
        if ($label.hasClass('selected')) {
            $label.removeClass('selected');
            $input.removeAttr('checked');
            $(this).closest('.label_list').find('label.selected').length == 0 && $btn.removeClass('btn_primary').addClass('btn_disabled');
        } else {
            $label.addClass('selected');
            $input.attr('checked', 'checked');
            $btn.removeClass('btn_disabled').addClass('btn_primary');
        }
        if ($(this).closest('.label_list').find('label.selected').length == 0) {
            $('#labelConfirm').attr("disabled", true);
        } else {
            $('#labelConfirm').attr("disabled", false);
        }

    });

    //打开标签对话框 back
    function openLabelModalBack(index) {
        //初始化模态框
        var $modal = $('#labelModalBack');
        $modal.attr('data-index', index);
        $modal.find('.img_item label').removeClass('selected');
        //$('.lableNames').removeAttr('readonly');
        $('#labelConfirmBack').removeClass('btn_primary').addClass('btn_disabled');
        //document.getElementById('lableNames').removeAttribute("readonly");
        initAllLabelBack();
        $modal.modal('show');
    }

    //选择单个label back
    $('.label_listBack').on('click', 'li', function (e) {
        var $label = $(this).find('label'), $input = $(this).find('input'), $btn = $('#labelConfirmBack');
        e.preventDefault();
        if ($label.hasClass('selected')) {
            $label.removeClass('selected');
            $input.removeAttr('checked');
            $(this).closest('.label_listBack').find('label.selected').length == 0 && $btn.removeClass('btn_primary').addClass('btn_disabled');
        } else {
            $label.addClass('selected');
            $input.attr('checked', 'checked');
            $btn.removeClass('btn_disabled').addClass('btn_primary');
        }
        if ($(this).closest('.label_listBack').find('label.selected').length == 0) {
            $('#labelConfirmBack').attr("disabled", true);
        } else {
            $('#labelConfirmBack').attr("disabled", false);
        }

    });

//确定按钮
    $('.modal-footer').on('click', '#labelConfirm', function () {
        var $selected = $('.label_list').find('li label.selected'),
            arrId = [], arrName = [],label1Ids = [], label1Names = [];
        $.each($selected, function (i, item) {
            arrId.push($(item).find('input').val());
            arrName.push($(item).find('span').text());
            label1Ids.push($(item).parents('ul').siblings("label").attr("data-id"));
            label1Names.push($(item).parents('ul').siblings("label").text().split("：")[0]);
        });
        var $modal = $('#labelModal');
        $modal.modal('hide');
        $('.lableIds').val(arrId.join(' '));
        $('.lableNames').val(arrName.join(' ')).attr('readonly', 'readonly');
        $('.label1Ids').val(label1Ids.join(' ')).attr('readonly', 'readonly');
        $('.label1Names').val(label1Names.join(' ')).attr('readonly', 'readonly');
        //$('#lableNames').attr('readonly','readonly');
    });

    //确定按钮 back
    $('.modal-footerBack').on('click', '#labelConfirmBack', function () {
        var $selected = $('.label_listBack').find('li label.selected'),
            arrId = [], arrName = [];
        $.each($selected, function (i, item) {
            arrId.push($(item).find('input').val());
            arrName.push($(item).find('span').text());
        });
        var $modal = $('#labelModalBack');
        $modal.modal('hide');
        $('.lableIdsBack').val(arrId.join(' '));
        $('.lableNamesBack').val(arrName.join(' ')).attr('readonly', 'readonly');
        //$('#lableNames').attr('readonly','readonly');
    });

//取消和关闭按钮
    $('.closeLabelModel').on('click', function () {
        var $modal = $('#labelModal');
        $modal.modal('hide');
    });
    //取消和关闭按钮 back
    $('.closeLabelModelBack').on('click', function () {
        var $modal = $('#labelModalBack');
        $modal.modal('hide');
    });
    /*新增*/
    $("#insert").click(function () {
        $("#maxTitle").text("新增页面标签关系");
        initDialog();
        labelConfirmCode = "0";
        $('#main').css("background-color", "#F0F0F0");
        $(".outDiv").show();
    });

    /*修改*/
    $(".update").click(function () {
        $("#maxTitle").text("修改页面标签关系");
        labelConfirmCode = "1";
        $('#main').css("background-color", "#F0F0F0");
        var menuId = $(this).attr("thisId");
        $.ajax({
                url: "/publicportal/l_getHelpMenuLable.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuLabel.menuId": menuId
                },
                success: function (res) {
                    if (res.token == "success") {
                        var item = res.data;
                        initDialog();
                        $("#menuId").attr("disabled",true)
                        $(".outDiv").show();
                        $("#menuId").val(item.menuId);
                        $("#menuDesc").val(item.menuDesc);
                        $(".lableNames").val(item.labelNames);
                        $(".lableIds").val(item.labelIds);
                        $(".label1Names").val(item.label1Names);
                        $(".label1Ids").val(item.label1Ids);
                        //应该需要添加完成后自动展示相关联的知识内容
                    } else {

                    }
                }
            }
        );
    });


    /*删除*/
    $(".delete").click(function () {
        var menuId = $(this).attr("thisId");
        $.ajax({
                url: "/publicportal/l_delete.action",
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuLabel.menuId": menuId
                },
                success: function (res) {
                    if (res.token == "success") {
                        alert("删除成功");
                        $("#form").submit();
                        //应该需要添加完成后自动展示相关联的知识内容
                    } else {

                    }
                }
            }
        );
    });


    /*提交*/
    $("#confirm").click(function () {
        $("#confirm").attr("disabled", true);
        setTimeout("$('#confirm').removeAttr('disabled')", 2000); //设置三秒后提交按钮可用
        var flag = confirmCheck();
        if (flag == false) {
            return;
        }
        var menuId = $("#menuId").val();
        var menuDesc = $("#menuDesc").val();
        var lableNames = $(".lableNames").val();
        var lableIds = $(".lableIds").val();
        var label1Names = $(".label1Names").val();
        var label1Ids = $(".label1Ids").val();
        var URL = "/publicportal/l_insert.action";
        var message = "新建成功";
        if (labelConfirmCode == "0") {
            URL = "/publicportal/l_insert.action";
            message = "新建成功";
        } else {
            URL = "/publicportal/l_update.action";
            message = "修改成功";
        }
        $.ajax({
                url: URL,
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuLabel.menuId": menuId,
                    "helpMenuLabel.menuDesc": menuDesc,
                    "helpMenuLabel.labelNames": lableNames,
                    "helpMenuLabel.labelIds": lableIds,
                    "helpMenuLabel.label1Names": label1Names,
                    "helpMenuLabel.label1Ids": label1Ids
                },
                success: function (res) {
                    if (res.token == "success") {
                        alert(message);
                        initDialog();
                        //应该需要添加完成后自动展示相关联的知识内容
                        $("#form").submit();
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
        $("#menuId").attr("disabled",false)
        $(".menuId").css("display", "none");
        $(".menuId").text("SHOP端页面Id不能为空");
        $("#menuId").val("");
        $("#menuDesc").val("");
        $(".lableNames").val("");
        $(".lableIds").val("");
        $(".label1Names").val("");
        $(".label1Ids").val("");
        $(".outDiv").hide();
    }

    function confirmCheck() {
        var menuId = $("#menuId").val();
        var menuDesc = $("#menuDesc").val();
        var lableNames = $(".lableNames").val();
        if (menuId == "") {
            $(".menuId").css("display", "block");
            return false;
        }
        var reg = /^[0-9]*$/;
        if (!reg.test($("#menuId").val())) {
            $(".menuId").text("SHOP端页面Id只能是数字哦");
            $(".menuId").css("display", "block");
            return false;
        } else {
            $(".menuId").css("display", "none");
            $(".menuId").text("SHOP端页面Id不能为空");
        }
        if (menuDesc == "") {
            $(".menuDesc").css("display", "block");
            return false;
        }
        if (lableNames == "") {
            $(".lableNames_fail").css("display", "block");
            return false;
        }
        return true;
    }

    $("#menuId").blur(function () {
        var menuId = $("#menuId").val();
        $.ajax({
                url: "/publicportal/l_menuIdRepeatCheck.action",
                type: "POST",
                ansync: false,
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                data: {
                    "helpMenuLabel.menuId": menuId
                },
                success: function (res) {
                    if (res.token == "error") {
                        $(".menuId").text("menuId已存在");
                        $(".menuId").css("display", "block");
                        $(".menuDesc").css("display", "none");
                        $("#confirm").attr("disabled", true);
                        return;
                    } else {
                        $(".menuId").text("SHOP端页面Id不能为空");
                        $(".menuId").css("display", "none");
                        $("#confirm").attr("disabled", false);
                    }
                }
            }
        );
    });
});


//显示标签列表
function initAllLabel() {
    var $labels = $('.label_list'), $btn = $('#labelConfirm');
    var lableIds = $('.lableIds').val().split(' ');
    $.ajax({
        type: "GET",
        url: "/lable/getAllLableList.action",
        contentType: 'application/json',
        cache: false,
        timeout: 15000,
        //dataType: 'json',
        success: function (res) {
            if (res.token == "faild") {
                $(".label_list").html("暂无标签数据");
                return;
            }
            $labels.empty();
            // res = eval("(" + res + ")");
            $.each(res.data, function (i, item) {
                $labels.append($("#label_item").html());
                var $label_item = $labels.find('.label_item').eq(i),
                    $group_title = $label_item.find('.label_group'),
                    $labels_block = $label_item.find('.labels_block');
                $group_title.attr('data-id', item.id);
                $group_title.text(item.name + '：');
                $.each(item.childList, function (j, label) {
                    $labels_block.append('<li>' +
                        '<label class="frm_checkbox_label aLabel">' +
                        '<input class="frm_checkbox" value="' + label.id + '" type="checkbox">' +
                        '<i class="icon_checkbox"></i>' +
                        '<span>' + label.name + '</span>' +
                        '</label>' +
                        '</li>');
                    var myLabel = $label_item.find('.aLabel').eq(j);
                    for (var t = 0; t < lableIds.length; t++) {
                        if (label.id == lableIds[t]) {
                            myLabel.addClass('selected');
                            myLabel.find('.frm_checkbox').attr('checked', 'checked');
                        }
                    }

                });
            });
            $labels.find('label.selected').length > 0 && $btn.removeClass('btn_disabled').addClass('btn_primary');
        }
    });
}


//显示标签列表
function initAllLabelBack() {
    var $labels = $('.label_listBack'), $btn = $('#labelConfirmBack');
    var lableIds = $('.lableIdsBack').val().split(' ');
    $.ajax({
        type: "GET",
        url: "/lable/getAllLableList.action",
        contentType: 'application/json',
        cache: false,
        timeout: 15000,
        //dataType: 'json',
        success: function (res) {
            if (res.token == "faild") {
                $(".label_listBack").html("暂无标签数据");
                return;
            }
            $labels.empty();
            // res = eval("(" + res + ")");
            $.each(res.data, function (i, item) {
                $labels.append($("#label_item").html());
                var $label_item = $labels.find('.label_item').eq(i),
                    $group_title = $label_item.find('.label_group'),
                    $labels_block = $label_item.find('.labels_block');
                $group_title.attr('data-id', item.id);
                $group_title.text(item.name + '：');
                $.each(item.childList, function (j, label) {
                    $labels_block.append('<li>' +
                        '<label class="frm_checkbox_label aLabel">' +
                        '<input class="frm_checkbox" value="' + label.id + '" type="checkbox">' +
                        '<i class="icon_checkbox"></i>' +
                        '<span>' + label.name + '</span>' +
                        '</label>' +
                        '</li>');
                    var myLabel = $label_item.find('.aLabel').eq(j);
                    for (var t = 0; t < lableIds.length; t++) {
                        if (label.id == lableIds[t]) {
                            myLabel.addClass('selected');
                            myLabel.find('.frm_checkbox').attr('checked', 'checked');
                        }
                    }

                });
            });
            $labels.find('label.selected').length > 0 && $btn.removeClass('btn_disabled').addClass('btn_primary');
        }
    });
}