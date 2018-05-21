/**
 * Created with IntelliJ IDEA.
 * User: qiaoshuyi
 * Date: 14-12-6
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */
(function(){
    /**
     * 点击页面空白处隐藏弹框pos弹出框位置
     * 点击按钮样式open_box，弹框样式pop_box，关闭弹框样式close_box
     * 点击按钮属性pop对应弹框id
     * @param options
     */
    $.fn.popBox = function(options){
        var defaults = {
            openBoxClass: ".open_box",
            popBoxClass: ".pop_box",
            closeBoxClass: ".close_box",
            pos: false,
            side:'bottom',
            x:0,
            y:0
        }
        var options = $.extend(defaults, options);
        this.click(function(e){
            var e = e || window.event; //浏览器兼容性
            var elem = e.target || e.srcElement;
            var $btn = $(elem).closest(options.openBoxClass);//页面弹出框按钮
            var $pop = $(elem).closest(options.popBoxClass);//页面弹出框
            if($btn.length == 0 && $pop.length == 0){ //不是按钮和弹出框内部元素
                $(options.popBoxClass).hide();
            }else if($btn.length > 0){//按钮的内部元素
                $(options.popBoxClass).hide();
                if($btn.css('cursor') == 'pointer'){ //按钮可点击
                    var pop_id = $btn.attr('pop'),$pop = $('#'+pop_id);
                    $pop.show();
                }
            }else{//弹出框内部元素
                var $close = $(elem).closest(options.closeBoxClass);
                if($close.length > 0){
                    $pop.hide();
                }
            }
            stopPropagation(e);
        });
    }
    function stopPropagation(e) {
        if (e.stopPropagation)
            e.stopPropagation();
        else
            e.cancelBubble = true;
    }

    /**
     * 错误提示
     * @param options
     * msg提示信息
     * show显示提示
     * remove移除提示
     */
    $.fn.showTip = function(options){
        var tip = "<span class='response_tip'></span>";
        var defaults = {
            msg: 'error',
            show: true,
            remove: true
        }
        var options = $.extend(defaults, options);
        var $this = $(this);
        $this.each(function(){
            $this.find('.response_tip').remove();
            if(options.show){ //显示提示
                $this.append(tip);
                $this.find('.response_tip').html(options.msg);
                if(options.remove){
                    setTimeout(function(){
                        $this.find('.response_tip').remove();
                    },5000);
                }
            }
        });
    }

    //确定提示框
    $.fn.confirmer = function (options) {
        var defaults = {
            msg: "Are you sure?"
        }
        var options = $.extend(defaults, options);
        var control = $(this);
        $(control).click(function () {
            return confirm(options.msg);
        });
    };

    /**
     * 提示插件
     * $(selector).toolTip({   //selector 为jquery选择器
     *  msg:'your messages!',    //你的提示消息  必填
     *  side:'top', 上右下左 默认为1（上）
     *  color:'#FFF', //提示文字色 默认为白色 可选
     *  bg:'#F00',//提示窗背景色 默认为红色 可选
     *  x:0,//横向偏移  正数向右偏移 负数向左偏移 默认为0 可选
     *  y:0,//纵向偏移  正数向下偏移 负数向上偏移 默认为0 可选
     * })
     */
    $.fn.toolTip = function(options){
        var defaults = {
            msg:'',
            side:"top",
            x:0,
            y:0
        }
        var options = $.extend(defaults,options);
        var $tips =$('<div class="tooltip"><div class="tooltip_inner">'+options.msg+'</div><i class="tooltip_arrow tooltip_arrow_out tooltip_arrow_'+options.side+'"></i><i class="tooltip_arrow tooltip_arrow_in tooltip_arrow_'+options.side+'"></i></div>').appendTo(document.body);
        $(this).hover(function(){
            var $el = $(this),data = $el.attr('data-tooltip');
            $tips.find('.tooltip_inner').text(data);
            var el_top = $el.offset().top,
                el_left = $el.offset().left,
                el_height = $el.height(),
                el_width = $el.width();
            switch(options.side){
                case "top":
                    $tips.css({
                        top: el_top - $tips.height() + options.y,
                        left: el_left + (el_width-$tips.width())/2 + options.x
                    });
                    break;
                case "right":
                    $tips.css({
                        top: el_top - el_height/2 + options.y,
                        left: el_left + el_width + options.x
                    });
                    break;
                case "bottom":
                    $tips.css({
                        top: el_top + el_height + options.y,
                        left: el_left + (el_width-$tips.width())/2 + options.x
                    });
                    break;
                case "left":
                    $tips.css({
                        top: el_top - el_height/2 + options.y,
                        left: el_left -$tips.width() + options.x
                    });
                    break;
            }
            $tips.show();
        },function(){
            $tips.hide();
        });
    }

    /**
     * table排序
     * @param options
     */
    $.fn.tableSorter = function(options){
        var defaults = {
            order:"asc", //asc desc
            thi:0, //th索引
            type:'digit'
        }
        var options = $.extend(defaults,options);
        var tds_text = [];
        $(this).each(function(){
            var $this = $(this),$tr = $this.find('tr').not(':eq(0)');
            //获取排序字段
            $tr.each(function(){
                var text = $(this).find('td:eq('+options.thi+')').text();
                tds_text.push(text);
            });
            //设置排序
            tds_text.sort(function(a,b){
                if(options.type == 'time'){//时间转换
                    a = a.replace(/-/g,'/');
                    a = new Date(a).getTime();
                    b = b.replace(/-/g,'/');
                    b = new Date(b).getTime();
                }
                if(options.order == 'asc'){ //升序
                    return a-b;
                }else{ //降序
                    return b-a;
                }
            });
            //tr重新排序
            for(var i=0,len=tds_text.length; i<len; i++){
                var td_text = tds_text[i];
                $tr.each(function(){
                    var this_text = $(this).find('td:eq('+options.thi+')').text();
                    if(this_text == td_text){
                        $this.append($(this));
                    }
                });
            }
        });
    }

})(jQuery);