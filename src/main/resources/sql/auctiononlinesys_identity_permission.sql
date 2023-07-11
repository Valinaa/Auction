create table identity_permission
(
    identity_id   int not null comment '身份id'
        primary key,
    permission_id int null comment '权限id'
);

