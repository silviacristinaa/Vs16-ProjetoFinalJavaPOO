package br.com.dbc.vemser.imperiodasfichas.enums;

import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum NomeJogoEnum {
    ROLETA_CLASSICA("Roleta Clássica"),
    ROLETA_DAS_CORES("Roleta das Cores"),
    CACA_NIQUEL("Caça Níquel"),
    BLACKJACK("BlackJack"),
    ROLETA("Roleta");

    private final String nome;
}