package com.macina.FinCheck.controller.controllers;

import com.macina.FinCheck.controller.GenericController;
import com.macina.FinCheck.model.models.Expense;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/v1/expense")
public class ExpenseController extends GenericController<Expense> {


    public ExpenseController(GenericRepository<Expense> repository) {
        super(repository);
    }
}
