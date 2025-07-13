-- Inserção de dados na tabela "Jogo"

INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'ROLETA_CLASSICA', 'Aposte em PAR (0) ou ÍMPAR (1). Se acertar, ganha o dobro do valor apostado!', 5);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'ROLETA_DAS_CORES', 'Aposte em uma cor: VERMELHO (0), AZUL (1), AMARELO (2), VERDE (3). Acerte e ganhe 4x!', 10);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'CACA_NIQUEL', 'Aperte a alavanca da sorte. Se acertar, ganhe o dobro do valor apostado!', 10);
INSERT INTO Jogo (ID, nome_jogo, regras, valor_inicial)
VALUES(SEQ_JOGO.nextval, 'BLACKJACK', 'Chegue o mais próximo de 21 sem ultrapassar. Se vencer, ganhe o triplo do valor apostado!', 10);

-- Inserção de dados na tabela "Jogadores"

INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Lucas', 'lf', 25, 'email@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Gabriela', 'gabss', 21, 'email2@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'William', 'will', 19, 'email3@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Robson', 'rob', 20, 'email4@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Silvia', 'sil', 20, 'email5@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Sarah', 'sarinha', 18, 'email6@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Sara', 'sasa', 19, 'email7@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Rafael', 'rafalazzari', 35, 'email8@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Cristina', 'cris', 40, 'email9@gmail.com');
INSERT INTO Jogador (ID_JOGADOR, nome, nickname, idade, email)
VALUES(SEQ_JOGADOR.nextval, 'Oscar', 'tito', 18, 'email10@gmail.com');

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

-- Inserção de dados na tabela "Partida"

INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 14:00:00', 'DD/MM/YYYY HH24:MI:SS'), 10,  'S', 1, 1);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 14:30:00', 'DD/MM/YYYY HH24:MI:SS'), 15, 'N', 2, 2);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 15:00:00', 'DD/MM/YYYY HH24:MI:SS'), 20, 'S', 3, 3);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 15:30:00', 'DD/MM/YYYY HH24:MI:SS'), 30, 'S', 4, 1);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 16:00:00', 'DD/MM/YYYY HH24:MI:SS'), 10, 'N', 5, 4);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 16:30:00', 'DD/MM/YYYY HH24:MI:SS'), 20,  'S', 6, 2);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 17:00:00', 'DD/MM/YYYY HH24:MI:SS'), 15, 'N', 7, 3);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 17:30:00', 'DD/MM/YYYY HH24:MI:SS'), 25,  'S', 8, 4);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 18:00:00', 'DD/MM/YYYY HH24:MI:SS'), 15,  'N', 9, 1);
INSERT INTO Partida (id_partida, data_hora, fichas_apostadas, ganhou, id_jogador, id_jogo)
VALUES (SEQ_PARTIDA.NEXTVAL, TO_DATE('17/06/2025 18:30:00', 'DD/MM/YYYY HH24:MI:SS'), 20, 'S', 10, 2);

-- Inserção de dados na tabela "Usuario"

INSERT INTO usuario (id_usuario, login, senha, ativo)
VALUES (seq_usuario.nextval, 'user',
        'f674b02cf77556e9db68ee4bb66a9455677226bd8936bc5c170f049819c9f0e626176a826d8123bf', 'S');

-- Inserção de cargos ADMIN e USUARIO
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE ' || 'USUARIO_CARGO';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE ' || 'CARGO';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE ' || 'USUARIO';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;

BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE ' || 'seq_usuario';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
END;

BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE ' || 'seq_cargo';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
END;

CREATE TABLE USUARIO(
    ID_USUARIO NUMBER NOT NULL,
    LOGIN varchar2(512) UNIQUE NOT NULL,
    SENHA varchar2(512) NOT NULL,
    ATIVO CHAR(1) NOT NULL,
    PRIMARY KEY(ID_USUARIO),
    CONSTRAINT CHK_ATIVO CHECK (ATIVO IN('S', 'N'))
);

CREATE SEQUENCE seq_usuario
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

CREATE TABLE CARGO (
    ID_CARGO NUMBER NOT NULL,
    NOME varchar2(512) UNIQUE NOT NULL,
    PRIMARY KEY(ID_CARGO)
);

CREATE SEQUENCE seq_cargo
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

-- Apenas dois cargos: ADMIN e USUARIO
INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_ADMIN'); -- 1

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_USUARIO'); -- 2

CREATE TABLE USUARIO_CARGO (
    ID_USUARIO NUMBER NOT NULL,
    ID_CARGO NUMBER NOT NULL,
    PRIMARY KEY(ID_USUARIO, ID_CARGO),
    CONSTRAINT FK_USUARIO_CARGO_CARGO FOREIGN KEY (ID_CARGO) REFERENCES CARGO (ID_CARGO),
    CONSTRAINT FK_USUARIO_CARGO_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO (ID_USUARIO)
);

-- Usuário admin (ID 1)
INSERT INTO usuario (id_usuario, login, senha, ativo)
     VALUES (seq_usuario.nextval, 'admin', '$2a$10$X.AxPaR2usWOKRyUT2ZX/.k5sdTQNkSUly3AlSibvxImFrNb9ulAu', 'S');

-- Usuário comum (ID 2)
INSERT INTO usuario (id_usuario, login, senha, ativo)
     VALUES (seq_usuario.nextval, 'user', '$2a$10$X.AxPaR2usWOKRyUT2ZX/.k5sdTQNkSUly3AlSibvxImFrNb9ulAu', 'S');

-- ADMIN tem ambos os cargos (admin e usuario)
INSERT INTO USUARIO_CARGO (ID_USUARIO, ID_CARGO)
VALUES (1,1); -- ADMIN como ROLE_ADMIN

INSERT INTO USUARIO_CARGO (ID_USUARIO, ID_CARGO)
VALUES (1,2); -- ADMIN também como ROLE_USUARIO

-- USER apenas como ROLE_USUARIO
INSERT INTO USUARIO_CARGO (ID_USUARIO, ID_CARGO)
VALUES (2,2);

UPDATE CARGO SET NOME = 'ADMIN' WHERE NOME = 'ROLE_ADMIN';
UPDATE CARGO SET NOME = 'USUARIO' WHERE NOME = 'ROLE_USUARIO';
