import { Injectable } from '@angular/core';

@Injectable()
export class InfoService {

    item : any;
    image: any;

    setItem( selectedItem, itemImage ) {
        this.item = selectedItem;
        this.image = itemImage;
    }

    getItem() {
        return this.item;
    }

    getImage() {
        return this.image;
    }
}