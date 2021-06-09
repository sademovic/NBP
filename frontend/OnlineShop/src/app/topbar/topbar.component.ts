import { Component, OnInit } from '@angular/core';
import { UserService } from '../common/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

  me: any;

  constructor( private userService: UserService, private router: Router ) { }

  ngOnInit() {
    this.me = this.userService.getUser();
  }

  onNavigate(path) {
    this.router.navigate([path]);
  }
}
