package com.macina.FinCheck.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



@Setter
@Getter
@Entity
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String name;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(optional = true)
    private ExpenseMacroCategory expMacroCategory;

    public Expense(String name, Double quantity, LocalDate date, ExpenseMacroCategory expMacroCategory) {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.expMacroCategory = expMacroCategory;
    }

    public Expense(Integer id, String name, Double quantity, LocalDate date, ExpenseMacroCategory expMacroCategory) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.expMacroCategory = expMacroCategory;
    }

    public Expense() {
    }

}
