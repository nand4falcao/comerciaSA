CREATE DATABASE Clientes;
USE Clientes;

CREATE TABLE Cliente (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR (100) NOT NULL,
	cpf VARCHAR (14) UNIQUE NOT NULL,
	dataNasc DATE NOT NULL,
	endereco VARCHAR (90) NOT NULL
);

CREATE TABLE Contato (
    idContato INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL, -- FK para ligar ao Cliente
    tipoContato VARCHAR(20) NOT NULL, -- RF06, RN02: Indicar se o contato é por email ou telefone
    valorContato VARCHAR(100) NOT NULL,     -- Ex: '99999-9999' ou 'maria@email.com'
    observacao VARCHAR(255),
    CONSTRAINT fk_cliente_contato 
        FOREIGN KEY (cliente_id) REFERENCES Cliente(id) 
        ON DELETE CASCADE -- Atende a RN07
);