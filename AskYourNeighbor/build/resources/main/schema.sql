drop table ADMIN_ACCOUNT if exists;
create table ADMIN_ACCOUNT
(
    id       bigint not null,
    username varchar(255),
    password varchar(255),
    primary key (id)
);

drop table FORM if exists;
create table FORM
(
    id                  bigint not null,
    name_of_applicant   varchar(255),
    city                varchar(255),
    street              varchar(255),
    number              varchar(255),
    phone_number        bigint,
    product_name        varchar(255),
    description         varchar(255),
    category_id         bigint,
    amount              integer,
    unit_id             bigint,
    price               decimal(19, 2),
    availability_start  date,
    availability_end    date,
    accepting_person_id bigint,
    send_date           date,
    primary key (id)
);

drop table UNIT if exists;
create table UNIT
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

drop table CATEGORY if exists;
create table CATEGORY
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

ALTER TABLE FORM
    ADD CONSTRAINT fk_form_admin FOREIGN KEY (accepting_person_id) REFERENCES ADMIN_ACCOUNT (id);
ALTER TABLE FORM
    ADD CONSTRAINT fk_form_unit FOREIGN KEY (unit_id) REFERENCES UNIT (id);
ALTER TABLE FORM
    ADD CONSTRAINT fk_form_category FOREIGN KEY (category_id) REFERENCES CATEGORY (id) NOCHECK;
