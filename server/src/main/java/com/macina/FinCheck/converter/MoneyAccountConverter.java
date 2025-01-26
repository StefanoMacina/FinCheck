package com.macina.FinCheck.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.macina.FinCheck.model.models.MoneyAccount;


public class MoneyAccountConverter extends StdConverter<Long, MoneyAccount> {
    @Override
    public MoneyAccount convert(Long id) {
        MoneyAccount m = new MoneyAccount();
        m.setId(id);
        return m;
    }
}