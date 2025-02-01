import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, throwError } from 'rxjs';
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
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  /** Generic method for fetching data passing a url
   * 
   * @param endpoint 
   * @returns 
   */
  private fetchData<T>(endpoint: string): Observable<Response<T>> {
    return this.http.get<Response<T>>(`${this.url}${endpoint}`).pipe(
      catchError(this.handleError)
    );
  }

  /** Generic method for loading data into subject aftet fetching it from server, the component will use the subject
   * 
   * @param endpoint 
   * @param subjectKey 
   */
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
        this.handleError(err);
      }
    });
  }

  /** Generic method for getting subject as observable
   * 
   * @param key 
   * @returns 
   */
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

  getMoneyAccounts(): Observable<MoneyAccount[]> {
    return this.getSubjectObservable<MoneyAccount[]>('moneyAccounts');
  }

  getAllMoneyCategoryGroup(): void {
    this.loadDataIntoSubject<CategoryGroup[]>('categoryGroup', 'categoryGroups');
  }

  getMoneyCategories(): Observable<CategoryGroup[]> {
    return this.getSubjectObservable<CategoryGroup[]>('categoryGroups');
  }

  create<T>(endpoint: string, data: T): Observable<Response<T>> {
    return this.http.post<Response<T>>(`${this.url}${endpoint}`, data).pipe(
      catchError(this.handleError)
    );
  }

  update<T>(endpoint: string, id: string, data: T): Observable<Response<T>> {
    return this.http.put<Response<T>>(`${this.url}${endpoint}/${id}`, data).pipe(
      catchError(this.handleError)
    );
  }

  delete(endpoint: string, id: string): Observable<Response<void>> {
    return this.http.delete<Response<void>>(`${this.url}${endpoint}/${id}`).pipe(
      catchError(this.handleError)
    );
  }
}