package com.olx.items.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.olx.items.service.businesslogic.CategoryManager;
import com.olx.items.service.businesslogic.ProductManager;
import com.olx.items.service.businesslogic.UserManager;
import com.olx.items.service.models.Category;
import com.olx.items.service.models.Product;
import com.olx.items.service.models.User;
import com.olx.items.service.validation.ProductModelValidator;

@RestController
@RequestMapping("products/")
public class ProductController {
	
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductModelValidator productModelValidator;
	@Autowired
	private UserManager userManager;
	@Autowired
	private CategoryManager categoryManager;
	
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Product product,
    								  @RequestParam(required = true) Long userId,
    								  @RequestParam(required = true) Long categoryId, 
    								  Errors errors) {		
		
		User user = userManager.getUserById(userId);
		product.setUser(user);
		
		Category category = categoryManager.getCategoryById(categoryId);
		product.setCategory(category);
		
		productModelValidator.validate(product, errors);
		
		if (errors.hasErrors()) {
		    return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(productManager.save(product), HttpStatus.OK);
    }
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product product, Errors errors) {
		
		productModelValidator.validateUpdate(product, errors);
		if (errors.hasErrors()) {
		    return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		
		Product updatedProduct = productManager.update(product, id);
		return new ResponseEntity<Object>(updatedProduct, HttpStatus.OK);
    }
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
    public List<Product> search(@RequestParam(required = true) String name) {
		return productManager.search(name);
    }
	
	@RequestMapping(value = "my/active", method = RequestMethod.GET)
    public ResponseEntity<Object> activeProducts(@RequestParam(required = true) Long id) {
		User user = userManager.getUserById(id);
		if(user == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji korisnik sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(productManager.getActive(id), HttpStatus.OK);
    }
	
	@RequestMapping(value = "my/arhived", method = RequestMethod.GET)
    public ResponseEntity<Object> arhivedProducts(@RequestParam(required = true) Long id) {
		User user = userManager.getUserById(id);
		if(user == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji korisnik sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(productManager.getArhived(id), HttpStatus.OK);
    }
	
	@RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
		return productManager.getAllProducts();
    }
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) {
		Product product = productManager.getProductById(id);
		if(product == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji produkt sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(product, HttpStatus.OK);
    }
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProductById(@PathVariable("id") Long id) {
		Product product = productManager.getProductById(id);
		if(product == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji produkt sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		productManager.delete(id);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
    }	
}