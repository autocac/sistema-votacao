use tabelas

drop database SIS_VOT_DB;

create database SIS_VOT_DB;

USE SIS_VOT_DB;

--Primeiro: você deve criar um login, que é um "cara" que tem permisssão de se logar no SQL Sever
 DROP LOGIN sisvot;
CREATE LOGIN sisvot WITH PASSWORD = 's1i2s3v4o5t';

--Segundo: você deve criar um usuário para o banco de dados que deseja mapeando esse usuário para o login criado, 
--assim seu usuário conseguirá se logar no SQL Server e entrar no banco de dados desejado.
 DROP USER sisvot;
CREATE USER sisvot FROM LOGIN sisvot;

--Terceiro: você deve dar ou remover permissões ao usuário porque até o segundo passo o usuário criado 
--só tem direito a entrar no banco de dados, dando as permissões o usuário já pode operar no banco de dados. 
--Se o usuário for comum você pode adicioná-lo apenas as roles de db_reader e db_writer, que permitirá 
--que o usuário faça select, insert, delete e update em todas as tabelas do referido banco de dados.
EXEC SP_ADDROLEMEMBER 'DB_DATAREADER', 'sisvot';

EXEC SP_ADDROLEMEMBER 'DB_DATAWRITER', 'sisvot';

create table TB_USUARIO (
	LOGIN VARCHAR(15) PRIMARY KEY,
	SENHA VARCHAR(15) NOT NULL,
	TIPO CHAR(1) NOT NULL,
	NOME VARCHAR(50) NULL,
	FL_TROCAR_SENHA CHAR(1) NOT NULL DEFAULT 'N'
)

INSERT INTO TB_USUARIO (LOGIN, SENHA, TIPO, NOME, FL_TROCAR_SENHA)
VALUES ('admin', 'admin', 'A', 'Administrador do Sistema', 'N')

--SELECT * FROM TB_USUARIO

create table TB_VOTACAO (
	ID_VOTACAO INT PRIMARY KEY,
	DESCRICAO VARCHAR(200) NOT NULL,
	DT_INI DATETIME NOT NULL,
	DT_FIM DATETIME NOT NULL,
	LOGIN_ADMIN VARCHAR(15) NULL 
		CONSTRAINT FK_USUARIO FOREIGN KEY 
			REFERENCES TB_USUARIO(LOGIN),
	FL_SECRETA CHAR(1) NOT NULL DEFAULT 'N',
	NM_ARQ_IMG_FUNDO VARCHAR(20)  NULL,
	CONTENT_TYPE_IMG_FUNDO VARCHAR(50)  NULL,
	IMAGEM_FUNDO VARBINARY(MAX)  NULL
)

INSERT INTO TB_VOTACAO (ID_VOTACAO, DESCRICAO, DT_INI, DT_FIM, LOGIN_ADMIN, FL_SECRETA)
VALUES (1, 'Bicho Preferido', '10-10-2010', '10-11-2114', 'admin', 'N')

--select *  from TB_VOTACAO  where ID_VOTACAO = 1

--drop table TB_CANDIDATO
create table TB_CANDIDATO (
	ID_CANDIDATO INT PRIMARY KEY,
	ID_VOTACAO INT NOT NULL,
	NOME VARCHAR(50) NOT NULL,
	IMAGEM VARBINARY(MAX) NULL,
	IMG_CONT_TYPE VARCHAR(30) NULL,
	DESCRICAO VARCHAR(200) NULL,
	NUMERO_VOTOS SMALLINT NOT NULL DEFAULT 0,
	
	CONSTRAINT FK_VOTACAO FOREIGN KEY (ID_VOTACAO)
		REFERENCES TB_VOTACAO(ID_VOTACAO)

)

INSERT INTO TB_CANDIDATO (ID_VOTACAO, ID_CANDIDATO, NOME, IMAGEM, IMG_CONT_TYPE, DESCRICAO, NUMERO_VOTOS)
VALUES (1, 1, 'Macaco Simão', NULL, NULL, 'Macaco Prego da espécie Cebus apella', 0)

INSERT INTO TB_CANDIDATO (ID_VOTACAO, ID_CANDIDATO, NOME, IMAGEM, IMG_CONT_TYPE, DESCRICAO, NUMERO_VOTOS)
VALUES (1, 2, 'Cão', NULL, NULL, 'Canis lupus familiaris', 0)

INSERT INTO TB_CANDIDATO (ID_VOTACAO, ID_CANDIDATO, NOME, IMAGEM, IMG_CONT_TYPE, DESCRICAO, NUMERO_VOTOS)
VALUES (1, 3, 'Gato', NULL, NULL, 'Felis silvestris catus', 0)

-- select * from TB_CANDIDATO where ID_VOTACAO = 1

create table TB_ELEITORADO (
	LOGIN VARCHAR(15) NOT NULL,
	ID_VOTACAO INT NOT NULL,
	FL_COMPARECEU CHAR(1) NOT NULL DEFAULT 'N',
	ID_CANDIDATO_VOTADO INT NULL,
	
	CONSTRAINT PK_TB_ELEITORADO PRIMARY KEY (LOGIN, ID_VOTACAO),
	
	CONSTRAINT FK_CANDIDATO FOREIGN KEY (ID_CANDIDATO_VOTADO)
			REFERENCES TB_CANDIDATO(ID_CANDIDATO)

)

insert into TB_ELEITORADO (LOGIN, ID_VOTACAO, FL_COMPARECEU, ID_CANDIDATO_VOTADO)
values ('admin', 1, 'N', NULL)

--select * from TB_ELEITORADO
