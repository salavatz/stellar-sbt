import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './user-form/user-form.component';
import { UserService } from './service/user.service';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AuthenticationService } from "./service/authentication.service";
import { TransactionsComponent } from './transactions/transactions.component';
import { TransactionService } from "./service/transaction.service";
import { RegisterComponent } from './register/register.component';
import {TokenInterceptor} from "./interceptors/token.interceptor";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSortModule,
  MatTableModule,
  MatIconModule,
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule } from '@angular/material';
import {MaterialModule} from "./material/material.module";
import { LayoutComponent } from './layout/layout.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
import { AddAccountComponent } from './add-account/add-account.component';
import { UserAccountComponent } from './user-account/user-account.component';
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    LoginComponent,
    LogoutComponent,
    HeaderComponent,
    FooterComponent,
    TransactionsComponent,
    RegisterComponent,
    LayoutComponent,
    SidenavListComponent,
    AddAccountComponent,
    UserAccountComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MaterialModule,
    FlexLayoutModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
    UserService, AuthenticationService, TransactionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
