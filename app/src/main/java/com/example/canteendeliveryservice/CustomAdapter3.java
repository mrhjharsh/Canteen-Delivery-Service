package com.example.canteendeliveryservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteendeliveryservice.R;

import java.util.List;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder> {

    private List<String> orders;

    public CustomAdapter3(List<String> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_design3, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String order = orders.get(position);
        holder.snack.setText(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView snack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            snack = itemView.findViewById(R.id.snack);
        }
    }
}
