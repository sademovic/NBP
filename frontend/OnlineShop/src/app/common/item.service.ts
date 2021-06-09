import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { UserService } from './user.service';

@Injectable()
export class ItemService {

    baseUrl : any;

    token: any;
    me: any;

    itemGetSuccess = new EventEmitter<any>();
    itemUploadSuccess = new EventEmitter<any>();
    itemSellSuccess = new EventEmitter<any>();
    itemDeleteSuccess = new EventEmitter<any>();

    constructor ( private http: HttpClient, private userService: UserService ) {
        
    }

    uploadItem( item, image ) {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        var params = new HttpParams().set( 'categoryId', item.categoryN ).set( 'userId', this.me.id );
        this.http.post( this.baseUrl + "itemservice/products/add", item, { headers: { 'Authorization': "Bearer " + this.token }, params: params } ).subscribe({
            next: (res: any) => {
                console.log("Success", res);
                if (!image) {
                    this.itemUploadSuccess.emit(res);
                    return;
                }
                const formData = new FormData();
                const file = new File([image], "image");
                formData.append("image", file);
                console.log(this.me.id);
                var params = new HttpParams().set( 'productId', res.id );
                this.http.post( this.baseUrl + "itemservice/products/add/image", formData, { headers: { 'Authorization': "Bearer " + this.token }, params: params } ).subscribe({
                    next: res => {
                        console.log("Success", res);
                        this.itemUploadSuccess.emit(res);
                    },
                    error: error => console.error('Error!', error)});
            },
            error: error => console.error('Error!', error)
        });
    }

    getItems() {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.get( this.baseUrl + "itemservice/products/all", { headers: { 'Authorization': "Bearer " + this.token } } ).subscribe({
            next: res => {
                console.log("success", res);
                this.itemGetSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }

    sellItem( body, itemId ) {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.put(this.baseUrl + "itemservice/products/" + itemId, body, { headers: { 'Authorization': "Bearer " + this.token } }).subscribe({
            next: res => {
                console.log("success", res);
                this.itemSellSuccess.emit();
            },
            error: error => console.error('error!', error)});
    }

    updateViews( body, itemId ) {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.put(this.baseUrl + "itemservice/products/" + itemId, body, { headers: { 'Authorization': "Bearer " + this.token } }).subscribe({
            next: res => {
                console.log("success", res);
            },
            error: error => console.error('error!', error)});
    }

    deleteItem( itemId ) {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.delete( this.baseUrl + "itemservice/products/" + itemId, { headers: { 'Authorization': "Bearer " + this.token } } ).subscribe({
            next: res => {
                console.log("success", res);
                this.itemDeleteSuccess.emit();
            },
            error: error => console.error('error!', error)});
    }
}