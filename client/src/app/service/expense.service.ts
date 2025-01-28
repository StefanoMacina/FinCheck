import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExpenseResponse } from '../models/expense.interface';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  constructor(private http : HttpClient) { }

  private url : string = "http://localhost:8081/api/v1/expense"; // todo : use env file instead

  getAllExpensesGroupedByDate() : Observable<ExpenseResponse>{
    return this.http.get<ExpenseResponse>(`${this.url}/groupedByDate`).pipe(
     catchError(error => {
      throw error;
     })
    )
  }

  addExpense(){

  }

  deleteExpense(){

  }

  deleteAllExpenses(){

  }
}
