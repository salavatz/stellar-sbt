import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  email = '';
  password = '';
  matcher = new MyErrorStateMatcher();
  isLoadingResults = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      'email' : [null, Validators.required],
      'password' : [null, Validators.required]
    });
  }

  onFormSubmit(form: NgForm) {
    this.authenticationService.login(form)
      .subscribe(res => {
        if (res.token) {
          sessionStorage.setItem('token', res.token);
          this.router.navigate(['']);
        }
      }, (err) => {
        console.log(err);
      });
  }

  register() {
    this.router.navigate(['register']);
  }

}
