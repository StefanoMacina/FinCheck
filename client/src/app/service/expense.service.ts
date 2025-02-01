import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError } from 'rxjs';
import { CategoryGroup, Expense, GroupedByDate, MoneyAccount, Response } from '../models/expense.interface';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private url: string = "http://localhost:8081/api/v1/";
  private subjects: Map<string, BehaviorSubject<any>> = new Map();

  constructor(private http: HttpClient) {
    this.subjects.set('moneyAccounts', new BehaviorSubject<MoneyAccount[]>([]));
    this.subjects.set('categoryGroups', new BehaviorSubject<CategoryGroup[]>([]));
    this.subjects.set('groupedByDate', new BehaviorSubject<GroupedByDate<Expense>[]>([]));
  }

  // Generic method for fetching data
  private fetchData<T>(endpoint: string): Observable<Response<T>> {
    return this.http.get<Response<T>>(`${this.url}${endpoint}`).pipe(
      catchError(error => {
        throw error;
      })
    );
  }

  // Generic method for loading data into subjects
  private loadDataIntoSubject<T>(endpoint: string, subjectKey: string): void {
    this.fetchData<T>(endpoint).subscribe({
      next: (resp) => {
        const subject = this.subjects.get(subjectKey);
        if (subject) {
          subject.next(resp.response.data);
        }
      },
      error: (err) => {
        console.error(`Error fetching ${subjectKey}`, err);
      }
    });
  }

  // Generic method for getting subject as observable
  private getSubjectObservable<T>(key: string): Observable<T> {
    const subject = this.subjects.get(key);
    if (!subject) {
      throw new Error(`Subject ${key} not found`);
    }
    return subject.asObservable();
  }



  getAllExpensesGroupedByDate(): Observable<Response<GroupedByDate<Expense>[]>> {
    return this.fetchData<GroupedByDate<Expense>[]>('expense/groupedByDate');
  }

  getAllMoneyAccount(): void {
    this.loadDataIntoSubject<MoneyAccount[]>('moneyAccount', 'moneyAccounts');
  }

  getAllMoneyCategoryGroup(): void {
    this.loadDataIntoSubject<CategoryGroup[]>('categoryGroup', 'categoryGroups');
  }

  getMoneyAccounts(): Observable<MoneyAccount[]> {
    return this.getSubjectObservable<MoneyAccount[]>('moneyAccounts');
  }

  getMoneyCategories(): Observable<CategoryGroup[]> {
    return this.getSubjectObservable<CategoryGroup[]>('categoryGroups');
  }

  create<T>(endpoint: string, data: T): Observable<Response<T>> {
    return this.http.post<Response<T>>(`${this.url}${endpoint}`, data).pipe(
      catchError(error => {
        throw error;
      })
    );
  }

  update<T>(endpoint: string, id: string, data: T): Observable<Response<T>> {
    return this.http.put<Response<T>>(`${this.url}${endpoint}/${id}`, data).pipe(
      catchError(error => {
        throw error;
      })
    );
  }

  delete(endpoint: string, id: string): Observable<any> {
    return this.http.delete(`${this.url}${endpoint}/${id}`).pipe(
      catchError(error => {
        throw error;
      })
    );
  }
}