function lankeren1() {
    // var sto = window.localStorage;
    var sto = window.sessionStorage;
    var res0 = sto.getItem("account");
    var account;
    try {
        account =  $.parseJSON(res0);
    }catch (e) {
        account = null;
    }
    var lis ;
    if(account == null){
        lis = "   <li><a href=\"login.html\"><span class=\"glyphicon glyphicon-log-in\"></span> 登录</a></li>\n" +
            "                    <li><a href=\"register.html\"><span class=\"glyphicon glyphicon-user\"></span> 注册</a></li>\n" +
            "                    <li><a href=\"SaleGoods.html\"><span class=\"glyphicon glyphicon-gift\"></span> 发布商品</a></li>\n" +
            "                    <li><a href=\"contactLankeren.html\"><span class=\"glyphicon glyphicon-pencil\"></span> 联系管理</a></li>\n" +
            "                    <li><a href=\"contactLankeren.html\"><span class=\"glyphicon glyphicon-subtitles\"></span> 本站公告</a></li>"
    }else{
        lis = " <li><a href=\"#\" id=\"topAccountName\" onclick='myInfoPage()'><span class=\"glyphicon glyphicon-user\"></span> "+ account.name +"</a></li>\n" +
            "            <li><a href=\"javascript:;\" onclick='logout()'><span class=\"glyphicon glyphicon-log-out\"></span> 注销</a></li>\n" +
            "            <li><a href=\"SaleGoods.html\"><span class=\"glyphicon glyphicon-gift\"></span> 发布商品</a></li>\n" +
            "            <li><a href=\"contactLankeren.html\"><span class=\"glyphicon glyphicon-pencil\"></span> 联系管理</a></li>\n" +
            "            <li><a href=\"contactLankeren.html\"><span class=\"glyphicon glyphicon-subtitles\"></span> 本站公告</a></li>";
    }
    // $(".lankeren1").innerHTML = lis;
    document.write(lis);
}


function logout(){
    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8000/logout",
        async: false,
        success: function (data) {
            console.log(data);
            if(data.msg === "ok"){
                var s = window.sessionStorage;
                s.setItem("account", null);
                s.setItem("AccountInfo", null);
                window.location.href = "./index.html";
            }
        },
        error: function (e) {
            console.log("失败了" + e.valueOf());
            console.log(e);
        }
    });
}


layui.use(['element', 'laypage'], function() {
    var element = layui.element
        , laypage = layui.laypage; //导航的hover效果、二级菜单等功能，需要依赖element模块

    //监听导航点击
    element.on('nav(demo)', function (elem) {
        //console.log(elem)
        layer.msg(elem.text());
    });


    // 分页
    //总页数大于页码总数
    // laypage.render({
    //     elem: 'myAuctionListPage'
    //     ,count: 20 //数据总数
    //     ,jump: function(obj){
    //         // console.log(obj)
    //     }
    // });

});





