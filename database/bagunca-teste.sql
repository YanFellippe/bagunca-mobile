CREATE DATABASE IF NOT EXISTS bagunca_mobile;
USE bagunca_mobile;

CREATE TABLE login_usuario(
	id_login INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(120),
    email VARCHAR(120),
    senha VARCHAR(45)
);

CREATE TABLE atividades(
id_atividades INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
id_login INTEGER NOT NULL,
descricao VARCHAR (50),
status VARCHAR(30)NOT NULL,
CONSTRAINT id_login FOREIGN KEY (id_login) references login_usuario(id_login)
);

INSERT INTO login_usuario(usuario, email, senha) VALUES
('admin', 'admin@gmail.com', upper(md5('12345')));

SELECT * FROM login_usuario;