package br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterDTO {

    @NotNull
    private String login;

    @NotNull
    private String senha;

    @NotNull
    private String confirmarSenha;

}
