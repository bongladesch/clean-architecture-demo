create table persons(
    id varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    primary key (id),
    constraint unique_full_name unique (first_name, last_name)
);

create table email_addresses(
    id varchar(255) not null,
    email varchar(255) not null,
    person_id varchar(255) not null,
    primary key (id),
    foreign key (person_id) references persons(id)
);
