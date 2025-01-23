package com.macina.FinCheck.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.macina.FinCheck.model.models.ExpCategoryGroup;

public class ExpCategoryGroupConverter extends StdConverter<Integer, ExpCategoryGroup> {
    @Override
    public ExpCategoryGroup convert(Integer id) {
        ExpCategoryGroup group = new ExpCategoryGroup();
        group.setId(id);
        return group;
    }
}