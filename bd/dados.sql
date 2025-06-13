-- Inserção de dados na tabela "Jogo"

INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'Roleta Clássica', 'Aposte em PAR (0) ou ÍMPAR (1). Se acertar, ganha o dobro do valor apostado!', 5);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'Roleta das Cores', 'Aposte em uma cor: VERMELHO (0), AZUL (1), AMARELO (2), VERDE (3). Acerte e ganhe 4x!', 10);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'Caça Níquel', 'Aperte a alavanca da sorte. Se acertar, ganhe o dobro do valor apostado!', 10);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'BlackJack', 'Chegue o mais próximo de 21 sem ultrapassar. Se vencer, ganhe o triplo do valor apostado!', 10);
