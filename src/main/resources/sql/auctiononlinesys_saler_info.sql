create table saler_info
(
    id             int           not null
        primary key,
    busine_name    varchar(255)  null comment '商家名称',
    saler_name     varchar(255)  null comment '经营者名称',
    busine_address varchar(255)  null,
    busine_contant varchar(255)  null,
    saler_email    varchar(255)  null,
    apply_reason   varchar(255)  null,
    account        varchar(255)  null,
    status         int default 0 null comment '0-待审核
1-已通过
2-失败',
    apply_time     datetime      null
);

