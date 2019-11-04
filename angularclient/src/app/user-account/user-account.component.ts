import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import {Account} from "../model/account";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  displayedColumns = ['assetType', 'assetCode', 'balance']
  account: Account;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.account = new Account();
    this.userService.getAccount()
      .subscribe(data => {
        this.account = data;
        console.log(data);
      });
  }

  createAccount() {
    this.userService.createAccount()
      .subscribe();
    this.router.navigate(['']);
  }

}
