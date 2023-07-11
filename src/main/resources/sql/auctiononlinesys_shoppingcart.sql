create table shoppingcart
(
    id  int auto_increment
        primary key,
    gid int null comment '购物车中的商品id',
    aid int null comment '用户id'
);

INSERT INTO auctiononlinesys.shoppingcart (id, gid, aid) VALUES (2, 2, 6);