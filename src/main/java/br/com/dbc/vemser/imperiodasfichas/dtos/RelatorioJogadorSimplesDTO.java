package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioJogadorSimplesDTO {
    private Integer idJogador;
    private String nome;
    private String email;
    private String nickname;
    private Integer idade;
    private Integer fichas;
    private Double dinheiro;

}