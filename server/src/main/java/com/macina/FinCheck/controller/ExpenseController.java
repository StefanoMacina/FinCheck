package com.macina.FinCheck.controller;

import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<ResponseData<Map<String,List<Expense>>>> getAll(){
        ResponseData<Map<String,List<Expense>>> r = expenseService.findAll();
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Map<String,Expense>>> getById(@PathVariable Integer id){
        ResponseData<Map<String,Expense>> r = expenseService.findById(id);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Map<String,Expense>>> save(
            @RequestBody Expense expense
    ){
        ResponseData<Map<String,Expense>> r = expenseService.save(expense);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData<Map<String,Object>>> delete(@RequestBody List<Integer> ids){
       ResponseData<Map<String,Object>> r = expenseService.delete(ids);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }
}
