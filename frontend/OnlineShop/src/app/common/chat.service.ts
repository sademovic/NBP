import { Injectable } from '@angular/core';

@Injectable()
export class ChatService {

    chat : any;

    setChat(openedChat) {
        this.chat = openedChat;
    }

    getChat() {
        return this.chat;
    }
}