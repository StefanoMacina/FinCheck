package com.macina.FinCheck.model.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.macina.FinCheck.converter.ExpCategoryGroupConverter;
import com.macina.FinCheck.converter.MoneyAccountConverter;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @ManyToOne(optional = false)
    @JsonDeserialize(converter = ExpCategoryGroupConverter.class)
    private ExpCategoryGroup expCategoryGroup;

    @ManyToOne(optional = false)
    @JsonDeserialize(converter = MoneyAccountConverter.class)
   // @JsonManagedReference
    private MoneyAccount moneyAccount;

    public Expense(String name, Double quantity, LocalDate date, ExpCategoryGroup expCategoryGroup, MoneyAccount moneyAccount) {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.expCategoryGroup = expCategoryGroup;
        this.moneyAccount = moneyAccount;
    }
}
