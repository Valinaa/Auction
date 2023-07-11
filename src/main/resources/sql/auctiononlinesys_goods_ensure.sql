create table goods_ensure
(
    gid       int not null comment '商品id'
        primary key,
    pack_mail int null comment '包邮',
    oimei     int null comment '假一赔十',
    ensure    int null comment '协议保障'
);

INSERT INTO auctiononlinesys.goods_ensure (gid, pack_mail, oimei, ensure) VALUES (2, 1, 1, 0);
INSERT INTO auctiononlinesys.goods_ensure (gid, pack_mail, oimei, ensure) VALUES (3, 1, 0, 1);