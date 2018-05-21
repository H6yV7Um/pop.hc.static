$(function(){
    // ������ַ����
    var currentAreaInfo;
    // ��ʼ����ǰ������Ϣ
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
        //�޸��ļ���ַid����ԭ����д���ĳɴӺ�̨��̬��ȡ�ļ�id
        currentAreaInfo =  {"currentLevel": level,"currentProvinceId": $('#provinceId').val(),"currentProvinceName":$('#provinceName').find('em').html(),"currentCityId": $('#cityId').val(),"currentCityName":$('#cityName').find('em').html(),"currentAreaId": $('#areaId').val(),"currentAreaName":$('#areaName').find('em').html(),"currentTownId":$('#fouthId').val(),"currentTownName":$('#fouthName').find('em').html(),"hasFouth":""};
        //�ж���ʾ���˷���ʾ���½����ز�����Ʒ����10Ԫ��
        var transportHtml="";
        if(currentAreaInfo.currentProvinceId == 26 || currentAreaInfo.currentProvinceId == 31){
            transportHtml="������Ʒ�ڴ��������ÿ��10Ԫ�˷�";
        }
        jQuery("#transportMsg").html(transportHtml);
    }



    // ��ʼ����ǰ������Ϣ
    CurrentAreaInfoInit();
    initForth();

    /**
     * ��Ʒҳ���������󣬴�ֵֻ��������������е��ļ���ַ��Ҫչ��
     * **/

    function initForth(){
        if(currentAreaInfo.currentAreaId!=''&&currentAreaInfo.currentTownId==''){
            initList("/help/front/initFouth.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId + "&cityId=" + currentAreaInfo.currentCityId + "&areaId=" + currentAreaInfo.currentAreaId,"#stock_town_item",3);
        }
    }
    // ��ȡ�б�
    function initList(url,selectElement,index){
        jQuery.ajax({
            type: "POST",
            async: true,
            url: url,
            dataType: 'json',
            success: function(data){
                if (data != null && data != '') {
                    // ȡ���Ƴ��¼�
                    $("#store-selector").unbind("mouseout");

                    // ���û�е��ļ�ֱ����ʾ�����б�
                    if(isNotNullAndUndefined(data.hasNext) && !data.hasNext){

                        var objList = data.resultList;
                        var tempHtml = "";
                        // ������������������Ҫ�ں��׷��<li>��ʾ��ʽ
                        var num = 0;
                        // ��ӡ����
                        if(objList != null){
                            jQuery.each(objList, function(i){
                                tempHtml += objList[i];
                                num++;
                            });
                        }
                        // ����ǵ������Ҳ���û�з��������׷��<li>
                        if(num % 2 != 0 ){
                            tempHtml += "<li></li>";
                        }
                        if(num == 0){
                            tempHtml ='<div class="fail-tip"><div>���ǽ������ڴ�����ͨ���ָ����ķ���</div><div class="wish">�����ڴ�!<b></b></div></div>'
                        }
                        // ��ӡ�����б�
                        jQuery("#servicebox").html(tempHtml);
                        // �رյ�ַ����ʾ
                        $('#store-selector').removeClass('hover');
                        // �ָ���ַ�����»���
                        $('#addressselect').removeAttr("style");
                        // �ڵ�ַ������ʾѡ��ĵ�ַ
                        $("#store-selector .text div").html(currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+(currentAreaInfo.currentTownName==null?'':currentAreaInfo.currentTownName));
                        $("#store-selector .text div").attr("title",currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+(currentAreaInfo.currentTownName==null?'':currentAreaInfo.currentTownName));
                        // �����ļ���Ϊfalse
                        currentAreaInfo.hasFouth = false;

                        //�ж���ʾ���˷���ʾ���½����ز�����Ʒ����10Ԫ��
                        var transportHtml="";
                        if(currentAreaInfo.currentProvinceId == 26 || currentAreaInfo.currentProvinceId == 31){
                            transportHtml="������Ʒ�ڴ��������ÿ��10Ԫ�˷�";
                        }
                        jQuery("#transportMsg").html(transportHtml);

                    }else{
                        // ��ȡ��һ����ַ��Ϣ
                        var objList = data.resultList;
                        if(objList != null){
                            // ģ����һ��tab
                            $('#JD-stock').find('[data-widget="tab-item"]').eq(index).trigger('click');
                            // ��ʾ��һ��tab
                            $('#JD-stock').find('[data-widget="tab-item"]').eq(index).removeClass('hide').addClass('hover');
                            // ��ʾHTML
                            var html = "<ul class='area-list'>";
                            var longhtml = "";
                            var longerhtml = "";
                            jQuery.each(objList, function(i){
                                var id = this.id;
                                var name = this.name;
                                // ���ݵ�ַ������ʾ��Ӧ����ʽ
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
                            // �ڵ�ַ������ʾ��ַ��Ϣ
                            jQuery(selectElement).html(html);
                            // �����ļ���ʶ��Ϊtrue
                            currentAreaInfo.hasFouth = true;
                        }else{
                            // ȡ���Ƴ��¼�
                            $("#store-selector").unbind("mouseout");
                            // ��յ����б�
                            jQuery(selectElement).html("");
                            // ��ӡ�����б�
                            //jQuery("#servicebox").html(tempHtml);
                            // �رյ�ַ����ʾ
                            $('#store-selector').removeClass('hover');
                            // �ָ���ַ�����»���
                            $('#addressselect').removeAttr("style");
                            // �ڵ�ַ������ʾѡ��ĵ�ַ
                            $("#store-selector .text div").html(currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName);
                            $("#store-selector .text div").attr("title",currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName);
                            // �����ļ���Ϊfalse
                            currentAreaInfo.hasFouth = false;
                        }
                    }
                }
            },
            error: function(data, status, e){
                alert(e+"");
                // ��ʱ��ʾ���ļ�
                $('#JD-stock').find('[data-widget="tab-item"]').eq(index).trigger('click');
                // ����ַ����ʾΪ��
                $('#'+getIdNameByLevel(index+1)).html("");
            }
        });
    }

    // ��text mouseover�¼�
    $("#store-selector").bind("mouseover",function () {
        $('#store-selector').addClass('hover');
        $('#addressselect').css("border-bottom", "0 none");
    });

    $("#store-selector").bind("mouseout",function(){
        // �رյ�ַ����ʾ
        $('#store-selector').removeClass('hover');
        // �ָ���ַ�����»���
        $('#addressselect').removeAttr("style");
    });

    // �����¼�
    var JdStock = {
        init: function(selector, fn) {
            // �����ַһ������
            this.areaLevel = 3;
            // ������ı���
            this.jdStock = $(selector);
            // ������ı���
            this.jdStockArea = this.jdStock.find('[data-widget="tab-item"]');
            // ������ı���
            this.areaList = this.jdStock.find('[data-widget="tab-content"]');
            // �����ص�����
            this.callback = fn;
            // ִ���¼���
            this.bindEvent();

        },
        bindEvent: function() {
            // ��������
            var _this = this;

            this.jdStock.Jtab({
                event: 'click',
                compatible:true,
                index:currentAreaInfo.currentLevel
            });

            // �󶨵�ַͷ�����¼�
            this.jdStockArea.bind('click', function() {
                var index = parseInt( $(this).attr('data-index'), 10 );
                _this.jdStockArea.find('a').removeClass('hover');
                _this.jdStockArea.eq(index).find('a').addClass('hover');
            });

            // �󶨵�ַ�б������¼�
            this.areaList.find('a').livequery('click', function(e) {
                // ��������
                var areaLevel = parseInt($(e.target).parents('.mc').attr('data-area'), 10),
                    text = $(e.target).text(),
                    value = $(e.target).attr('data-value');

                // �������A��ǩ����
                if (e.target.nodeName !== 'A') {
                    return;
                }

                // ���õ�ǰѡ�����
                _this.setArea(areaLevel, text, value);

                // �л�����һ��tab
                //_this.switchTab(areaLevel+1);

                // �����ǰ��������tab����
                for (var i = areaLevel;  i <= _this.areaLevel - 1; i++) {
                    _this.resetTab(i+1);
                }

            });

        },
        switchTab: function(index) {
            // ����������һ������ͷ����¼�
            if(index !== 3){
                this.jdStockArea.eq(index).trigger('click');
            }
        },
        resetTab: function(index) {
            // �����ַ��Ϣ����һ���Ժ��ͷ��Ϣɾ��
            this.jdStockArea.eq(index).addClass("hide");
        },
        setArea: function(index, name, value) {
            // ��ʾ��ַͷ������
            this.jdStockArea.eq(index).find('em').html(name.length>6?name.substring(0,6):name);
            // ����һ��ͷ��������Ϊ��ѡ��
            this.jdStockArea.eq(index + 1).find('em').html("��ѡ��");
            // ����ǵ�һ��
            if(index ==0){
                // ����ʡ�����ԣ���������Ϊ��
                currentAreaInfo.currentProvinceId = value;
                currentAreaInfo.currentProvinceName = name;
                currentAreaInfo.currentCityId = 0;
                currentAreaInfo.currentCityName = "";
                currentAreaInfo.currentAreaId = 0;
                currentAreaInfo.currentAreaName = "";
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
                // ��ʾ����Ч��
                $("#stock_city_item").html("<div class='iloading'>���ڼ����У����Ժ�...</div>");
                // ��ȡ��һ����ַ
                initList("/help/front/initCity.action?tem="+new Date().getTime()+"&provinceId=" + currentAreaInfo.currentProvinceId,"#stock_city_item",index+1);
            }else if(index == 1){
                // �����е����ԣ���������Ϊ��
                currentAreaInfo.currentCityId = value;
                currentAreaInfo.currentCityName = name;
                currentAreaInfo.currentAreaId = 0;
                currentAreaInfo.currentAreaName = "";
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
                $("#stock_area_item").html("<div class='iloading'>���ڼ����У����Ժ�...</div>");
                initList("/help/front/initArea.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId + "&cityId=" + currentAreaInfo.currentCityId,"#stock_area_item",index+1);
            }else if(index == 2){
                // �����������ԣ���������Ϊ��
                currentAreaInfo.currentAreaId = value;
                currentAreaInfo.currentAreaName = name;
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
                $("#stock_town_item").html("<div class='iloading'>���ڼ����У����Ժ�...</div>");
                initList("/help/front/initFouth.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId + "&cityId=" + currentAreaInfo.currentCityId + "&areaId=" + currentAreaInfo.currentAreaId,"#stock_town_item",index+1);
            }else if(index == 3){
                // ����������ԣ���������Ϊ��
                currentAreaInfo.currentTownId = value;
                currentAreaInfo.currentTownName = name;
                initList("/help/front/initService.action?tem="+new Date().getTime()+ "&provinceId=" + currentAreaInfo.currentProvinceId+"&cityId=" + currentAreaInfo.currentCityId+"&areaId=" + currentAreaInfo.currentAreaId + "&fouthId=" + currentAreaInfo.currentTownId,"#stock_town_item",index+1);
            }
        }
    };

    // ��ʼ��
    JdStock.init('#JD-stock', function(val, name, index) {

    });



    // ������������
    // �ж϶����Ƿ�Ϊ�ջ�undefined
    isNotNullAndUndefined = function(param) {
        if (param == null || typeof(param) == 'undefined') {
            return false;
        } else {
            return true;
        }
    };


    // �رղ㷽��
    closeAdd = function() {
        // ������ʾ����ʽ����
        $('#store-selector').removeClass('hover');
        // ����ַ��ʾ�����»��߻ָ�
        //$('#addressselect').css("border-bottom", "1px solid #CECBCE");
        $('#addressselect').removeAttr("style");
    };

    // ���ݼ����ȡ��Ӧ��DIVID
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