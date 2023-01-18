create schema dance_school_db collate utf8_general_ci;

create table alunno
(
    matricola bigint auto_increment
        primary key,
    address varchar(255) null,
    birthday date null,
    birthday_place varchar(255) null,
    cap varchar(255) null,
    city varchar(255) null,
    fiscal_code varchar(255) null,
    name varchar(255) null,
    parent_fiscal_code varchar(255) null,
    surname varchar(255) null,
    telephone varchar(255) null,
    active bit not null,
    gender varchar(10) null,
    constraint alunno_pk
        unique (fiscal_code)
);

create table corso
(
    id bigint auto_increment
        primary key,
    description varchar(255) null,
    name varchar(255) null,
    year varchar(255) null,
    active bit not null
);

create table iscrizione
(
    id bigint auto_increment
        primary key,
    subscribe_date date null,
    course bigint null,
    student bigint null,
    constraint FKbbugve1clxxer2o456m2h301o
        foreign key (course) references corso (id),
    constraint FKsbqvmwh1pyu301ae9poue4djt
        foreign key (student) references alunno (matricola)
);

create table pagamento
(
    id bigint auto_increment
        primary key,
    amount double not null,
    payment_date date null,
    related_course varchar(255) null,
    student bigint null,
    constraint FKm2pl23iwkw7q9owltpx50ki58
        foreign key (student) references alunno (matricola)
);

