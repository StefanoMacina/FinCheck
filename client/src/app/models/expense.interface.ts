
export interface GenericResponse<T> {
    data : {
        exp : T
    };
    msg: string;
    code: number;
    entityError: any
}

export interface ObjectExpense {
    date: Date;
    list: Expense[];
    balance: number;
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
