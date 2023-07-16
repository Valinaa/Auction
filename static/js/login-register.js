function myInfoPage() {
    var storge = window.sessionStorage;

    var b = storge.getItem("AccountInfo");
    console.log(b);
    console.log(typeof b);
    if (b != null && b !== "null") {
        console.log("不为空呀");
        // 利用之前存的....
        goOtherPage($.parseJSON(b));
    }

    var res0 = storge.getItem("account");
    var account;
    try {
        account = $.parseJSON(res0);
    } catch (e) {
        account = null;
    }
    var href;
    var data = {
        "account": account.account,
        "identity": account.identity
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
            console.log(xmlhttp.responseText);
            var res = xmlhttp.responseText;
            var j = $.parseJSON(res);
            if (j.msg === "ok") {
                storge.setItem("AccountInfo", JSON.stringify(j.AccountInfo));
                goOtherPage(j.AccountInfo);
            } else if (j.msg === "f") {
                console.log("出错了");
            }

        }
    }
    xmlhttp.open("POST", "http://localhost:8000/getAccountInfo", true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    xmlhttp.send(jsonData);

}


function goOtherPage(AccountInfo) {
    if (AccountInfo.identity === 0 || AccountInfo.identity === 1 || AccountInfo.identity === 2 || AccountInfo.identity === 4) {
        href = "./PersonHomePage.html";
    } else if (AccountInfo.identity === 3) {
        href = "./lankeren.html";
    } else {
        href = "./404.html";
    }
    window.location.href = href;
}


// 购物车跳转
function shoppingCart() {
    var storge = window.sessionStorage;
    var b = storge.getItem("account");
    if (b != null) {
        myInfoPage();
    } else {
        window.location.href = "./login.html";
    }


}


$(document).ready(function () {

    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8000/getAuctionList/1/1",
        async: false,
        success: function (data) {
            console.log(data);
            // console.log(data.data.AuctionList);
            var storage = window.sessionStorage;
            storage.setItem("AuctionListData", JSON.stringify(data.data.AuctionList));
            setMyPageHelper(data.data.totalSize, data.data.AuctionNums);
            setAuctionListContent(data.data);
        },
        error: function (e) {
            console.log("失败了" + e);
            setMyPageHelper(null, null);
        }
    });


    $("body").on("click", '.myAuctionListA, .myAuctionsListBtnIndex', function () {
        var s = window.sessionStorage;
        var res0 = s.getItem("account"); var account ;
        var aid = -1;
        try {
            account = $.parseJSON(res0);
            aid = account.id;
        }catch (e) {
            account = null;
        }
        var gid = 0;
        if ($(this)[0].tagName === "A") {
            gid = $(this)[0].parentNode.id;
        } else {
            gid = $(this)[0].parentNode.parentNode.id;
        }

        $.ajax({
            type: "get",
            dataType: "json",
            url: "http://localhost:8000/getGoodInfoById/" + gid + "/" + aid,
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

    });


});


function setAuctionListContent(AuctionListData) {
    if (AuctionListData != null) {
        var theList = AuctionListData.AuctionList;
        // console.log(theList.length);
        $(".goodCardIndex").remove();
        for (var i in theList) {
            // console.log("i ==== " + i);
            var AuctionDivs = "                       <div class=\"layui-col-md3 goodCardIndex\">\n" +
                "                            <div class=\"layui-card\">\n" +
                "                                <div class=\"layui-card-header\">拍卖卖家: " + theList[i].salerName + "</div>\n" +
                "                                <div class=\"layui-card-body clearfix\" id=\"" + theList[i].id + "\">\n" +
                "                                    <a href=\"#\" class=\"myAuctionListA\">\n" +
                "                                        <img class=\"img-responsive\" src=\"" + theList[i].pic + "\"\n" +
                "                                             alt=\"\">\n" +
                "                                        <dl style=\"margin-top: 10px\">\n" +
                "                                            <dd style=\"font-size: 20px; color: black\">" + theList[i].goodName + "</dd>\n" +
                "                                        </dl>\n" +
                "                                    </a>\n" +
                "                                    <div class=\"layui-col-md7\">\n" +
                "                                        <span style=\"font-size: 17px\">当前竞拍价: " + theList[i].nowPrice + "元</span><br>\n" +
                "                                        <span>已有" + theList[i].aucNum + "次参与竞拍</span>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"layui-col-md5\">\n" +
                "                                        <button type=\"button\" class=\"layui-btn myAuctionsListBtnIndex\">立即竞拍</button>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>";

            // document.write(AuctionDivs);
            // $("#setAuctionListContent").prepend(AuctionDivs);
            $("#myAuctionListPageIndexDiv").before(AuctionDivs);

        }
    }

}

function setMyPageHelper(totalSize, AuctionNums) {

    layui.use(['element', 'laypage'], function () {
        var element = layui.element
            , laypage = layui.laypage; //导航的hover效果、二级菜单等功能，需要依赖element模块

        // 分页
        laypage.render({
            elem: 'myAuctionListPageIndex'
            , count: (totalSize === null || totalSize === 0) ? 10 : (totalSize * 10) //数据总数
            , jump: function (obj) {
                // console.log(obj);
                var curr = obj.curr;
                pageJump(curr);
            }
        });
    });

    if (totalSize === null && AuctionNums === null) {
        return;
    }
    //  共-999页,还剩-999件拍卖品正在竞拍中
    $(".myPageHelper").empty().text("共" + totalSize + "页,还剩" + AuctionNums.allowAuc + "件拍卖品正在竞拍中, " + AuctionNums.unOpen + "件尚未到开始时间")

}


function pageJump(curr) {
    if (curr === null) {
        console.log("curr 不能为空");
        return;
    }
    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8000/getAuctionList/" + curr + "/1",
        async: false,
        success: function (data) {
            // console.log(data);
            // console.log(data.data.AuctionList);
            var storage = window.sessionStorage;
            storage.setItem("AuctionListData", JSON.stringify(data.data));
            // setMyPageHelper(data.data.totalSize, data.data.AuctionNums);
            setAuctionListContent(data.data);
        },
        error: function (e) {
            console.log("失败了" + e);
        }
    });
}


function searchGoods() {

    var condition = $(".mySearchContents").val();
    var myData = {
        condition: condition,
        curr: 1 ,
        pageSize: 1
    };
// 查看
    $.ajax({
        type: "post",
        dataType: "json",
        url: "http://localhost:8000/searchAuctionList",
        // contentType:"application/json",
        // data: JSON.stringify(myData),
        data: myData,
        async: false,
        success: function (data) {
            console.log(data);
            if(data.msg === "ok"){
                var storage = window.sessionStorage;
                storage.setItem("AuctionListData", JSON.stringify(data.data.AuctionList));
                setMyPageHelper(data.data.totalSize, data.data.AuctionNums);
                setAuctionListContent(data.data);
            }

        },
        error: function (e) {
            console.log("搜索失败...");
            window.location.href = "./404.html";
        }

    });


}