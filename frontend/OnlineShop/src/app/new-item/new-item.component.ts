import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Item } from '../common/item';
import { CategoryService } from '../common/category.service';
import { ItemService } from '../common/item.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-item',
  templateUrl: './new-item.component.html',
  styleUrls: ['./new-item.component.css']
})
export class NewItemComponent implements OnInit {

  item = new Item();
  imageData: File = null;
  image: any;

  categories: any;

  alphanumeric = /^[0-9a-zA-Z]+$/;

  @ViewChild('itemForm', { static: false }) itemForm: NgForm;
  @ViewChild('catInput', { static: false }) catInput: ElementRef;

  constructor( private categoryService: CategoryService, private itemService: ItemService, private router: Router ) { }

  ngOnInit() {
    this.categoryService.getCategories();
    this.categoryService.catSuccess.subscribe((res) => {
      this.categories = res;
    });
    this.itemService.itemUploadSuccess.subscribe((res) => {
      this.router.navigate(['/browse']);
    });
  }

  imgUpload(event) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (event) => {
        this.image = reader.result;
      }
    }
  }

  onSubmit(event) {
    event.preventDefault();
    this.itemService.uploadItem(this.item, this.image);
  }

  checkName() {
    if (this.item.name) {
      if (!this.item.name.match(this.alphanumeric)) {
        this.itemForm.form.controls['name'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.itemForm.form.controls['name'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  checkLoc() {
    if (this.item.location) {
      if (this.item.location.length < 3) {
        this.itemForm.form.controls['location'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.itemForm.form.controls['location'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  checkPrice() {
    if (this.item.price) {
      if (this.item.price <= 0) {
        this.itemForm.form.controls['price'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.itemForm.form.controls['price'].setErrors(null);
        return false;
      }
    }
    else return false;
  }
}
