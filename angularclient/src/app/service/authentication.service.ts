import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {of} from "rxjs/observable/of";
import { Router } from '@angular/router';
import {JwtHelperService} from "@auth0/angular-jwt";

const apiUrl = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  isLoggedIn = false;
  redirectUrl: string;

  constructor(private http: HttpClient, private router: Router, private jwtHelper: JwtHelperService) {
    const token = sessionStorage.getItem('token');
    this.isLoggedIn = !this.jwtHelper.isTokenExpired(token);
  }

  login(data: any): Observable<any> {
    return this.http.post<any>(apiUrl + 'login', data)
      .pipe(
        tap(_ => this.isLoggedIn = true),
        catchError(this.handleError('login', []))
      );
  }

  logout(): Observable<any> {
    sessionStorage.removeItem('token')
    this.router.navigate(['login']);
    return this.http.get<any>(apiUrl + 'logout')
      .pipe(
        tap(_ => this.isLoggedIn = false),
        catchError(this.handleError('logout', []))
      );
  }

  register(data: any): Observable<any> {
    return this.http.post<any>(apiUrl + 'register', data)
      .pipe(
        tap(_ => this.log('login')),
        catchError(this.handleError('login', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead
      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }
}
