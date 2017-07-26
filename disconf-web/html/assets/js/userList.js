(function ($) {


    getSession();

    var appId = -1;
    var envId = -1;
    var version = "#";

    fetchUserList();

    //
    // 渲染主列表
    //
    function fetchUserList() {

        // 参数不正确，清空列表
        $("#mainlist_error").hide();
        var parameter = ""

        url = "/api/user/list";

        $.ajax({
            type: "GET",
            url: url
        }).done(function (data) {
            if (data.success === "true") {
                var html = "";
                var result = data.page.result;
                $.each(result, function (index, item) {
                    html += renderItem(item, index);
                });
                if (html != "") {
                    $("#userlist").show();
                    $("#accountBody").html(html);
                } else {
                    $("#accountBody").html("");
                }

            } else {
                $("#accountBody").html("");
                $("#mainlist").hide();
            }

            bindDetailEvent(result);

        });
        var mainTpl = $("#tbodyTpl").html();
        // 渲染主列表
        function renderItem(item, i) {

            var link = "";

            del_link = '<a id="itemDel'
                + item.id
                + '" style="cursor: pointer; cursor: hand; " ><i title="删除" class="icon-remove"></i></a>';

            edit_link = '<a target="_blank" href="modifyUser.html?userId='+ item.id +'"><i title="修改" class="icon-edit"></i></a>';

            return Util.string.format(mainTpl,'', i+1,
                item.id, item.name, item.role, item.ownapps,
                del_link, edit_link);
        }
    }

    // 详细列表绑定事件
    function bindDetailEvent(result) {
        if (result == null) {
            return;
        }
        $.each(result, function (index, item) {
            var id = item.id;

            // 绑定删除事件
            $("#itemDel" + id).on("click", function (e) {
                deleteDetailTable(id, item.name);
            });

        });

    }

    // 删除
    function deleteDetailTable(id, name) {

        var ret = confirm("你确定要删除吗 " + name + "?");
        if (ret == false) {
            return false;
        }

        $.ajax({
            type: "DELETE",
            url: "/api/user/" + id
        }).done(function (data) {
            alert(data.result);
            if (data.success === "true") {
                fetchUserList();
            }
        });
    }

})(jQuery);
