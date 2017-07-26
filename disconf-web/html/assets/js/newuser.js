var roleId = -1;
var visitorRole = null;
getSession();

// visitorRole = window.VISITOR.role;
//
// validateRole();
//
// function validateRole() {
//     alert(visitorRole);
//     if (window.VISITOR.role !== "2") {
//         $("#error").removeClass("hide");
//         $("#error").html("您没有添加用户的权限！");
//         $("#item_submit").attr("disabled", true);
//     }
// }
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
                html += '<li><a rel=' + item.id + ' href="#">角色: '
                    + item.name + '</a></li>';
            });
            $("#roleChoice").html(html);
        }
    });

$("#roleChoice").on('click', 'li a', function () {
    $("#roleChoiceA span:first-child").text($(this).text());
    roleId = $(this).attr('rel');
});

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");
    var name = $("#name").val();
    var user_password = $("#user_password").val();
    var confirm_password = $("#confirm_password").val();
    var ownapps = $("#ownapps").val();

    // 验证
    if (!name || !user_password || !confirm_password) {
        $("#error").removeClass("hide");
        $("#error").html("表单不能为空或填写格式错误！");
        return;
    }

    if(user_password != confirm_password) {
        $("#error").removeClass("hide");
        $("#error").html("两次输入的密码不一致！");
        return;
    }

    if(roleId==-1) {
        $("#error").removeClass("hide");
        $("#error").html("用户角色不能为空！如果下拉列表为空，说明您有没有添加用户的权限！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/api/user/modify",
        data: {
            "name": name,
            "user_password": user_password,
            "confirm_password": confirm_password,
            "ownapps": ownapps,
            "role": roleId
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
            $("#sidebarcur").html(html);
        }
    });
