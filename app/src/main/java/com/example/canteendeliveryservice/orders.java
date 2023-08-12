package com.example.canteendeliveryservice;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteendeliveryservice.CustomAdapter3;
import com.example.canteendeliveryservice.R;
import com.example.canteendeliveryservice.login_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class orders extends AppCompatActivity {

    RecyclerView rv;
    CustomAdapter3 ca;
    DatabaseReference myRef;
    List<String> orders = new ArrayList<>(); // Moved the orders list inside the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Initialize RecyclerView and its layout manager
        rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Create the adapter and set it to the RecyclerView


        // Retrieve data from Firebase
        myRef = FirebaseDatabase.getInstance().getReference().child("ORDERS");
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        String e = String.valueOf(dataSnapshot.child(login_page.emi).getValue());
                       // parseOrderData(e); // Parse the order data and update the orders list



                        String temp = "";
                        String o = "";
                        int k = 0;
                        for (int i = 0; i < e.length(); i++) {
                            if (k == 1 && e.charAt(i) == '?') {
                                if (dataSnapshot.child(login_page.emi).getChildrenCount() != orders.size()) {
                                    orders.add(temp);
                                }
                                temp = "";
                                k = 0;
                                continue;
                            }
                            if (e.charAt(i) == '?') {
                                k = 1;
                                continue;
                            }
                            if (k == 1) {
                                temp = temp + e.charAt(i);
                            }
                        }
                        updateRecyclerView(); // Update the RecyclerView after data retrieval
                    }
                }
            }
        });

        ca = new CustomAdapter3(orders);
        rv.setAdapter(ca);


    }

    private void updateRecyclerView() {
        if (orders.isEmpty()) {
            // Show a message when the orders list is empty
            TextView ord = findViewById(R.id.noitem);
            ord.setVisibility(View.VISIBLE);
        } else {
            // Hide the message when the orders list has data
            TextView ord = findViewById(R.id.noitem);
            ord.setVisibility(View.INVISIBLE);
        }
        ca.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
}
