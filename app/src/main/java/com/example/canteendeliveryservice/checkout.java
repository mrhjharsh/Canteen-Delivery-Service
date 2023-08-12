package com.example.canteendeliveryservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Random;

public class checkout extends AppCompatActivity implements PaymentResultListener {
    static  FirebaseDatabase database;
    static DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if(CheckNetwork.isInternetAvailable(checkout.this)) //returns true if internet available
        {


            Checkout.preload(getApplicationContext());

            final Activity activity = this;

            final Checkout co = new Checkout();

            try {

                JSONObject options = new JSONObject();
                options.put("name", "CANTEEN DELIVERY SERVICE");
                options.put("description", "Software Development");
                options.put("send_sms_hash", true);
                options.put("allow_rotation", true);
                options.put("currency", "INR");
                options.put("amount", cart.money*100);

                JSONObject preFill = new JSONObject();
                preFill.put("email", "hj9993019161@gmail.com");
                preFill.put("contact", "9993019161");
                options.put("prefill", preFill);

                co.open(activity, options);
            }
            catch(Exception e)
            {
                Log.d("error",e.getMessage());
            }

                      }
        else
        {
            Toast.makeText(checkout.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onPaymentSuccess(String s) {
        finish();
        startActivity(new Intent(checkout.this,orders.class));
        Toast.makeText(checkout.this, "ORDER Successful", Toast.LENGTH_SHORT).show();

        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage("9993019161",null,"?"+address.message+"?",null,null);
        }catch (Exception e)
        {
            Toast.makeText(checkout.this, "NOT", Toast.LENGTH_SHORT).show();
        }


        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ORDERS");
        myRef.child(login_page.emi).push().setValue("?"+address.message+"?");

        startActivity(new Intent(checkout.this , orders.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        finish();
        startActivity(new Intent(checkout.this , MainActivity.class));
        Toast.makeText(this, "Something went wrong please retry", Toast.LENGTH_SHORT).show();
    }
}