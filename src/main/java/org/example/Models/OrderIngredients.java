package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderIngredients {

    private String success;
    private List<Ingredients> data;
    public OrderIngredients(List<Ingredients> data, String success) {
        this.data = data;
        this.success = success;
    }

    public OrderIngredients() {

    }
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Ingredients> getData() {
        return data;
    }

    public void setData(List<Ingredients> data) {
        this.data = data;
    }


}
