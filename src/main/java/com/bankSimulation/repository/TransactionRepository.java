package com.bankSimulation.repository;

import com.bankSimulation.dto.TransactionDTO;
import com.bankSimulation.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions ORDER BY creation_date DESC LIMIT 10",nativeQuery = true)
    List<Transaction> findLastTenTransaction();

    @Query("SELECT t FROM Transaction t WHERE t.receiver.id = ?1 OR t.sender.id = ?1")
    List<Transaction> findTransactionById(Long id);

}
