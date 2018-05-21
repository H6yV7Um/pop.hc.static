/**
 * Created by zhaojianhong on 2018/4/10.
 */

/**
 * Created by zhaojianhong on 2018/4/8.
 */

$(function () {
        if ($('.label_list').find('label.selected').length == 0) {
            $('#labelConfirm').attr("disabled", true);
        }

//添加标签按钮，获取所有标签
        $('#getAllLabel').on('click', function () {
            openLabelModal(1);
        });

//打开标签对话框
        function openLabelModal(index) {
            //初始化模态框
            var $modal = $('#labelModal');
            $modal.attr('data-index', index);
            $modal.find('.img_item label').removeClass('selected');
            $('#labelConfirm').removeClass('btn_primary').addClass('btn_disabled');
            //document.getElementById('lableNames').removeAttribute("readonly");
            initAllLabel();
            $modal.modal('show');
        }

        //选择单个label
        $('.label_list').on('click', 'li', function (e) {
            var $label = $(this).find('label'), $input = $(this).find('input'), $btn = $('#labelConfirm');
            //e.preventDefault();
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

//取消和关闭按钮
        $('.closeLabelModel').on('click', function () {
            var $modal = $('#labelModal');
            $modal.modal('hide');
        });

    }
);

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