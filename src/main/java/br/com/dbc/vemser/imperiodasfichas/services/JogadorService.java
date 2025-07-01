package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorResponseDTO;
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

    private static final String NICKNAME_JA_UTILIZADO = "Esse nickname já está sendo usado por outro jogador.";
    private static final String EMAIL_JA_UTILIZADO = "Esse email já está sendo usado por outro jogador.";

    private final JogadorRepository jogadorRepository;
    private final CarteiraService carteiraService;
    private final PartidaService partidaService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public JogadorResponseDTO adicionarJogador(JogadorRequestDTO jogador) throws RegraDeNegocioException {

        if (buscarJogadorPorNickname(jogador.getNickname()) != null) {
            throw new RegraDeNegocioException(NICKNAME_JA_UTILIZADO);
        }

        if (buscarJogadorPorEmail(jogador.getEmail()) != null) {
            throw new RegraDeNegocioException(EMAIL_JA_UTILIZADO);
        }

        JogadorEntity jogadorEntity = objectMapper.convertValue(jogador, JogadorEntity.class);
        jogadorEntity = jogadorRepository.adicionar(jogadorEntity);
        log.info("Jogador adicionado com sucesso! ID: {}", jogadorEntity.getIdJogador());

        emailService.sendEmailCreateJogador(jogadorEntity);

        CarteiraResponseDTO carteiraCriadaResponse = carteiraService.adicionarCarteira(jogadorEntity.getIdJogador());

        CarteiraEntity carteiraCompleta = objectMapper.convertValue(carteiraCriadaResponse, CarteiraEntity.class);

        jogadorEntity.setCarteira(carteiraCompleta);

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogadorEntity, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(carteiraCompleta.getIdCarteira());
        return jogadorDTO;
    }

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
            throw new RegraDeNegocioException("Jogador com ID " + idJogador + " não encontrado.");
        }

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogador, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(jogador.getCarteira().getIdCarteira());
        return jogadorDTO;
    }

    public JogadorResponseDTO atualizarJogador(Integer id, JogadorRequestDTO jogador) throws Exception {
        JogadorResponseDTO jogadorRecuperado = buscarJogadorPorId(id);

        JogadorEntity jogadorComMesmoNickname = buscarJogadorPorNickname(jogador.getNickname());

        if (jogadorComMesmoNickname != null && !jogadorComMesmoNickname.getIdJogador().equals(jogadorRecuperado.getIdJogador())) {
            throw new RegraDeNegocioException(NICKNAME_JA_UTILIZADO);
        }
        if (buscarJogadorPorEmail(jogador.getEmail()) != null && !buscarJogadorPorEmail(jogador.getEmail()).getIdJogador().equals(jogadorRecuperado.getIdJogador())) {
            throw new RegraDeNegocioException(EMAIL_JA_UTILIZADO);
        }

        JogadorEntity jogadorAtualizar = objectMapper.convertValue(jogador, JogadorEntity.class);
        JogadorEntity jogagorAtualizado = jogadorRepository.editar(id, jogadorAtualizar);

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogagorAtualizado, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(jogadorRecuperado.getIdCarteira());

        return jogadorDTO;
    }

    public void removerJogador(Integer idJogador) throws Exception {
        JogadorResponseDTO jogadorDTO = buscarJogadorPorId(idJogador);

        partidaService.removerPartidasPorIdJogador(idJogador);
        jogadorRepository.remover(idJogador);

        JogadorEntity jogador = objectMapper.convertValue(jogadorDTO, JogadorEntity.class);
        emailService.sendEmailDeleteJogador(jogador);
    }

    private JogadorEntity buscarJogadorPorNickname(String nickname) throws RegraDeNegocioException {
        return jogadorRepository.buscarPorNickname(nickname);
    }

    private JogadorEntity buscarJogadorPorEmail(String email) throws RegraDeNegocioException {
        return jogadorRepository.buscarPorEmail(email);
    }

    public List<JogadorRankingDTO> getRanking() throws RegraDeNegocioException {
        List<JogadorRankingDTO> ranking = jogadorRepository.getRankingPorVitorias();

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setRank(i + 1);
        }

        return ranking;
    }
}
