package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.*;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.NomeJogoEnum;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JogadaService {

    private final RoletaParImparService roletaParImparService;
    private final JogadorService jogadorService;
    private final JogoService jogoService;
    private final PartidaService partidaService;
    private final CarteiraService carteiraService;
    private final ObjectMapper objectMapper;

    public JogadaResponseDTO jogarRoletaParImpar(RoletaParImparRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.ROLETA_CLASSICA);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada, jogoDTO);

        int resultadoRoleta = roletaParImparService.girarRoleta();
        boolean ganhou = roletaParImparService.verificarResultado(resultadoRoleta, jogada.getOpcao());

        atualizarFichas(jogada, ganhou, carteiraDTO);

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        JogadaResponseDTO jogadaResponseDTO = new JogadaResponseDTO();
        jogadaResponseDTO.setGanhou(ganhou);
        return jogadaResponseDTO;
    }

    private static void atualizarFichas(RoletaParImparRequestDTO jogada, boolean ganhou, CarteiraResponseDTO carteiraDTO) {
        if (ganhou) {
            carteiraDTO.setFichas(carteiraDTO.getFichas()  + (jogada.getQuantidadeFichasApostado() * 2));
        } else  {
            carteiraDTO.setFichas(carteiraDTO.getFichas() - jogada.getQuantidadeFichasApostado());
        }
    }

    private CarteiraResponseDTO validacoesFichasApostadas(RoletaParImparRequestDTO jogada, JogoResponseDTO jogoDTO) throws RegraDeNegocioException {
        if (jogada.getQuantidadeFichasApostado() < jogoDTO.getValorInicial()) {
            throw new RegraDeNegocioException("Quantidade de fichas insuficientes. Deve ser pelo menos " + jogoDTO.getValorInicial());
        }

        CarteiraResponseDTO carteiraDTO = carteiraService.buscarCarteiraPorIdJogador(jogada.getIdJogador());
        if (carteiraDTO.getFichas() < jogada.getQuantidadeFichasApostado()) {
            throw new RegraDeNegocioException("Fichas insuficientes, compre mais para poder jogar.");
        }
        return carteiraDTO;
    }
}
