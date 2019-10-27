import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Transaction} from "../model/transaction";
import {TransactionService} from "../service/transaction.service";
import {MyErrorStateMatcher} from "../login/login.component";

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent {

  transaction: Transaction;
  matcher = new MyErrorStateMatcher();

  constructor(private route: ActivatedRoute, private router: Router, private transactionService: TransactionService) {
    this.transaction = new Transaction();
  }

  onSubmit() {
    console.log(this.transaction);
    this.transactionService.send(this.transaction).subscribe();
  }

  gotoUserList() {
    this.router.navigate(['/transactions']);
  }
}
