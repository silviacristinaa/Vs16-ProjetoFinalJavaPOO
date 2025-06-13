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

INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Lucas', 'lf', 25);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Gabriela', 'gabss', 21);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'William', 'will', 19);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Robson', 'rob', 20);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Silvia', 'sil', 20);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Sarah', 'sarinha', 18);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Sara', 'sasa', 19);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Rafael', 'rafalazzari', 35);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Cristina', 'cris', 40);
INSERT INTO Jogadores (ID, nome, nickname, idade)
VALUES(SEQ_JOGADORES.nextval, 'Oscar', 'tito', 18);
