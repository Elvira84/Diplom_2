package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderIngredientsResponse {
    private String success;
    private List<IngredientsResponse> data;
}
