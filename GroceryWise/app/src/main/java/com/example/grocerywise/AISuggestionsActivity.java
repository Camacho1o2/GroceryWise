package com.example.grocerywise;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AISuggestionsActivity extends AppCompatActivity {

    private AIManager aiManager;
    private TextView aiSuggestionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_suggestions);

        aiSuggestionsTextView = findViewById(R.id.ai_suggestions_text);
        aiManager = new AIManager();

        fetchSuggestions();
    }

    private void fetchSuggestions() {
        aiSuggestionsTextView.setText("Fetching suggestions...");
        aiManager.fetchSuggestions(suggestions -> runOnUiThread(() -> {
            if (suggestions.isEmpty()) {
                aiSuggestionsTextView.setText("No suggestions available.");
            } else {
                aiSuggestionsTextView.setText(String.join(", ", suggestions));
            }
        }), error -> runOnUiThread(() -> {
            aiSuggestionsTextView.setText("Failed to load suggestions.");
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }));
    }
}
