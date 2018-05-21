/**
 * Created with IntelliJ IDEA.
 * User: qiaoshuyi
 * Date: 14-12-18
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
function attention(msg,width){
    var self = this;
    var w = width?width:280;
    jqm.alert({
        w:w,
        title:'提示',
        type:'attention',
        content:'<div>'+msg+'</div>',
        self:self,
        onConfirm:function(){
        }
    });
    $("#jqmAlertBtn").html("关闭")
}
function jqmConfirm(content,confirmFun,cancelFun,btntxt1,btntxt2){
    var self = this;
    jqm.confirm({
        w:400,
        title:'提示',
        content:content,
        self:self,
        onConfirm:function(){
            if(confirmFun){
                confirmFun();
            }
        },
        onClose:function(){
            if(cancelFun){
                cancelFun();
            }
        }
    });
    $('.modal .fakeMsg').css('text-align','center');
    $(".btn-m").html(btntxt1?btntxt1:'确定');
    $(".btn-s").html(btntxt2?btntxt2:'取消');
}

//判断值是否为空
function isEmpty(val){
    if(val.length == 0){
        return true;
    }else{
        return false;
    }
}

//验证输入字符长度，忽略中英文
function gtLength1(val, maxlength){
    var curlength = val.length;
    if(curlength > maxlength){
        return true;
    }else{
        return false;
    }
}

//验证输入字符长度
function gtLength(val, maxlength){
    var curlength = val.realLength();
    if(curlength > maxlength){
        return true;
    }else{
        return false;
    }
}

//扩展length方法，非ASCII码为两个字符
String.prototype.realLength = function(){
    return this.replace(/[^\x00-\xff]/g, "**").length;
}

//去掉首尾空格回车换行
function trimUtil(val){
//    val = val.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
//    val = val.replace(/(^\s*)|(\s*$)/g,'');
//    val = val.replace(/(^\r*)|(\r*$)|(^\n*)|(\n*$)/g,'');
//    val = val.replace(/\n/g,'<br>');
    val = val.replace(/(^\s*)|(\s*$)/g,'');
    val = val.replace(/(^\r*)|(\r*$)|(^\n*)|(\n*$)/g,'');
    return val;
}

function replaceUtil(val){
    return val.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'<br>');
}

function replaceReUtil(val){
    return val.replace(/<br>/g,'\n').replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/&amp;/g,'&');
}
//获取url参数
function getUrlParam(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
//年-月-日
function formatDate2String(time){
    var date = "";
    date = date + time.getFullYear() +"-";
    if(time.getMonth()<9){
        date = date + "0"+(time.getMonth()+1)+"-";
    }else{
        date = date + (time.getMonth()+1)+"-";
    }
    if(time.getDate()<10){
        date = date + "0"+time.getDate();
    }else{
        date = date + time.getDate();
    }
    return date;
}

//時:分
function formatTime2String(time){
    var t = "";
    if(time.getHours()<9){
        t = t + "0"+(time.getHours())+":";
    }else{
        t = t + (time.getHours())+":";
    }
    if(time.getMinutes()<10){
        t = t + "0"+time.getMinutes();
    }else{
        t = t + time.getMinutes();
    }
    return t;
}


//时间差
function getDifftime(t1 ,t2){
    var begin_time = new Date(t1.replace(/-/g,"\/"));
    var end_time = new Date(t2.replace(/-/g,"\/"));
    var diff_time = (end_time - begin_time)/(1000*60*60*24);
    return diff_time;
}

//设置input文本框的默认值
function setInputDefault(obj,clickClass,blurClass){
    $(obj).on({
        click:function(){
            if($(obj).val()==obj.defaultValue){$(obj).val('');$(obj).removeClass(blurClass).addClass(clickClass);}
        },
        blur:function(){
            if($(obj).val()==''){$(obj).val(obj.defaultValue);$(obj).removeClass(clickClass).addClass(blurClass);}
        }
    });
}
function removeHTMLTag(str) {
    str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
    str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
    //str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
    str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
    return str;
}
