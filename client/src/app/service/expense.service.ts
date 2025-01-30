import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {  GenericResponse, MoneyAccount, ObjectExpense } from '../models/expense.interface';
import { BehaviorSubject, catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private url : string = "http://localhost:8081/api/v1/"; // todo : use env file instead
  private moneyAccountsSubject: BehaviorSubject<MoneyAccount[]> = new BehaviorSubject<MoneyAccount[]>([]);

  constructor(private http : HttpClient) { }

  /** Transactions requests */

  getAllExpensesGroupedByDate() : Observable<GenericResponse<ObjectExpense[]>>{
    return this.http.get<GenericResponse<ObjectExpense[]>>(`${this.url}expense/groupedByDate`).pipe(
     catchError(error => {
      throw error;
     })
    )
  }

  addTransaction(){

  }

  deleteTransaction(){

  }

  deleteAllTransactions(){

  }

  /** Money Account requests */

  getAllMoneyAccount(): void {
    this.http.get<GenericResponse<MoneyAccount[]>>(`${this.url}moneyAccount`).pipe(
      catchError(error => {
        throw error;
      })
    ).subscribe({
      next: (response) => {
        this.moneyAccountsSubject.next(response.data.exp);
      },
      error: (err) => {
        console.error('Error fetching money accounts', err);
      }
    });
  }

  getMoneyAccounts(): Observable<MoneyAccount[]> {
    return this.moneyAccountsSubject.asObservable();
  }

}
