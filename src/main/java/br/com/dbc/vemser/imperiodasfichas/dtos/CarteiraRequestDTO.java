package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CarteiraRequestDTO {
    @PositiveOrZero()
    private int fichas;

    @PositiveOrZero()
    private double dinheiro;
}
