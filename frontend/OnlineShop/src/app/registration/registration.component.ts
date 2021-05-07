import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../common/user';
import { NgForm } from '@angular/forms';
import { UserService } from '../common/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../common/login-registration.css']
})
export class RegistrationComponent implements OnInit {

  user = new User();
  passwordRepeat : string = '';
  letters = /^[a-zA-Z]+$/;
  @ViewChild('signupForm', {static: false}) signupForm: NgForm;

  constructor( private router: Router, private userService: UserService ) { }

  ngOnInit() {
    this.userService.registerSuccess.subscribe((res) => {
      this.router.navigate(['/login']);
    });
  }

  onSubmit(event) {
    event.preventDefault();
    this.user.phoneNumber = this.user.phoneNumberNum.toString();
    console.log(this.user);
    this.userService.register(this.user);
  }

  onNavigate(path) {
    this.router.navigate([path]);
  }

  checkPwLength() {
    if (this.user.password) {
      if (this.user.password.length < 6) {
        this.signupForm.form.controls['password1'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.signupForm.form.controls['password1'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  checkFN() {
    if (this.user.firstName) {
      if (this.user.firstName[0] != this.user.firstName[0].toUpperCase() || !this.user.firstName.match(this.letters)) {
        this.signupForm.form.controls['firstName'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.signupForm.form.controls['firstName'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  checkLN() {
    if (this.user.lastName) {
      if (this.user.lastName[0] != this.user.lastName[0].toUpperCase() || !this.user.lastName.match(this.letters)) {
        this.signupForm.form.controls['lastName'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.signupForm.form.controls['lastName'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return email.match(re);
  }

  checkMail() {
    if (this.user.email) {
      if (!this.validateEmail(this.user.email)) {
        this.signupForm.form.controls['email'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.signupForm.form.controls['email'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  checkLoc() {
    if (this.user.location) {
      if (this.user.location.length < 3) {
        this.signupForm.form.controls['location'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.signupForm.form.controls['location'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  checkNumLength() {
    if (this.user.phoneNumberNum) {
      if (!Number.isInteger(this.user.phoneNumberNum) || this.user.phoneNumberNum < 10000000000 || this.user.phoneNumberNum > 99999999999) {
        this.signupForm.form.controls['phoneNumber'].setErrors({'incorrect': true});
        return true;
      }
      else {
        this.signupForm.form.controls['phoneNumber'].setErrors(null);
        return false;
      }
    }
    else return false;
  }

  PwMatch() {
    if (this.user.password) {
      if (this.user.password != this.passwordRepeat) {
        this.signupForm.form.controls['password2'].setErrors({'incorrect': true});
        return false;
      }
      else {
        this.signupForm.form.controls['password2'].setErrors(null);
        return true;
      }
    }
    else {
      if (this.signupForm && this.signupForm.form.controls['password2']) {
        this.signupForm.form.controls['password2'].setErrors({'incorrect': true});
      }
      return false;
    }
  }
}
