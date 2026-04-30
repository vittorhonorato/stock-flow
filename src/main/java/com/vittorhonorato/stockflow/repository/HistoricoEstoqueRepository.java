package com.vittorhonorato.stockflow.repository;

import com.vittorhonorato.stockflow.entity.HistoricoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoEstoqueRepository extends JpaRepository<HistoricoEstoque, Long> {
}
