import { Injectable } from '@angular/core';

@Injectable()
export class AuthenticationService {

  constructor() { }

  authenticate(username, password) {
    if (username === "javainuse" && password === "password") {
      sessionStorage.setItem('token', username)
      return true;
    } else {
      return false;
    }
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('token')
    console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('token')
  }
}
