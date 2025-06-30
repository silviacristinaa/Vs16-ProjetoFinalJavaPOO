package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.*;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogada.*;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.enums.CoresDaRoletaEnum;
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

    public RoletaParImparResponseDTO jogarRoletaParImpar(RoletaParImparRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.ROLETA_CLASSICA);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada.getQuantidadeFichasApostado(), jogoDTO, jogada.getIdJogador());
        carteiraDTO.setFichas(carteiraDTO.getFichas() - jogada.getQuantidadeFichasApostado());

        int resultadoRoleta = roletaParImparService.girarRoleta();
        boolean ganhou = roletaParImparService.verificarResultado(resultadoRoleta, jogada.getOpcao());

        int fichasRecebidas = 0;
        if (ganhou) {
            fichasRecebidas = atualizarFichas(jogada.getQuantidadeFichasApostado(), carteiraDTO, 2);
        }

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        RoletaParImparResponseDTO response = new RoletaParImparResponseDTO();
        response.setGanhou(ganhou);
        response.setFichasRecebidas(fichasRecebidas);
        response.setNumeroSorteado(resultadoRoleta);
        response.setSaldoFinalDeFichas(carteira.getFichas());
        return response;
    }

    public RoletaCoresResponseDTO jogarRoletaCores(RoletaCoresRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.ROLETA_DAS_CORES);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada.getQuantidadeFichasApostado(), jogoDTO, jogada.getIdJogador());
        carteiraDTO.setFichas(carteiraDTO.getFichas() - jogada.getQuantidadeFichasApostado());

        int resultadoRoleta = roletaCoresService.girarRoleta();
        boolean ganhou = roletaCoresService.verificarResultado(resultadoRoleta, jogada.getOpcao().getTipo());

        int fichasRecebidas = 0;
        if (ganhou) {
            fichasRecebidas = atualizarFichas(jogada.getQuantidadeFichasApostado(), carteiraDTO, 4);
        }

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        RoletaCoresResponseDTO response = new RoletaCoresResponseDTO();
        response.setGanhou(ganhou);
        response.setFichasRecebidas(fichasRecebidas);
        response.setCorSorteada(CoresDaRoletaEnum.fromTipo(resultadoRoleta));
        response.setSaldoFinalDeFichas(carteira.getFichas());
        return response;
    }

    public CacaNiquelResponseDTO jogarCacaNiquel(CacaNiquelRequestDTO jogada) throws Exception {
        JogadorResponseDTO jogadorDTO = jogadorService.buscarJogadorPorId(jogada.getIdJogador());
        JogoResponseDTO jogoDTO = jogoService.buscarPorNomeJogo(NomeJogoEnum.CACA_NIQUEL);

        CarteiraResponseDTO carteiraDTO = validacoesFichasApostadas(jogada.getQuantidadeFichasApostado(), jogoDTO, jogada.getIdJogador());
        carteiraDTO.setFichas(carteiraDTO.getFichas() - jogada.getQuantidadeFichasApostado());

        List<String> resultadoCacaNiquel = cacaNiquelService.girarRolos();
        boolean ganhou = cacaNiquelService.verificarResultado(resultadoCacaNiquel);

        int fichasRecebidas = 0;
        if (ganhou) {
            fichasRecebidas = atualizarFichas(jogada.getQuantidadeFichasApostado(), carteiraDTO, 2);
        }

        CarteiraEntity carteira = objectMapper.convertValue(carteiraDTO, CarteiraEntity.class);
        carteiraService.atualizarCarteira(carteira);

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO(LocalDateTime.now(),
                jogada.getQuantidadeFichasApostado(), ganhou, jogoDTO.getIdJogo(), jogadorDTO.getIdJogador());
        PartidaResponseDTO partida = partidaService.adicionarPartida(partidaRequestDTO);

        CacaNiquelResponseDTO respose = new CacaNiquelResponseDTO();
        respose.setGanhou(ganhou);
        respose.setFichasRecebidas(fichasRecebidas);
        respose.setSimbolosSorteados(resultadoCacaNiquel);
        respose.setSaldoFinalDeFichas(carteira.getFichas());
        return respose;
    }

    private static int atualizarFichas(int quantidadeFichasApostadas, CarteiraResponseDTO carteiraDTO, int multiplicadorFichas) {
        int fichasRecebidas = quantidadeFichasApostadas * multiplicadorFichas;
        carteiraDTO.setFichas(carteiraDTO.getFichas()  + fichasRecebidas);
        return fichasRecebidas;
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
