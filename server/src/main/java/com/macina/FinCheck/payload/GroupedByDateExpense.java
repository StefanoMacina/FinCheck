package com.macina.FinCheck.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupedByDateExpense<T> {

    private LocalDate date;

    private List<T> list;

    private Double balance;

}
