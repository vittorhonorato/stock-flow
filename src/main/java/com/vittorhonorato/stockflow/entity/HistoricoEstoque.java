package com.vittorhonorato.stockflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_historico_estoque")
public class HistoricoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false, length = 30)
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "motivo", length = 255)
    private String motivo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "data_movimentacao", nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao;

    @PrePersist
    public void prePersist() {
        this.dataMovimentacao = LocalDateTime.now();
    }

    public HistoricoEstoque() {
    }

    public HistoricoEstoque(Long id, TipoMovimentacao tipoMovimentacao, Integer quantidade, String motivo, Produto produto) {
        this.id = id;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public Produto getProduto() {
        return produto;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}