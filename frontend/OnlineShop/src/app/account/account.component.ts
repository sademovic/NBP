import { Component, OnInit } from '@angular/core';
import { UserService } from '../common/user.service';
import { InfoService } from '../common/info.service';
import { Router } from '@angular/router';
import { ItemService } from '../common/item.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  me: any;
  filter: any;
  dcImage: any;
  items: any;

  constructor( private userService: UserService, private infoService: InfoService, private router: Router, private itemService: ItemService ) { }

  ngOnInit() {
    this.me = this.userService.getUser();
    this.filter = "active";
    this.itemService.getItems();
    this.itemService.itemGetSuccess.subscribe((res) => {
      this.items = res;
    });
  }

  isFilter(sel) {
    return sel == this.filter;
  }

  setFilter(sel) {
    this.filter = sel;
  }

  filMatch(item) {
    if (this.me.id != item.user.id) return false;
    if (this.filter == 'active') return !item.status;
    if (this.filter == 'sold') return item.status;
    return item.status;
  }

  onItemSelect(item) {
    if (item && item.images && item.images[0]) this.dcImage = window.atob(item.images[0].image);
    this.infoService.setItem(item, this.dcImage);
    this.router.navigate(['/info']);
  }

  hasImage(item) {
    if (item && item.images && item.images[0]) return true;
    else return false;
  }

  getUrl(item) {
    this.dcImage = window.atob(item.images[0].image);
    return this.dcImage;
  }
}
