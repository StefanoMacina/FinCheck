import { Component, OnDestroy, OnInit } from '@angular/core';
import { ExpenseService } from '../service/expense.service';
import { ExpenseResponse } from '../models/expense.interface';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  standalone: false,
})
export class Tab1Page implements OnInit, OnDestroy {
  loading = false;
  private subscription?: Subscription;
  error: string | null = null;
  expenses: ExpenseResponse | null = null;

  constructor( private expenseService : ExpenseService ) {}

  ngOnInit(){
    this.loadExpenses();
  }

  loadExpenses(){
    this.loading = true;

   this.subscription = this.expenseService.getAllExpensesGroupedByDate()
    .subscribe({
      next: (data) => {
        this.expenses = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load expenses. Please try again later.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  ngOnDestroy(){
    if(this.subscription){
      this.subscription.unsubscribe();
    }
  }

}