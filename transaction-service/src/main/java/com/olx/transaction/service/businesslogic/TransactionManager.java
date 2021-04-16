package com.olx.transaction.service.businesslogic;

import java.util.List;

import com.olx.transaction.service.models.Transaction;
import com.olx.transaction.service.models.User;

public interface TransactionManager {
	
	Transaction getTransactionById(Long id);
	
	Transaction save(Transaction transaction);
	
	Transaction closeTransaction(Transaction transaction);
	
	List<Transaction> getClosedTransactions(User user);
	
	List<Transaction> getActiveTransactions(User user);
	
	void delete(Long id);
}
