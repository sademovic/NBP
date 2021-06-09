import { Component, OnInit } from '@angular/core';
import { AdminService } from '../common/admin.service';
import { CategoryService } from '../common/category.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  logs: any;
  
  constructor( private adminService: AdminService, private categoryService: CategoryService ) { }

  ngOnInit() {
    this.adminService.getLogs();
    this.adminService.logsSuccess.subscribe((res) => {
      this.logs = res;
    });
  }

}
