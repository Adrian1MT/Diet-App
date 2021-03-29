create database pruebaDietas collate utf8mb4_spanish_ci;

use pruebaDietas;

create table RECETA
(idReceta int AUTO_INCREMENT primary key,
nomReceta varchar(100) not null);

create table MENU
(comida enum('desayuno','almuerzo','comida','merienda','cena') primary key);

create table DIETA
(tipo enum('diaria','fin de semana','semanal') primary key);

create table CONTIENE
(tipoDieta enum('diaria','fin de semana','semanal'),
dia enum('lunes','martes','miércoles','jueves','viernes','sábado','domingo'),
tipoComida enum('desayuno','almuerzo','comida','merienda','cena'),
idReceta int,
constraint fk_idReceta foreign key(idReceta) references RECETA(idReceta) on delete cascade on update cascade,
constraint fk_tipoComida foreign key(tipoComida) references MENU(comida) on delete cascade on update cascade,
constraint fk_tipoDieta foreign key(tipoDieta) references DIETA(tipo) on delete cascade on update cascade,
primary key(idReceta,tipoComida,tipoDieta));

insert into RECETA values (1,'Café con leche');
insert into RECETA values (2,'Pincho de tortilla');
insert into RECETA values (3,'Arroz a la cubana');
insert into RECETA values (4,'Filete de ternera con patatas');
insert into RECETA values (5,'Arroz con leche');
insert into RECETA values (6,'Té con pastas');
insert into RECETA values (7,'Tortilla francesa');
insert into RECETA values (8,'Yogur natural');

insert into MENU values ('desayuno');
insert into MENU values ('almuerzo');
insert into MENU values ('comida');
insert into MENU values ('merienda');
insert into MENU values ('cena');

insert into DIETA values ('diaria');
insert into DIETA values ('fin de semana');
insert into DIETA values ('semanal');

insert into contiene values ('diaria','lunes','desayuno',1);
insert into contiene values ('diaria','lunes','almuerzo',2);
insert into contiene values ('diaria','lunes','comida',3);
insert into contiene values ('diaria','lunes','comida',4);
insert into contiene values ('diaria','lunes','comida',5);
insert into contiene values ('diaria','lunes','merienda',6);
insert into contiene values ('diaria','lunes','cena',7);
insert into contiene values ('diaria','lunes','cena',8);