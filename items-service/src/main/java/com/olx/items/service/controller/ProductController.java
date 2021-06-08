package com.olx.items.service.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.olx.items.service.businesslogic.CategoryManager;
import com.olx.items.service.businesslogic.ProductManager;
import com.olx.items.service.businesslogic.UserManager;
import com.olx.items.service.businesslogic.impl.DefaultProductManager.StatisticModel;
import com.olx.items.service.models.Category;
import com.olx.items.service.models.Image;
import com.olx.items.service.models.Product;
import com.olx.items.service.models.User;
import com.olx.items.service.validation.ProductModelValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("products/")
@Api(tags = { "Product Controller" })
public class ProductController {
	
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductModelValidator productModelValidator;
	@Autowired
	private UserManager userManager;
	@Autowired
	private CategoryManager categoryManager;
	

	@ApiOperation(value = "Add product", notes = "This service method is used to add a new product.")
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
	
	@RequestMapping(value = "add/image", method = RequestMethod.POST)
    public Product addProductImage(@RequestParam(required = true) Long productId, MultipartHttpServletRequest req) {		
		
		Product product =productManager.getProductById(productId);
		if (product!=null) {
			List<Image> images = new ArrayList<Image>();
			MultipartFile multipartFile = req.getFile("image");
			try {
				images.add(new Image(Base64.getEncoder().encode(multipartFile.getBytes())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			product.setImages(images);

			productManager.save(product);
		}
		return product;
    }
	
	@ApiOperation(value = "Update product", notes = "This service method is used to update a product.")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product product, Errors errors) {
		
		productModelValidator.validateUpdate(product, errors);
		if (errors.hasErrors()) {
		    return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		
		Product updatedProduct = productManager.update(product, id);
		return new ResponseEntity<Object>(updatedProduct, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Search", notes = "This service method is used to search for products by name.")
	@RequestMapping(value = "search", method = RequestMethod.GET)
    public List<Product> search(@RequestParam(required = true) String name) {
		return productManager.search(name);
    }
	
	@ApiOperation(value = "Get active products", notes = "This service method is used to get active products from user.")
	@RequestMapping(value = "my/active", method = RequestMethod.GET)
    public ResponseEntity<Object> activeProducts(@RequestParam(required = true) Long id) {
		User user = userManager.getUserById(id);
		if(user == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji korisnik sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(productManager.getActive(id), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Get arhived products", notes = "This service method is used to get arhived products from user.")
	@RequestMapping(value = "my/arhived", method = RequestMethod.GET)
    public ResponseEntity<Object> arhivedProducts(@RequestParam(required = true) Long id) {
		User user = userManager.getUserById(id);
		if(user == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji korisnik sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(productManager.getArhived(id), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Get all products", notes = "This service method is used to get all products.")
	@RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
		return productManager.getAllProducts();
    }
	
	@ApiOperation(value = "Get product by id", notes = "This service method is used to get product by id.")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id) {
		Product product = productManager.getProductById(id);
		if(product == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji produkt sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(product, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Delete product", notes = "This service method is used to delete product by id.")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProductById(@PathVariable("id") Long id) {
		Product product = productManager.getProductById(id);
		if(product == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji produkt sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		productManager.delete(id);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Send mail", notes = "This service method is used to send mail to owner of product with product id.")
	@RequestMapping(value = "mail", method = RequestMethod.POST)
    public void sendMail(@RequestParam(required = true) Long id, 
    		@RequestParam(required = true) String body) {
		productManager.sendMail(id, body);
    }
	
	@RequestMapping(value = "statistic", method = RequestMethod.GET)
    public List<StatisticModel> search(@RequestParam(required = true) Long time) {
		return productManager.getStatistic(time);
    }
}