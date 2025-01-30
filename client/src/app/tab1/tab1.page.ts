import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ExpenseService } from '../service/expense.service';
import { Subscription } from 'rxjs';
import { IonModal } from '@ionic/angular';
import { GenericResponse, MoneyAccount, ObjectExpense } from '../models/expense.interface';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  standalone: false,
})
export class Tab1Page implements OnInit, OnDestroy {
  @ViewChild(IonModal) modal!: IonModal;

  loading = false;
  private subscription?: Subscription;
  private moneyAccountsSubscription?: Subscription;
  error: string | null = null;
  expenses: GenericResponse<ObjectExpense[]> | null = null;
  moneyAccounts: MoneyAccount[] = [];

  constructor( private expenseService : ExpenseService ) {}

  ngOnInit(){
    this.loadExpenses();
    this.loadMoneyAccount();
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

  loadMoneyAccount() {
    this.expenseService.getAllMoneyAccount(); 
    this.moneyAccountsSubscription = this.expenseService.getMoneyAccounts().subscribe({
      next: (accounts) => {
        this.moneyAccounts = accounts; 
      },
      error: (err) => {
        console.error('Error fetching money accounts', err);
      }
    });
  }

  cancel() {
    this.modal.dismiss(null, 'cancel');
  }

  confirm() {
    this.modal.dismiss();
  }

  onWillDismiss(){}

  ngOnDestroy(){
    if(this.subscription){
      this.subscription.unsubscribe();
    }
    if (this.moneyAccountsSubscription) {
      this.moneyAccountsSubscription.unsubscribe();
    }
  }

}