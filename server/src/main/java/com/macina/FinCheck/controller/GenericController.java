package com.macina.FinCheck.controller;

import com.macina.FinCheck.model.GenericEntity;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import com.macina.FinCheck.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

public abstract class GenericController<T extends GenericEntity<T>> {

    private final GenericService<T> service;

    public GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<T>(repository) {};
    }

    @GetMapping
    public ResponseEntity<ResponseData<Map<String, List<T>>>> getAll(){
        ResponseData<Map<String,List<T>>> r = service.findAll();
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Map<String,T>>> getById(@PathVariable Long id){
        ResponseData<Map<String,T>> r = service.findById(id);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Map<String,T>>> save(
            @RequestBody T expense
    ){
        ResponseData<Map<String,T>> r = service.save(expense);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData<Map<String,Object>>> delete(@RequestBody List<Long> ids){
        ResponseData<Map<String,Object>> r = service.delete(ids);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }
}
