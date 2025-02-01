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
  
  subjectKeys: string[] = []
  loading = false;
  error: string | null = null;
  expenses: GroupedByDate<Expense>[] = [];
  moneyAccounts: MoneyAccount[] = [];
  moneyCategories: CategoryGroup[] = [];
  private subscriptions: Subscription[] = [];
  
  private dataConfig: { key: string; endpoint: string; targetProperty: keyof Tab1Page }[];
  
  constructor(private expenseService: ExpenseService) {

    this.dataConfig = [
      { key: "groupedByDateTransactions", endpoint: "expense/groupedByDate", targetProperty: "expenses" },
      { key: "moneyAccounts", endpoint: "moneyAccount", targetProperty: "moneyAccounts" },
      { key: "categoryGroups", endpoint: "categoryGroup", targetProperty: "moneyCategories" }
    ]
  }

  ngOnInit() {
    this.dataConfig.forEach(({key,endpoint,targetProperty}) => {
      this.loadData(key,endpoint, targetProperty)
    })
  }

  /**
   * 
   * @param key key of behaviorsubject to track into the subject, used to creare and clean the subject
   * @param endpoint endpoint to call
   * @param targetProperty property of this class to attach data for html
   */
  loadData<T>(key: string, endpoint: string, targetProperty: keyof this) {
    this.subjectKeys.push(key);
    const sub = this.expenseService.getAll<T>(key, endpoint).subscribe({
      next: resp => {
        (this as any)[targetProperty] = resp;
      },
      error: err => {
        console.error(`Error fetching ${key}:`, err);
        this.error = `Failed to load ${key}. Please try again later.`;
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
    const { data, role } = event.detail;
    if (role === 'confirm') {
      console.log('Modal confirmed with data:', data);
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    this.subjectKeys.forEach(k => this.expenseService.clearSubject(k))
  }
}