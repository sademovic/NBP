import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { UserService } from './user.service';

@Injectable()
export class AdminService {

    baseUrl;

    token: any;

    logsSuccess = new EventEmitter<any>();

    constructor ( private http: HttpClient, private userService: UserService ) {
        
    }

    getLogs() {
        this.token = this.userService.getToken();
        this.baseUrl = this.userService.getBaseUrl();

        this.http.get( this.baseUrl + "logservice/logs", { headers: { 'Authorization': "Bearer " + this.token } } ).subscribe({
            next: res => {
                console.log("success", res);
                this.logsSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }
}