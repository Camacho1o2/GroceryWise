package com.example.grocerywise;

import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AIManager {
    private static final String TAG = "AIManager";
    private static final String GEMINI_API_URL = "https://api.gemini.com/v1/recommendations"; // Replace with your actual API endpoint
    private static final String API_KEY = "YOUR_GEMINI_API_KEY"; // Replace with your Gemini API Key
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient;

    public AIManager() {
        this.httpClient = new OkHttpClient();
    }

    public void fetchSuggestions(Consumer<List<String>> onSuccess, Consumer<String> onError) {
        JSONObject payload = new JSONObject();
        try {
            payload.put("userId", "sampleUser123"); // Example payload, replace as needed
            payload.put("context", "grocery-shopping");
        } catch (JSONException e) {
            Log.e(TAG, "JSON Payload Error: " + e.getMessage());
            onError.accept("Failed to create request payload.");
            return;
        }

        RequestBody body = RequestBody.create(payload.toString(), JSON);
        Request request = new Request.Builder()
                .url(GEMINI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "API Call Failed: " + e.getMessage());
                onError.accept("Failed to connect to Gemini API.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        Log.d(TAG, "API Response: " + responseBody);
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        JSONArray recommendations = jsonResponse.getJSONArray("recommendations");

                        List<String> suggestions = new ArrayList<>();
                        for (int i = 0; i < recommendations.length(); i++) {
                            suggestions.add(recommendations.getString(i));
                        }
                        onSuccess.accept(suggestions);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Parsing Error: " + e.getMessage());
                        onError.accept("Error parsing Gemini API response.");
                    }
                } else {
                    Log.e(TAG, "API Error: " + response.code() + " - " + response.message());
                    onError.accept("Gemini API error: " + response.message());
                }
            }
        });
    }
}
