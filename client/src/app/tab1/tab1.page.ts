import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ExpenseService } from '../service/expense.service';
import { Subscription } from 'rxjs';
import { IonModal } from '@ionic/angular';
import { CategoryGroup, MoneyAccount, GroupedByDate, Expense } from '../models/expense.interface';
import {FormGroup, FormControl} from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  standalone: false,
  providers:[DatePipe]
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
  submitForm: FormGroup = new FormGroup({});
  isExpense : boolean = true;
  selectedTransaction: Expense | undefined | null;

  profileForm = new FormGroup({    
      transactionName: new FormControl(''),    
      value: new FormControl(''),
      date: new FormControl(''),
      account: new FormControl(''),
      category: new FormControl(''),
    });

  constructor(
    private expenseService: ExpenseService,
    private datePipe: DatePipe
  ) {
    this.dataConfig = [
      { key: "groupedByDateTransactions", endpoint: "expense/groupedByDate", targetProperty: "expenses" },
      { key: "moneyAccounts", endpoint: "moneyAccount", targetProperty: "moneyAccounts" },
      { key: "categoryGroups", endpoint: "categoryGroup", targetProperty: "moneyCategories" }
    ]
  }

  ngOnInit() {
    this.dataConfig.forEach(({ key, endpoint, targetProperty }) => {
      this.loadData(key, endpoint, targetProperty)
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

  toggleLabel(event: any) {
    this.isExpense = event.detail.checked;
  }

  openUpdateModal(expense: Expense) {
    this.selectedTransaction = expense;
    
    const dateParts = expense.date.split('-');
    const formattedDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`;

    // Gestiamo sia il caso in cui riceviamo un oggetto che un numero
    const accountId = typeof expense.moneyAccount === 'object' 
        ? expense.moneyAccount.id.toString()
        : expense.moneyAccount.toString();

    const categoryId = typeof expense.expCategoryGroup === 'object'
        ? expense.expCategoryGroup.id.toString()
        : expense.expCategoryGroup.toString();

    this.profileForm.patchValue({
        transactionName: expense.name,
        value: Math.abs(expense.quantity).toString(),
        date: formattedDate,
        account: accountId,
        category: categoryId
    });

    this.isExpense = expense.quantity < 0;
    document.getElementById('open-modal')?.click();
}

  compareWith = (o1: any, o2: any) => {
    if (!o1 || !o2) {
        return false;
    }
    const val1 = typeof o1 === 'object' ? o1.id : o1;
    const val2 = typeof o2 === 'object' ? o2.id : o2;
    return val1.toString() === val2.toString();
  };

  save() {
    let {transactionName, value, date, account, category} = this.profileForm.value;
    let newTransaction:Expense = {
      id: this.selectedTransaction?.id || null,
      name: transactionName!,
      description: null,
      quantity: this.isExpense ? Number(value)!*-1 : Number(value),
      date: this.datePipe.transform(date, 'dd-MM-yyyy')!,
      expCategoryGroup: Number(category)!,
      moneyAccount: Number(account)!
    };

    console.log('Dati inviati:',newTransaction);
    this.expenseService.create("expense",newTransaction).subscribe({
      next: resp => {
        this.expenseService.refreshData("groupedByDateTransactions","expense/groupedByDate")
        this.modal.dismiss();
      },
      error: error => {
        console.log(error)
      }
    })
  }

  onWillDismiss(event: any) {
    const { role } = event.detail;
    if (role === 'confirm') {
      console.log('Modal confirmed with data:', event.detail.data);
    }
    this.selectedTransaction = null;
    setTimeout(() => this.profileForm.reset(), 200);
    this.isExpense = true;
  }
  
  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    this.subjectKeys.forEach(k => this.expenseService.clearSubject(k))
  }
}