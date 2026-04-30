package com.vittorhonorato.stockflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 160)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false, length = 20)
    private TipoDocumento tipoDocumento;

    @Column(name = "documento", nullable = false, unique = true, length = 20)
    private String documento;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_cadastral", nullable = false, length = 20)
    private SituacaoCadastral situacaoCadastral;

    @Column(name = "email", length = 160)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_ultima_validacao")
    private LocalDateTime dataUltimaValidacao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "fornecedor")
    private List<Produto> produtos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();

        if (this.ativo == null) {
            this.ativo = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Fornecedor() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public SituacaoCadastral getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataUltimaValidacao() {
        return dataUltimaValidacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setSituacaoCadastral(SituacaoCadastral situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setDataUltimaValidacao(LocalDateTime dataUltimaValidacao) {
        this.dataUltimaValidacao = dataUltimaValidacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}