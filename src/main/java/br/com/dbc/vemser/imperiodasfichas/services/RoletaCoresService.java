package br.com.dbc.vemser.imperiodasfichas.services;

import org.springframework.stereotype.Service;

@Service
public class RoletaCoresService {

    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        return resultado == opcaoEscolhida;
    }

    public int girarRoleta() {
        return (int) (Math.random() * 4);
    }
}
