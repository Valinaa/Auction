//  个人信息板块切换
function changeActiveDaohang(g, myclass) {

    var a1 = $(".myactive-daohang");
    a1.removeClass("myactive-daohang");
    $(g).addClass("myactive-daohang");

    console.log(myclass);
    $(".right-info-div").addClass("myActiveInRightInfo");
    $(".right-info-psw").addClass("myActiveInRightInfo");
    $(".right-info-shopcart").addClass("myActiveInRightInfo");
    $(".right-info-myauction").addClass("myActiveInRightInfo");
    $(".right-info-myGoods").addClass("myActiveInRightInfo");
    $(".right-info-myOrderInfo").addClass("myActiveInRightInfo");
    $(".right-info-myVIP").addClass("myActiveInRightInfo");
    $(".right-info-mysaler").addClass("myActiveInRightInfo");


    var div0 = $("." + myclass);
    div0.removeClass("myActiveInRightInfo");

    mycurrentPage = 1;
}


// 点击修改后的文字回显
function myinfoUpdateBtn() {
    $("#username").val($("#myinfo-div-name").text().trim());
    $("#myaccount").val($("#myinfo-div-account").text().trim());
    $("#myidentity").val($("#myinfo-div-identity").text().trim());
    $("#mysgin").val($("#myinfo-div-sgin").text().trim());
    $("#mylove").val($("#myinfo-div-love").text().trim());
    $("#mysex").val($("#myinfo-div-sex").text().trim());
    $("#myphone").val($("#myinfo-div-phone").text().trim());
    $("#myemail").val($("#myinfo-div-email").text().trim());
    $("#mylocation").val($("#myinfo-div-address").text().trim());

}


//  基本信息修改
function myinfoUpdateSubmitBtn() {
    var stroge = window.sessionStorage;
    var res0 = stroge.getItem("AccountInfo");
    var AccountInfo;
    try {
        AccountInfo = $.parseJSON(res0);
    } catch (e) {
        AccountInfo = null;
    }
    var name;
    if (($("#myinfo-div-account").text().trim() === $("#username").val())) {
        name = null;
    } else {
        name = $("#username").val();
    }
    var data = {
        "aid": AccountInfo.aid,
        "name": name,
        "sex": $("#mysex").val(),
        "location": $("#mylocation").val(),
        "phone": $("#myphone").val(),
        "email": $("#myemail").val(),
        "personalSign": $("#mysgin").val(),
        "love": $("#mylove").val(),
        "account": $("#myaccount").val(),
        // "identity": $("#myidentity").val()
        "identity": AccountInfo.identity
    };
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
                stroge.setItem("AccountInfo", jsonData);
                flushMyInfo();

                var accounttemp = window.sessionStorage.getItem("account");
                try {
                    accounttemp = $.parseJSON(accounttemp);
                }catch (e) {
                    accounttemp = null;
                }
                $("#topAccountName")[0].text = name;
                accounttemp.name = name;
            } else if (j.msg === "f") {
                console.log("出现了不可意料的错误");
            }

        }
    }
    xmlhttp.open("POST", "http://localhost:8080/updateAccountInfo", true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    xmlhttp.send(jsonData);
};


function flushMyInfo() {
    var stroge = window.sessionStorage;
    var res0 = stroge.getItem("AccountInfo");
    var AccountInfo;
    try {
        AccountInfo = $.parseJSON(res0);
    } catch (e) {
        AccountInfo = null;
    }
    //  如果是正常进入的，替换默认值
    if (AccountInfo != null) {
        // var username = $("#myinfo-div-name");
        var username = $(".myinfo-div-name");
        var useraccount = $("#myinfo-div-account");
        var useridentity = $("#myinfo-div-identity");
        var usersex = $("#myinfo-div-sex");
        var useradress = $("#myinfo-div-address");
        // var usersgin = $("#myinfo-div-sgin");
        var usersgin = $(".myinfo-div-sgin");
        var userphone = $("#myinfo-div-phone");
        var useremail = $("#myinfo-div-email");
        var userlove = $("#myinfo-div-love");
        var con = AccountInfo.identity;
        if (con === 0) {
            con = "普通用户";
        } else if (con === 1) {
            con = "普通会员";
        } else if (con === 2) {
            con = "卖家";
        } else if (con === 3) {
            con = "管理员";
        } else if (con === 4) {
            con = "卖家会员";
        }
        Infojudge(AccountInfo.name, username);
        Infojudge(AccountInfo.account, useraccount);
        Infojudge(con, useridentity);
        Infojudge(AccountInfo.sex, usersex);
        Infojudge(AccountInfo.location, useradress);
        Infojudge(AccountInfo.personalSign, usersgin);
        Infojudge(AccountInfo.phone, userphone);
        Infojudge(AccountInfo.email, useremail);
        Infojudge(AccountInfo.love, userlove);
    }


    function Infojudge(obj0, obj1) {
        if (obj0 != null) {
            obj1.empty();
            obj1.text(obj0);
        }
    }
}


