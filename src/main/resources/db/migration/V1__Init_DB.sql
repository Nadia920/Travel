/*create table app_role
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint app_role_primary_key
            primary key,
    role varchar(29) not null
);

create table company
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint company_primary_key
            primary key,
    name   varchar(45)  not null,
    rating varchar(255) not null
);

create table country
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint country_primary_key
            primary key,
    name varchar(255) not null
);

create table city
(
   id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint city_primary_key
            primary key,
    name       varchar(255) not null,
    country_id bigint not null
        constraint  country_id_foreign_key
            references country ON UPDATE CASCADE ON DELETE RESTRICT
);

create table bus_station
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint bus_station_primary_key
            primary key,
    code    varchar(5)  not null,
    name    varchar(45) not null,
    city_id bigint      not null
        constraint  city_id_foreign_key
            references city ON UPDATE CASCADE ON DELETE RESTRICT
);

create table bus
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint bus_primary_key
            primary key,
    name        varchar(45) not null,
    side_number varchar(255) not null,
    company_id  bigint      not null
        constraint company_id_foreign_key
            references company ON UPDATE CASCADE ON DELETE RESTRICT
);

create table trip
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint trip_primary_key
            primary key,
    all_seats            integer          not null,
    arrival_date         timestamp        not null,
    departure_date       timestamp        not null,
    free_seats           integer          not null,
    price                double precision not null,
    status               varchar(255),
    bus_station_arrival_id   bigint           not null
        constraint bus_station_arrival_id_foreign_key
            references bus_station ON UPDATE CASCADE ON DELETE RESTRICT,
    bus_station_departure_id bigint           not null
        constraint bus_station_departure_id_foreign_key
            references bus_station ON UPDATE CASCADE ON DELETE RESTRICT,
    bus_id             bigint           not null
        constraint bus_id_foreign_key
            references bus ON UPDATE CASCADE ON DELETE RESTRICT
);

create table wallet
(
   id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint wallet_primary_key
            primary key,
    sum double precision not null
);

create table users
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint users_primary_key
            primary key,
    email        varchar(40),
    first_name   varchar(40) not null,
    last_name    varchar(40) not null,
    patronymic   varchar(40) not null,
    login        varchar(40) not null,
    password     varchar(40) not null,
    phone_number varchar(40) not null,
    role_id      bigint      not null
        constraint role_id_foreign_key
            references app_role,
    wallet_id    bigint not null
        constraint wallet_id_foreign_key
            references wallet ON UPDATE CASCADE ON DELETE RESTRICT
);

create table user_order
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint order_primary_key
            primary key,
    final_cost double precision not null,
    order_date timestamp        not null,
    status     varchar(8),
    trip_id  bigint           not null
        constraint trip_id_foreign_key
            references trip,
    user_id    bigint           not null
        constraint user_id_foreign_key
            references users ON UPDATE CASCADE ON DELETE RESTRICT
);

create table ticket
(
    id  bigint  not null GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1)
        constraint ticket_primary_key
            primary key,
    price    double precision not null,
    order_id bigint           not null
        constraint order_id_foreign_key
            references user_order (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

create sequence hibernate_sequence
  start 1
  increment 1;*/
