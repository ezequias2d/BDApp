INSERT INTO Fornecedor (nome, localidade, tipo, cpf, cnpj)
VALUES
('João Antonio', 'Brasil', 'Alimentos', '11833366655', DEFAULT),
('SA Alimentos', 'Argentina', 'Alimentos', DEFAULT, '42321987000283'),
('Nascimento Silva', 'Brasil', 'Bebidas', '12654766655', NULL),
('Jordan Banks', 'Estados Unidos', 'Alimentos', '10868243565', NULL),
('Risitas Lagos', 'Mexico', 'Bebidas', '11855696655', NULL),
('Knorr', 'Alemanha', 'Alimentos', DEFAULT, '23224587012382'),
('Coca-Cola', 'Estados Unidos', 'Bebidas', DEFAULT, '13221227999982'),
('Nescafe', 'Suiça', 'Bebidas', DEFAULT, '00811227039982'),
('Vitarella', 'Brasil', 'Alimentos', DEFAULT, '03856982009982'),
('Maria Gefina', 'Argentina', 'Alimentos', '65053365455', DEFAULT);

INSERT INTO Cliente (nome, cpf, pais, estado, cidade, garantia, dataCadastro, limiteCredito)
VALUES
('João Antonio', '12345678992', 'Brasil', 'Pernambuco', 'Cachoeirinha', '2022-1-1'::timestamp, '2013-2-3'::timestamp, '5000'),
('Joanasita Malaquita', '12547896582', 'Chile', 'Santiago', 'Santiago', '2022-1-1'::timestamp, '2014-2-3'::timestamp, '9000'),
('José Sabuguino', '12365985620', 'Estados Unidos', 'Texas', 'Houston', '2022-1-1'::timestamp, '2015-2-3'::timestamp, '2000'),
('Chone Pepsi', '12548798562', 'Estados Unidos', 'Texas', 'Dallas', '2022-1-1'::timestamp, '2015-2-3'::timestamp, '1000'),
('Marguerita Calabina', '98562365874', 'Chile', 'Santiago', 'Santiago', '2022-1-1'::timestamp, '2013-2-3'::timestamp, '13000'),
('Maradona Aposentado', '23564874521', 'Argentina', 'Buenos Aires', 'Buenos Aires', '2022-1-1'::timestamp, '2014-2-3'::timestamp, '6000'),
('Guacamole Sahena', '21569856232', 'Mexico', 'Estado do Mexico', 'Cidade do Mexico', '2022-1-1'::timestamp, '2015-2-3'::timestamp, '12200'),
('Jolie Namure', '45879856236', 'Espanha', 'Comunidade de Madrid', 'Madrid', '2022-1-1'::timestamp, '2012-2-3'::timestamp, '8000'),
('Dois Camaros', '32658785485', 'Canada', 'Ontario', 'Toronto', '2022-1-1'::timestamp, '2010-2-3'::timestamp, '5100'),
('Zé da Pitú', '98562415489', 'Brasil', 'Pernambuco', 'Cachoeirinha', '2022-1-1'::timestamp, '2016-2-3'::timestamp, '999999');

INSERT INTO TelefoneCliente (idCliente, telefone)
VALUES
(1, '819965254585'),
(2, '549872165659'),
(3, '216598153265'),
(4, '326598754219'),
(5, '321469873216'),
(6, '874652318455'),
(7, '654219698555'),
(8, '548548948654'),
(9, '654006987486'),
(1, '186406980354'),
(10, '684065400655');

INSERT INTO EmailCliente (idCliente, email)
VALUES
(1, 'joao@gmail.com'),
(2, 'joanasita@gmail.com'),
(3, 'joao@gmail.com'),
(4, 'pepsi@gmail.com'),
(5, 'marguerita@gmail.com'),
(6, 'maradonaaposentado@gmail.com'),
(7, 'mole@gmail.com'),
(8, 'jonamur@gmail.com'),
(9, 'doiscamaros@gmail.com'),
(10, 'zedapitu@gmail.com'),
(1, 'joao2@gmail.com');

INSERT INTO Produto (status, preco, precoMinimo, garantia, idFornecedor)
VALUES
('Disponivel', '2,5', '1', '2022-1-1'::timestamp, 1),
('Disponivel', '3', '3', '2022-1-1'::timestamp, 2),
('Disponivel', '2', '1,5', '2022-1-1'::timestamp, 3),
('Disponivel', '10', '8', '2022-1-1'::timestamp, 4),
('Disponivel', '4', '1', '2022-1-1'::timestamp, 5),
('Disponivel', '3,2', '2,3', '2022-1-1'::timestamp, 6),
('Disponivel', '5', '4', '2022-1-1'::timestamp, 7),
('Disponivel', '4,5', '3,8', '2022-1-1'::timestamp, 8),
('Disponivel', '2', '1,15', '2022-1-1'::timestamp, 9),
('Disponivel', '25', '23,5', '2022-1-1'::timestamp, 10);

