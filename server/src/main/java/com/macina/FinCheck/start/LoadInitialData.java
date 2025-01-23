package com.macina.FinCheck.start;

import com.macina.FinCheck.model.models.ExpCategoryGroup;
import com.macina.FinCheck.repository.repo.ExpCategoryGroupRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoadInitialData {

    @Autowired
    ExpCategoryGroupRepository expCategoryGroupRepository;

    @PostConstruct
    public void initData(){
        addDefaultGroupCategories();
    }

    private void addDefaultGroupCategories(){
            List<String> l = List.of(
                    "Alimentazione",
                    "Abitazione",
                    "Trasporti",
                    "Salute",
                    "Istruzione",
                    "Tempo libero",
                    "Tecnologia",
                    "Abbigliamento",
                    "Spese personali",
                    "Risparmi e Investimenti"
            );
            for(String c : l){
                if(!expCategoryGroupRepository.existsByName(c)){
                    expCategoryGroupRepository.save(new ExpCategoryGroup(c));
                }
            }
    }
}
