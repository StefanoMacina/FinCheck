package com.macina.FinCheck.repository.repo;

import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends GenericRepository<Expense> {
}
