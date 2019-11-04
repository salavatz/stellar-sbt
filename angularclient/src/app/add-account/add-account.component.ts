import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../service/user.service";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {MyErrorStateMatcher} from "../login/login.component";

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {

  addAccountForm: FormGroup;
  publicKey = '';
  secretKey = '';
  matcher = new MyErrorStateMatcher();
  isLoadingResults = false;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.addAccountForm = this.formBuilder.group({
      'publicKey' : [null, Validators.required],
      'secretKey' : [null, Validators.required]
    });
  }

  onFormSubmit(form: NgForm) {
    this.userService.addAccount(form).subscribe( () => {
      this.router.navigate(['/user/account']);
    }
    );
  }
}
