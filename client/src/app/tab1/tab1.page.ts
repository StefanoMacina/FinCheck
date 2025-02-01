import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ExpenseService } from '../service/expense.service';
import { Subscription } from 'rxjs';
import { IonModal } from '@ionic/angular';
import { CategoryGroup, MoneyAccount, GroupedByDate, Expense } from '../models/expense.interface';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  standalone: false,
})
export class Tab1Page implements OnInit, OnDestroy {
  @ViewChild(IonModal) modal!: IonModal;
  
  loading = false;
  error: string | null = null;
  expenses: GroupedByDate<Expense>[] | null = null;
  moneyAccounts: MoneyAccount[] = [];
  moneyCategories: CategoryGroup[] = [];

  private subscriptions: Subscription[] = [];

  constructor(private expenseService: ExpenseService) {}

  ngOnInit() {
    this.loadExpenses();
    this.loadMoneyAccounts();
    this.loadMoneyCategories();
  }

  loadExpenses() {
    this.loading = true;
    const sub = this.expenseService.getAllExpensesGroupedByDate()
      .subscribe({
        next: (response) => {
          this.expenses = response.response.data;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load expenses. Please try again later.';
          this.loading = false;
          console.error('Error loading expenses:', error);
        },
        complete: () => {
          this.loading = false;
        }
      });
    
    this.subscriptions.push(sub);
  }

  loadMoneyAccounts() {
    this.expenseService.getAllMoneyAccount();
    const sub = this.expenseService.getMoneyAccounts().subscribe({
      next: (accounts) => {
        this.moneyAccounts = accounts;
      },
      error: (err) => {
        console.error('Error fetching money accounts:', err);
        this.error = 'Failed to load money accounts. Please try again later.';
      }
    });

    this.subscriptions.push(sub);
  }

  loadMoneyCategories() {
    this.expenseService.getAllMoneyCategoryGroup();
    const sub = this.expenseService.getMoneyCategories().subscribe({
      next: (categories) => {
        this.moneyCategories = categories;
      },
      error: (err) => {
        console.error('Error fetching categories:', err);
        this.error = 'Failed to load categories. Please try again later.';
      }
    });

    this.subscriptions.push(sub);
  }

  cancel() {
    this.modal.dismiss(null, 'cancel');
  }

  confirm() {
    this.modal.dismiss();
  }

  onWillDismiss(event: any) {
    // Handle modal dismiss event
    const { data, role } = event.detail;
    if (role === 'confirm') {
      // Handle confirmed dismiss
      console.log('Modal confirmed with data:', data);
    }
  }

  ngOnDestroy() {
    // Unsubscribe from all subscriptions
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}