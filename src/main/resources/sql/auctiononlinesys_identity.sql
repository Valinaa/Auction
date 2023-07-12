create table identity
(
    id            int          not null
        primary key,
    identity_name varchar(255) null,
    `dec`         varchar(255) null comment '描述'
);

INSERT INTO auction.identity (id, identity_name, `dec`) VALUES (0, 'user', '普通用户');
INSERT INTO auction.identity (id, identity_name, `dec`) VALUES (1, 'vip', '普通会员');
INSERT INTO auction.identity (id, identity_name, `dec`) VALUES (2, 'saler', '卖家');
INSERT INTO auction.identity (id, identity_name, `dec`) VALUES (3, 'admin', '管理员');
INSERT INTO auction.identity (id, identity_name, `dec`) VALUES (4, 'vipSaler', '会员卖家');