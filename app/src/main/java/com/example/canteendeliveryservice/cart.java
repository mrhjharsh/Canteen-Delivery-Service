package com.example.canteendeliveryservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {

    RecyclerView rv;
    static  int money = 0;
    FirebaseDatabase database;

    String delete = "";
    DatabaseReference myRef;
    static  CustomAdapter2 ca;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if(CheckNetwork.isInternetAvailable(cart.this)) //returns true if internet available
        {

            TextView sum = findViewById(R.id.sum);
            int s = 0;
            for (int i = 0; i <MainActivity.e.length(); i++) {
                s = s + CustomAdapter.price[Integer.parseInt(MainActivity.e.charAt(i)+"")];
            }
            sum.setText("TOTAL : "+ s +"/-");
            money = s+10;
            s = 0;
            TextView noitem   = findViewById(R.id.noitem);

            if(MainActivity.e.length() == 0){
                noitem.setVisibility(View.VISIBLE);
            }
            else{
                noitem.setVisibility(View.INVISIBLE);
            }
            rv = findViewById(R.id.list);
            rv.setLayoutManager(new LinearLayoutManager(cart.this));
            ca = new CustomAdapter2();
            rv.setAdapter(ca);
            rv.addOnItemTouchListener(
                    new RecyclerItemClickListener(cart.this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference("CART ITEM");
                            Toast.makeText(cart.this, CustomAdapter.name[Integer.parseInt(MainActivity.e.charAt(position)+"")] + " REMOVED FROM CART !", Toast.LENGTH_SHORT).show();
                            myRef.child(login_page.emi).setValue(MainActivity.e.substring(0,position) + MainActivity.e.substring(position+1, MainActivity.e.length()));
                            startActivity(new Intent(cart.this , MainActivity.class));
                        }

                        @Override public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
            Button checkout = findViewById(R.id.checkout);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(cart.this , address.class));
                }
            });
            SwipeRefreshLayout sr = findViewById(R.id.swipe);
            sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("CART ITEM");
                    myRef.child(login_page.emi).setValue(MainActivity.e);
                    CustomAdapter2 ca  =  new CustomAdapter2();
                    ca.notifyDataSetChanged();
                    sr.setRefreshing(false);
                }
            });



        }
        else
        {
            Toast.makeText(cart.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }





    }
}