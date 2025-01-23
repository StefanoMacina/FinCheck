package com.macina.FinCheck.model.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.macina.FinCheck.converter.ExpCategoryGroupConverter;
import com.macina.FinCheck.model.GenericEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expense implements GenericEntity<Expense> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String name;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(optional = false)
    @JsonDeserialize(converter = ExpCategoryGroupConverter.class)
    private ExpCategoryGroup expCategoryGroup;

    public Expense(String name, Double quantity, LocalDate date, ExpCategoryGroup expCategoryGroup) {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.expCategoryGroup = expCategoryGroup;
    }
}
