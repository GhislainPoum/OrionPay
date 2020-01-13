package com.poum.orionpay;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.poum.orionpay.Models.DataTransaction;
import com.poum.orionpay.PaymentMode.OptionPayActivity;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressWarnings("ALL")
public class SmsActivity extends AppCompatActivity {

    private static final int STORAGE_CODE = 100;
    EditText main_no_id;
    EditText main_smsField_id;

    Button main_smsSendBtn_id;
    Button btn_alert_pay;


    DatabaseReference reference;

    DataTransaction dataTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main_no_id = findViewById(R.id.main_no_id);
        main_smsField_id = findViewById(R.id.main_smsField_id);
        main_smsSendBtn_id = findViewById(R.id.main_smsSendBtn_id);
        btn_alert_pay = findViewById(R.id.btn_alert_pay);

        main_smsSendBtn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:" + main_no_id.getText().toString()));
                intent.putExtra("sms_body", main_smsField_id.getText().toString());
                startActivity(intent);

            }
        });

        btn_alert_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataTransaction = new DataTransaction();

                reference = FirebaseDatabase.getInstance().getReference().child("DataTransaction");

                dataTransaction.setMain_no_id(main_no_id.getText().toString().trim());
                dataTransaction.setMain_smsField_id(main_smsField_id.getText().toString().trim());


                reference.push().setValue(dataTransaction);
                Toast.makeText(SmsActivity.this, "Save",Toast.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){

                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);

                    } else {
                        savePdf();

                    }
                } else {
                    savePdf();

                }

                Intent intent = new Intent(SmsActivity.this, OptionPayActivity.class);
                startActivity(intent);
            }
        });


    }

    private void savePdf() {
        Document mDoc = new Document();
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            mDoc.open();
            String mText = main_smsField_id.getText().toString();
            mDoc.addAuthor("ORION");

            mDoc.add(new Paragraph(mText));

            mDoc.close();

            Toast.makeText(this, mFileName + ".pdf\nis saved to\n" + mFilePath, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE :{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePdf();

                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
