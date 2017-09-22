package com.example.gabriel.bakingapp.Utils;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class RecipeCards implements Serializable, Parcelable{

    private int recipe_id;
    private String recipe_name, jsonString;
    private int recipe_servings_size;



public RecipeCards(int id, String name, int servings, String jSONString){
    recipe_id = id;
    recipe_name = name;
    recipe_servings_size = servings;
    jsonString = jSONString;
}

    protected RecipeCards(Parcel in) {
        recipe_id = in.readInt();
        recipe_name = in.readString();
        jsonString = in.readString();
        recipe_servings_size = in.readInt();
    }

    public static final Creator<RecipeCards> CREATOR = new Creator<RecipeCards>() {
        @Override
        public RecipeCards createFromParcel(Parcel in) {
            return new RecipeCards(in);
        }

        @Override
        public RecipeCards[] newArray(int size) {
            return new RecipeCards[size];
        }
    };

    public int getRecipe_id(){
        return recipe_id;
    }

    public String getRecipe_name(){
        return recipe_name;
    }

    public int getRecipe_servings_size(){
        return recipe_servings_size;
    }
    public String getJSONString(){return jsonString;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipe_id);
        dest.writeString(recipe_name);
        dest.writeString(jsonString);
        dest.writeInt(recipe_servings_size);
    }
}