//  基本信息展示
// 进入页面就启用.
window.onload = function () {
    flushMyInfo();
    myVIPPart();
};


//  修改密码

// $("#myinfo-updatePsw").click( function () {
function myinfoUpdatePsw() {
    var p = $("#mypassword0").val();
    var p1 = $("#mypassword1").val();
    var p2 = $("#mypassword2").val();
    console.log(p);
    console.log(p1);
    console.log(p2);
    console.log(typeof p);
    console.log(typeof p1);
    console.log(typeof p2);
    if (p1 != p2 || p == "" || p1 == "" || p2 == "") {
        myTips("修改失败");
    } else if (p1 === p2) {
        var res0 = window.sessionStorage.getItem("AccountInfo");
        var AccountInfo;
        try {
            AccountInfo = $.parseJSON(res0);
        } catch (e) {
            AccountInfo = null;
        }
        var data = {
            "oldPassword": p,
            "password": p1,
            "id": AccountInfo.aid
        };
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
                var res = xmlhttp.responseText;
                var j = $.parseJSON(res);
                if (j.msg === "ok") {
                    myTips("修改成功");
                } else if (j.msg === "f") {
                    console.log("出现了不可预知的错误");
                    myTips("出现了不可预知的错误");
                }

            }
        }
        xmlhttp.open("POST", "http://localhost:8080/updateAccountPsw", true);
        xmlhttp.setRequestHeader("Content-type", "application/json");
        xmlhttp.send(jsonData);
    }


};


function myTips(msg) {
    //提示层
    layer.msg(msg);
};

var mycurrentPage = 1;

$(document).ready(function () {

    // 头像旋转
    $(".myHeadImgCircle").hover(function () {
        // console.log("进来了吗");
        $(".myHeadImgCircle").addClass("layui-anim-rotate layui-anim-loop");
    }, function () {
        $(".myHeadImgCircle").removeClass("layui-anim-rotate layui-anim-loop");
    });


    //申请商家部分
    layui.use(['form'], function () {
        var form = layui.form;
        layui.$("#mySalerToApply").on("click", function () {
            var data = form.val('salerToApplyForm');
            console.log(JSON.stringify(data));
            salerToApply(data);
        });
    });


    layui.$("#myGoodsEditBtn").on("click", function () {
        var data = layui.form.val('updateGoods');

    });

});


//  商家申请
function salerToApply(data) {
    var res0 = window.sessionStorage.getItem("account");
    var account;
    try {
        account = $.parseJSON(res0);
    } catch (e) {
        account = null;
        myTips("您好像还没登录呢!");
        return ;
    }
    data.account = account.account;
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
                myTips("提交成功");
            } else if (j.msg === "f") {
                myTips("提交失败");
            } else if (j.msg === "exists") {
                myTips("你已经提交过了，请耐心等待管理员审核.");
            }

        }
    }
    xmlhttp.open("POST", "http://localhost:8080/salerApply", true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    xmlhttp.send(jsonData);

}


//  vip 部分
function myVIPPart() {

    var res0 = window.sessionStorage.getItem("account");
    var account;
    try {
        account = $.parseJSON(res0);
    } catch (e) {
        account = null;
    }
    if (account.identity == 1 || account.identity == 4) {
        $("#myVIPIcon").removeClass("btn-default").addClass("btn-danger");
        $("#myVIPWord").empty().text("续费特权");
    }

}

