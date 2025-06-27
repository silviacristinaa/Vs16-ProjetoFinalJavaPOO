package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

@Data
public class JogadorResponseDTO extends JogadorRequestDTO {
    private Integer idJogador;
    private Integer idCarteira;
}
