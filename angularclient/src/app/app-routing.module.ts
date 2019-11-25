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
import {OffersComponent} from "./offers/offers.component";

const routes: Routes = [
  { path: 'users', canActivate: [AuthGuard], component: UserListComponent/*, runGuardsAndResolvers: 'always' */},
  { path: 'login', component: LoginComponent, data: { title: 'Login' }, runGuardsAndResolvers: 'always'},
  { path: 'logout', component: LogoutComponent, runGuardsAndResolvers: 'always' },
  { path: 'transactions', canActivate: [AuthGuard], component: TransactionsComponent/*, runGuardsAndResolvers: 'always' */},
  { path: 'offers', canActivate: [AuthGuard], component: OffersComponent/*, runGuardsAndResolvers: 'always' */},
  { path: 'register', component: RegisterComponent, data: { title: 'Register' }/*, runGuardsAndResolvers: 'always'*/},
  { path: 'user/add_account', canActivate: [AuthGuard], component: AddAccountComponent, data: { title: 'Add account' }/*, runGuardsAndResolvers: 'always'*/},
  { path: 'user/account', canActivate: [AuthGuard], component: UserAccountComponent, data: { title: 'Account' }/*, runGuardsAndResolvers: 'always'*/}
  // { path: '**', redirectTo: 'adduser' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
