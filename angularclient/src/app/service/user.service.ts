import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import {catchError, tap} from "rxjs/operators";
import {log} from "util";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;
  private addAccountUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/users';
    this.addAccountUrl = 'http://localhost:8080/user/';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  public addAccount(data: any): Observable<any> {
    return this.http.post<any>(this.addAccountUrl + 'add_account', data);
  }

  public getAccount(): Observable<any> {
    console.log(this.http.get(this.addAccountUrl + 'account'));
    return this.http.get(this.addAccountUrl + 'account');
  }
}
