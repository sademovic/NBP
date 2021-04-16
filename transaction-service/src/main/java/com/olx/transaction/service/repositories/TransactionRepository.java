package com.olx.transaction.service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.olx.transaction.service.models.Transaction;
import com.olx.transaction.service.models.User;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	
	List<Transaction> findByOwnerAndClosed(User owner, Boolean status);
}