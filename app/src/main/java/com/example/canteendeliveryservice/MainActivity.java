package com.example.canteendeliveryservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
RecyclerView rv;
    static  String e = "";

FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {



            rv = findViewById(R.id.list);
            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            //rv.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            CustomAdapter ca = new CustomAdapter();
            rv.setAdapter(ca);
            TextView cart = findViewById(R.id.cart);
            TextView profile = findViewById(R.id.profile);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
                    {
                        finish();
                        startActivity(new Intent(MainActivity.this ,profile.class ));

                                  }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            TextView order = findViewById(R.id.order);
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
                    {
                        startActivity(new Intent(MainActivity.this ,orders.class ));


                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
                    {
                        startActivity(new Intent(MainActivity.this, com.example.canteendeliveryservice.cart.class));


                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            rv.addOnItemTouchListener(
                    new RecyclerItemClickListener(MainActivity.this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // do whatever
                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference("CART ITEM");
                            e = e + position;
                            SharedPreferences s;
                            s  = getSharedPreferences("db1",MODE_PRIVATE);
                            myRef.child(login_page.emi).setValue(e);
                            Toast.makeText(MainActivity.this, CustomAdapter.name[position]+ " ADDED TO CART", Toast.LENGTH_SHORT).show();
                        }

                        @Override public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );


        }
        else
        {
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }



    }



    @Override
    protected void onStart() {

        if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {

            myRef  = FirebaseDatabase.getInstance().getReference();
            myRef.child("CART ITEM").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot ds = task.getResult();
                            e = String.valueOf(ds.child(String.valueOf(login_page.emi)).getValue());
                            if(e.equals("null"))e = "";
                        }
                    }}});
            if(e.equals(null))e = "";
                       }
        else
        {
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }




        super.onStart();
    }
}