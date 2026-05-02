package com.vittorhonorato.stockflow.service.impl;

import com.vittorhonorato.stockflow.dto.request.AjusteEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.request.MovimentacaoEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.response.HistoricoEstoqueResponseDTO;
import com.vittorhonorato.stockflow.entity.HistoricoEstoque;
import com.vittorhonorato.stockflow.entity.Produto;
import com.vittorhonorato.stockflow.entity.TipoMovimentacao;
import com.vittorhonorato.stockflow.exception.estoques.EstoqueInsuficienteException;
import com.vittorhonorato.stockflow.exception.estoques.ProdutoInativoParaMovimentacaoException;
import com.vittorhonorato.stockflow.exception.produtos.ProdutoNaoEncontradoException;
import com.vittorhonorato.stockflow.mapper.HistoricoEstoqueMapper;
import com.vittorhonorato.stockflow.repository.HistoricoEstoqueRepository;
import com.vittorhonorato.stockflow.repository.ProdutoRepository;
import com.vittorhonorato.stockflow.service.EstoqueService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final HistoricoEstoqueRepository historicoEstoqueRepository;
    private final HistoricoEstoqueMapper historicoEstoqueMapper;

    public EstoqueServiceImpl(ProdutoRepository produtoRepository, HistoricoEstoqueRepository historicoEstoqueRepository, HistoricoEstoqueMapper historicoEstoqueMapper) {
        this.produtoRepository = produtoRepository;
        this.historicoEstoqueRepository = historicoEstoqueRepository;
        this.historicoEstoqueMapper = historicoEstoqueMapper;
    }

    @Override
    @Transactional
    public HistoricoEstoqueResponseDTO entrada(MovimentacaoEstoqueRequestDTO request) {
        Produto produtoAtivo = buscarProdutoAtivo(request.produtoId());

        produtoAtivo.setQuantidadeAtual(produtoAtivo.getQuantidadeAtual() + request.quantidade());
        produtoRepository.save(produtoAtivo);

        HistoricoEstoque historico = criarHistorico(
                produtoAtivo,
                TipoMovimentacao.ENTRADA,
                request.quantidade(),
                request.motivo()
        );

        HistoricoEstoque historicoSalvo = historicoEstoqueRepository.save(historico);

        return historicoEstoqueMapper.toResponseDTO(historicoSalvo);
    }

    @Override
    @Transactional
    public HistoricoEstoqueResponseDTO saida(MovimentacaoEstoqueRequestDTO request) {
        Produto produtoAtivo = buscarProdutoAtivo(request.produtoId());

        if (produtoAtivo.getQuantidadeAtual() < request.quantidade()) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente. Quantidade atual: "
                            + produtoAtivo.getQuantidadeAtual()
                            + ", quantidade solicitada: "
                            + request.quantidade()
            );
        }

        produtoAtivo.setQuantidadeAtual(produtoAtivo.getQuantidadeAtual() - request.quantidade());
        produtoRepository.save(produtoAtivo);

        HistoricoEstoque historico = criarHistorico(
                produtoAtivo,
                TipoMovimentacao.SAIDA,
                request.quantidade(),
                request.motivo()
        );

        HistoricoEstoque historicoSalvo = historicoEstoqueRepository.save(historico);

        return historicoEstoqueMapper.toResponseDTO(historicoSalvo);
    }

    @Override
    @Transactional
    public HistoricoEstoqueResponseDTO ajuste(AjusteEstoqueRequestDTO request) {
        Produto produtoAtivo = buscarProdutoAtivo(request.produtoId());

        int quantidadeAntiga = produtoAtivo.getQuantidadeAtual();
        int novaQuantidade = request.novaQuantidade();

        int quantidadeAjustada = novaQuantidade - quantidadeAntiga;

        produtoAtivo.setQuantidadeAtual(novaQuantidade);
        produtoRepository.save(produtoAtivo);

        HistoricoEstoque historico = criarHistorico(
                produtoAtivo,
                TipoMovimentacao.AJUSTE,
                Math.abs(quantidadeAjustada),
                request.motivo()
        );

        HistoricoEstoque historicoSalvo = historicoEstoqueRepository.save(historico);

        return historicoEstoqueMapper.toResponseDTO(historicoSalvo);
    }

    @Override
    public Page<HistoricoEstoqueResponseDTO> listaHistoricoPorProduto(Long produtoId, Pageable pageable) {

        if (!produtoRepository.existsById(produtoId)) {
            throw new ProdutoNaoEncontradoException(
                    "Produto não encontrado com o id: " + produtoId
            );
        }

        return historicoEstoqueRepository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId, pageable)
                .map(historicoEstoqueMapper::toResponseDTO);
    }

    private Produto buscarProdutoAtivo(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado na base com o id: " + id));

        if (!produto.getAtivo()) {
            throw new ProdutoInativoParaMovimentacaoException(
                    "Não é possível movimentar estoque de um produto inativo"
            );
        }

        return produto;
    }

    private HistoricoEstoque criarHistorico(
            Produto produto,
            TipoMovimentacao tipoMovimentacao,
            Integer quantidade,
            String motivo
    ) {

        HistoricoEstoque historico = new HistoricoEstoque();
        historico.setProduto(produto);
        historico.setTipoMovimentacao(tipoMovimentacao);
        historico.setQuantidade(quantidade);
        historico.setMotivo(motivo);

        return historico;
    }
}
