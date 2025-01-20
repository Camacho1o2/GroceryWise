package com.example.grocerywise;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class OfflineModeActivity extends AppCompatActivity {

    private OfflineModeManager offlineManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);

        offlineManager = new OfflineModeManager(this);
        TextView offlineDataTextView = findViewById(R.id.offline_data_text);

        List<String> cachedList = offlineManager.getSavedGroceryList();
        List<String> cachedSuggestions = offlineManager.getAISuggestions();

        offlineDataTextView.setText("Cached List: " + String.join(", ", cachedList) +
                "\nCached AI Suggestions: " + String.join(", ", cachedSuggestions));
    }
}