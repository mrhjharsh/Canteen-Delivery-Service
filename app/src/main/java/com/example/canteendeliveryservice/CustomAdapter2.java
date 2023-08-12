package com.example.canteendeliveryservice;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder>{
    //CustomAdapter2 ca;
    static int[] image2;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int[] heart;
    static String[] name2;
    static String[] price2;

    List<String>prices = new ArrayList<>();
    List<String>names = new ArrayList<>();
    List<Integer>images = new ArrayList<>();

    CustomAdapter2(){
        image2 = new int[10];
         heart = new int[MainActivity.e.length()];//
         name2 = new String[10];
         price2 = new String[10];

        image2[0] = R.drawable.patties;
        image2[1] = R.drawable.chocolava;
        image2[2] = R.drawable.chole_kulche;
        image2[3] = R.drawable.kachori;
        image2[4] = R.drawable.pasta;
        image2[5] = R.drawable.tede_mere;
        image2[6] = R.drawable.maggie;
        image2[7] = R.drawable.coconut_biscuit;
        image2[8] = R.drawable.cold_drinks;
        image2[9] = R.drawable.peanuts;

        name2[0] = "PATTIES";
        name2[1] = "CHOCO LAVA CAKE";
        name2[2] = "CHOLE KULCHE";
        name2[3] = "KACHORI";
        name2[4] = "PASTA";
        name2[5] = "TEDHE MEDHE";
        name2[6] = "MAGGIE";
        name2[7] = "COCONUT BISCUIT";
        name2[8] = "COLD DRINK";
        name2[9] = "PEANUTS PACKET";

        price2[0] = "14 /-";
        price2[1] = "40 /-";
        price2[2] = "35 /-";
        price2[3] = "35 /-";
        price2[4] = "30 /-";
        price2[5] = "15 /-";
        price2[6] = "30 /-";
        price2[7] = "10 /-";
        price2[8] = "20 /-";
        price2[9] = "10 /-";



        for (int i = 0; i < MainActivity.e.length(); i++){
            images.add(image2[Integer.parseInt(MainActivity.e.charAt(i)+"")]);
            names.add(name2[Integer.parseInt(MainActivity.e.charAt(i)+"")]);
            prices.add(price2[Integer.parseInt(MainActivity.e.charAt(i)+"")]);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_design2, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position){
        holder.img.setBackgroundResource(images.get(position));
        holder.price.setText(prices.get(position));
        holder.heart.setBackgroundResource(R.drawable.baseline_remove_circle_24);
        holder.snack.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton heart;
        TextView snack;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            heart = itemView.findViewById(R.id.heart);
            snack = itemView.findViewById(R.id.snack);
            price = itemView.findViewById(R.id.price);
        }
    }

}

