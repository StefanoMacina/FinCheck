package com.macina.FinCheck.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.macina.FinCheck.model.models.ExpCategoryGroup;


public class ExpCategoryGroupConverter extends StdConverter<Long, ExpCategoryGroup> {
    @Override
    public ExpCategoryGroup convert(Long id) {
        ExpCategoryGroup e = new ExpCategoryGroup();
        e.setId(id);
        return e;
    }
}