$(function(){
    // 声明地址对象
    var currentAreaInfo;
    // 初始化当前地域信息
    function CurrentAreaInfoInit(){
        var level = 0;
        if($('#fouthName').find('em').html() !== null){
            level = 3;
        }else if($('#areaName').find('em').html() !== null){
            level = 2;
        }else if($('#cityName').find('em').html() !== null){
            level = 1;
        }else{
            level = 0;
        }
        //修改四级地址id，由原来的写死改成从后台动态读取四级id
        currentAreaInfo =  {"currentLevel": level,"currentProvinceId": $('#provinceId').val(),"currentProvinceName":$('#provinceName').find('em').html(),"currentCityId": $('#cityId').val(),"currentCityName":$('#cityName').find('em').html(),"currentAreaId": $('#areaId').val(),"currentAreaName":$('#areaName').find('em').html(),"currentTownId":$('#fouthId').val(),"currentTownName":$('#fouthName').find('em').html(),"hasFouth":""};
        //判断显示免运费提示（新疆西藏部分商品加上10元）
        var transportHtml="";
        if(currentAreaInfo.currentProvinceId == 26 || currentAreaInfo.currentProvinceId == 31){
            transportHtml="部分商品在此区域加收每件10元运费";
        }
        jQuery("#transportMsg").html(transportHtml);
    }



    // 初始化当前地域信息
    CurrentAreaInfoInit();
    initForth();

    /**
     * 商品页过来的请求，传值只传到三级，如果有第四级地址需要展开
     * **/

    function initForth(){
        if(currentAreaInfo.currentAreaId!=''&&currentAreaInfo.currentTownId==''){
            initList("/help/front/initFouth.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId + "&cityId=" + currentAreaInfo.currentCityId + "&areaId=" + currentAreaInfo.currentAreaId,"#stock_town_item",3);
        }
    }
    // 获取列表
    function initList(url,selectElement,index){
        jQuery.ajax({
            type: "POST",
            async: true,
            url: url,
            dataType: 'json',
            success: function(data){
                if (data != null && data != '') {
                    // 取消移除事件
                    $("#store-selector").unbind("mouseout");

                    // 如果没有第四级直接显示服务列表
                    if(isNotNullAndUndefined(data.hasNext) && !data.hasNext){

                        var objList = data.resultList;
                        var tempHtml = "";
                        // 记数器，单数服务需要在后边追加<li>显示样式
                        var num = 0;
                        // 打印服务
                        if(objList != null){
                            jQuery.each(objList, function(i){
                                tempHtml += objList[i];
                                num++;
                            });
                        }
                        // 如果是单数并且不能没有服务，在最后追加<li>
                        if(num % 2 != 0 ){
                            tempHtml += "<li></li>";
                        }
                        if(num == 0){
                            tempHtml ='<div class="fail-tip"><div>我们将尽快在此区域开通各种各样的服务。</div><div class="wish">敬请期待!<b></b></div></div>'
                        }
                        // 打印服务列表
                        jQuery("#servicebox").html(tempHtml);
                        // 关闭地址层显示
                        $('#store-selector').removeClass('hover');
                        // 恢复地址栏的下划线
                        $('#addressselect').removeAttr("style");
                        // 在地址栏中显示选择的地址
                        $("#store-selector .text div").html(currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+(currentAreaInfo.currentTownName==null?'':currentAreaInfo.currentTownName));
                        $("#store-selector .text div").attr("title",currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+(currentAreaInfo.currentTownName==null?'':currentAreaInfo.currentTownName));
                        // 将第四级置为false
                        currentAreaInfo.hasFouth = false;

                        //判断显示免运费提示（新疆西藏部分商品加上10元）
                        var transportHtml="";
                        if(currentAreaInfo.currentProvinceId == 26 || currentAreaInfo.currentProvinceId == 31){
                            transportHtml="部分商品在此区域加收每件10元运费";
                        }
                        jQuery("#transportMsg").html(transportHtml);

                    }else{
                        // 获取下一级地址信息
                        var objList = data.resultList;
                        if(objList != null){
                            // 模拟下一级tab
                            $('#JD-stock').find('[data-widget="tab-item"]').eq(index).trigger('click');
                            // 显示下一级tab
                            $('#JD-stock').find('[data-widget="tab-item"]').eq(index).removeClass('hide').addClass('hover');
                            // 显示HTML
                            var html = "<ul class='area-list'>";
                            var longhtml = "";
                            var longerhtml = "";
                            jQuery.each(objList, function(i){
                                var id = this.id;
                                var name = this.name;
                                // 根据地址长度显示相应的样式
                                if(name.length > 12){
                                    longerhtml += "<li class='longer-area'><a href='#none' data-value='"+id+"'>"+name+"</a></li>";
                                }
                                else if(name.length > 5){
                                    longhtml += "<li class='long-area'><a href='#none' data-value='"+id+"'>"+name+"</a></li>";
                                }
                                else{
                                    html += "<li><a href='#none' data-value='"+id+"'>"+name+"</a></li>";
                                }
                            });
                            html +=longhtml+longerhtml+"</ul>";
                            // 在地址层中显示地址信息
                            jQuery(selectElement).html(html);
                            // 将第四级标识置为true
                            currentAreaInfo.hasFouth = true;
                        }else{
                            // 取消移除事件
                            $("#store-selector").unbind("mouseout");
                            // 清空地区列表
                            jQuery(selectElement).html("");
                            // 打印服务列表
                            //jQuery("#servicebox").html(tempHtml);
                            // 关闭地址层显示
                            $('#store-selector').removeClass('hover');
                            // 恢复地址栏的下划线
                            $('#addressselect').removeAttr("style");
                            // 在地址栏中显示选择的地址
                            $("#store-selector .text div").html(currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName);
                            $("#store-selector .text div").attr("title",currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName);
                            // 将第四级置为false
                            currentAreaInfo.hasFouth = false;
                        }
                    }
                }
            },
            error: function(data, status, e){
                alert(e+"");
                // 超时显示第四级
                $('#JD-stock').find('[data-widget="tab-item"]').eq(index).trigger('click');
                // 将地址栏显示为空
                $('#'+getIdNameByLevel(index+1)).html("");
            }
        });
    }

    // 绑定text mouseover事件
    $("#store-selector").bind("mouseover",function () {
        $('#store-selector').addClass('hover');
        $('#addressselect').css("border-bottom", "0 none");
    });

    $("#store-selector").bind("mouseout",function(){
        // 关闭地址层显示
        $('#store-selector').removeClass('hover');
        // 恢复地址栏的下划线
        $('#addressselect').removeAttr("style");
    });

    // 定义事件
    var JdStock = {
        init: function(selector, fn) {
            // 定义地址一共几级
            this.areaLevel = 3;
            // 声明层的别名
            this.jdStock = $(selector);
            // 声明层的别名
            this.jdStockArea = this.jdStock.find('[data-widget="tab-item"]');
            // 声明层的别名
            this.areaList = this.jdStock.find('[data-widget="tab-content"]');
            // 声明回调函数
            this.callback = fn;
            // 执行事件绑定
            this.bindEvent();

        },
        bindEvent: function() {
            // 声明别名
            var _this = this;

            this.jdStock.Jtab({
                event: 'click',
                compatible:true,
                index:currentAreaInfo.currentLevel
            });

            // 绑定地址头链接事件
            this.jdStockArea.bind('click', function() {
                var index = parseInt( $(this).attr('data-index'), 10 );
                _this.jdStockArea.find('a').removeClass('hover');
                _this.jdStockArea.eq(index).find('a').addClass('hover');
            });

            // 绑定地址列表链接事件
            this.areaList.find('a').livequery('click', function(e) {
                // 声明对象
                var areaLevel = parseInt($(e.target).parents('.mc').attr('data-area'), 10),
                    text = $(e.target).text(),
                    value = $(e.target).attr('data-value');

                // 如果不是A标签返回
                if (e.target.nodeName !== 'A') {
                    return;
                }

                // 设置当前选择地区
                _this.setArea(areaLevel, text, value);

                // 切换到下一个tab
                //_this.switchTab(areaLevel+1);

                // 清除当前地区后面tab内容
                for (var i = areaLevel;  i <= _this.areaLevel - 1; i++) {
                    _this.resetTab(i+1);
                }

            });

        },
        switchTab: function(index) {
            // 如果不是最后一级摸拟头点击事件
            if(index !== 3){
                this.jdStockArea.eq(index).trigger('click');
            }
        },
        resetTab: function(index) {
            // 点击地址信息后将下一级以后的头信息删除
            this.jdStockArea.eq(index).addClass("hide");
        },
        setArea: function(index, name, value) {
            // 显示地址头的名称
            this.jdStockArea.eq(index).find('em').html(name.length>6?name.substring(0,6):name);
            // 将下一级头的名称置为请选择
            this.jdStockArea.eq(index + 1).find('em').html("请选择");
            // 如果是第一级
            if(index ==0){
                // 设置省的属性，将其它置为空
                currentAreaInfo.currentProvinceId = value;
                currentAreaInfo.currentProvinceName = name;
                currentAreaInfo.currentCityId = 0;
                currentAreaInfo.currentCityName = "";
                currentAreaInfo.currentAreaId = 0;
                currentAreaInfo.currentAreaName = "";
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
                // 显示加载效果
                $("#stock_city_item").html("<div class='iloading'>正在加载中，请稍候...</div>");
                // 获取下一级地址
                initList("/help/front/initCity.action?tem="+new Date().getTime()+"&provinceId=" + currentAreaInfo.currentProvinceId,"#stock_city_item",index+1);
            }else if(index == 1){
                // 设置市的属性，将其它置为空
                currentAreaInfo.currentCityId = value;
                currentAreaInfo.currentCityName = name;
                currentAreaInfo.currentAreaId = 0;
                currentAreaInfo.currentAreaName = "";
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
                $("#stock_area_item").html("<div class='iloading'>正在加载中，请稍候...</div>");
                initList("/help/front/initArea.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId + "&cityId=" + currentAreaInfo.currentCityId,"#stock_area_item",index+1);
            }else if(index == 2){
                // 设置区的属性，将其它置为空
                currentAreaInfo.currentAreaId = value;
                currentAreaInfo.currentAreaName = name;
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
                $("#stock_town_item").html("<div class='iloading'>正在加载中，请稍候...</div>");
                initList("/help/front/initFouth.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId + "&cityId=" + currentAreaInfo.currentCityId + "&areaId=" + currentAreaInfo.currentAreaId,"#stock_town_item",index+1);
            }else if(index == 3){
                // 设置镇的属性，将其它置为空
                currentAreaInfo.currentTownId = value;
                currentAreaInfo.currentTownName = name;
                initList("/help/front/initService.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId+"&cityId=" + currentAreaInfo.currentCityId+"&areaId=" + currentAreaInfo.currentAreaId + "&fouthId=" + currentAreaInfo.currentTownId,"#stock_town_item",index+1);
            }
        }
    };

    // 初始化
    JdStock.init('#JD-stock', function(val, name, index) {

    });



    // 公共方法定义
    // 判断对象是否为空或undefined
    isNotNullAndUndefined = function(param) {
        if (param == null || typeof(param) == 'undefined') {
            return false;
        } else {
            return true;
        }
    };


    // 关闭层方法
    closeAdd = function() {
        // 将层显示的样式隐藏
        $('#store-selector').removeClass('hover');
        // 将地址显示栏的下划线恢复
        //$('#addressselect').css("border-bottom", "1px solid #CECBCE");
        $('#addressselect').removeAttr("style");
    };

    // 根据级别获取对应的DIVID
    function getIdNameByLevel(level){
        var idName = "";
        if (level == 1){
            idName = "stock_province_item";
        }
        else if (level == 2){
            idName = "stock_city_item";
        }
        else if (level == 3){
            idName = "stock_area_item";
        }
        else if (level == 4){
            idName = "stock_town_item";
        }
        return idName;
    };

})