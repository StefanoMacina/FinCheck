package com.macina.FinCheck.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macina.FinCheck.model.GenericEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExpCategoryGroup implements GenericEntity<ExpCategoryGroup> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 4, max = 55)
    private String name;

    public ExpCategoryGroup(String name) {
        this.name = name;
    }
}

