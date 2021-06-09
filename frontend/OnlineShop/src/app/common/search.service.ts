import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { UserService } from './user.service';

@Injectable()
export class SearchService {

    baseUrl;

    token: any;

    searchSuccess = new EventEmitter<any>();

    constructor ( private http: HttpClient, private userService: UserService ) {
        
    }

    getSearch(value) {
        this.token = this.userService.getToken();
        this.baseUrl = this.userService.getBaseUrl();
        var params = new HttpParams().set( 'name', value );
        this.http.get( this.baseUrl + "itemservice/products/search", { headers: { 'Authorization': "Bearer " + this.token }, params: params } ).subscribe({
            next: res => {
                console.log("success", res);
                this.searchSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }
}