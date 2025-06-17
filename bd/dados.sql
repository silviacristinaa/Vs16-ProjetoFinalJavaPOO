-- Inserção de dados na tabela "Jogo"

INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'Roleta Clássica', 'Aposte em PAR (0) ou ÍMPAR (1). Se acertar, ganha o dobro do valor apostado!', 5);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'Roleta das Cores', 'Aposte em uma cor: VERMELHO (0), AZUL (1), AMARELO (2), VERDE (3). Acerte e ganhe 4x!', 10);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'Caça Níquel', 'Aperte a alavanca da sorte. Se acertar, ganhe o dobro do valor apostado!', 10);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'BlackJack', 'Chegue o mais próximo de 21 sem ultrapassar. Se vencer, ganhe o triplo do valor apostado!', 10);

-- Inserção de dados na tabela "Jogadores"

INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Lucas', 'lf', 25);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Gabriela', 'gabss', 21);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'William', 'will', 19);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Robson', 'rob', 20);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Silvia', 'sil', 20);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Sarah', 'sarinha', 18);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Sara', 'sasa', 19);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Rafael', 'rafalazzari', 35);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Cristina', 'cris', 40);
INSERT INTO Jogador (ID, nome, nickname, idade)
VALUES(SEQ_JOGADOR.nextval, 'Oscar', 'tito', 18);

-- Inserção de dados na tabela "Carteira"

INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 20, 100.00, 1);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 15, 50.00, 2);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 10, 80.00, 3);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 25, 20.00, 4);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 30, 150.00, 5);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 12, 60.00, 6);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 18, 40.00, 7);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 22, 90.00, 8);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 16, 75.00, 9);
INSERT INTO Carteira (ID, fichas, dinheiro, id_jogador)
VALUES (SEQ_CARTEIRA.NEXTVAL, 14, 55.00, 10);
