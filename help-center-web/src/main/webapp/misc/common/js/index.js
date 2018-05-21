/**
 * Created with IntelliJ IDEA.
 * User: zhaoyiqing1
 * Date: 15-1-20
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    function getIndexData(){
        var url = '/index/indexString.action';
        $.ajax({
            type:'GET',
            url:url,
            dataType: 'json',
            success:function(res){
                $('#newMessageCount').text(res.newMessageCount);
                $('#newUserCount').text(res.newUserCount);
                $('#totalUserCount').text(res.totalUserCount);
            }
        })
    }
    setInterval(getIndexData,30000);
})