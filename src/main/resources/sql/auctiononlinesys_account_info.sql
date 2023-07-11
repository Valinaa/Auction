create table account_info
(
    aid           int          not null
        primary key,
    sex           varchar(255) null,
    location      varchar(255) null,
    phone         varchar(255) null,
    email         varchar(255) null,
    personal_sign varchar(255) null,
    love          varchar(255) null
);

INSERT INTO auctiononlinesys.account_info (aid, sex, location, phone, email, personal_sign, love) VALUES (4, null, null, null, null, null, null);
INSERT INTO auctiononlinesys.account_info (aid, sex, location, phone, email, personal_sign, love) VALUES (5, null, null, null, null, null, null);
INSERT INTO auctiononlinesys.account_info (aid, sex, location, phone, email, personal_sign, love) VALUES (6, null, null, null, null, null, null);