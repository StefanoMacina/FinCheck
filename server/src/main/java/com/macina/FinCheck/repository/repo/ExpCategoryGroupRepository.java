package com.macina.FinCheck.repository.repo;

import com.macina.FinCheck.model.models.ExpCategoryGroup;
import com.macina.FinCheck.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpCategoryGroupRepository extends GenericRepository<ExpCategoryGroup> {
    Boolean existsByName(String name);
}
