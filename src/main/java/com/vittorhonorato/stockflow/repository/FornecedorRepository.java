package com.vittorhonorato.stockflow.repository;

import com.vittorhonorato.stockflow.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Optional<Fornecedor> findByDocumento(String documento);
    boolean existsByDocumento(String documento);
}
