package com.example.canteendeliveryservice;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class profile extends AppCompatActivity {
    SharedPreferences s;

    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    String check;
    public ImageView IVPreviewImage;
    String imageUriString;
    Bitmap selectedImageBitmap;
    public static final String PRODUCT_PHOTO = "photo";
    String str_bitmap;
    private  Bitmap bitmap;
    private ImageView  imageView_photo;
    public static Bitmap product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(CheckNetwork.isInternetAvailable(profile.this)) //returns true if internet available
        {
            IVPreviewImage = findViewById(R.id.imageView5);

            EditText name = findViewById(R.id.name);
            EditText email = findViewById(R.id.email);
            EditText phone = findViewById(R.id.phone);
            EditText room = findViewById(R.id.room);

            s  = getSharedPreferences("db2",MODE_PRIVATE);
            check = s.getString("check","");
            name.setText(s.getString("name", ""));
            email.setText(s.getString("email", ""));
            phone.setText(s.getString("phone", ""));
            room.setText(s.getString("who", ""));


            imageUriString = s.getString("image", "");
            if(check.equals("1")){
                str_bitmap =getDefaults(PRODUCT_PHOTO, this);
                bitmap=decodeBase64(str_bitmap);
                Log.d("harsh" , bitmap+"");
                IVPreviewImage.setImageBitmap((Bitmap) bitmap);
            }

            IVPreviewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChooser();
                }
            });

            Button b = findViewById(R.id.button3);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s  = getSharedPreferences("db2",MODE_PRIVATE);
                    SharedPreferences.Editor edit = s.edit();
                    edit.putString("name", name.getText().toString());
                    edit.putString("email", email.getText().toString());
                    edit.putString("phone", phone.getText().toString());
                    edit.putString("room", room.getText().toString());
                    edit.apply();
                    Toast.makeText(profile.this , "DETAILS SAVED"  , Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(profile.this , MainActivity.class));
                }
            });


        }
        else
        {
            Toast.makeText(profile.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }




    }
    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();

                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                            s  = getSharedPreferences("db2",MODE_PRIVATE);
                            SharedPreferences.Editor edit = s.edit();
                            edit.putString("check","1");
                            edit.apply();
                            IVPreviewImage.setImageBitmap(selectedImageBitmap);
//---------set the image to bitmap
                            product_image= selectedImageBitmap;
//____________convert image to string
                            String str_bitmap = BitMapToString(product_image);
//__________create two method setDefaults() andgetDefaults()
                            setDefaults(PRODUCT_PHOTO, str_bitmap, this);
                            getDefaults(PRODUCT_PHOTO, this);


                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    public static void setDefaults(String str_key, String value, Context context)
    {
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString(str_key, value);
        edit.apply();
    }
    public static String getDefaults(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static String BitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.DEFAULT);
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,   decodedByte.length);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(profile.this , MainActivity.class));
        super.onBackPressed();
    }
}