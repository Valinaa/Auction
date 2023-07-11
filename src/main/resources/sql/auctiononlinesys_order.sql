create table `order`
(
    order_id     varchar(255)   not null
        primary key,
    good_name    varchar(255)   null,
    start_price  decimal(10, 2) null,
    end_price    decimal(10, 2) null,
    create_time  datetime       null,
    status       int            null comment '0-待支付
1-已支付
2-超时
3-待发货
4-运送中
5-已完成
6-已取消',
    goods_id     int            null,
    saler_id     int            null,
    account_id   int            null,
    address      varchar(255)   null,
    account_name varchar(255)   null,
    account      varchar(255)   null
);

