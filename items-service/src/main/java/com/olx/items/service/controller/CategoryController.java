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
import org.springframework.web.bind.annotation.RestController;

import com.olx.items.service.businesslogic.CategoryManager;
import com.olx.items.service.models.Category;
import com.olx.items.service.validation.CategoryModelValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("category/")
@Api(tags = { "Category Controller" })
public class CategoryController {
	
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private CategoryModelValidator categoryModelValidator;
	
	@ApiOperation(value = "Add category", notes = "This service method is used to add a new category.")
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Category category, Errors errors) {
		
		categoryModelValidator.validate(category, errors);
		
		if (errors.hasErrors()) {
		    return new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(categoryManager.save(category), HttpStatus.OK);
    }

	@ApiOperation(value = "Get all category", notes = "This service method is used to get all category.")
	@RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Category> getAllCategory() {
		return categoryManager.getAllCategory();
    }
	
	@ApiOperation(value = "Get category by id", notes = "This service method is used to get category by Id.")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategoryById(@PathVariable("id") Long id) {
		Category category = categoryManager.getCategoryById(id);
		if(category == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji kategorija sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(category, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Delete a category", notes = "This service method is used to delete category.")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategoryById(@PathVariable("id") Long id) {
		Category category = categoryManager.getCategoryById(id);
		if(category == null) {
			return new ResponseEntity<Object>(new Error("Ne postoji kategorija sa id = "+ id.toString()), HttpStatus.BAD_REQUEST);
		}
		categoryManager.delete(id);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
    }
}