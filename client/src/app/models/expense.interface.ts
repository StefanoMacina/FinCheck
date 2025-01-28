export interface ExpenseResponse {
    data : {
        exp: ObjectExpense[];
    };
    msg: string;
    code: number;
    entityError: any
}

export interface ObjectExpense {
    date: Date;
    list: Expense[];
}

export interface Expense {
    id: number;
    name: string;
    quantity: number;
    description: string;
    date: Date;
    expCategoryGroup: CategoryGroup;
    moneyAccount: MoneyAccount;
}

export interface CategoryGroup {
    id: number;
    name: string;
}

export interface MoneyAccount {
    id: number;
    name: string;
    amount: number;
}