INSERT INTO NomeProduto (idProduto, nome)
VALUES
(1, 'Bolacha Maria'),
(2, 'Salgadinho Petit Salgou'),
(2, 'Petit Salt'),
(2, 'Petit Salagou'),
(3, 'Jatoba'),
(4, 'Pringles'),
(5, 'Suco Suquita'),
(6, 'Caldo Knorr'),
(7, 'Coca-Cola'),
(8, 'Cafe Nescafe'),
(9, 'Treloso'),
(9, 'TrelosoRU'),
(9, 'TrelosoNA'),
(9, 'TrelosoSA'),
(10, 'Geladinho'),
(10, 'Coldzinho');

INSERT INTO DescricaoProduto (idProduto, descricao)
VALUES
(1, 'Bolacha Maria gostosa'),
(2, 'Petit bem salgado'),
(2, 'Petit very salgado'),
(2, 'Petit salgadito'),
(3, 'Jatoba a 2 reais'),
(4, 'Pringles cara demais'),
(5, 'Suco Suquita bem refrescante'),
(6, 'Caldo Knorr pra fazer sopa'),
(7, 'Coca-Cola é viciante'),
(8, 'Cafe Nescafe mais ou menos'),
(9, 'Treloso bom demais'),
(9, 'Treloso bom demaisRU'),
(9, 'Treloso bom demaisNA'),
(9, 'Treloso bom demaisSA'),
(10, 'Geladinho gelado'),
(10, 'Coldzinho very gelado');

INSERT INTO Armazem (nome, tamanho, endereco)
VALUES
('Armazem do Farinheiro', 10, 'Rua Calangos Calmos'),
('Tenda do Quentinho', 30, 'Rua Dificil de Encontrar'),
('Armazem 2 Irmaos', 900, 'Rua Dust 2');

INSERT INTO Estoque (codigo, quantidade, idProduto, idArmazem)
VALUES
(123, 30000, 1, 1),
(321, 50000, 2, 2),
(213, 10000, 3, 3),
(214, 20000, 4, 3),
(215, 30000, 5, 3),
(322, 40000, 6, 2),
(124, 20000, 7, 1),
(216, 10000, 8, 3),
(217, 90000, 9, 3),
(323, 10000, 10, 2);

INSERT INTO Pedido (dataPedido, modoEncomenda, statusPedido, dataPrazo, valor, idCliente)
VALUES
('2014-12-12'::timestamp, 'Sedex', 'Entregue', '2014-12-20'::timestamp, '25', 1),
('2014-12-12'::timestamp, 'Presencial', 'Entregue', '2014-12-20'::timestamp, '45', 2),
('2015-12-12'::timestamp, 'Sedex', 'Entregue', '2015-12-20'::timestamp, '225', 3),
('2015-12-12'::timestamp, 'Sedex', 'Entregue', '2015-12-20'::timestamp, '5000', 4),
('2016-12-12'::timestamp, 'Presencial', 'Entregue', '2016-12-20'::timestamp, '1125', 5),
('2016-12-12'::timestamp, 'Sedex', 'Entregue', '2016-12-22'::timestamp, '12500', 10),
('2017-12-12'::timestamp, 'Sedex', 'Entregue', '2017-12-20'::timestamp, '3972', 6),
('2017-12-12'::timestamp, 'Sedex', 'Entregue', '2017-12-20'::timestamp, '1118', 7),
('2018-12-12'::timestamp, 'Sedex', 'Entregue', '2018-12-20'::timestamp, '870', 8),
('2018-12-12'::timestamp, 'Sedex', 'Entregue', '2018-12-20'::timestamp, '604,2', 9),
('2019-12-12'::timestamp, 'Presencial', 'Entregue', '2019-12-12'::timestamp, '8740', 2),
('2019-10-5'::timestamp, 'Presencial', 'Entregue', '2019-10-8'::timestamp, '9450', 5);

INSERT INTO ItemPedido (idProduto, idPedido, quantidade, precoVenda, precoTotal)
VALUES
(10, 6, 900, '25', '12500'),
(8, 11, 2300, '3,8', '8740'),
(4, 12, 1050, '9', '9450'),
(1, 1, 10, '2,5', '25'),
(2, 2, 15, '3', '45'),
(3, 3, 150, '1,5', '225'),
(5, 4, 5000, '1', '5000'),
(6, 5, 450, '2,5', '1125'),
(7, 6, 993, '4', '3972'),
(9, 7, 559, '2', '1118'),
(10, 8, 870, '1', '870'),
(1, 9, 318, '1,9', '604,2');