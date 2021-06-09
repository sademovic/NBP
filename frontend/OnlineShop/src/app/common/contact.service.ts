import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserService } from './user.service';
import { ChatService } from './chat.service';

@Injectable()
export class ContactService {

    baseUrl : any;

    token: any;
    me: any;

    newChatSuccess = new EventEmitter<any>();
    newMessageSuccess = new EventEmitter<any>();
    getChatsSuccess = new EventEmitter<any>();
    newChat: any;

    constructor ( private http: HttpClient, private userService: UserService, private chatService: ChatService ) {
        
    }

    createChat(userId, senderId) {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        var params = new HttpParams().set( 'senderId', userId ).set( 'receiverId', senderId );
        this.http.post(this.baseUrl + "messageservice/chat/add", { }, { headers: { 'Authorization': "Bearer " + this.token }, params: params }).subscribe({
            next: res => {
                console.log("success", res);
                this.chatService.setChat(res);
                this.newChatSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }

    newMessage(message, chatId) {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.post(this.baseUrl + "messageservice/chat/" + chatId + "/message/add", message, { headers: { 'Authorization': "Bearer " + this.token } }).subscribe({
            next: res => {
                console.log("success", res);
                this.chatService.setChat(res);
                this.newMessageSuccess.emit();
            },
            error: error => console.error('error!', error)});
    }

    getChats() {
        this.token = this.userService.getToken();
        this.me = this.userService.getUser();
        this.baseUrl = this.userService.getBaseUrl();
        this.http.post( this.baseUrl + "messageservice/chat/my", { "userId": this.me.id }, { headers: { 'Authorization': "Bearer " + this.token } } ).subscribe({
            next: res => {
                console.log("success", res);
                this.getChatsSuccess.emit(res);
            },
            error: error => console.error('error!', error)});
    }
}