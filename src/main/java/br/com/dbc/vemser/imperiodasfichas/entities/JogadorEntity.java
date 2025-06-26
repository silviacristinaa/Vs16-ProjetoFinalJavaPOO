package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogadorEntity {

    private Integer idJogador;
    private String nome;
    private String nickname;
    private int idade;
    private CarteiraEntity carteira;
    private Set<PartidaEntity> partidas;


//    public JogadorEntity() {
//        this.partidas = new HashSet<>();
//    }
}
