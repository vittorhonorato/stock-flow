package com.vittorhonorato.stockflow.mapper;

import com.vittorhonorato.stockflow.dto.request.ProdutoRequestDTO;
import com.vittorhonorato.stockflow.dto.response.ProdutoResponseDTO;
import com.vittorhonorato.stockflow.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequestDTO request) {
        Produto produto = new Produto();

        produto.setNome(request.nome());
        produto.setSku(request.sku());
        produto.setDescricao(request.descricao());
        produto.setPrecoDeCusto(request.precoDeCusto());
        produto.setPrecoDeVenda(request.precoDeVenda());
        produto.setQuantidadeMinima(request.quantidadeMinima());
        produto.setQuantidadeAtual(0);
        produto.setAtivo(true);

        return produto;
    }

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getDescricao(),
                produto.getPrecoDeCusto(),
                produto.getPrecoDeVenda(),
                produto.getQuantidadeAtual(),
                produto.getQuantidadeMinima(),
                produto.getAtivo(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.getFornecedor().getId(),
                produto.getFornecedor().getNome(),
                produto.getDataCriacao(),
                produto.getDataAtualizacao()
        );
    }

    public void updateEntityFromDTO(ProdutoRequestDTO request, Produto produto) {
        produto.setNome(request.nome());
        produto.setSku(request.sku());
        produto.setDescricao(request.descricao());
        produto.setPrecoDeCusto(request.precoDeCusto());
        produto.setPrecoDeVenda(request.precoDeVenda());
        produto.setQuantidadeMinima(request.quantidadeMinima());
    }
}
