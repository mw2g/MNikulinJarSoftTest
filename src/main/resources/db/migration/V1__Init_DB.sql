create table category
(
    category_id integer         not null auto_increment,
    name        varchar(255)    not null unique,
    req_name    varchar(255)    not null unique,
    deleted     boolean         not null,
    primary key (category_id)
);

create table banner
(
    banner_id   integer         not null auto_increment,
    name        varchar(255)    not null unique,
    price       decimal(8,2)    not null,
    category_id integer,
    content     text            not null,
    deleted     boolean         not null,
    primary key (banner_id)
);

create table request
(
    request_id integer          not null auto_increment,
    banner_id  integer,
    user_agent text             not null,
    ip_address varchar(255)     not null,
    date       datetime         not null,
    primary key (request_id)
);

alter table banner
    add constraint banner_category_fk
        foreign key (category_id)
            references category (category_id);

alter table request
    add constraint request_banner_fk
        foreign key (banner_id)
            references banner (banner_id);
