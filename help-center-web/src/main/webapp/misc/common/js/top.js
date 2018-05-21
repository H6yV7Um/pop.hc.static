$(function(){
    getLoginCookie();
    function getLoginCookie(){
        var url = '/index/getLoginCookie.action';
        $.ajax({
            type:'GET',
            url:url,
            dataType: 'json',
            success:function(res){
                $('.nickname').html(res.nickName);
                $('.avatar').attr("src",res.logo);
            }
        })
    }



    //$(".nickname").html(getServiceNoNameFromCookie());
    //$(".avatar").attr("src",getServiceNoLogoFromCookie());

    //备份jquery的ajax方法
    var _ajax=$.ajax;

    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){}
        }
        if(opt.error){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }

        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                if(XMLHttpRequest.responseText=="NotLogin") {
                    window.location.href= window.location.href;
                    return;
                }
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus){
                //成功回调方法增强处理
                if(data.responseText=="NotLogin") {
                    window.location.href= window.location.href;
                    return;
                }
                fn.success(data, textStatus);
            }
        });
        _ajax(_opt);
    };
});

function getServiceNoNameFromCookie(){
    var serviceno = getServicenoFromCookie();
    if(serviceno==null||serviceno[1]==null)
        return "";
    return decodeURI(serviceno[1]);
}

function getServiceNoLogoFromCookie(){
    var serviceno = getServicenoFromCookie();
    if(serviceno==null||serviceno[2]==null)
        return "/misc/common/css/i/getheadimg.png";
    return decodeURIComponent(serviceno[2]).replace("http:","");
}

function getServicenoIdFromCookie(){
    var serviceno = getServicenoFromCookie();
    if(serviceno==null||serviceno[0]==null)
        return "";
    return serviceno[0];
}

function getValueByCookieName(name){
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return arr[2];
    else
        return null;
}

function getServicenoFromCookie(){
    var cookie = getValueByCookieName("no_id");
    if(cookie == null)
        return null
    var serviceno = cookie.split("&&")[1];
    return serviceno.split("$#%")
}

function checkIsLogin(){
    if(getValueByCookieName("no_id")==null){
        alert("当前用户已失效，请重新登录！");
        window.location.href = window.location.href;
    }
}

/**
 * 校验输入的页码
 * @param toPage
 * @param totalPage
 */
function validPage(toPage, totalPage){
    if(toPage!=''){
        var r = /^\+?[1-9][0-9]*$/;
        if(!r.test(toPage)){
            alert("请输入大于0的整数！");
            return false;
        }
        else if(toPage*1>totalPage*1){
            alert("您输入的页数已超过范围！");
            return false;
        }
    }else{
        alert("请输入页数！");
        return false;
    }
    return true;
}
/**
 * 跳转页面
 * @param url
 */
function goToPage(event){
    $('.pagination .fail').hide();
    var toPage = document.getElementsByName("toPage")[0].value;
    var totalPage = document.getElementsByName("totalPage")[0].value;

    if(toPage!=''){
        var r = /^\+?[1-9][0-9]*$/;
        if(!r.test(toPage)){
            $('.pagination .fail').text("请输入大于0的整数！").show();
            document.getElementsByName("toPage")[0].value='';
            return;
        }
        else if(toPage*1>totalPage*1){
            $('.pagination .fail').text("您输入的页数已超过范围！").show();
            document.getElementsByName("toPage")[0].value='';
            return;
        }
//        if(url.indexOf("?")>-1){
//            url += "&";
//        }else{
//            url += "?";
//        }
//        url += "currentPage="+toPage ;
//        window.location=url;
        var formId = event.target.parentNode.parentNode.getAttribute('data-form');
        $("#currentPage").val(toPage);
        document.getElementById(formId).submit();
    }
    else{
        $('.pagination .fail').text("请输入页数！").show();
    }
}
function goToPageA(toPage,event){
    var formId;
    if(event.target.tagName == 'IMG'){
       formId = event.target.parentNode.parentNode.getAttribute('data-form');
    }else{
        formId = event.target.parentNode.getAttribute('data-form');
    }
    $("#currentPage").val(toPage);
    document.getElementById(formId).submit();
}

function initPagination(page,func,data){
    var $page = $(".pagination");
    if(page.totalCount==0){
        $page.html("<p class='empty_tips'>暂无数据</p>");
    }else{
        //显示分页
        $page.pagination(page.totalCount, {
            prev_text: '<img src="/misc/common/css/i/arrow_left.png"/>',
            next_text: '<img src="/misc/common/css/i/arrow_right.png"/>',
            items_per_page: page.pageSize, //每页显示10项
            current_page: data.currentPage-1,
            num_edge_entries: 2, //边缘页数
            num_display_entries: 2,//主体页数
            callback_flag: false,
            callback: function(current, obj){
                data.currentPage = current+1;
                func(data);
                return false;
            }
        });
    }
}