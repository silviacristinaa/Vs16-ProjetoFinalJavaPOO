package br.com.dbc.vemser.imperiodasfichas.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CacaNiquelService {

    public static final List<String> SIMBOLOS = List.of("🍒", "⭐", "💎");

    public boolean verificarResultado(List<String> resultado) {
        return resultado.stream().allMatch(s -> s.equals(resultado.get(0)));
    }

    public List<String> girarRolos() {
        Random random = new Random();

        List<String> resultado = new ArrayList<>();

        for (int i = 0; i < SIMBOLOS.size(); i++) {
            String simboloSorteado = SIMBOLOS.get(random.nextInt(SIMBOLOS.size()));
            resultado.add(simboloSorteado);
        }

        return resultado;
    }
}
