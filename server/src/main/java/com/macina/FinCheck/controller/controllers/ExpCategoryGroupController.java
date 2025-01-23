package com.macina.FinCheck.controller.controllers;

import com.macina.FinCheck.controller.GenericController;
import com.macina.FinCheck.model.models.ExpCategoryGroup;
import com.macina.FinCheck.model.models.Expense;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "api/v1/categoryGroup")
public class ExpCategoryGroupController extends GenericController<ExpCategoryGroup> {


    public ExpCategoryGroupController(GenericRepository<ExpCategoryGroup> repository) {
        super(repository);
    }
}
