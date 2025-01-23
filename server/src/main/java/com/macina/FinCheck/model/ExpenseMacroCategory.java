package com.macina.FinCheck.model;

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
public class ExpenseMacroCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 4, max = 20)
    private String name;

    @OneToMany(mappedBy = "expMacroCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenseList = new ArrayList<>();

    public ExpenseMacroCategory(String name, List<Expense> expenseList) {
        this.name = name;
        this.expenseList = expenseList;
    }
}
