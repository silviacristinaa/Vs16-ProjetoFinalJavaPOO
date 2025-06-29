package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.*;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JogadaService {

    private final RoletaParImparService roletaParImparService;
    private final RoletaCoresService roletaCoresService;
    private final CacaNiquelService cacaNiquelService;
    private final JogadorService jogadorService;
    private final JogoService jogoService;
    private final PartidaService partidaService;
    private final CarteiraService carteiraService;
    private final ObjectMapper objectMapper;

    public JogadaResponseDTO jogarRoletaParImpar(RoletaParImparRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.ROLETA_CLASSICA);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada.getQuantidadeFichasApostado(), jogoDTO, jogada.getIdJogador());

        int resultadoRoleta = roletaParImparService.girarRoleta();
        boolean ganhou = roletaParImparService.verificarResultado(resultadoRoleta, jogada.getOpcao());

        atualizarFichas(jogada.getQuantidadeFichasApostado(), ganhou, carteiraDTO, 2);

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        JogadaResponseDTO jogadaResponseDTO = new JogadaResponseDTO();
        jogadaResponseDTO.setGanhou(ganhou);
        return jogadaResponseDTO;
    }

    public JogadaResponseDTO jogarRoletaCores(RoletaCoresRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.ROLETA_DAS_CORES);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada.getQuantidadeFichasApostado(), jogoDTO, jogada.getIdJogador());

        int resultadoRoleta = roletaCoresService.girarRoleta();
        boolean ganhou = roletaCoresService.verificarResultado(resultadoRoleta, jogada.getOpcao().getTipo());

        atualizarFichas(jogada.getQuantidadeFichasApostado(), ganhou, carteiraDTO, 4);

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        JogadaResponseDTO jogadaResponseDTO = new JogadaResponseDTO();
        jogadaResponseDTO.setGanhou(ganhou);
        return jogadaResponseDTO;
    }

    public JogadaResponseDTO jogarCacaNiquel(CacaNiquelRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.CACA_NIQUEL);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada.getQuantidadeFichasApostado(), jogoDTO, jogada.getIdJogador());

        List<String> resultadoCacaNiquel = cacaNiquelService.girarRolos();
        boolean ganhou = cacaNiquelService.verificarResultado(resultadoCacaNiquel);

        atualizarFichas(jogada.getQuantidadeFichasApostado(), ganhou, carteiraDTO, 2);

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        JogadaResponseDTO jogadaResponseDTO = new JogadaResponseDTO();
        jogadaResponseDTO.setGanhou(ganhou);
        return jogadaResponseDTO;
    }

    private static void atualizarFichas(int quantidadeFichasApostadas, boolean ganhou, CarteiraResponseDTO carteiraDTO, int multiplicadorFichas) {
        if (ganhou) {
            carteiraDTO.setFichas(carteiraDTO.getFichas()  + (quantidadeFichasApostadas * multiplicadorFichas));
        } else  {
            carteiraDTO.setFichas(carteiraDTO.getFichas() - quantidadeFichasApostadas);
        }
    }

    private CarteiraResponseDTO validacoesFichasApostadas(int quantidadeFichasApostadas, JogoResponseDTO jogoDTO, int idJogador) throws RegraDeNegocioException {
        if (quantidadeFichasApostadas < jogoDTO.getValorInicial()) {
            throw new RegraDeNegocioException("Quantidade de fichas insuficientes. Deve ser pelo menos " + jogoDTO.getValorInicial());
        }

        CarteiraResponseDTO carteiraDTO = carteiraService.buscarCarteiraPorIdJogador(idJogador);
        if (carteiraDTO.getFichas() < quantidadeFichasApostadas) {
            throw new RegraDeNegocioException("Fichas insuficientes, compre mais para poder jogar.");
        }
        return carteiraDTO;
    }
}
