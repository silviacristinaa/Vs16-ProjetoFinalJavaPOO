package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioPartidasJogadorDTO {
    private Long idJogador;
    private String nomeJogador;
    private String email;
    private Long idPartida;
    private LocalDateTime dataPartida;
    private String nomeJogo;
    private Integer fichasGanhas;
    private BigDecimal valorPremio;
}