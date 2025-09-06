package com.bank.statement.repository;

import com.bank.statement.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> findByAccountIdAndTxDateBetween(Long accountId, LocalDate from, LocalDate to);
}
