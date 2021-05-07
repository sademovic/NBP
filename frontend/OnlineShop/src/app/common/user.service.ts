import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";

@Injectable()
export class UserService {

    baseUrl = "http://localhost:8080/";

    loginHeaders = {  };

    loginFailed = new EventEmitter();
    loginSuccess = new EventEmitter<any>();
    registerSuccess = new EventEmitter();

    private myUser: any;
    private token: string;

    constructor ( private http: HttpClient ) {  }

    login( user ) {
        let email = user.username;
        return this.http.post( this.baseUrl + "auth", user, { headers: this.loginHeaders } ).subscribe({
            next: (res: {token: string}) => {
                console.log("success", res);
                this.token = res.token;
                var params = new HttpParams().set( 'email', email );
                console.log(params);
                this.http.post( this.baseUrl + "userservice/authenticate", {  }, { headers: { 'Authorization': "Bearer " + res.token }, params: params } ).subscribe({
                    next: res => {
                        console.log("token success", res);
                        this.myUser = res;
                        this.loginSuccess.emit(res);
                    },
                    error: error => console.error('There was a token error!', error)
                });
            },
            error: error => {
                console.error('There was a login error!', error)
                this.loginFailed.emit();
            }
        });
    }
    
    register( user ) {
        return this.http.post( this.baseUrl + "userservice/register", user, { headers: this.loginHeaders } ).subscribe({
            next: res => {
                console.log("success", res);
                this.registerSuccess.emit();
            },
            error: error => console.error('There was a registration error!', error)
        });
    }

    getUser() {
        return this.myUser;
    }

    setUser(user) {
        console.log(user);
        this.myUser = user;
    }

    getToken() {
        return this.token;
    }

    getBaseUrl() {
        return this.baseUrl;
    }
}
