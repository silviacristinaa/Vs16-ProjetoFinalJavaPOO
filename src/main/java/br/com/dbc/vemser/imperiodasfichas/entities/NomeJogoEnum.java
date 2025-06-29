package br.com.dbc.vemser.imperiodasfichas.entities;

import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NomeJogoEnum {
    ROLETA_CLASSICA("Roleta Clássica"),
    ROLETA_DAS_CORES("Roleta das Cores"),
    CACA_NIQUEL("Caça Níquel"),
    BLACKJACK("BlackJack");

    private String nome;

    public static NomeJogoEnum fromNome(String nome) throws RegraDeNegocioException {
        for (NomeJogoEnum value : values()) {
            if (value.nome.equalsIgnoreCase(nome)) {
                return value;
            }
        }
        throw new RegraDeNegocioException("Nome de jogo inválido: " + nome);
    }
}