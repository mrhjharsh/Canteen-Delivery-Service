package com.example.canteendeliveryservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    static String a = "";
    static EditText name;
    static EditText number;
    static EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(CheckNetwork.isInternetAvailable(sign_up.this)) //returns true if internet available
        {


            Button b1 = findViewById(R.id.button2);
            name  = findViewById(R.id.name);
            number  = findViewById(R.id.number);
            email  = findViewById(R.id.email);
            EditText password  = findViewById(R.id.password);
            EditText cpassword  = findViewById(R.id.cpassword);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(name.getText().toString().isEmpty() || number.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                            password.getText().toString().isEmpty() || cpassword.getText().toString().isEmpty()) {
                        Toast.makeText(sign_up.this, "Fill all column", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.getText().toString().equals(cpassword.getText().toString())) {
                            String root_email = "";
                            for (int i = 0; i < email.getText().toString().length(); i++) {
                                if(email.getText().toString().charAt(i)=='.'){
                                    break;
                                }else {
                                    root_email = root_email + email.getText().toString().charAt(i);
                                }
                            }
                            a = root_email.toString();
                            Log.d("xxxxx" , a);
                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference("STUDENT ID");
                            myRef.child(root_email).setValue(email.getText().toString().trim() + "," + password.getText().toString().trim());
                            Toast.makeText(sign_up.this, "SIGN-IN SUCCESSFULLY ! NOW YOU CAN LOGIN", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(sign_up.this, "Password Mismatched", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });






        }
        else
        {
            Toast.makeText(sign_up.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }










    }
}