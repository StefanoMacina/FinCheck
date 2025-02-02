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
    id: number | null;
    name: string;
    quantity: number;
    description: string | null;
    date: string;
    expCategoryGroup: CategoryGroup | number;
    moneyAccount: MoneyAccount | number;
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


