package br.com.dbc.vemser.imperiodasfichas.enums;

import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
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

    public static CoresDaRoletaEnum fromTipo(int tipo) throws RegraDeNegocioException {
        for (CoresDaRoletaEnum cor : values()) {
            if (cor.getTipo().equals(tipo)) {
                return cor;
            }
        }
        throw new RegraDeNegocioException("Tipo de cor inválida: " + tipo);
    }
}
