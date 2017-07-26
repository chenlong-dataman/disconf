(function ($) {

    getSession();

    var appId = Util.param.getAppId();

    fetchItem();

    //
    // 获取App详细信息
    //
    function fetchItem() {

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "/api/app/" + appId
        }).done(
            function (data) {
                if (data.success === "true") {
                    var result = data.result;
                    $("#appid").text(result.id);
                    $("#name").val(result.name);
                    $("#desc").val(result.desc);
                    $("#emails").val(result.emails);
                    $("#currentData").text(
                            result.id + " * " + result.name);
                    // 获取APP下的配置数据
                    fetchApps(appId);
                }
            });
    }

    // 提交
    $("#submit").on("click", function (e) {
        $("#error").addClass("hide");
        var me = this;
        var name = $("#name").val();
        var desc = $("#desc").val();
        var emails = $("#emails").val();

        // 验证
        if (!name || !desc || !emails) {
            $("#error").removeClass("hide");
            $("#error").html("表单不能为空或填写格式错误！");

            return;
        }
        $.ajax({
            type: "PUT",
            url: "/api/app/" + appId,
            data: {
                "name": name,
                "desc": desc,
                "emails" : emails
            }
        }).done(function (data) {
            $("#error").removeClass("hide");
            if (data.success === "true") {
                $("#error").html(data.result);
            } else {
                Util.input.whiteError($("#error"), data);
            }
        });
    });

})(jQuery);
