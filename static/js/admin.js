//  个人信息板块切换
function adminDaohang(g, myclass) {

    var a1 = $(".myactive-daohang");
    a1.removeClass("myactive-daohang");
    $(g).addClass("myactive-daohang");

    // console.log(myclass);
    //  与普通用户共用的三个class
    // 基本信息、修改密码、会员特权
    $(".right-info-div").addClass("myActiveInRightInfo");
    $(".right-info-psw").addClass("myActiveInRightInfo");
    $(".right-info-myVIP").addClass("myActiveInRightInfo");

    // 自定义class名部分
    $(".right-info-adminUser").addClass("myActiveInRightInfo");
    $(".right-info-adminGoods").addClass("myActiveInRightInfo");
    $(".right-info-adminAuctions").addClass("myActiveInRightInfo");
    $(".right-info-adminOrder").addClass("myActiveInRightInfo");
    $(".right-info-adminSaler").addClass("myActiveInRightInfo");

    var div0 = $("." + myclass);
    div0.removeClass("myActiveInRightInfo");

    adcurrentPage = 1;
}

var adcurrentPage = 1;

// jq载入
$(document).ready(function(){

    // 头像旋转
    $(".myHeadImgCircle").hover(function () {
        // console.log("进来了吗");
        $(".myHeadImgCircle").addClass("layui-anim-rotate layui-anim-loop");
    }, function () {
        $(".myHeadImgCircle").removeClass("layui-anim-rotate layui-anim-loop");
    });



});


function adminAjax(url) {

    $.ajax({
        type: "get",
        dataType: "json",
        url: url,
        async: false,
        success: function (data) {
            // console.log(data);
            if (data.msg === "ok") {
                myTips("操作成功!");
            }else {
                myTips("操作失败");
            }
        },
        error: function (e) {
            console.log("出现了不可预料的错误" + e);
        }
    })
}


//时间转换函数
function showTime(tempDate) {
    var d = new Date(tempDate);
    var year = d.getFullYear();
    var month = d.getMonth();
    month++;
    var day = d.getDate();
    var hours = d.getHours();

    var minutes = d.getMinutes();
    var seconds = d.getSeconds();
    month = month<10 ? "0"+month:month;
    day = day<10 ? "0"+day:day;
    hours = hours<10 ? "0"+hours:hours;
    minutes = minutes<10 ? "0"+minutes:minutes;
    seconds = seconds<10 ? "0"+seconds:seconds;


    var time = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    return time;
}

// 用户身份转换
function showUserIdentity(identityNum) {
    var arr = ["普通用户", "会员", "卖家", "管理员", "会员卖家"];
    return arr[identityNum];
}

// 商品分类
function showGoodType(i) {
    // 这里都可以变成向服务器请求查询实时的
    var arr = ["陶瓷", "手表", "名画", "古剑", "旗袍"];
    return arr[i];
}

function showGoodsStatus(i) {
    var arr = ["已下架", "拍卖中", "未开始", "已成交", "不存在"];
    return arr[i];
}

function showAuctionStatus(i) {
    var arr = ["未开始", "拍卖中", "已成交"];
    return arr[i];
}

function showOrderStatus(i) {
    var arr = ["待支付", "已支付", "超时支付已失效", "待发货", "运送中"];
    return arr[i];
}


function showSalerApply(i) {
    var arr = ["待审核", "已通过", "已拒绝"];
    return arr[i];
}


var res0 = window.sessionStorage.getItem("account");
var account ;
try {
    account = $.parseJSON(res0);
}catch (e) {
    account = null;
}

