package com.macina.FinCheck.controller;

import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/v1/expense")
public class ExpenseController extends GenericController<Expense>{


    public ExpenseController(GenericRepository<Expense> repository) {
        super(repository);
    }
}
