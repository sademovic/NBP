package com.olx.transaction.service.controller;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.olx.transaction.service.businesslogic.ProductManager;
import com.olx.transaction.service.businesslogic.TransactionManager;
import com.olx.transaction.service.businesslogic.UserManager;
import com.olx.transaction.service.models.Product;
import com.olx.transaction.service.models.Transaction;
import com.olx.transaction.service.models.User;
import com.olx.transaction.service.validation.TransactionModelValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "Transaction Controller" })
public class TransactionController {
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private TransactionModelValidator transactionModelValidator;
	
	@ApiOperation(value = "Add a new transaction", notes = "This service method is used to add a new transaction to the system.")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object>  addTransaction(@RequestBody TransactionForm requestBody, Errors errors) {
		
		User owner = userManager.getUserById(requestBody.owner);
		User buyer = userManager.getUserById(requestBody.buyer);
		Product product = productManager.getProductById(requestBody.product);
		
		Transaction transaction = new Transaction();
		transaction.setOwner(owner);
		transaction.setBuyer(buyer);
		transaction.setProduct(product);
		transaction.setClosed(false);
		transaction.setCreatedAt(new Date().getTime());
		
		transactionModelValidator.validate(transaction, errors);		
		
		if(errors.hasErrors()) {		
			return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>(transactionManager.save(transaction), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Close transaction", notes = "This service method is used to close transaction.")
	@RequestMapping(value = "{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<Object> closeTransaction(@PathVariable("id") @NotBlank Long id) {
		Transaction transaction = transactionManager.getTransactionById(id);
		if(transaction == null) {
			 return new ResponseEntity<Object>(new Error("Id of transaction not exist!"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(transactionManager.closeTransaction(transaction), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Get closed transaction", notes = "This service method is used to get closed transaction to the system for user with {id}.")
	@RequestMapping(value = "{id}/closed", method = RequestMethod.GET)
    public ResponseEntity<Object> getClosedTransactions(@PathVariable("id") @NotBlank Long id) {
		User user = userManager.getUserById(id);
		if(user == null) {
			 return new ResponseEntity<Object>(new Error("Id of user not exist!"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(transactionManager.getClosedTransactions(user), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Get active transaction", notes = "This service method is used to get active transaction to the system for user with {id}.")
	@RequestMapping(value = "{id}/active", method = RequestMethod.GET)
    public ResponseEntity<Object> getActiveTransactions(@PathVariable("id") @NotBlank Long id) {
		User user = userManager.getUserById(id);
		if(user == null) {
			 return new ResponseEntity<Object>(new Error("Id of user not exist!"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(transactionManager.getActiveTransactions(user), HttpStatus.OK);
    }

	public static class TransactionForm {
		public Long owner;
		public Long buyer;
		public Long product;
	}
}
