package com.example.canteendeliveryservice;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    FirebaseDatabase database;
    DatabaseReference myRef;
    static int[] image = new int[10];
    int[] heart = new int[10];
    static String[] name = new String[10];
    static int[] price = new int[10];
    CustomAdapter(){

        image[0] = R.drawable.patties;
        image[1] = R.drawable.chocolava;
        image[2] = R.drawable.chole_kulche;
        image[3] = R.drawable.kachori;
        image[4] = R.drawable.pasta;
        image[5] = R.drawable.tede_mere;
        image[6] = R.drawable.maggie;
        image[7] = R.drawable.coconut_biscuit;
        image[8] = R.drawable.cold_drinks;
        image[9] = R.drawable.peanuts;



        name[0] = "PATTIES";//choko
        name[1] = "CHOCO LAVA CAKE";
        name[2] = "CHOLE KULCHE";
        name[3] = "KACHORI";
        name[4] = "PASTA";
        name[5] = "TEDHE MEDHE";
        name[6] = "MAGGIE";
        name[7] = "COCONUT BISCUIT";
        name[8] = "COLD DRINK";
        name[9] = "PEANUTS PACKET";


        price[0] = 14;
        price[1] = 40;
        price[2] = 35;
        price[3] = 35;
        price[4] = 30;
        price[5] = 15;
        price[6] = 30;
        price[7] = 10;
        price[8] = 20;
        price[9] = 10;


        for (int i = 0; i < 10; i++) {
            heart[i] = R.drawable.baseline_favorite_24;
        }



    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_design, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.img.setBackgroundResource(image[position]);
        holder.price.setText(price[position] + " /-");
        holder.heart.setBackgroundResource(R.drawable.baseline_favorite_24);
        holder.snack.setText(name[position]);
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef  = FirebaseDatabase.getInstance().getReference();
                myRef.child("CART ITEM").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot ds = task.getResult();
                                SharedPreferences s;
                                MainActivity.e = String.valueOf(ds.child(String.valueOf(login_page.emi)).getValue());
                                Log.d("qqqq", ""+login_page.anns);
                                if(MainActivity.e .equals("null"))MainActivity.e  = "";
                            }
                        }}});
                if(MainActivity.e .equals(null))MainActivity.e  = "";
               new CustomAdapter2();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
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
