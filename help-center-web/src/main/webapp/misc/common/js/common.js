/**
 * Created by zhaojianhong on 2018/4/12.
 */
$(function () {
    /*����B:ֱ�ӻ�ȡ����·��,����·��ƥ��*/
    var prefix = ".menu_item a[href='";
    var menuHref = window.location.href;
    var suffix = "']";
     //����
    menuHref_after = menuHref.substring(menuHref.indexOf("jd.com") + 6);
   /* //����
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

/*����A:�����Ϣ����session��,ҳ����ش�session��ȡֵ*/
/*
 var menuId = $.session.get("menuId");
 var menuName = $.session.get("menuName");
 var menuHref = $.session.get("menuHref");
 /!*����˵���*!/
 $(".menu_item").click(function () {
 var menu = $(this);
 $.session.set("menuId",menu.attr("data-index"));
 $.session.set("menuName",menu.find("a").text());
 $.session.set("menuHref",menu.find("a").attr("href"));
 });*/
