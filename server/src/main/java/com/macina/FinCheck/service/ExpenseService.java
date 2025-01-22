package com.macina.FinCheck.service;

import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.repository.ExpenseRepository;
import com.macina.FinCheck.service.impl.IExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpenseService implements IExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> findById(Integer id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public void delete( List<Integer> ids) {
       expenseRepository.deleteAllByIdInBatch(ids);
    }
}