var res1 = window.sessionStorage.getItem("account");
var account;
try {
    account = $.parseJSON(res1);
} catch (e) {
    account = null;
}

function personalAjax(url, name) {

    $.ajax({
        type: "get",
        dataType: "json",
        url: url,
        async: false,
        success: function (data) {
            console.log(data);
            if (data.msg === "ok") {
                var storage = window.sessionStorage;
                storage.setItem(name, JSON.stringify(data));
                // setMyPageHelper(data.data.totalSize, data.data.AuctionNums);
                $(".pagesTotal").empty().text("当前第" + mycurrentPage + "页, 共" + data.totalSize + "页");

            }

        },
        error: function (e) {
            console.log("获取列表失败" + e);
        }
    })
}

var mygoodsStatus = ["已下架", "拍卖中", "未开始", "已结束", "已成交", "不存在"];

function myShoppingCart() {
    if (account == null) {
        myTips("请先登录");
        return;
    }
    var url = "http://localhost:8080/getShoppingCartList/" + account.id + "/" + mycurrentPage + "/" + 1;
    var na = "myCartList";
    personalAjax(url, na);
    var res0 = window.sessionStorage.getItem("myCartList");
    var myCartList;
    try {
        myCartList = $.parseJSON(res0).list;
    } catch (e) {
        myCartList = null;
    }
    if (myCartList != null) {
        $("#myShoopingCart-table").empty();
        for (var i in myCartList) {

            var trs = "    <tr>\n" +
                "                                        <td>" + myCartList[i].id + "</td>\n" +
                "                                        <td>" + myCartList[i].good_name + "</td>\n" +
                "                                        <td>" + myCartList[i].now_price + "</td>\n" +
                "                                        <td>" + mygoodsStatus[myCartList[i].status] + "</td>\n" +
                "                                        <td>\n" +
                "                                            <a href=\"javascript:;\" onclick=\"goodInfo(" + myCartList[i].id + ")\">查看</a>\n" +
                "                                            <a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\"\n" +
                "                                               title=\"真的要删除我吗\" onclick=\"btnConfirm(" + myCartList[i].id + ")\">删除</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>";

            $("#myShoopingCart-table").append(trs);
        }
        $(".myPagerPre").attr("onclick", '').attr("onclick", "myPagerPre('myCartList')");
        $(".myPagerNex").attr("onclick", '').attr("onclick", "myPagerNex('myCartList')");

    }
}


// 查看商品信息
function goodInfo(gid) {
    var aid = account.id == null ? -1 : account.id;
    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8080/getGoodInfoById/" + gid + "/" + aid,
        async: false,
        success: function (data) {
            console.log(data);
            var s = window.sessionStorage;
            s.setItem("GoodInfo", JSON.stringify(data.GoodInfo));

            window.location.href = "./GoodInfo.html";
        },
        error: function (e) {
            console.log("查看商品信息失败...");
            window.location.href = "./404.html";
        }
    });
}


function btnConfirm(id) {
    //询问框
    layer.confirm('你确定要删除这条记录吗？', {
        btn: ['确认', '取消'] //按钮
    }, function (gid) {
        layer.msg('删除成功', {icon: 1});
        // 确认
        delShoppingCart(id);// 服务器删除
        myShoppingCart(); // 相应刷新
    }, function (e) {
        console.log(e);
    });
}

// 删除购物车中的
function delShoppingCart(gid) {
    var aid = account.id == null ? -1 : account.id;
    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8080/addShopCart/" + aid + "/" + gid,
        async: false,
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            console.log("删除失败...");
            window.location.href = "./404.html";
        }
    });
}

function myPagerPre(name) {
    console.log("进来了吗111");
    if (mycurrentPage > 1) {
        mycurrentPage = mycurrentPage - 1;
        switch (name) {
            case "myCartList":
                myShoppingCart();
                break;
            case "myAuctionRecordList":
                myAuction();
                break;
            case "auctionInfoList":
                getMyAuctionInfo();
                break;
            case "myOrderList":
                getMyOrderList();
                break;

            default:
                ;
        }
    }
}

