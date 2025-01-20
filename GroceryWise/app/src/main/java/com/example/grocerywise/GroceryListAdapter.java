package com.example.grocerywise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder> {

    private final List<String> groceryList;
    private final OnItemClickListener onItemClickListener;

    public GroceryListAdapter(List<String> groceryList, OnItemClickListener listener) {
        this.groceryList = groceryList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_grocery_item, parent, false);
        return new GroceryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        String item = groceryList.get(position);
        holder.groceryItemName.setText(item);

        holder.deleteButton.setOnClickListener(v -> onItemClickListener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }



    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void addItem(String item) {
        groceryList.add(item);
        notifyItemInserted(groceryList.size() - 1);
    }

    public void updateList(List<String> newList) {
        groceryList.clear();
        groceryList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class GroceryViewHolder extends RecyclerView.ViewHolder {
        TextView groceryItemName;
        ImageButton deleteButton;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
//            groceryItemName = itemView.findViewById(R.id.grocery_item_name);
//            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }



}
