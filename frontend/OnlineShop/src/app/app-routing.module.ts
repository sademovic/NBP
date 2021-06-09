import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { BrowseComponent } from './browse/browse.component';
import { NewItemComponent } from './new-item/new-item.component';
import { AccountComponent } from './account/account.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { MessagesComponent } from './messages/messages.component';
import { ChatComponent } from './chat/chat.component';
import { ItemInfoComponent } from './item-info/item-info.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { MailComponent } from './mail/mail.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'browse', component: BrowseComponent },
  { path: 'newitem', component: NewItemComponent },
  { path: 'account', component: AccountComponent },
  { path: 'admin', component: AdminPanelComponent },
  { path: 'messages', component: MessagesComponent },
  { path: 'chat', component: ChatComponent },
  { path: 'info', component: ItemInfoComponent },
  { path: 'statistics', component: StatisticsComponent },
  { path: 'mail', component: MailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }