package com.vittorhonorato.stockflow.repository;

import com.vittorhonorato.stockflow.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsBySkuIgnoreCase(String sku);
    Optional<Produto> findBySkuIgnoreCase(String sku);
}
