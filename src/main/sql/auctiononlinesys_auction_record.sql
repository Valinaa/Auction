create table auction_record
(
    id           int auto_increment
        primary key,
    good_name    varchar(200)             null,
    start_price  decimal(10, 2)           null,
    now_price    decimal(10, 2)           null,
    my_plus      varchar(255)             null comment '我的加价',
    account_id   int                      null,
    account_name varchar(255)             null,
    status       varchar(255) default '1' null comment '0-未开始
1-未成交
2-已成交',
    end_time     datetime                 null,
    start_time   datetime                 null,
    saler_id     int                      null comment '卖家id',
    gid          int                      null comment '竞拍的是哪个商品',
    create_time  datetime                 null comment '竞拍时间'
);

INSERT INTO auction.auction_record (id, good_name, start_price, now_price, my_plus, account_id, account_name, status, end_time, start_time, saler_id, gid, create_time) VALUES (1, '测试商品0', 50.00, 56.00, '6.0', 5, 'lankeren', '1', '2021-06-30 04:00:00', '2021-06-29 19:33:03', 5, 2, '2021-06-29 19:37:27');