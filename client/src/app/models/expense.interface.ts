export interface Response<T> {
    response: {
        data: T;
        msg: string;
        code: number;
        entityError: any;
    }
}

export interface GroupedByDate<T> {
    date: string;
    list: T[];
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


