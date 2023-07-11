create table goods_auction
(
    id          int auto_increment
        primary key,
    good_name   varchar(255)   null,
    good_type   int            null,
    start_price decimal(12, 2) null,
    price_plus  decimal(12, 2) null,
    start_time  datetime       null,
    end_time    datetime       null,
    account_id  int            null,
    goods_dec   varchar(255)   null,
    pic         varchar(255)   null,
    status      varchar(255)   null comment '0-未上架
1-拍卖中
2-未到拍卖时间
3-已成交
4-不存在',
    saler_name  varchar(255)   null,
    now_price   decimal(10, 2) null
);

create index goods_auction_account_id_index
    on goods_auction (account_id);

INSERT INTO auctiononlinesys.goods_auction (id, good_name, good_type, start_price, price_plus, start_time, end_time, account_id, goods_dec, pic, status, saler_name, now_price) VALUES (2, '测试商品0', 2, 50.00, 5.00, '2021-06-29 19:33:03', '2021-06-30 04:00:00', 5, '测试商品内容测试0000000000', '/picFiles/3c6b7260-cb3c-451a-bd88-32c7fda0f561.jpg', '1', 'lankeren', 56.00);
INSERT INTO auctiononlinesys.goods_auction (id, good_name, good_type, start_price, price_plus, start_time, end_time, account_id, goods_dec, pic, status, saler_name, now_price) VALUES (3, '测试商品111', 3, 123.00, 66.00, '2021-06-29 19:39:38', '2021-06-30 07:00:00', 6, '测试商品666666', '/picFiles/a20c1d61-eaa1-4b12-aa37-f551faca91ea.jpg', '1', 'lankeren', 123.00);