import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { UserService } from './user.service';

@Injectable()
export class CategoryService {

    baseUrl;

    token: any;

    catSuccess = new EventEmitter<any>();
    statSuccess = new EventEmitter<any>();

    constructor ( private http: HttpClient, private userService: UserService ) {
        
    }

    uploadCategory( cat ) {
        this.token = this.userService.getToken();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.post( this.baseUrl + "itemservice/category/add", {"name": cat}, { headers: { 'Authorization': "Bearer " + this.token } } ).subscribe({
            next: res => {
                console.log("success", res);
            },
            error: error => console.error('error!', error)});
    }

    getCategories() {
        this.token = this.userService.getToken();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.get( this.baseUrl + "itemservice/category/all", { headers: { 'Authorization': "Bearer " + this.token } } ).subscribe({
            next: res => {
                console.log("success", res);
                this.catSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }

    getCategoryStats( reqTime ) {
        this.token = this.userService.getToken();
        this.baseUrl = this.userService.getBaseUrl();
        var params = new HttpParams().set( 'time', reqTime );
        this.http.get( this.baseUrl + "itemservice/products/statistic", { headers: { 'Authorization': "Bearer " + this.token }, params: params } ).subscribe({
            next: res => {
                console.log("success", res);
                this.statSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }
}