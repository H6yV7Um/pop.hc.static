//京东头的js
(function() {
    var navigations = [
        {e:"computer",c:"电脑办公"},
        {e:"electronic",c:"家用电器"},
        {e:"digital",c:"手机数码"},
        {e:"home",c:"家居生活"},
        {e:"clothing",c:"服饰鞋帽"},
        {e:"beauty",c:"个护化妆"},
        {e:"watch",c:"钟表首饰"},
        {e:"sports",c:"运动健康"},
        {e:"baby",c:"母婴玩具"},
        {e:"food",c:"食品饮料"}
    ];
    $.each(navigations, function(i) {
        if (navigations[i]["e"] == document.body.id) {
            $("#nav-extra").before("<div class='curr'><a href='//www.360buy.com/" + navigations[i]["e"] + ".html'>" + navigations[i]["c"] + "</a></div>");
        }
    });
})();
$(".allsort").hoverForIE6({current:"allsorthover",delay:200});
$(".allsort .item").hoverForIE6({delay:150});






