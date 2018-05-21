/**
 * Created by zhaojianhong on 2018/4/12.
 */
$(function () {
    /*方案B:直接获取请求路径,根据路径匹配*/
    var prefix = ".menu_item a[href='";
    var menuHref = window.location.href;
    var suffix = "']";
     //线上
    menuHref_after = menuHref.substring(menuHref.indexOf("jd.com") + 6);
   /* //本地
    menuHref_after = menuHref.substring(menuHref.indexOf("t:8086") + 6);*/
    menuHref_after = menuHref_after.substring(0,menuHref_after.indexOf("action")+6);
    for (var i in $(".menu_item")) {
        var menu = $(".menu_item").eq(i);
        var itemi = $(".menu_item").eq(i).attr("hrefmore").split(",");
        for (var j in itemi) {
            if (menuHref_after == itemi[j].substring(0,menuHref_after.indexOf("action")+6)) {
                menu.css("background-color", "#DA4A42");
                menu.css("color", "#FFF");
                $("#crumbs_something").text(menu.text());
                $("#crumbs_something").attr("href", menuHref)
                return;
            }
        }
    }
    /* var suffix = "']";
     var menu = $(prefix + menuHref_after + suffix);
     menu.css("background-color", "#DA4A42");
     menu.css("color", "#FFF");
     $("#crumbs_something").text(menu.text());
     $("#crumbs_something").attr("href", menuHref);*/
});

/*方案A:相关信息放在session中,页面加载从session中取值*/
/*
 var menuId = $.session.get("menuId");
 var menuName = $.session.get("menuName");
 var menuHref = $.session.get("menuHref");
 /!*点击菜单项*!/
 $(".menu_item").click(function () {
 var menu = $(this);
 $.session.set("menuId",menu.attr("data-index"));
 $.session.set("menuName",menu.find("a").text());
 $.session.set("menuHref",menu.find("a").attr("href"));
 });*/
