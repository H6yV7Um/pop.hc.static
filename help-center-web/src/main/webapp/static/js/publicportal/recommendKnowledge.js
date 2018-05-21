
/*/!**
 * Created by zhaojianhong on 2018/5/17.
 *!/*/
$(document).ready(function(){
    //清除默认样式
        var clear = '<style>*{box-sizing: border-box;} ul,li{margin: 0;padding: 0; list-style: none;}a:hover, a:visited, a:link, a:active{text-decoration: none;color: black;}#list li a:hover{text-decoration: none;}</style>';
    $('head').append(clear);


    //刷新页面时请求到数据并保存
    var menuId = "404";
    $.ajax({
        url: "/publicportal/k_getData.action",
        type: "POST",
        contentType: 'application/x-www-form-urlencoded',
        dataType: 'json',
        data: {
            "helpMenuLabel.menuId": menuId
        },
        success: function (res) {
            console.log("request success");
            console.log(res);
            var data = res.data;
            //如果data里面有数据，才插入图标
            if(data.length!=0){
                var optList = $(".h-gotop-list");
                optList.prepend('<li id="prob" class="li-item item05">' +
                    '<div class="link-dlk"></div>'+
                    '</li>');
            }
            //列表头部
            var list = '<ul id="list" style="width: 123px;background:white;border-radius: 2px;border: 1px solid #blackTwo29;box-shadow:0 0 6px 0 ;position: fixed;top:300px;right:52px;display:none;"><li id="sp" style="width: 123px;height: 32px;list-style: none;padding-left: 13px;font-size: 14px;font-weight: bold;padding-top: 5px; line-height: 17px;border-bottom: 1px solid rgba(219 219 219);">常见问题</li></ul>';
            $('body').append(list);

            //将数据塞进list
            for(var i in data){
                var item = '<li style="padding:10px 14px 10px 13px;border-bottom: 1px dotted rgba(219 219 219);"><a href="'+data[i].url+'" style="color:rgb(99,99,99);max-height:30px;font-size: 12px; line-height: 15px;text-overflow:-o-ellipsis-lastline;overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;cursor:pointer;">'+data[i].name+'</a></li>';
                $('#list').append(item);

            }

            //鼠标移入item时换背景色
            $('#list li').on({"mouseover":function(){
                $(this).css("background","rgb(246,246,246)")
                $(this).find('a').css("color","rgb(0,0,0)")
            },"mouseout":function(){
                $(this).css("background","white")
                $(this).find('a').css("color","rgb(99,99,99)")
            }})

            var oList = document.getElementById('list');
            var oBox = document.getElementById('prob');
            oBox.onmouseover = oList.onmouseover = function(){
                $('#list').css("display","block");
                $('#prob').css({"background":'url(img/problem.png) no-repeat center center'});
            }
            oBox.onmouseout = oList.onmouseout = function(){
                $('#list').css("display","none");
                $('#prob').css({"background":'url(img/icon-01.png) no-repeat center center'});
            }


        }
    })

})

