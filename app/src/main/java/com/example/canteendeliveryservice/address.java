package com.example.canteendeliveryservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class address extends AppCompatActivity {
String[]permissions = {"android.permission.SEND_SMS"};
static String message = "";

    static  FirebaseDatabase database;
    static DatabaseReference myRef;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 80){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        if(CheckNetwork.isInternetAvailable(address.this)) //returns true if internet available
        {

            Button b1 = findViewById(R.id.button4);
            EditText name = findViewById(R.id.name);
            EditText room = findViewById(R.id.room);
            EditText mobile = findViewById(R.id.mobile);
            EditText ward = findViewById(R.id.ward);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu menu = new PopupMenu(address.this,v);
                    if(name.getText().toString().isEmpty() || room.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() ||
                            ward.getText().toString().isEmpty()){
                        Toast.makeText(address.this, "FILL ALL COLUMNS", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int price= 0;
                        message =  "NAME : "+name.getText().toString()+"\n" + "ROOM NO. : " +room.getText().toString()
                                +"\n"+"PHONE NO. : "+mobile.getText().toString() +"\n" + "WARD : "+ward.getText().toString()+"\n" +"ORDER : \n";
                        for (int i = 0; i < MainActivity.e.length(); i++) {
                            message = message + CustomAdapter.name[Integer.parseInt(MainActivity.e.charAt(i)+"")]+" x 1" +"\n";
                            price = price + CustomAdapter.price[Integer.parseInt(MainActivity.e.charAt(i)+"")];
                        }
                        price = price+10;
                        message = message + "PRICE : " + price+"/-";

                        requestPermissions(permissions , 80);

                        menu.getMenu().add("COD");
                        menu.getMenu().add("ONLINE");
                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @SuppressLint("UnlocalizedSms")
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                finish();
                                if(item.getTitle().equals("COD")){
                                    startActivity(new Intent(address.this,orders.class));
                                    Toast.makeText(address.this, "ORDER Successful", Toast.LENGTH_SHORT).show();
                                    String num = mobile.getText().toString();
                                    try {
                                        SmsManager smsManager=SmsManager.getDefault();
                                        smsManager.sendTextMessage("9993019161",null,"?"+message+"?",null,null);
                                    }catch (Exception e)
                                    {
                                        Toast.makeText(address.this, "not send", Toast.LENGTH_SHORT).show();
                                    }
                                    database = FirebaseDatabase.getInstance();
                                    myRef = database.getReference("ORDERS");
                                    myRef.child(login_page.emi).push().setValue("?"+address.message+"?");
                                }
                                if(item.getTitle().equals("ONLINE")){
                                    finish();
                                    Intent i = new Intent(address.this, checkout.class);
                                    startActivity(i);
                                }
                                return true;
                            }
                        });
                        menu.show();
                    }

                }
            });

                     }
        else
        {
            Toast.makeText(address.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }




    }
}