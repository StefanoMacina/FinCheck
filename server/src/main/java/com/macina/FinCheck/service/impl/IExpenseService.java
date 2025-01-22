package com.macina.FinCheck.service.impl;

import com.macina.FinCheck.model.Expense;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface IExpenseService {

    Expense save(Expense expense);

    Optional<Expense> findById(Integer id);

    List<Expense> findAll();

    void delete(@RequestBody List<Integer> ids);
}
