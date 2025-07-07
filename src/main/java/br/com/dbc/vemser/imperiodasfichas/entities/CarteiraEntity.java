package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarteiraEntity {
    private Integer idCarteira;
    private int fichas;
    private double dinheiro;
    private Integer idJogador;
}