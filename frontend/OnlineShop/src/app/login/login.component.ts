import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../common/user';
import { UserService } from '../common/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../common/login-registration.css']
})
export class LoginComponent implements OnInit {

  user = new User();
  failed: boolean = false;
  
  @ViewChild('loginForm', {static: false}) loginForm: NgForm;

  constructor( private router: Router, private userService: UserService ) { }

  ngOnInit() {
    this.userService.loginFailed.subscribe((res) => {
      this.failed = true;
      this.loginForm.reset();
    });

    this.userService.loginSuccess.subscribe((res) => {
      console.log(res);
      this.router.navigate(['/browse']);
    });
  }

  onSubmit(event) {
    event.preventDefault();
    console.log(this.user);
    this.userService.login({ "username": this.user.email, "password": this.user.password });
  }

  onNavigate(path) {
    this.router.navigate([path]);
  }

  testingSkip() {
    this.router.navigate(['/browse']);
  }
}