layui.use(['layer','table','form'], function() {
    var table = layui.table
        ,form = layui.form, $ = layui.jquery, layer = layui.layer;

    // 用户管理
    table.on('tool(test)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        var aid = tr[1].textContent;

        if (layEvent === 'forbidden') { //禁用
            //
            console.log(tr[1].textContent);

            // console.log(data);
            // console.log(tr);
            console.log(tr[0].childNodes[8].childNodes[0].childNodes[1]);
            if(account!=null && account.identity === 3) {
                var url = "http://localhost:8000/forbiddenAccount/" + parseInt(aid) + "/" + obj.data.status;
                $.ajax({
                    type: "get",
                    dataType: "json",
                    url: url,
                    async: false,
                    success: function (data) {
                        // console.log(data);
                        if (data.msg === "ok") {
                            obj.update({
                                status: data.Leaststatus
                            });
                            var mya = tr[0].childNodes[8].childNodes[0].childNodes[1];
                            console.log(data.Leaststatus == 1);
                            console.log(data.Leaststatus == 0);
                            if(data.Leaststatus == 1){
                                mya.innerText = "禁用";
                                mya.className = "layui-btn layui-btn-danger layui-btn-xs";
                            }else if(data.Leaststatus == 0){
                                mya.innerText = "激活";
                                mya.className = "layui-btn layui-btn-primary layui-btn-xs";
                            }
                            myTips("操作成功!");
                        }else {
                            myTips("操作失败");
                        }
                    },
                    error: function (e) {
                        console.log("出现了不可预料的错误" + e);
                    }
                });
            }
        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除该用户吗？。？', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
                // var aid = tr[1].textContent;
                var url = "http://localhost:8000/delAccount/" + parseInt(aid);
                adminAjax(url);
            });
        } else if (layEvent === "pswReset") {
            // var aid = tr[1].textContent;
            var url = "http://localhost:8000/pswReset/" + parseInt(aid);
            adminAjax(url);
        } else if(layEvent === "identityManager"){
            // 身份管理

            $.ajax({
                type: "get",
                dataType: "json",
                url: "http://localhost:8000/identityManagerInfoList",
                async: false,
                success: function (res) {
                    // console.log(data);
                    if (res.msg === "ok" && account!= null && account !== "null"){
                        var divs = "" ;
                        var list = res.identityNameList;
                        for (var i in list){
                            var c = "checked = \"\"";
                            var b = data.identity == list[i].id;
                            divs += "<label><input type=\"radio\" name=\"myIndentityCheck\" value=\""+ list[i].id +"\""+(b===true?c:"")+">"+ list[i].dec +"</label>";
                        }

                        //询问框
                        layer.confirm(divs, {
                            btn: ['修改','取消'] //按钮
                        }, function(data){
                            layer.msg('修改成功', {icon: 1});
                            console.log(data); // 1
                            var identityId = $("input[name='myIndentityCheck']:checked").val();
                            console.log(identityId);
                            updateIndentityInfo(aid, identityId);
                            obj.update({
                                identity: identityId
                            });

                        }, function (e) {
                            console.log(e); // 1
                        });

                    }
                },
                error: function (e) {
                    console.log("出现了不可预料的错误" + e);
                }
            });

        }
    });

    // 商品信息管理
    table.on('tool(adGoodsInfo)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        var gid = parseInt(tr[1].textContent);

        if (layEvent === 'detail') { //查看商品信息
            //
            console.log(tr[1].textContent);
            // 查看用户是否加入购物车了
            var aid = account.id == null ? -1 : account.id;
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

        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除该商品吗？。？', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令

            });
        }
    });

    // 竞拍记录
    table.on('tool(adAuctionRecord)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if (layEvent === 'del') { //删除
            layer.confirm('真的删除该竞拍记录吗？。？', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令

            });
        }
    });

    // 订单管理
    table.on('tool(order)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        console.log(data);

        if (layEvent === 'detail') { //查看
            //
            console.log(tr[1].textContent);


            var myhtml= "<a href='javascript:;' onclick=\"getGoodsInfoByOrder("+ data.goods_id +")\">查看订单商品</a>";

            // 居中弹出窗
            layui.use('layer', function () {
                var $ = layui.jquery, layer = layui.layer;

                layer.open({
                    title: '订单信息'
                    , content: myhtml
                });
            });

        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除该订单吗？。？', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令

            });
        }
    });

    // 商家申请
    table.on('tool(adsalerApply)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        var sid = parseInt(tr[1].textContent);
        var agree = 1;
        var refuse = 2;
        if (layEvent === 'agree') { // 同意
            //
            console.log(tr[1].textContent);
            // adsalerApply
            var url = "http://localhost:8000/adsalerApply/" + sid + "/" + agree;

            $.ajax({
                type: "get",
                dataType: "json",
                url: url,
                async: false,
                success: function (data) {
                    // console.log(data);
                    if (data.msg === "ok") {
                        obj.update({
                            status: agree
                        });
                        console.log(showSalerApply(agree));
                        $("#adagree" + sid).remove();
                        $("#adrefuse" + sid).remove();
                        myTips("操作成功!");
                    }else {
                        myTips("操作失败");
                    }
                },
                error: function (e) {
                    console.log("出现了不可预料的错误" + e);
                }
            });

        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除该用户的申请记录吗？。？', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令

            });
        } else if (layEvent === "refuse") {
            console.log("refuse");
            var url = "http://localhost:8000/adsalerApply/" + sid + "/" + refuse;

            $.ajax({
                type: "get",
                dataType: "json",
                url: url,
                async: false,
                success: function (data) {
                    // console.log(data);
                    if (data.msg === "ok") {
                        obj.update({
                            status: refuse
                        });
                        $("#adagree" + sid).remove();
                        $("#adrefuse" + sid).remove();
                        myTips("操作成功!");
                    }else {
                        myTips("操作失败");
                    }
                },
                error: function (e) {
                    console.log("出现了不可预料的错误" + e);
                }
            });
        }
    });

});

function getGoodsInfoByOrder(gid) {
    // 查看用户是否加入购物车了
    var aid = account.id == null ? -1 : account.id;
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
}


function updateIndentityInfo(aid, identity) {
    $.ajax({
        type: "get",
        dataType: "json",
        url: "http://localhost:8000/updateIndentityInfo/" + aid + "/" + identity,
        success: function (res) {
            // console.log(data);
            if (res.msg === "ok" ){
                // console.log(res);
            }
        },
        error: function (e) {
            console.log("出现了不可预料的错误" + e);
        }
    });
}
