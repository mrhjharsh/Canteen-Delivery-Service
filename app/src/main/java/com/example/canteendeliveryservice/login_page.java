package com.example.canteendeliveryservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_page extends AppCompatActivity {
    String[]permissions = {"android.permission.SEND_SMS"};

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences s;
    public static String emi = "";
    String check= "";
    static  String anns = "";
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
    protected void onCreate(Bundle savedInstanceState){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        if(CheckNetwork.isInternetAvailable(login_page.this)) //returns true if internet available
        {






            TextView signin = findViewById(R.id.textView2);
            TextView signin2 = findViewById(R.id.textView);
            Button b = findViewById(R.id.button);
            EditText email = findViewById(R.id.email);
            EditText password = findViewById(R.id.password);
            s  = getSharedPreferences("db1",MODE_PRIVATE);
            check = s.getString("login","");

            requestPermissions(permissions , 80);
            if(check.equals("1")){
                //login success;
                finish();
                emi = s.getString("email","");
                startActivity(new Intent(login_page.this , MainActivity.class));
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                        Toast.makeText(login_page.this, "Enter email and password ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String bb = String.valueOf(email.getText().toString());
                        StringBuilder a = new StringBuilder(bb);
                        a.delete(bb.length()-4 , bb.length());
                        myRef  = FirebaseDatabase.getInstance().getReference();
                        myRef.child("STUDENT ID").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){
                                        DataSnapshot ds = task.getResult();
                                        String e = String.valueOf(ds.child(String.valueOf(a)).getValue());
                                        String fetch_email = "";
                                        String fetch_password = "";
                                        int k = 0;
                                        for (int i = 0; i < e.length(); i++) {
                                            if(e.charAt(i) == ','){
                                                k = 1;
                                                continue;
                                            }
                                            if(k == 0){
                                                fetch_email = fetch_email + e.charAt(i);
                                            }
                                            if(k == 1){
                                                fetch_password = fetch_password + e.charAt(i);
                                            }
                                        }
                                        String root_email = "";
                                        for (int i = 0; i < email.getText().toString().length(); i++) {
                                            if(email.getText().toString().charAt(i)=='.'){
                                                break;
                                            }else {
                                                root_email = root_email + email.getText().toString().charAt(i);
                                            }
                                        }
                                        s  = getSharedPreferences("db1",MODE_PRIVATE);
                                        SharedPreferences.Editor edit = s.edit();
                                        anns = root_email.toString();
                                        edit.putString("email", root_email.toString());

                                        if(email.getText().toString().trim().equals(fetch_email.trim()) && password.getText().toString().trim().equals(fetch_password.trim())){
                                            finish();
                                            edit.putString("login", "1");
                                            edit.apply();
                                            Toast.makeText(login_page.this, "LOGIN SUCCESSFULLY ", Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(login_page.this  , MainActivity.class));
                                        }
                                        else {
                                            Toast.makeText(login_page.this,"WRONG EMAIL AND PASSWORD" , Toast.LENGTH_SHORT).show();
                                        }



                                    }

                                }
                                else {
                                    Toast.makeText(login_page.this, "Email and Password doesn't match", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });


            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login_page.this, sign_up.class));
                }
            });
            signin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login_page.this, sign_up.class));
                }
            });






        }
        else
        {
            Toast.makeText(login_page.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }



    }
}