package com.vittorhonorato.stockflow.service.impl;

import com.vittorhonorato.stockflow.dto.request.ProdutoRequestDTO;
import com.vittorhonorato.stockflow.dto.response.ProdutoResponseDTO;
import com.vittorhonorato.stockflow.entity.Categoria;
import com.vittorhonorato.stockflow.entity.Fornecedor;
import com.vittorhonorato.stockflow.entity.Produto;
import com.vittorhonorato.stockflow.exception.categorias.CategoriaNaoEncontradaException;
import com.vittorhonorato.stockflow.exception.fornecedores.FornecedorNaoEncontradoException;
import com.vittorhonorato.stockflow.exception.produtos.ProdutoCategoriaInativaException;
import com.vittorhonorato.stockflow.exception.produtos.ProdutoFornecedorInativoException;
import com.vittorhonorato.stockflow.exception.produtos.ProdutoNaoEncontradoException;
import com.vittorhonorato.stockflow.exception.produtos.ProdutoSkuDuplicadoException;
import com.vittorhonorato.stockflow.mapper.ProdutoMapper;
import com.vittorhonorato.stockflow.repository.CategoriaRepository;
import com.vittorhonorato.stockflow.repository.FornecedorRepository;
import com.vittorhonorato.stockflow.repository.ProdutoRepository;
import com.vittorhonorato.stockflow.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoServiceImpl(CategoriaRepository categoriaRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }


    @Override
    public ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO) {
        String skuNormalizado = normalizarTexto(requestDTO.sku());
        String nomeNormalizado = normalizarTexto(requestDTO.nome());

        if (produtoRepository.existsBySkuIgnoreCase(skuNormalizado)) {
            throw new ProdutoSkuDuplicadoException("Já existe um produto cadastrado com esse SKU");
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com o id: " + requestDTO.categoriaId()));

        Fornecedor fornecedor = fornecedorRepository.findById(requestDTO.fornecedorId())
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o id: " + requestDTO.fornecedorId()));

        if (!categoria.getAtiva()) {
            throw new ProdutoCategoriaInativaException("Não é possível cadastrar produto em categoria inativa");
        }

        if (!fornecedor.getAtivo()) {
            throw new ProdutoFornecedorInativoException("Não é possível cadastrar produto com fornecedor inativo");
        }

        Produto produto = produtoMapper.toEntity(requestDTO);
        produto.setSku(skuNormalizado);
        produto.setNome(nomeNormalizado);
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);

        Produto produtoSave = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoSave);
    }

    @Override
    public Page<ProdutoResponseDTO> listAll(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(produtoMapper::toResponseDTO);
    }

    @Override
    public ProdutoResponseDTO produtoById(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toResponseDTO)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o id: " + id));
    }

    @Override
    public ProdutoResponseDTO produtoBySku(String sku) {
        String skuNormalizado = normalizarTexto(sku);

        return produtoRepository.findBySkuIgnoreCase(skuNormalizado)
                .map(produtoMapper::toResponseDTO)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o SKU: " + skuNormalizado));
    }

    @Override
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO) {
        Produto produto = getProduto(id);

        String nomeNormalizado = normalizarTexto(requestDTO.nome());
        String skuNormalizado = normalizarTexto(requestDTO.sku());

        boolean skuFoiAlterado = !produto.getSku().equalsIgnoreCase(skuNormalizado);

        if (skuFoiAlterado && produtoRepository.existsBySkuIgnoreCase(skuNormalizado)) {
            throw new ProdutoSkuDuplicadoException("Já existe um produto cadastrado com esse SKU");
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com o id: " + requestDTO.categoriaId()));

        Fornecedor fornecedor = fornecedorRepository.findById(requestDTO.fornecedorId())
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o id: " + requestDTO.fornecedorId()));


        produtoMapper.updateEntityFromDTO(requestDTO, produto);
        produto.setNome(nomeNormalizado);
        produto.setSku(skuNormalizado);
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);

        Produto produtoSave = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoSave);
    }

    @Override
    public void desativar(Long id) {

        Produto produto = getProduto(id);

        produto.setAtivo(false);

        produtoRepository.save(produto);
    }

    private Produto getProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o id: " + id));
        return produto;
    }

    private String normalizarTexto(String texto) {
        return texto.trim();
    }


}
