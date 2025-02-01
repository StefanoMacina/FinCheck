package com.macina.FinCheck.service;

import com.macina.FinCheck.enums.ERROR_ENUM;
import com.macina.FinCheck.enums.MESSAGE_ENUM;
import com.macina.FinCheck.model.models.Expense;
import com.macina.FinCheck.payload.GroupedByDateExpense;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService extends GenericService<Expense>{

    @Autowired
    public ExpenseService(GenericRepository<Expense> repository) {
        super(repository);
    }

    /**
     * Specific method to retrieve all expenses grouped by date
     * @return an object with key "exp" and as value a list of object of type GroupedByDateExpense
     */
    public ResponseData<List<GroupedByDateExpense<Expense>>> findAllGroupedByDate() {
        Map<String,Object> m = new HashMap<>();

        String msg = MESSAGE_ENUM.SUCCESS.getMsg();
        Integer error = ERROR_ENUM.SUCCESS.getCode();
        List<GroupedByDateExpense<Expense>> groupedExpenses = new ArrayList<>();

        try {
            List<Expense> expenses = repository.findAll();
            if (expenses.isEmpty()) {
                msg = MESSAGE_ENUM.EMPTY_SET.getMsg();
                error = ERROR_ENUM.EMPTY_SET.getCode();
            } else {

                Map<LocalDate, List<Expense>> groupedByDate = expenses.stream()
                        .collect(Collectors.groupingBy(Expense::getDate));

                groupedExpenses = groupedByDate.entrySet().stream()
                        .map(entry -> GroupedByDateExpense.<Expense>builder()
                                .date(entry.getKey())
                                .balance(entry.getValue().stream()
                                        .mapToDouble(Expense::getQuantity)
                                        .sum()
                                )
                                .list(entry.getValue())
                                .build())
                        .toList();

            }
        } catch (Exception e) {
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            error = ERROR_ENUM.SERVER_ERROR.getCode();
        }

        return new ResponseData<>(groupedExpenses,msg,error,null);
    }
}
