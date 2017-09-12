package com.example.gabriel.bakingapp.Utils;


import java.io.Serializable;

public class RecipeCards implements Serializable{

    private int recipe_id;
    private String recipe_name;
    private int recipe_servings_size;

public RecipeCards(int id, String name, int servings){
    recipe_id = id;
    recipe_name = name;
    recipe_servings_size = servings;
}

    public int getRecipe_id(){
        return recipe_id;
    }

    public String getRecipe_name(){
        return recipe_name;
    }

    public int getRecipe_servings_size(){
        return recipe_servings_size;
    }
}
