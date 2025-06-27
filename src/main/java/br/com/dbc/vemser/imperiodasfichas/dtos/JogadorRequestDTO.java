package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class JogadorRequestDTO {
    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Size(max = 100)
    private String nickname;
    @NotNull
    @Min(value = 18)
    @Max(value = 90)
    private Integer idade;
}
