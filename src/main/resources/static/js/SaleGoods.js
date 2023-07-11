//  文件上传
layui.use('upload', function () {
    var $ = layui.jquery
        , upload = layui.upload;

    var res0 = window.sessionStorage.getItem("account");
    var account;
    try {
        account = $.parseJSON(res0);
    }catch (e) {
        account = null;
    }

    //拖拽上传
    upload.render({
        elem: '#picFile'
        // , url: 'https://httpbin.org/post' //改成您自己的上传接口
        , url: 'http://localhost:8080/savePic/' + account.id //改成您自己的上传接口
        , done: function (res) {
            layer.msg('上传成功');
            // layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.files.file);
            layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.data.src);
            console.log(res);
        }
    });
});

//  表单内容
layui.use(['form', 'layedit', 'laydate'], function () {
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate;

    //日期时间选择器
    laydate.render({
        elem: '#test5'
        , type: 'datetime'
    });

    //表单取值
    layui.$('#LAY-component-form-getval').on('click', function () {
        var data = form.val('example');
        alert(JSON.stringify(data));
    });

});


// 提交
$(document).ready(function () {

    // ----------------隐藏bug

    var res0 = window.sessionStorage.getItem("account");
    try {
        var account = $.parseJSON(res0)
        if(account == null){
            window.location.href = "./login.html";
        }
    }catch (e) {
        window.location.href = "./login.html";
    }

    //  --------------------

    layui.$("#salerGoodInfoSubmit").on("click", function () {
        var data = layui.form.val('example');

        MypostSubmitToGood(data);
    });

});


function MypostSubmitToGood(data) {

    if(data.status === "on"){
        data.status = 1;
    }else{
        data.status = 0;
    }
    var res0 = window.sessionStorage.getItem("account");
    var account;
    try {
        account = $.parseJSON(res0);
    }catch (e) {
        account = null;
    }

    data.salerName = account.name;
    data.accountId = account.id;
    var jsonData = JSON.stringify(data);
    console.log(jsonData);
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            // console.log(xmlhttp.responseText);
            var res = xmlhttp.responseText;
            var j = $.parseJSON(res);
            if (j.msg === "ok") {

                // 居中弹出窗
                layui.use('layer', function () {
                    var $ = layui.jquery, layer = layui.layer;

                    layer.open({
                        title: 'By-Lankeren'
                        , content: '提交成功~~~'
                    });
                });

            } else if (j.msg === "f") {
                // 居中弹出窗
                layui.use('layer', function () {
                    var $ = layui.jquery, layer = layui.layer;

                    layer.open({
                        title: 'By-Lankeren'
                        , content: '您没有发布权限或者出现了不可预料的错误，请重新再试~~~'
                    });
                });
            }

        }
    }
    xmlhttp.open("POST", "http://localhost:8080/saveGood", true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    xmlhttp.send(jsonData);
}
