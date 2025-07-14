package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidasJogadorDTO {
    private Integer idJogador;
    private String nome;
    private String nickname;
    private Integer idPartida;
    private LocalDateTime dataHora;
    private Integer fichasApostadas;
    private String ganhou; // "S" ou "N"
    private String nomeJogo;
}