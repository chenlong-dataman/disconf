//
// 获取指定配置下的配置数据
//
function fetchItems(appId, envId, version, curConfigId) {

    var parameter = ""

    url = "/api/web/config/simple/list";
    url += "?";
    url += "appId=" + appId + "&";
    url += "envId=" + envId + "&";
    url += "version=" + version + "&";

    $.ajax({
        type: "GET",
        url: url
    }).done(function (data) {
        if (data.success === "true") {
            var html = '<li style="margin-bottom:10px">配置文件/配置项列表</li>';
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += renderItem(item);
            });
            $("#sidebarcur").html(html);
        }
    });
    var mainTpl = $("#tItemTpl").html();
    // 渲染主列表
    function renderItem(item) {

        var link = "";
        var key = "";
        if (item.type == "配置文件") {
            link = 'modifyFile.html?configId=' + item.configId;
            key = '<i title="配置文件" class="icon-file"></i>' + item.key;
        } else {
            link = 'modifyItem.html?configId=' + item.configId;
            key = '<i title="配置项" class="icon-leaf"></i>' + item.key;
        }

        var style = "";
        if (item.configId == curConfigId) {
            style = "active";
        }

        return Util.string.format(mainTpl, key, link, style);
    }
}

//
// 获取用户数据
//
function fetchUsers(curUserId) {

    var parameter = ""

    url = "/api/user/list";

    $.ajax({
        type: "GET",
        url: url
    }).done(function (data) {
        if (data.success === "true") {
            var html = '<li style="margin-bottom:10px">用户名</li>';
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += renderUser(item);
            });
            $("#sidebarcur").html(html);
        }
    });
    var mainTpl = $("#tUserTpl").html();
    // 渲染主列表
    function renderUser(item) {

        var link = "";
        var key = "";
        link = 'modifyUser.html?userId=' + item.id;
        key = '<i title="配置文件" class="icon-file"></i>' + item.name;

        var style = "";
        if (item.id == curUserId) {
            style = "active";
        }

        return Util.string.format(mainTpl, key, link, style);
    }
}

//
// 获取App数据
//
function fetchApps(curAppId) {

    var parameter = ""

    url = "/api/app/list";

    $.ajax({
        type: "GET",
        url: url
    }).done(function (data) {
        if (data.success === "true") {
            var html = '<li style="margin-bottom:10px">APP</li>';
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += renderApp(item);
            });
            $("#sidebarcur").html(html);
        }
    });
    var mainTpl = $("#tAppTpl").html();
    // 渲染主列表
    function renderApp(item) {

        var link = "";
        var key = "";
        link = 'modifyApp.html?appId=' + item.id;
        key = item.name;

        var style = "";
        if (item.id == curAppId) {
            style = "active";
        }

        return Util.string.format(mainTpl, key, link, style);
    }
}

//
// 获取环境数据
//
function fetchEnvs(curEnvId) {

    var parameter = ""

    url = "/api/env/list";

    $.ajax({
        type: "GET",
        url: url
    }).done(function (data) {
        if (data.success === "true") {
            var html = '<li style="margin-bottom:10px">环境名称</li>';
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += renderEnv(item);
            });
            $("#sidebarcur").html(html);
        }
    });
    var mainTpl = $("#tEnvTpl").html();
    // 渲染主列表
    function renderEnv(item) {

        var link = "";
        var key = "";
        link = 'modifyEnv.html?envId=' + item.id;
        key = item.name;

        var style = "";
        if (item.id == curEnvId) {
            style = "active";
        }

        return Util.string.format(mainTpl, key, link, style);
    }
}
