import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
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

  /** Add new  Subject dynamically by passing subject key for subjects map
   * 
   * @param key key of new subject
   */
  private addDynamicSubject<T>(key: string){
    let subject = this.subjects.get(key);
    if (!subject) {
      subject = new BehaviorSubject<T>([] as T);
      this.subjects.set(key, subject);
    }
    return subject;
  }

  /** Generic method for getting subject as observable
   * 
   * @param key 
   * @returns 
   */
  public getSubjectObservable<T>(key: string): Observable<T> {
    const subject = this.subjects.get(key);
    if (!subject) {
      throw new Error(`Subject ${key} not found`);
    }
    return subject.asObservable();
  }

  /** Clear unused behaviorsubjects
   * 
   * @param key subject to clear
   */
  clearSubject(key: string): void {
    const subject = this.subjects.get(key);
    if (subject) {
      subject.complete(); 
      this.subjects.delete(key); 
    }
  }

  /**
   * 
   * @param key key of new subject to track, used for retrieving data and cleanup when component will destroy
   * @param endpoint endpoint to call
   * @returns observable of specific type
   */
  getAll<T>(key: string, endpoint: string): Observable<T>{
    const subject = this.addDynamicSubject<T>(key);
    
    this.fetchData<T>(endpoint).subscribe({
      next: (resp) => {
        subject.next(resp.response.data);
      },
      error: (err) => {
        console.error(`Error fetching ${key}:`, err);
        throw err;
      }
    });

    return this.getSubjectObservable<T>(key);
  }

  refreshData<T>(key: string, endpoint: string): void {
    this.fetchData<T>(endpoint).subscribe({
      next: (resp) => {
        const subject = this.subjects.get(key);
        if (subject) {
          subject.next(resp.response.data);
        }
      },
      error: (err) => {
        console.error(`Error refreshing ${key}:`, err);
        throw err;
      }
    });
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

  delete(endpoint: string, list: Set<number>): Observable<Response<void>> {
    return this.http.request<Response<void>>('DELETE', `${this.url}${endpoint}`, {
      body: Array.from(list),  
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }) 
    }).pipe(
      catchError(this.handleError)
    );
  }
}