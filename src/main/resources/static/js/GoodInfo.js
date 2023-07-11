var s = window.sessionStorage;
var res0 = s.getItem("GoodInfo");
var GoodInfo;
try {
    GoodInfo = $.parseJSON(res0);
} catch (e) {
    GoodInfo = null;
}

//  放大镜
$(function () {
    $("#big-pic-imgtag").zoomio();

    if (GoodInfo != null) {
        // 设置左侧
        $("#big-pic-imgtag").attr("src", GoodInfo.pic);
        GoodInfo.pack_mail === 1 ? $("#myPackMailSpan").removeClass("layui-bg-gray").addClass("layui-bg-green") : 1 + 1;
        GoodInfo.oimei === 1 ? $("#myOiMeiSpan").removeClass("layui-bg-gray").addClass("layui-bg-green") : 1 + 1;
        GoodInfo.ensure === 1 ? $("#myEnsureSpan").removeClass("layui-bg-gray").addClass("layui-bg-green") : 1 + 1;
        GoodInfo.Exshoppingcart === 1 ? $(".myshopCartBtnInGoodInfo").removeClass("layui-btn-primary").addClass("layui-btn-warm").empty().text("已加入购物车") : 1 + 1;


        $("#myGoodTitle").empty().text(GoodInfo.good_name);
        $("#startTime").empty().text(GoodInfo.start_time);
        $("#endTime").empty().text(GoodInfo.end_time);
        $("#myTypeGoodInfo").empty().text(GoodInfo.goodType);
        $("#startPrice").empty().text(GoodInfo.start_price);
        $("#nowPrice").empty().text(GoodInfo.now_price + "元");
        $("#myGoodDes").empty().text(GoodInfo.goods_dec);
        $("#GoodInfoStatus").empty().text(GoodInfo.status === "1" ? "正在拍卖" : "已结束");
        $("#myPricePlus").attr("placeholder", "竞拍最低限" + GoodInfo.price_plus + "元");

    }


    $(".myAuctionBtnInGoodInfo").on("click", function () {
        var res1 = s.getItem("account");
        var aacount;
        try {
            account = $.parseJSON(res1);
        } catch (e) {
            account = null;
        }
        var my_plus = $("#myPricePlus").val();
        console.log("myPlus == " + my_plus);
        if (account == null || GoodInfo == null || my_plus === "") {
            myTips("请先登录--或者出现了不可预知的错误--或请填写你的加价");
            return;
        }
        var myData = {
            accountId: account.id
                , accountName: account.name
            , gid: GoodInfo.id
            , salerId: GoodInfo.account_id
            , goodName: GoodInfo.good_name
            , startPrice: GoodInfo.now_price
            , myPlus: my_plus
            , status: GoodInfo.status
            , eTime: GoodInfo.end_time
            , sTime: GoodInfo.start_time
        };
        $.ajax({
            type: "post",
            dataType: "json",
            url: "http://localhost:8080/auction",
            contentType:"application/json",
            data: JSON.stringify(myData),

            success: function (data) {
                if (data.msg === "ok") {
                    myTips("你已加价成功!");
                    var np = (parseFloat(GoodInfo.now_price) + parseFloat(my_plus));
                    $("#nowPrice").empty().text(np + "元");
                    $("#myPricePlus").val("");
                    GoodInfo.now_price = np;
                } else if (data.msg === "f") {
                    myTips("加价失败，请重新再试!");
                }
            },
            error: function (e) {
                myTips("加价失败，请重新再试!");
            }
        });

    });

    $(".myshopCartBtnInGoodInfo").on("click", function () {
        var res1 = s.getItem("account");
        var aacount;
        try {
            account = $.parseJSON(res1);
        } catch (e) {
            account = null;
        }
        if (account == null || GoodInfo == null) {
            myTips("出现了不可预料的错误.....");
            return;
        }

        $.ajax({
            type: "get",
            dataType: "json",
            url: "http://localhost:8080/addShopCart/" + account.id + "/" + GoodInfo.id,

            success: function (data) {
                if (data.msg === "ok") {
                    myTips("添加购物车成功！");
                    $(".myshopCartBtnInGoodInfo").removeClass("layui-btn-primary").addClass("layui-btn-warm").empty().text("已加入购物车");
                    GoodInfo.Exshoppingcart = 1;
                } else if (data.msg === "cancle") {
                    myTips("已成功移出购物车！");
                    $(".myshopCartBtnInGoodInfo").removeClass("layui-btn-warm").addClass("layui-btn-primary").empty().text("加入购物车");
                    GoodInfo.Exshoppingcart = 0;
                } else if (data.msg === "f") {
                    myTips("添加失败！");
                }
            },
            error: function () {
                myTips("添加购物车失败, 请重新再试！");
            }
        });


    });

});

layui.use(['util', 'laydate', 'layer'], function () {
    var util = layui.util
        , laydate = layui.laydate
        , $ = layui.$
        , layer = layui.layer;

    //倒计时
    var thisTimer, setCountdown = function (y, M, d, H, m, s) {
        var endTime = new Date(y, M || 0, d || 1, H || 0, m || 0, s || 0) //结束日期
            , serverTime = new Date(); //假设为当前服务器时间，这里采用的是本地时间，实际使用一般是取服务端的

        clearTimeout(thisTimer);
        util.countdown(endTime, serverTime, function (date, serverTime, timer) {
            var str = "距离结束: " + date[0] + '天' + date[1] + '时' + date[2] + '分' + date[3] + '秒';
            lay('#theCountDownInGoodInfo').html(str);
            thisTimer = timer;
        });
    };
    if (GoodInfo != null) {
        var myd = new Date(GoodInfo.end_time);
        setCountdown(myd.getFullYear(), myd.getMonth(), myd.getDate(), myd.getHours(), myd.getMinutes(), myd.getSeconds());
    } else {
        setCountdown(2099, 1, 1);
    }


});

function myTips(msg) {
    //提示层
    layer.msg(msg);
};
