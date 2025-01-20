package com.example.grocerywise;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageListActivity extends AppCompatActivity {

    private GroceryListAdapter groceryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        RecyclerView groceryRecyclerView = findViewById(R.id.grocery_recycler_view);
        groceryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryListAdapter = new GroceryListAdapter(new ArrayList<>(), this);
        groceryRecyclerView.setAdapter(groceryListAdapter);

        EditText addItemEditText = findViewById(R.id.add_item_edit_text);
        Button addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener(view -> {
            String item = addItemEditText.getText().toString().trim();
            if (!item.isEmpty()) {
                groceryListAdapter.addItem(item);
            }
            addItemEditText.setText("");
        });

        addButton.setEnabled(false);
        addItemEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addButton.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        TextView emptyTextView = findViewById(R.id.empty_list_text_view);
        if (groceryListAdapter.getItemCount() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("groceryList", new ArrayList<>(groceryListAdapter.getGroceryList()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<String> savedList = savedInstanceState.getStringArrayList("groceryList");
        if (savedList != null) {
            groceryListAdapter.updateList(savedList);
        }
    }

    public List<String> getGroceryList() {
        return new ArrayList<>(groceryList); // Return a copy to preserve encapsulation
    }


}