package com.macina.FinCheck.service;

import com.macina.FinCheck.enums.ERROR_ENUM;
import com.macina.FinCheck.enums.GENERIC_ENUM;
import com.macina.FinCheck.enums.MESSAGE_ENUM;
import com.macina.FinCheck.model.GenericEntity;
import com.macina.FinCheck.payload.ResponseData;
import com.macina.FinCheck.repository.GenericRepository;
import java.util.*;

public abstract class GenericService<T extends GenericEntity<T>> {

    protected final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public ResponseData<T> save(T entity) {

        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();
        T savedEntity = null;
        try{
            savedEntity = repository.save(entity);
        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            error = ERROR_ENUM.SERVER_ERROR.getCode();
            savedEntity = entity;
        }
        return new ResponseData<>(savedEntity,msg,error,savedEntity);
    }

    public ResponseData<T> findById(Long id) {
        String msg = MESSAGE_ENUM.SUCCESS.getMsg();;
        Integer error = ERROR_ENUM.SUCCESS.getCode();
        T entity = null;

        try{
            Optional<T> r = repository.findById(id);
            if(r.isPresent()){
               entity = r.get();
            } else {
                msg = MESSAGE_ENUM.EMPTY_SET.getMsg();
                error = ERROR_ENUM.NOT_FOUND.getCode();
            }
        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            error = ERROR_ENUM.SERVER_ERROR.getCode();
        }
        return new ResponseData<>(entity,msg,error,entity);
    }

    public ResponseData<List<T>> findAll() {

        String msg = MESSAGE_ENUM.SUCCESS.getMsg();
        Integer error = ERROR_ENUM.SUCCESS.getCode();
        List<T> entities = null;
        try{
            List<T> l = repository.findAll();
            if(l.isEmpty()){
                msg = MESSAGE_ENUM.EMPTY_SET.getMsg();
                error = ERROR_ENUM.EMPTY_SET.getCode();
            } else{
                entities = l;
            }
        }catch (Exception e){
            msg = MESSAGE_ENUM.SERVER_ERROR.getMsg();
        }
        return new ResponseData<>(entities,msg,error,null);
    }

    public ResponseData<Map<String, Object>> delete(List<Long> ids) {
        // Check for empty input
        if (ids == null || ids.isEmpty()) {
            return new ResponseData<>(
                    null,
                    MESSAGE_ENUM.EMPTY_SET.getMsg(),
                    ERROR_ENUM.EMPTY_SET.getCode(),
                    ERROR_ENUM.EMPTY_SET
            );
        }

        int totalDeleted = 0;
        List<Map<String, Object>> failedDetails = new ArrayList<>();

        // Process each ID
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

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("totalDeleted", totalDeleted);
        resultData.put("totalFailed", failedDetails.size());
        resultData.put("failedIds", failedDetails);

        String responseMessage;
        Integer responseCode;

        if (totalDeleted == 0) {
            responseMessage = MESSAGE_ENUM.SERVER_ERROR.getMsg();
            responseCode = ERROR_ENUM.SERVER_ERROR.getCode();
        } else if (failedDetails.isEmpty()) {
            responseMessage = MESSAGE_ENUM.SUCCESS.getMsg();
            responseCode = ERROR_ENUM.SUCCESS.getCode();
        } else {
            responseMessage = totalDeleted + " items deleted successfully, " + failedDetails.size() + " failed";
            responseCode = ERROR_ENUM.PARTIAL_SUCCESS.getCode();
        }

        // Return formatted response
        return new ResponseData<>(
                resultData,
                responseMessage,
                responseCode,
                failedDetails.isEmpty() ? null : failedDetails
        );
    }

}