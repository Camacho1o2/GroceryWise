package com.example.grocerywise;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OfflineModeManager {

    private static final String PREFERENCES_NAME = "GroceryWisePreferences";
    private static final String GROCERY_LIST_KEY = "GroceryList";

    private final SharedPreferences sharedPreferences;

    public OfflineModeManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveGroceryList(List<String> groceryList) {
        JSONArray jsonArray = new JSONArray(groceryList);
        sharedPreferences.edit().putString(GROCERY_LIST_KEY, jsonArray.toString()).apply();
    }

    public List<String> loadGroceryList() {
        String jsonString = sharedPreferences.getString(GROCERY_LIST_KEY, null);
        List<String> groceryList = new ArrayList<>();

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    groceryList.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return groceryList;
    }
}
