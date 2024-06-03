package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IngredientsResponse {
    private String _id;
    private String name;
    private String type;
    private float proteins;
    private float fat;
    private float carbohydrates;
    private float calories;
    private float price;
    private String image;
    private String image_mobile;
    private String mage_large;
    private String __v;

}
