package com.macina.FinCheck.service;

import com.macina.FinCheck.enums.ERROR_ENUM;
import com.macina.FinCheck.enums.GENERIC_ENUM;
import com.macina.FinCheck.enums.MESSAGE_ENUM;
import com.macina.FinCheck.model.Expense;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.ExpenseRepository;
import com.macina.FinCheck.service.impl.IExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Transactional
public class ExpenseService implements IExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    public ResponseData<Map<String,Expense>> save(Expense expense) {
        Map<String, Expense> m = new HashMap<>();
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();

        try{
            Expense savedExpense = expenseRepository.save(expense);
            m.put(GENERIC_ENUM.expenseField.getMsg(),savedExpense);

        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            error = ERROR_ENUM.SERVER_ERROR.getCode();
        }
        return ResponseData.<Map<String,Expense>>builder()
                .data(m)
                .msg(msg)
                .code(error)
                .build();
    }

    @Override
    public ResponseData<Map<String,Expense>> findById(Integer id) {
        Map<String, Expense> m = new HashMap<>();
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();

        try{
            Optional<Expense> s = expenseRepository.findById(id);
            if(s.isPresent()){
                m.put(GENERIC_ENUM.expenseField.getMsg(), s.get());
            } else {
                msg = MESSAGE_ENUM.EMPTY_SET.getMsg();
                error = ERROR_ENUM.NOT_FOUND.getCode();
            }
        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            error = ERROR_ENUM.SERVER_ERROR.getCode();
        }
        return ResponseData.<Map<String,Expense>>builder()
                .data(m)
                .msg(msg)
                .code(error)
                .build();
    }

    @Override
    public ResponseData<Map<String,List<Expense>>> findAll() {
        Map<String, List<Expense>> m = new HashMap<>();
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();

        try{
            List<Expense> l = expenseRepository.findAll();
            if(l.isEmpty()){
                msg = MESSAGE_ENUM.EMPTY_SET.getMsg();
                error = ERROR_ENUM.EMPTY_SET.getCode();
            } else{
                m.put(GENERIC_ENUM.expenseField.getMsg(), l);
            }
        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
        }
        return ResponseData.<Map<String,List<Expense>>>builder()
                .data(m)
                .msg(msg)
                .code(error)
                .build();
    }

    @Override
    public ResponseData<Map<String, Object>> delete(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseData.<Map<String, Object>>builder()
                    .code(0)
                    .msg(MESSAGE_ENUM.EMPTY_SET.getMsg())
                    .entityError(ERROR_ENUM.EMPTY_SET)
                    .build();
        }

        int totalDeleted = 0;
        List<Map<String, Object>> failedDetails = new ArrayList<>();

        for (Integer id : ids) {
            try {
                if (!expenseRepository.existsById(id)) {
                    failedDetails.add(Map.of(
                            "id", id,
                            "error", ERROR_ENUM.NOT_FOUND
                    ));
                    continue;
                }

                expenseRepository.deleteById(id);
                totalDeleted++;
            } catch (Exception e) {
                failedDetails.add(Map.of(
                        "id", id,
                        "error", ERROR_ENUM.SERVER_ERROR
                ));
            }
        }

        Map<String,Object> r = new HashMap<>();
        r.put("totalDeleted", totalDeleted);
        r.put("totalFailed", failedDetails.size());
        r.put("failedIds", failedDetails);

        return ResponseData.<Map<String, Object>>builder()
                .data(r)
                .code(0)
                .msg(totalDeleted + " items deleted successfully")
                .build();
    }
}
