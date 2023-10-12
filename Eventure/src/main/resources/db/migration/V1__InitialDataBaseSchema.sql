CREATE TABLE role_types (
                       id                bigserial not null,
                       role_name             VARCHAR(255),
                       primary key (id)
);

CREATE TABLE eventure_users (
                       id bigserial not null,
                       user_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       country VARCHAR(255) NOT NULL,
                       interests VARCHAR(255),
                       image VARCHAR(255),
                       date_Of_Birth DATE NOT NULL,
                       role_id int8 constraint fk_role_id references role_types not null,
                       primary key (id)
);