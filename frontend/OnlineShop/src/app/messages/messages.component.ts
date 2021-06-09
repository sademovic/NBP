import { Component, OnInit } from '@angular/core';
import { ChatService } from '../common/chat.service';
import { Router } from '@angular/router';
import { ContactService } from '../common/contact.service';
import { UserService } from '../common/user.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {

  chatlist: any;
  me: any;
  noChat: boolean = true;
  filtSel: any;

  constructor( private chatService: ChatService, private router: Router, private contactService: ContactService, private userService: UserService ) { }

  ngOnInit() {
    this.me = this.userService.getUser();
    this.contactService.getChats();
    this.filtSel = 'buying';
    this.contactService.getChatsSuccess.subscribe((res) => {
      this.chatlist = res;
      if (this.chatlist[0]) this.noChat = false;
    });
  }

  openChat(chat) {
    this.chatService.setChat(chat);
    this.router.navigate(["/chat"]);
  }

  show(chat) {
    if (this.filtSel == 'buying') {
      if (chat) return chat.sender.id == this.me.id; else return true;
    }
    else {
      if (chat) return chat.receiver.id == this.me.id; else return true;
    }
  }

  onFilterSet(sel) {
    this.filtSel = sel;
  }
}
