package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Order {
    private List<String> ingredients;
    private List<Ingredients> data;


    public Order(List<Ingredients> data, boolean success) {
        this.data = data;

    }
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Order() {

    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}
