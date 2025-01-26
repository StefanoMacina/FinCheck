package com.macina.FinCheck.controller.controllers;

import com.macina.FinCheck.controller.GenericController;
import com.macina.FinCheck.model.models.Expense;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import com.macina.FinCheck.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "api/v1/expense")
public class ExpenseController extends GenericController<Expense> {

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseController(GenericRepository<Expense> repository, ExpenseService expenseService) {
        super(repository);
        this.expenseService = expenseService;
    }

    @GetMapping("/groupedByDate")
    public ResponseEntity<ResponseData<Map<String,Object>>> getAllGroupedByDate() {
        ResponseData<Map<String, Object>> response = expenseService.findAllGroupedByDate();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
