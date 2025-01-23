package com.macina.FinCheck.service;

import com.macina.FinCheck.enums.ERROR_ENUM;
import com.macina.FinCheck.enums.GENERIC_ENUM;
import com.macina.FinCheck.enums.MESSAGE_ENUM;
import com.macina.FinCheck.model.GenericEntity;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import java.util.*;

public abstract class GenericService<T extends GenericEntity<T>> {

    private final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public ResponseData<Map<String, T>> save(T entity) {
        Map<String, T> m = new HashMap<>();
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();

        try{
            T savedEntity = repository.save(entity);
            m.put(GENERIC_ENUM.expenseField.getMsg(),savedEntity);

        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            error = ERROR_ENUM.SERVER_ERROR.getCode();
        }
        return ResponseData.<Map<String,T>>builder()
                .data(m)
                .msg(msg)
                .code(error)
                .build();
    }

    public ResponseData<Map<String,T>> findById(Long id) {
        Map<String, T> m = new HashMap<>();
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();

        try{
            Optional<T> s = repository.findById(id);
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
        return ResponseData.<Map<String,T>>builder()
                .data(m)
                .msg(msg)
                .code(error)
                .build();
    }

    public ResponseData<Map<String, List<T>>> findAll() {
        Map<String, List<T>> m = new HashMap<>();
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();

        try{
            List<T> l = repository.findAll();
            if(l.isEmpty()){
                msg = MESSAGE_ENUM.EMPTY_SET.getMsg();
                error = ERROR_ENUM.EMPTY_SET.getCode();
            } else{
                m.put(GENERIC_ENUM.expenseField.getMsg(), l);
            }
        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
        }
        return ResponseData.<Map<String,List<T>>>builder()
                .data(m)
                .msg(msg)
                .code(error)
                .build();
    }

    public ResponseData<Map<String, Object>> delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseData.<Map<String, Object>>builder()
                    .code(0)
                    .msg(MESSAGE_ENUM.EMPTY_SET.getMsg())
                    .entityError(ERROR_ENUM.EMPTY_SET)
                    .build();
        }

        int totalDeleted = 0;
        List<Map<String, Object>> failedDetails = new ArrayList<>();

        for (Long id : ids) {
            try {
                if (!repository.existsById(id)) {
                    failedDetails.add(Map.of(
                            "id", id,
                            "error", ERROR_ENUM.NOT_FOUND
                    ));
                    continue;
                }

                repository.deleteById(id);
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