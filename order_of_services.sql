create schema db_order_service

USE db_order_service;

CREATE TABLE tb_usuarios(
	id_usuario INT PRIMARY KEY,
	usuario VARCHAR(50) NOT NULL,
	usuario_fone VARCHAR(15),
	login VARCHAR(15) NOT NULL UNIQUE,
	senha VARCHAR(15) NOT NULL
	
);

--comando para descrever tabela

DESCRIBE tb_usuarios
--modifica o tipo da coluna para autoincrement
ALTER TABLE tb_usuarios MODIFY COLUMN id_usuario INT(6) auto_increment



--insert dados na tabela

insert into tb_usuarios(usuario,usuario_cargo,login,senha,usuario_perfil) values ( 'Talita Souza' , 'atendente', 'talita.souza', 't@lit4','restrito');


select * from tb_usuarios

--atualizar cadastro 

UPDATE tb_usuarios set usuario_fone = '4529-3459' WHERE id_usuario = 1;

--deletar dados

DELETE from tb_usuarios where id_usuario = null;

--tabela clientes

CREATE table tb_clientes(
	id_cliente int PRIMARY key AUTO_INCREMENT,
	cliente_nome VARCHAR(50) not null,
	cliente_endereco VARCHAR(100),
	cliente_fone VARCHAR(50) not null,
	cliente_email VARCHAR(50)
	
);

insert into tb_clientes(cliente_nome,cliente_endereco,cliente_fone,cliente_email) values ( 'Francisco Morato' , 'Rua Bahia, 256 - Jacare, Cabreuva-sp', '9999-9999', 'francisco.morato@morato.com.br');
insert into tb_clientes(cliente_nome,cliente_endereco,cliente_fone,cliente_email) values ( 'Pedro Bino' , 'Rua Cilada - Jacare, Cabreuva-sp', '9999-9999', 'pedro@cilada.com.br');
insert into tb_clientes(cliente_nome,cliente_endereco,cliente_fone,cliente_email) values ( 'Moises Francisdo da Silva' , 'Rua Arcoeiro, 256 - Jacare, Cabreuva-sp', '9999-9999', 'moises@santos.com.br');
insert into tb_clientes(cliente_nome,cliente_endereco,cliente_fone,cliente_email) values ( 'Roque Oliveira' , 'Rua Bau Jacare, Cabreuva-sp', '9999-9999', 'roque@baudafelicidade.com.br');



SELECT * from tb_clientes

CREATE table tb_os(
	id_os int PRIMARY key AUTO_INCREMENT,
	os_data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	os_equipamento VARCHAR(150) not null,
	os_defeito VARCHAR(150) not null,
	os_servico varchar(150),
	os_tecnico VARCHAR(30),
	os_valor DECIMAL(10,2),
	id_cliente int not null, FOREIGN key(id_cliente) REFERENCES tb_clientes(id_cliente)
	
);

DESCRIBE tb_os

insert into tb_os(os_equipamento, os_defeito, os_servico, os_tecnico, os_valor, id_cliente) VALUES ('macbook pro','fica reiniciando','restaurar o sistema', 'Joao',100.00, 4);

-- inner join das tabelas , trazendo informacoes de 2 tabelas

SELECT OS.id_os,os_equipamento,os_defeito,os_servico,os_valor,
CL.cliente_nome, cliente_fone from tb_os as OS inner join tb_clientes as CL ON (OS.id_cliente = CL.id_cliente);

alter table tb_usuarios add COLUMN usuario_perfil varchar(20) not null

update tb_usuarios set usuario_cargo = 'administrador' where id_usuario =1;
update tb_usuarios set usuario_cargo = 'técnico' where id_usuario =3;
update tb_usuarios set usuario_cargo = 'administrador' where id_usuario =1;
 
 alter table tb_usuarios change usuario_fone usuario_cargo varchar(30);

update tb_usuarios set usuario_perfil = 'restrito' where id_usuario = 2;