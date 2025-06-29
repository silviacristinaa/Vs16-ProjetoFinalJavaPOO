package br.com.dbc.vemser.imperiodasfichas.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoresDaRoletaEnum {
    VERMELHO(1),
    AZUL(2),
    AMARELO(3),
    VERDE(4);

    private Integer tipo;
}
