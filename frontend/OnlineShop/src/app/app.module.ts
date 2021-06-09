import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { UserService } from './common/user.service';
import { BrowseComponent } from './browse/browse.component';
import { TopbarComponent } from './topbar/topbar.component';
import { NewItemComponent } from './new-item/new-item.component';
import { AccountComponent } from './account/account.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { MessagesComponent } from './messages/messages.component';
import { ChatComponent } from './chat/chat.component';
import { ChatService } from './common/chat.service';
import { ItemInfoComponent } from './item-info/item-info.component';
import { InfoService } from './common/info.service';
import { CategoryService } from './common/category.service';
import { ItemService } from './common/item.service';
import { AdminService } from './common/admin.service';
import { SearchService } from './common/search.service';
import { ContactService } from './common/contact.service';
import { StatisticsComponent } from './statistics/statistics.component';
import { MailComponent } from './mail/mail.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    BrowseComponent,
    TopbarComponent,
    NewItemComponent,
    AccountComponent,
    AdminPanelComponent,
    MessagesComponent,
    ChatComponent,
    ItemInfoComponent,
    StatisticsComponent,
    MailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    NgxChartsModule,
    BrowserAnimationsModule
  ],
  providers: [
    UserService,
    ChatService,
    InfoService,
    CategoryService,
    ItemService,
    AdminService,
    SearchService,
    ContactService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
