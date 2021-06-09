import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { InfoService } from '../common/info.service';
import { Router } from '@angular/router';
import { CategoryService } from '../common/category.service';
import { ItemService } from '../common/item.service';
import { SearchService } from '../common/search.service';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})

export class BrowseComponent implements OnInit {

  categories: any;
  items: any;
  dcImage: any;
  trending: any;

  @ViewChild('searchInput', {static: false}) searchInput: ElementRef;

  selectedCategory: number = -1;

  constructor( private infoService: InfoService, private router: Router, private categoryService: CategoryService, private itemService: ItemService, private searchService: SearchService ) { }

  ngOnInit() {
    this.categoryService.getCategories();
    this.categoryService.catSuccess.subscribe((res) => {
      this.categories = res;
    });
    this.itemService.getItems();
    this.itemService.itemGetSuccess.subscribe((res) => {
      this.items = res;
      if ( this.items && this.items.length > 0 ) {
        this.trending = this.items[0];
        for (let item of res) {
          if ( !this.sold(item) && (item.numberOfViews > this.trending.numberOfViews || this.sold(this.trending)) ) this.trending = item;
        }
      }
    });
    this.searchService.searchSuccess.subscribe((res) => {
      this.items = res;
    });
  }

  checkItems() {
    return this.items && this.items.length > 0;
  }

  onItemSelect(item) {
    if (item && item.images && item.images[0]) this.dcImage = window.atob(item.images[0].image);
    this.infoService.setItem(item, this.dcImage);
    this.router.navigate(['/info']);
  }

  selectCat(category: any) {
    if (this.selectedCategory != category.id) this.selectedCategory = category.id; else this.selectedCategory = -1;
  }

  isSelected(category: any) {
    return (category.id == this.selectedCategory);
  }

  catMatch(item: any) {
    if (this.selectedCategory == -1) return true;
    if (item.category && this.selectedCategory == item.category.id) return true;
    return false;
  }

  onSearch() {
    if (this.searchInput.nativeElement.value) {
      this.searchService.getSearch(this.searchInput.nativeElement.value);
    }
  }

  searchReset() {
    if (this.searchInput.nativeElement.value) {
      this.searchInput.nativeElement.value = '';
    }
    this.itemService.getItems();
  }

  hasImage(item) {
    if (item && item.images && item.images[0]) return true;
    else return false;
  }

  getUrl(item) {
    this.dcImage = window.atob(item.images[0].image);
    return this.dcImage;
  }

  sold(item) {
    return item.status;
  }
}
