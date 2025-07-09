package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioJogadorSimplesDTO;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JogadorService {

    private static final String NICKNAME_JA_UTILIZADO = "Esse nickname já está sendo usado por outro jogador.";
    private static final String EMAIL_JA_UTILIZADO = "Esse email já está sendo usado por outro jogador.";

    private final JogadorRepository jogadorRepository;
    private final CarteiraService carteiraService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public JogadorResponseDTO create(JogadorRequestDTO jogador) throws RegraDeNegocioException {
        if (jogadorRepository.findByNickname(jogador.getNickname()).isPresent()) {
            throw new RegraDeNegocioException(NICKNAME_JA_UTILIZADO);
        }

        if (jogadorRepository.findByEmail(jogador.getEmail()).isPresent()) {
            throw new RegraDeNegocioException(EMAIL_JA_UTILIZADO);
        }

        JogadorEntity jogadorEntity = objectMapper.convertValue(jogador, JogadorEntity.class);
        jogadorEntity = jogadorRepository.save(jogadorEntity);
        log.info("Jogador criado com sucesso! ID: {}", jogadorEntity.getIdJogador());

        emailService.sendEmailCreateJogador(jogadorEntity);

        CarteiraEntity carteiraCompleta = carteiraService.adicionarCarteira(jogadorEntity);
        jogadorEntity.setCarteira(carteiraCompleta);

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogadorEntity, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(carteiraCompleta.getIdCarteira());
        return jogadorDTO;
    }

    public List<JogadorResponseDTO> list() throws RegraDeNegocioException {
        return jogadorRepository.findAll().stream()
                .map(jogador -> {
                    JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogador, JogadorResponseDTO.class);
                    jogadorDTO.setIdCarteira(jogador.getCarteira().getIdCarteira());
                    return jogadorDTO;
                })
                .collect(Collectors.toList());
    }

    public Page<JogadorResponseDTO> listPaginado(Integer pagina, Integer tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);

        return jogadorRepository.findAll(pageable)
                .map(jogador -> {
                    JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogador, JogadorResponseDTO.class);
                    jogadorDTO.setIdCarteira(jogador.getCarteira().getIdCarteira());
                    return jogadorDTO;
                });
    }

    public JogadorResponseDTO findById(Integer idJogador) throws RegraDeNegocioException {
        JogadorEntity jogador = jogadorRepository.findById(idJogador)
                .orElseThrow(() -> new RegraDeNegocioException("Jogador com ID " + idJogador + " não encontrado."));

        JogadorResponseDTO jogadorDTO = objectMapper.convertValue(jogador, JogadorResponseDTO.class);
        jogadorDTO.setIdCarteira(jogador.getCarteira().getIdCarteira());
        return jogadorDTO;
    }

    public JogadorResponseDTO update(Integer idJogador, JogadorRequestDTO jogadorAtualizar) throws RegraDeNegocioException {
        JogadorEntity jogadorExistente = jogadorRepository.findById(idJogador)
                .orElseThrow(() -> new RegraDeNegocioException("Jogador não encontrado"));

        Optional<JogadorEntity> jogadorComMesmoNickname = jogadorRepository.findByNickname(jogadorAtualizar.getNickname());
        if (jogadorComMesmoNickname.isPresent() && !jogadorComMesmoNickname.get().getIdJogador().equals(idJogador)) {
            throw new RegraDeNegocioException(NICKNAME_JA_UTILIZADO);
        }

        Optional<JogadorEntity> jogadorComMesmoEmail = jogadorRepository.findByEmail(jogadorAtualizar.getEmail());
        if (jogadorComMesmoEmail.isPresent() && !jogadorComMesmoEmail.get().getIdJogador().equals(idJogador)) {
            throw new RegraDeNegocioException(EMAIL_JA_UTILIZADO);
        }

        jogadorExistente.setNome(jogadorAtualizar.getNome());
        jogadorExistente.setNickname(jogadorAtualizar.getNickname());
        jogadorExistente.setIdade(jogadorAtualizar.getIdade());
        jogadorExistente.setEmail(jogadorAtualizar.getEmail());

        JogadorEntity jogadorAtualizado = jogadorRepository.save(jogadorExistente);
        JogadorResponseDTO jogadorResponseDTO = objectMapper.convertValue(jogadorAtualizado, JogadorResponseDTO.class);
        jogadorResponseDTO.setIdCarteira(jogadorExistente.getCarteira().getIdCarteira());
        return jogadorResponseDTO;
    }

    public void delete(Integer idJogador) throws RegraDeNegocioException {
        JogadorEntity jogador = jogadorRepository.findById(idJogador)
                .orElseThrow(() -> new RegraDeNegocioException("Jogador não encontrado"));

        jogadorRepository.delete(jogador);
        emailService.sendEmailDeleteJogador(jogador);
    }



public List<JogadorRankingDTO> getRanking(Integer idJogador) throws RegraDeNegocioException {
    List<JogadorRankingDTO> ranking = jogadorRepository.getRankingPorVitorias();

    for (int i = 0; i < ranking.size(); i++) {
        ranking.get(i).setRank(i + 1);
    }

    if (idJogador != null) {
        return ranking.stream().filter(j -> j.getIdJogador().equals(idJogador)).toList();
    }

    return ranking;
}



    public Page<JogadorRankingDTO> getRankingPaginado(Integer idJogador, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        List<JogadorRankingDTO> ranking = jogadorRepository.getRankingPorVitorias();

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setRank(i + 1);
        }

        if (idJogador != null) {
            List<JogadorRankingDTO> filtrado = ranking.stream()
                    .filter(j -> j.getIdJogador().equals(idJogador))
                    .toList();

            return new PageImpl<>(filtrado, PageRequest.of(0, filtrado.size() == 0 ? 1 : filtrado.size()), filtrado.size());
        }

        if (pagina == null || tamanho == null) {
            return new PageImpl<>(ranking, PageRequest.of(0, ranking.size() == 0 ? 1 : ranking.size()), ranking.size());
        }

        Pageable pageable = PageRequest.of(pagina, tamanho);
        int inicio = (int) pageable.getOffset();
        int fim = Math.min(inicio + pageable.getPageSize(), ranking.size());

        if (inicio >= ranking.size()) {
            return Page.empty(pageable);
        }

        List<JogadorRankingDTO> pageContent = ranking.subList(inicio, fim);

        return new PageImpl<>(pageContent, pageable, ranking.size());
    }



    public List<RelatorioJogadorSimplesDTO> gerarRelatorioSimples() throws RegraDeNegocioException {
        log.info("Gerando relatório simplificado de jogadores...");
        try {
            List<RelatorioJogadorSimplesDTO> relatorio = jogadorRepository.relatorioJogadoresSimples();
            log.info("Relatório gerado com {} registros", relatorio.size());
            return relatorio;
        } catch (Exception e) {
            log.error("Erro ao gerar relatório: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao gerar relatório de jogadores");
        }
    }

    public Page<RelatorioJogadorSimplesDTO> gerarRelatorioSimplesPaginado(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        log.info("Gerando relatório simplificado paginado - página {} com {} registros", pagina, tamanho);
        try {
            Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("nome").ascending());
            Page<RelatorioJogadorSimplesDTO> relatorio = jogadorRepository.relatorioJogadoresSimplesPaginado(pageable);
            log.info("Relatório paginado gerado: {} itens de {} total",
                    relatorio.getNumberOfElements(), relatorio.getTotalElements());
            return relatorio;
        } catch (Exception e) {
            log.error("Erro ao gerar relatório paginado: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao gerar relatório paginado de jogadores");
        }


    }
}