function myPagerNex(name) {
    console.log("进来了吗222");
    var storage = window.sessionStorage;
    var res0 = storage.getItem(name);
    var data;
    try {
        data = $.parseJSON(res0);
    } catch (e) {
        data = null;
    }
    if (data != null) {
        if (mycurrentPage < data.totalSize) {
            mycurrentPage = mycurrentPage + 1;
            switch (name) {
                case "myCartList":
                    myShoppingCart();
                    break;
                case "myAuctionRecordList":
                    myAuction();
                    break;
                case "auctionInfoList":
                    getMyAuctionInfo();
                    break;
                case "myOrderList":
                    getMyOrderList();
                    break;
                default:
                    ;
            }
        }
    }
}


function mypagerdiv() {
    var divs = "                            <!--  上一页  下一页 -->\n" +
        "                            <div class=\"info-shopcart-mypager\">\n" +
        "                                <ul class=\"pager\">\n" +
        "                                    <li><a href=\"javascript:;\" class=\"myPagerPre\">上一页</a></li>\n" +
        "                                    <li><a href=\"javascript:;\" class=\"myPagerNex\">下一页</a></li>\n" +
        "                                </ul>\n" +
        "                            </div>";
    document.write(divs);
}

var statusArray = ["未开始", "正在拍卖", "已成交"];

//  竞拍记录
function myAuction() {
    if (account == null) {
        myTips("请先登录");
        return;
    }
    var url = "http://localhost:8080/getAuctionRecord/" + account.id + "/" + mycurrentPage + "/" + 1;
    var na = "myAuctionRecordList";
    personalAjax(url, na);
    var res0 = window.sessionStorage.getItem("myAuctionRecordList");
    var myAuctionRecordList;
    try {
        myAuctionRecordList = $.parseJSON(res0).list;
    } catch (e) {
        myAuctionRecordList = null;
    }
    if (myAuctionRecordList != null) {
        $("#myaucton-table").empty();
        for (var i in myAuctionRecordList) {

            var auctionTables = "    <tr>\n" +
                "                                        <td>" + myAuctionRecordList[i].id + "</td>\n" +
                "                                        <td>" + myAuctionRecordList[i].good_name + "</td>\n" +
                "                                        <td>" + myAuctionRecordList[i].now_price + "</td>\n" +
                "                                        <td>" + myAuctionRecordList[i].my_plus + "</td>\n" +
                "                                        <td>" + myAuctionRecordList[i].create_time + "</td>\n" +
                "                                        <td>" + statusArray[myAuctionRecordList[i].status] + "</td>\n" +
                "                                        <td>\n" +
                "                                            <a href=\"#\" data-toggle=\"tooltip\" data-placement=\"right\"\n" +
                "                                               title=\"快来看看心意的物品有没有被人抢走呀\" onclick=\"goodInfo(" + myAuctionRecordList[i].gid + ")\">查看</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>";

            $("#myaucton-table").append(auctionTables);
        }

        $(".myPagerPre").attr("onclick", '').attr("onclick", "myPagerPre('myAuctionRecordList')");
        $(".myPagerNex").attr("onclick", '').attr("onclick", "myPagerNex('myAuctionRecordList')");
    }
}

var myOrderStatus = ["待支付", "待发货", "超时支付失效", "运送中", "已完成", "暂无订单"];

