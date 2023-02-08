create table guitar (
    id serial primary key,
    manufacturer varchar (255) not null,
    model varchar (255) not null,
    finish varchar (255) not null,
    serial_number varchar (255) not null,
    amount numeric(19, 2) not null
);
