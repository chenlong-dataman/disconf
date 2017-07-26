(function ($) {

    getSession();

    var envId = Util.param.getEnvId();

    fetchItem();

    //
    // 获取配置项
    //
    function fetchItem() {

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "/api/env/" + envId
        }).done(
            function (data) {
                if (data.success === "true") {
                    var result = data.result;
                    $("#envid").text(result.id);
                    $("#name").text(result.name);

                    $("#currentData").text(
                            result.id + " * " + result.name);
                    // 获取APP下的配置数据
                    fetchEnvs(envId);
                }
            });
    }

    // 提交
    $("#submit").on("click", function (e) {
        $("#error").addClass("hide");
        var me = this;
        var value = $("#value").val();
        // 验证
        if (!value) {
            $("#error").removeClass("hide");
            $("#error").html("表单不能为空或填写格式错误！");
            return;
        }
        $.ajax({
            type: "PUT",
            url: "/api/env/" + envId,
            data: {
                "value": value
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
