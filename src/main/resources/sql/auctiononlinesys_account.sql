create table account
(
    id       int auto_increment
        primary key,
    name     varchar(255) default 'lankeren' null,
    account  varchar(255)                    null,
    password varchar(255)                    null,
    identity int          default 0          null comment '0-普通用户
1-普通会员
2-卖家
3-管理员
4-卖家会员',
    status   int          default 1          null comment '0-锁定
1-激活',
    reg_time datetime                        null
);

INSERT INTO auction.account (id, name, account, password, identity, status, reg_time) VALUES (4, 'lankerens', 'test1', '333', 0, 1, '2021-06-29 18:58:10');
INSERT INTO auction.account (id, name, account, password, identity, status, reg_time) VALUES (5, 'lankeren', 'lankeren', '111', 3, 1, '2021-06-29 19:22:11');
INSERT INTO auction.account (id, name, account, password, identity, status, reg_time) VALUES (6, 'lankeren', 'saler', '111', 2, 1, '2021-06-29 19:38:47');