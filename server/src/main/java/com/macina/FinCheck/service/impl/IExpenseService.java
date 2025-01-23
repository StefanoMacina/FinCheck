package com.macina.FinCheck.service.impl;

import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.payload.ResponseData;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IExpenseService {

    ResponseData<Map<String,Expense>> save(Expense expense);
    ResponseData<Map<String,Expense>> findById(Integer id);

    ResponseData<Map<String,List<Expense>>> findAll();

    ResponseData<Map<String, Object>> delete(@RequestBody List<Integer> ids);
}
