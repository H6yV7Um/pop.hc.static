/**
 * Created by zhaojianhong on 2018/4/13.
 */
$(function () {
    $.ajax({
        url: "/welcome/getKingName.action",
        contentType: 'application/x-www-form-urlencoded',
        dataType: 'json',
        success: function (res) {
            if (res.token == "success") {
                $("#welcome").text("»¶Ó­Äú,"+ res.kingName)
            } else {

            }
        }
    });
});