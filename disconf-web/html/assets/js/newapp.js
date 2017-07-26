var appId = -1;
var envId = -1;
var version = "";
getSession();
//


// validateRole();
//
// function validateRole() {
//     alert(window.VISITOR.role);
//     if(window.VISITOR.role !== "2") {
//         $("#error").removeClass("hide");
//         $("#error").html("您没有添加应用的权限！");
//         $("#item_submit").attr("disabled", true);
//     }
// }

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");
    var app = $("#app").val();
    var desc = $("#desc").val();
    var emails = $("#emails").val();

    // 验证
    if (!desc || !app || !emails) {
        $("#error").removeClass("hide");
        $("#error").html("表单不能为空或填写格式错误！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/api/app",
        data: {
            "app": app,
            "desc": desc,
            "emails": emails
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
