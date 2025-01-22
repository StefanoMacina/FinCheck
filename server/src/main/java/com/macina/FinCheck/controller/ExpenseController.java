package com.macina.FinCheck.controller;

import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAll(){
        List<Expense> expenseList = expenseService.findAll();
        return new ResponseEntity<>(expenseList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable Integer id){
        Optional<Expense> expense = expenseService.findById(id);
        return new ResponseEntity<>(expense.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> save(
            @RequestBody Expense expense
    ){
        Expense returnExpense = expenseService.save(expense);
        return new ResponseEntity<>(returnExpense, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids){
        expenseService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
