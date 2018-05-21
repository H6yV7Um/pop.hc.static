/**
 * Created by hongjian1 on 2014/11/18.
 */


//群发入口
$(function(){


    //群发对象，性别选择相关逻辑
    $('.jsDropdownBt').on('click', function(){
        $('.jsDropdownList').hide();
        $(this).siblings('.jsDropdownList').show();
    });

    $('.dropdown_data_item').on('click', function(){
        var itemName = $(this).find('.jsDropdownItem').attr('data-name'),
            typeName = $(this).find('.jsDropdownItem').attr('data-value');

        $(this).parents('.dropdown_menu').find('.jsBtLabel').text(itemName);
        $(this).parents('.jsDropdownList').hide();

        switch (typeName){
            case 'group':
                $('#js_group').css({display:'inline-block'});
                $("#isSendGroup").val(2);
                $.each($("#js_group").find("a"), function (i, item) {
                    if($(item).attr("data-name")=="未分组"){
                        $('#groupId').val($(item).attr("data-value"));
                        return false;
                    }
                });

                break;
            case '-1':
                $('#js_group').css({display:'none'});
                $("#isSendGroup").val(1);
                $('#groupId').val(0);
                break;
            case '-2':
                $("#msgType").val(1);
                break;
            case '-3':
                $("#msgType").val(2);
                break;
            default :
                $('#groupId').val(typeName);
        }
    });

    //群发内容提交
    $('#sendGroupMsg').on('click', function(){
        if($('#content').val()==""){
            $('#sendResponse').html('请输入消息内容');
            $('#sendResponse').css('display','inline-block');
            return false;
        }
        var options = {
            dataType : "json",
            success : function(data) {
                $('#sendResponse')[0].style.display = "inline-block";
                $('#sendResponse').html(data.desc);
            },
            error : function(result) {
                $('#sendResponse').html("发送失败，请重新发送！");
                $('#sendResponse').css('display','inline-block');
            }
        };
        $('#groupMsgForm').ajaxSubmit(options);
    });

    //群发内容切换条逻辑
    $('.tab_nav').on('click', function(){
        var tabClass = $(this).attr('data-tab');

        switch (tabClass){
            case '.js_textArea':
                //激活状态切换
                $(this).addClass('selected').siblings('.tab_nav').removeClass('selected');

                //内容切换
                $(tabClass).parent().show().siblings().hide();
                break;
            case '.js_appmsgArea':
                //查询素材库，打开素材列表
                queryMaterialList(1,10);
                $('.article_dialog_wrp').show();
                $('.mask').show();

                //重置确认按钮和激活状态
                $('.js_btn[data-index="0"]').addClass('disabled').parent().addClass('btn_disabled').removeClass('btn_default');
                $('.appmsg').removeClass('selected');
                break;
        }
    });

    function queryMaterialList(currentPage, pageSize) {
        $.ajax({
            type: "GET",
            url: "/message/queryMaterialListPage.action",
            contentType: 'application/json',
            cache: false,
            timeout: 15000,
            dataType: 'json',
            data: {currentPage: currentPage, pageSize: pageSize},
            success: function (res) {
                var $inner = $('.appmsg_col').find(".inner");
                $('.appmsg_col').find(".inner").html('')
                $.each(res.materialBeans, function (i, item) {
                    $inner.eq(i%2).append($("#img_item").html());
                    var $img_item = $inner.eq(i%2).find('.img_item').eq($inner.eq(i%2).find('.img_item').length-1);
                    var t = new Date(item.details[0].created);
                    if(item.details.length==1){
                        $img_item.find(".appmsg").attr('data-id', item.materialId);
                        $img_item.find('img').attr('src',item.details[0].photoUrl);
                        $img_item.find('.appmsg_date').text(item.details[0].title);
                        $img_item.find('.appmsg_date').append("<br/>").append(formatDate2String(t));
                        $img_item.find('.appmsg_title').hide()
                        $img_item.find('.appmsg_content').find('.lbl_content').prepend(item.details[0].digest);
                        return;
                    }
                    $img_item.find('.appmsg_date').append(formatDate2String(t));
                    $img_item.find(".appmsg").attr('data-id', item.materialId);
                    $img_item.find('.appmsg_title').text(item.details[0].title)
                    $img_item.find('img').attr('src',item.details[0].photoUrl);

                    $.each(item.details, function (j, childitem) {
                        if(j==0)return;
                        $img_item.find('.child').append($("#img_item_child").html());
                        var $img_item_child = $img_item.find(".img_item_child").eq(j-1);
                        $img_item_child.find('img').attr('src',childitem.photoUrl);
                        $img_item_child.find('.appmsg_title').text(childitem.title);
                    });
                });
                if(res.page.totalCount==0){
                    $(".appmsg_list").remove();
                    $("#norows").text("没有符合的记录");
                    return ;
                }
                showPage(res.page);
            }
        })
    }

    function formatDate2String(time){
        var date = "";
        date = date + time.getFullYear() +"-";
        if(time.getMonth()<9){
            date = date + "0"+(time.getMonth()+1)+"-";
        }else{
            date = date + (time.getMonth()+1)+"-";
        }
        if(time.getDay()<10){
            date = date + "0"+time.getDay();
        }else{
            date = date + time.getDay();
        }

        return date;
    }

    //弹窗关闭按钮
    $('.pop_closed, .js_btn[data-index="1"]').on('click', function(){
        closeWin();
    });


    //弹窗确定按钮
    $('.js_btn[data-index="0"]').on('click', function(){

        var $dialog_wrp = $(this).parents('.dialog_wrp');
        if($dialog_wrp.hasClass('article_dialog_wrp')){//图文对话框
            //图文按钮选中
            $('.tab_appmsg').addClass('selected').siblings().removeClass('selected');

            //编辑框显示图文
            var appmsg = "";
            $('.appmsg_list').find('.appmsg').each(function(){
                if($(this).hasClass('selected')){
                    $(this).removeClass('selected');
                    $(this).find('.appmsg_mask').remove();
                    $(this).find('.icon_card_selected').remove();
                    appmsg = $(this).parent().html();
                    //设置提交数据
                    $("#conType").val(1);//设置为图文格式
                    $("#content").val($(this).attr('data-id'));
                }
            });
            $('.js_appmsgArea').html(appmsg);

            //内容切换
            $('.js_appmsgArea').parent().show().siblings().hide();
        }

        closeWin();
    });

    //图文库列表点击事件
    $('.appmsg_list').delegate('.appmsg', 'click', function(){
        $('.appmsg').removeClass('selected');
        $(this).addClass('selected');

        //点击图文之后，激活确定按钮
        $('.article_dialog_wrp').find('.js_btn[data-index="0"]').removeClass('disabled').parent().removeClass('btn_disabled').addClass('btn_primary');
    });

    //显示分页
    function showPage($page){
        var pageSize = 10;
        $('.pagination').html($('#page').html());
        if($page.currentPage>1){//前一页箭头
            $('.page_prev').bind('click',function(){
               queryMaterialList($page.currentPage-1, pageSize);
            }).show();
        }else{
            $('.page_prev').hide();
        }
        $('.current_num').text($page.currentPage);
        $('.total_num').text($page.totalPage);
        if($page.currentPage<$page.totalPage){//后一页箭头
            $('.page_next').bind('click',function(){
                queryMaterialList($page.currentPage+1, pageSize);
            }).show();
        }else{
            $('.page_next').hide();
        }
        $('.goto_area').find('input[name="totalPage"]').val($page.totalPage);
        $('.goto_area').find('.btn').bind('click',function(){
            var page = $(this).siblings('input[name="toPage"]').val();
            if(validPage(page,$page.totalPage)){
                 queryMaterialList(page, pageSize);
            }
        });
    }

    //关闭模态层和弹框
    function closeWin(){
        $('.dialog_wrp').hide();
        $('.mask').hide();
    }

    //文字输入字数
    var limit = 600;
    var $obj = $('.js_textArea').find('.js_editorArea[contenteditable="true"]');
    $obj.on('keyup keydown',function(){
        var val = $(this).html();
        var len = val.length;
        if(limit >= len){
            var result = limit - len;
            $(this).parent().find('.js_editorTip').find('em').text(result);
            $('#contont').val(result);
            $("#conType").val(0);//设置为纯消息格式
            $("#content").val(result);
        }else{
//            return false;
        }
    });
});

