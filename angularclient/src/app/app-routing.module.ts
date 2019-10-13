import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './user-form/user-form.component';
import {LoginComponent} from "./login/login.component";
import {LogoutComponent} from "./logout/logout.component";
import {TransactionsComponent} from './transactions/transactions.component';
import {RegisterComponent} from "./register/register.component";
import {AuthGuard} from "./auth/auth.guard";
import {AddAccountComponent} from "./add-account/add-account.component";
import {UserAccountComponent} from "./user-account/user-account.component";

const routes: Routes = [
  { path: 'users', canActivate: [AuthGuard], component: UserListComponent },
  // { path: 'adduser', canActivate: [AuthGuard], component: UserFormComponent },
  { path: 'login', component: LoginComponent, data: { title: 'Login' }},
  { path: 'logout', component: LogoutComponent },
  { path: 'transactions', canActivate: [AuthGuard], component: TransactionsComponent },
  { path: 'register', component: RegisterComponent, data: { title: 'Register' }},
  { path: 'user/add_account', canActivate: [AuthGuard], component: AddAccountComponent, data: { title: 'Add account' }},
  { path: 'user/account', canActivate: [AuthGuard], component: UserAccountComponent, data: { title: 'Account' }}
  // { path: '**', redirectTo: 'adduser' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
