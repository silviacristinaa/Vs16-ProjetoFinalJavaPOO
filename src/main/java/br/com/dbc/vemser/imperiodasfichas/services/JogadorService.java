package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogadorRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogadorResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.JogadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final CarteiraService carteiraService;
    private final ObjectMapper objectMapper;

    public JogadorResponseDTO adicionarJogador(JogadorRequestDTO jogador) throws RegraDeNegocioException {
//        if (jogadorExiste(nickname)) {
//            throw new DadosDuplicadosException("Jogador com o nickname " + nickname + " já existe.");
//        }

        JogadorEntity jogadorEntity = objectMapper.convertValue(jogador, JogadorEntity.class);
        jogadorEntity = jogadorRepository.adicionar(jogadorEntity);
        log.info("Jogador adicionado com sucesso! ID: {}", jogadorEntity.getIdJogador());

        CarteiraResponseDTO carteiraCriadaResponse = carteiraService.adicionarCarteira(jogadorEntity.getIdJogador());

        CarteiraEntity carteiraCompleta = objectMapper.convertValue(carteiraCriadaResponse, CarteiraEntity.class);

        jogadorEntity.setCarteira(carteiraCompleta);

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogadorEntity, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(carteiraCompleta.getIdCarteira());
        return jogadorDTO;
    }

//    public boolean jogadorExiste(String nicknameJogador) throws RegraDeNegocioException {
//        return jogadorRepository.buscar(nicknameJogador) != null;
//    }

    public List<JogadorResponseDTO> listar() throws RegraDeNegocioException {
        List<JogadorEntity> jogadores = jogadorRepository.listar();

        List<JogadorResponseDTO> jogadorDTOS = jogadores.stream()
                .map(jogador -> {
                    JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogador, JogadorResponseDTO.class);
                    jogadorDTO.setIdCarteira(jogador.getCarteira().getIdCarteira());

                    return jogadorDTO;
                })
                .collect(Collectors.toList());

        return jogadorDTOS;
    }

    public JogadorResponseDTO buscarJogadorPorId(Integer idJogador) throws Exception {
        JogadorEntity jogador = jogadorRepository.buscarPorId(idJogador);
        if (jogador == null) {
            throw new RegraDeNegocioException("Jogador não encontrado!");
        }

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogador, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(jogador.getCarteira().getIdCarteira());
        return jogadorDTO;
    }

    public JogadorResponseDTO atualizarJogador(Integer id, JogadorRequestDTO jogador) throws Exception {
        JogadorResponseDTO jogadorRecuperado = buscarJogadorPorId(id);

        JogadorEntity jogadorAtualizar = objectMapper.convertValue(jogador, JogadorEntity.class);
        JogadorEntity jogagorAtualizado = jogadorRepository.editar(id, jogadorAtualizar);

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogagorAtualizado, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(jogadorRecuperado.getIdCarteira());

        return jogadorDTO;
    }

    public void removerJogador(Integer idJogador) throws Exception {
        buscarJogadorPorId(idJogador);

//        if (daoGenericoPartida != null) {
//            Partida partida = daoGenericoPartida.buscar(jogador.getIdJogador().toString());
//            if (partida != null) {
//                partida.setJogador(jogador);
//                daoGenericoPartida.remover(partida);
//            }
//        }
        jogadorRepository.remover(idJogador);
    }
}
