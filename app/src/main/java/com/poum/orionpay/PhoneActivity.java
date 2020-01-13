package com.poum.orionpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.poum.orionpay.Models.CountryData;

@SuppressWarnings("ALL")
public class PhoneActivity extends AppCompatActivity {

    EditText phone_number;
    Spinner country;

    Button btn_phone;

    FirebaseAuth firebaseAuth;

    FirebaseUser firebaseUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        country = (Spinner) findViewById(R.id.country);
        phone_number = (EditText) findViewById(R.id.phone_number);
        btn_phone = (Button) findViewById(R.id.btn_phone);

        country.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, CountryData.CountryNames));

        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(PhoneActivity.this);
                progressDialog.setTitle("Checking");
                progressDialog.setMessage("wait please ...");
                progressDialog.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
                progressDialog.show();
                progressDialog.setCancelable(true);

                final String code = CountryData.CountryCodes[country.getSelectedItemPosition()];
                final String number = phone_number.getText().toString().trim();


                if (number.isEmpty() || number.length() < 9){
                    phone_number.setError("A valid number is required");
                    phone_number.requestFocus();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                            String phonenumber = "+" + code + number;
                            Intent intent = new Intent(PhoneActivity.this, VerificationActivity.class);
                            intent.putExtra("phonenumber", phonenumber);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent intent = new Intent(PhoneActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
