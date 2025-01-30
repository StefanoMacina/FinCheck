package com.macina.FinCheck.controller.controllers;

import com.macina.FinCheck.controller.GenericController;
import com.macina.FinCheck.model.models.MoneyAccount;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import com.macina.FinCheck.service.MoneyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/moneyAccount")
public class MoneyAccountController extends GenericController<MoneyAccount> {

    public MoneyAccountController(GenericRepository<MoneyAccount> repository, MoneyAccountService expenseService) {
        super(repository);
    }

}
