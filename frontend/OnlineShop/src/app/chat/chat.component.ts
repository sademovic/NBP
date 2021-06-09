import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ChatService } from '../common/chat.service';
import { Router } from '@angular/router';
import { UserService } from '../common/user.service';
import { ContactService } from '../common/contact.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  chat: any;
  messages: any;
  me: any;

  @ViewChild('msgInput', {static: false}) newMsg: ElementRef;

  constructor( private chatService: ChatService, private router: Router, private userService: UserService, private contactService: ContactService ) { }

  ngOnInit() {
    this.chat = this.chatService.getChat();
    this.messages = this.chat.messages;
    console.log(this.chat);
    this.me = this.userService.getUser();
  }

  goBack() {
    this.router.navigate(["/messages"]);
  }

  send() {
    if (this.newMsg.nativeElement.value) {
      let d = new Date();
      let message: any = {
        "cratedAt": d.getTime(),
        "body": this.newMsg.nativeElement.value
      }
      this.contactService.newMessage(message, this.chat.id);
      this.contactService.newMessageSuccess.subscribe((res) => {
        this.newMsg.nativeElement.value = "";
        this.ngOnInit();
      });
    }
  }
}
