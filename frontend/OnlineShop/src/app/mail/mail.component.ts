import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { InfoService } from '../common/info.service';
import { UserService } from '../common/user.service';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class MailComponent implements OnInit {

  item: any;
  me: any;
  sendDisabled = false;
  sentSuccess = false;

  @ViewChild('mailInput', {static: false}) mailInput: ElementRef;

  constructor( private infoService: InfoService, private router: Router, private userService: UserService ) { }

  ngOnInit(): void {
    this.item = this.infoService.getItem();
    this.me = this.userService.getUser();
    this.userService.mailSuccess.subscribe((res) => {
      this.sentSuccess = true;
    });
  }

  goBack() {
    this.router.navigate(["/info"]);
  }

  send() {
    if (this.mailInput.nativeElement.value) {
      this.sendDisabled = true;
      let body = this.mailInput.nativeElement.value;
      body = this.me.email + " has sent you an email : \n\n" + body;
      this.userService.mailUser( this.item.id, body )
    }
  }
}
