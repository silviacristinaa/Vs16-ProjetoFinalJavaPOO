package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class JogadorRequestDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços.")
    @Schema(description = "Nome do jogador", required = true, example = "Fulano de Tal")
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Apelido único do jogador", required = true, example = "fulano")
    private String nickname;

    @NotNull
    @NotEmpty
    @Min(value = 18)
    @Max(value = 90)
    @Schema(description = "Idade do jogador (mínimo 18 anos)", required = true, example = "25")
    private Integer idade;

    @NotBlank
    @NotNull
    @NotEmpty
    @Size(max = 255)
    @Email
    @Schema(description = "Email do jogador", required = true, example = "email@gmail.com")
    private String email;
}
