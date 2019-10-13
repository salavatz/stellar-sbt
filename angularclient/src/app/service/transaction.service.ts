import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Transaction} from "../model/transaction";

@Injectable()
export class TransactionService {

  private transactionsUrl: string;

  constructor(private http: HttpClient) {
    this.transactionsUrl = 'http://localhost:8080/transactions';
  }

  public findAll(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.transactionsUrl);
  }

  public send(transaction: Transaction) {
    return this.http.post<Transaction>(this.transactionsUrl, transaction);
  }
}