// 我的商品
function getMyAuctionInfo() {
    if (account == null) {
        myTips("请先登录");
        return;
    } else {
        if (account.identity < 2) {
            myTips("你好像还不是卖家呢");
            return;
        }
    }
    var url = "http://localhost:8080/getMyAuction/" + account.id + "/" + mycurrentPage + "/" + 1;
    var na = "auctionInfoList";
    personalAjax(url, na);
    var res0 = window.sessionStorage.getItem("auctionInfoList");
    var auctionInfoList;
    try {
        auctionInfoList = $.parseJSON(res0).list;
    } catch (e) {
        auctionInfoList = null;
    }
    if (auctionInfoList != null) {
        $("#myGoods-table").empty();
        for (var i in auctionInfoList) {
            var orderStatus = auctionInfoList[i].orderStatus;
            if (orderStatus === undefined) {
                orderStatus = 5;
            }
            var tbodys = "<tr>\n" +
                "                                        <td>" + auctionInfoList[i].id + "</td>\n" +
                "                                        <td>" + auctionInfoList[i].good_name + "</td>\n" +
                "                                        <td>" + auctionInfoList[i].end_time + "</td>\n" +
                "                                        <td>" + auctionInfoList[i].start_price + "</td>\n" +
                "                                        <td>" + auctionInfoList[i].now_price + "</td>\n" +
                "                                        <td>" + mygoodsStatus[auctionInfoList[i].status] + "</td>\n" +
                "                                        <td>" + myOrderStatus[orderStatus] + "</td>\n" +
                "                                        <td>\n" +
                "                                            <a href=\"javascript:;\" onclick=\"goodInfo(" + auctionInfoList[i].id + ")\">查看</a>\n" +
                "                                            <a href=\"#\"  data-toggle=\"modal\" data-target=\"#myGoodEditBtn\">编辑</a>\n" +
                "                                            <a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\"\n" +
                "                                               title=\"真的要删除我吗\" onclick=\"btnConfirmGoods(" + auctionInfoList[i].id + ")\">删除</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>";


            $("#myGoods-table").append(tbodys);
        }

        $(".myPagerPre").attr("onclick", '').attr("onclick", "myPagerPre('auctionInfoList')");
        $(".myPagerNex").attr("onclick", '').attr("onclick", "myPagerNex('auctionInfoList')");
    }

}


function btnConfirmGoods(gid) {
    //询问框
    layer.confirm('你确定要删除这条记录吗？', {
        btn: ['确认', '取消'] //按钮
    }, function () {
        layer.msg('删除成功', {icon: 1});
        // 确认
        delMyGoods(gid);
        getMyAuctionInfo();
    }, function (e) {
        console.log(e);
    });
}

function delMyGoods(gid) {
    var aid = account.id == null ? -1 : account.id;
    if (aid === -1) {
        myTips("请先登录？？？");
        return;
    }
    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8080/delMyGoods/" + aid + "/" + gid,
        async: false,
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            console.log("删除失败...");
            window.location.href = "./404.html";
        }
    });
}

// 我的订单
function getMyOrderList() {
    if (account == null) {
        myTips("请先登录");
        return;
    }
    var url = "http://localhost:8080/getOrderList/" + account.id + "/" + mycurrentPage + "/" + 1;
    var na = "MyOrderList";
    personalAjax(url, na);
    var res0 = window.sessionStorage.getItem("MyOrderList");
    var MyOrderList;
    try {
        MyOrderList = $.parseJSON(res0).list;
    } catch (e) {
        MyOrderList = null;
    }
    if (MyOrderList != null) {
        $("#myOrder-table").empty();
        for (var i in MyOrderList) {
            var orderStatus = MyOrderList[i].status;
            var divs = " <tr>\n" +
                "                                        <td>" + MyOrderList[i].order_id + "</td>\n" +
                "                                        <td>" + MyOrderList[i].good_name + "</td>\n" +
                "                                        <td>" + MyOrderList[i].end_price + "</td>\n" +
                "                                        <td>" + MyOrderList[i].create_time + "</td>\n" +
                "                                        <td>" + myOrderStatus[MyOrderList[i].status] + "</td>\n" +
                "                                        <td>\n" +
                "                                            <a href=\"javascript:;\">查看</a>";

            var p = "";
            // 待支付
            if (orderStatus === 1) {
                p = "<a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\"\n" +
                    "            title=\"再不付款就超时退回了呦\">支付</a>\n" +
                    "                </td>\n" +
                    "                </tr>";
            } else if (orderStatus === 3) {
                p = "<a href=\"javascript:;\">确认收货</a>\n" +
                    "                </td>\n" +
                    "                </tr>";
            }

            divs = divs + p;
            $("#myOrder-table").append(divs);

        }
        $(".myPagerPre").attr("onclick", '').attr("onclick", "myPagerPre('myOrderList')");
        $(".myPagerNex").attr("onclick", '').attr("onclick", "myPagerNex('myOrderList')");

    }
}




