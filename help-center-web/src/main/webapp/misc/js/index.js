//����ͷ��js
(function() {
    var navigations = [
        {e:"computer",c:"���԰칫"},
        {e:"electronic",c:"���õ���"},
        {e:"digital",c:"�ֻ�����"},
        {e:"home",c:"�Ҿ�����"},
        {e:"clothing",c:"����Ьñ"},
        {e:"beauty",c:"������ױ"},
        {e:"watch",c:"�ӱ�����"},
        {e:"sports",c:"�˶�����"},
        {e:"baby",c:"ĸӤ���"},
        {e:"food",c:"ʳƷ����"}
    ];
    $.each(navigations, function(i) {
        if (navigations[i]["e"] == document.body.id) {
            $("#nav-extra").before("<div class='curr'><a href='//www.360buy.com/" + navigations[i]["e"] + ".html'>" + navigations[i]["c"] + "</a></div>");
        }
    });
})();
$(".allsort").hoverForIE6({current:"allsorthover",delay:200});
$(".allsort .item").hoverForIE6({delay:150});






