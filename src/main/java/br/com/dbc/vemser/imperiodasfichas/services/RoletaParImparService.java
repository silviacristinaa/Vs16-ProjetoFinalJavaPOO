package br.com.dbc.vemser.imperiodasfichas.services;

import org.springframework.stereotype.Service;

@Service
public class RoletaParImparService {

    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        boolean isPar = resultado % 2 == 0;
        if (isPar && opcaoEscolhida == 0) {
            return true;
        } else return !isPar && opcaoEscolhida == 1;
    }

    public int girarRoleta() {
        return (int) (Math.random() * 36);
    }
}
