package com.macina.FinCheck.service;

import com.macina.FinCheck.enums.ERROR_ENUM;
import com.macina.FinCheck.enums.MESSAGE_ENUM;
import com.macina.FinCheck.model.models.Expense;
import com.macina.FinCheck.model.models.MoneyAccount;
import com.macina.FinCheck.payload.GroupedByDateExpense;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MoneyAccountService extends GenericService<MoneyAccount>{

    @Autowired
    public MoneyAccountService(GenericRepository<MoneyAccount> repository) {
        super(repository);
    }


}
