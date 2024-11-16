create table Pelicula
(
    id          int auto_increment
        primary key,
    titulo      varchar(200) null,
    genero      varchar(200) null,
    año         int          null,
    descripcion varchar(200) null,
    director    varchar(200) null
);

create table Usuario
(
    id             int auto_increment
        primary key,
    nombre_usuario varchar(200)         null,
    contraseña     varchar(200)         null,
    admin          tinyint(1) default 0 null
);

create table Copia
(
    id          int auto_increment
        primary key,
    id_pelicula int          null,
    id_usuario  int          null,
    estado      varchar(200) null,
    soporte     varchar(200) null,
    constraint Copia_ibfk_1
        foreign key (id_pelicula) references Pelicula (id),
    constraint Copia_ibfk_2
        foreign key (id_usuario) references Usuario (id)
);

create index id_pelicula
    on Copia (id_pelicula);

create index id_usuario
    on Copia (id_usuario);


