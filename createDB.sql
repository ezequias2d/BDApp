-- Database: postgres

-- DROP DATABASE postgres;
	
CREATE TABLE Fornecedor (
	idFornecedor SERIAL PRIMARY KEY,
	nome VARCHAR (255),
	localidade VARCHAR (255),
	tipo VARCHAR (255),
	cpf VARCHAR(14),
	cnpj VARCHAR(18)
);
CREATE TABLE Produto (
	idProduto SERIAL PRIMARY KEY,
	status VARCHAR (255),
	preco MONEY,
	precoMinimo MONEY,
	garantia TIMESTAMP,
	idFornecedor INTEGER REFERENCES Fornecedor(idFornecedor)
);

CREATE TABLE DescricaoProduto (
	idProduto INTEGER REFERENCES Produto(idProduto),
	descricao VARCHAR (255),
	PRIMARY KEY (idProduto, descricao)
);

CREATE TABLE NomeProduto (
	idProduto INTEGER REFERENCES Produto(idProduto),
	nome VARCHAR (255),
	PRIMARY KEY (idProduto, nome)
);

CREATE TABLE Armazem (
	idArmazem SERIAL PRIMARY KEY,
	nome VARCHAR (255),
	tamanho INTEGER,
	endereco VARCHAR (255)
);

CREATE TABLE Estoque (
	idEstoque SERIAL PRIMARY KEY,
	codigo INTEGER,
	quantidade INTEGER,
	idProduto INTEGER REFERENCES Produto(idProduto),
	idArmazem INTEGER REFERENCES Armazem(idArmazem)
);

CREATE TABLE Cliente (
	idCliente SERIAL PRIMARY KEY,
	nome VARCHAR (255),
	cpf VARCHAR (14),
	pais VARCHAR (255),
	estado VARCHAR (255),
	cidade VARCHAR (255),
	garantia TIMESTAMP,
	dataCadastro TIMESTAMP,
	limiteCredito MONEY
);

CREATE TABLE TelefoneCliente (
	idCliente INTEGER REFERENCES Cliente(idCliente),
	telefone VARCHAR (255),
	PRIMARY KEY (idCliente, telefone)
);

CREATE TABLE EmailCliente (
	idCliente INTEGER REFERENCES Cliente(idCliente),
	email VARCHAR (255),
	PRIMARY KEY (idCliente, email)
);

CREATE TABLE Pedido (
	idPedido SERIAL PRIMARY KEY,
	dataPedido TIMESTAMP,
	modoEncomenda VARCHAR (255),
	statusPedido VARCHAR (255),
	dataPrazo TIMESTAMP,
	valor MONEY,
	idCliente INTEGER REFERENCES Cliente(idCliente)
);

CREATE TABLE ItemPedido (
	idProduto INTEGER REFERENCES Produto(idProduto),
	idPedido INTEGER REFERENCES Pedido(idPedido),
	quantidade INTEGER,
	precoVenda MONEY,
	precoTotal MONEY,
	PRIMARY KEY (idProduto, idPedido)
);

