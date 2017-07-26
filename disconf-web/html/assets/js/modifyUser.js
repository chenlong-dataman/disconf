(function ($) {

    var roleId = -1;
    var ownapps = '';

    getSession();

    var userId = Util.param.getUserId();

    //
    // 获取角色信息
    //
    $.ajax({
        type: "GET",
        url: "/api/role/list"    //这个api需要新加
    }).done(
        function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                $.each(result, function (index, item) {
                    html += '<li><a rel=' + item.id + ' href="#">'
                        + item.name + '</a></li>';
                });
                $("#roleChoice").html(html);
            }
        });

    $("#roleChoice").on('click', 'li a', function () {
        $("#roleChoiceA span:first-child").text($(this).text());
        $("#role").val($(this).text());
        roleId = $(this).attr('rel');
    });

    $.ajax({
        type: "GET",
        url: "/api/app/list"
    }).done(
        function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                $.each(result, function (index, item) {
                    html += '<li><a rel=' + item.id + ' href="#">[id='
                        + item.id + ']' + item.name + '</a></li>';
                });
                $("#sidebarapp").html(html);
            }
        });

    fetchUser();

    //
    // 获取用户信息
    //
    function fetchUser() {

        //
        // 获取此配置项的数据
        //
        $.ajax({
            type: "GET",
            url: "/api/user/" + userId
        }).done(
            function (data) {
                if (data.success === "true") {
                    var result = data.result;
                    $("#userid").text(result.id);
                    $("#name").text(result.name);
                    $("#role").val(result.role);
                    $("#ownappscur").val(result.ownapps);
                    $("#currentData").text(result.name + ' (userid=' + result.id + ')');
                    roleId = result.roleId;

                    //获取所有用户名
                    fetchUsers(userId);
                }
            });
    }

    // 提交
    $("#submit").on("click", function (e) {
        $("#error").addClass("hide");
        var me = this;

        var ownapps = $("#ownapps").val();
        //alert(roleId);
        //alert(ownapps);
        // 验证
        if (!roleId || !ownapps) {
            $("#error").removeClass("hide");
            $("#error").html("表单不能为空或填写格式错误！");
            return;
        }
        $.ajax({
            type: "PUT",
            url: "/api/user/" + userId,
            data: {
                "roleId": roleId,
                "ownapps": ownapps
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
