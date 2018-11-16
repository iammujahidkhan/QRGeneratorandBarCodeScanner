package com.justclack.qrgeneratorandbarcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.io.ByteArrayOutputStream;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    EditText title;
    Button generate;
    Spinner spn_bookOldNew;
    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        title = findViewById(R.id.title);
        generate = findViewById(R.id.generate);
        spn_bookOldNew = findViewById(R.id.spn_bookOldNew);
        generate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == generate) {
            if (TextUtils.isEmpty(title.getText())) {
                title.setError("Required");
                title.requestFocus();
            } else if (spn_bookOldNew.getSelectedItem().toString().trim().equals("Choose Format")) {
                spn_bookOldNew.requestFocus();
                Toast.makeText(this, "Choose Format", Toast.LENGTH_SHORT).show();
            } else {
                String text = title.getText().toString().trim();// Whatever you need to encode in the QR code
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = null;
                try {
                    if (spn_bookOldNew.getSelectedItem().toString().trim().equals("QR_CODE")) {
                        bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                    }
                    if (spn_bookOldNew.getSelectedItem().toString().trim().equals("AZTEC")) {
                        bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.AZTEC, 200, 200);
                    }
                    if (spn_bookOldNew.getSelectedItem().toString().trim().equals("CODE_128")) {
                        bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128, 200, 200);
                    }
                    if (spn_bookOldNew.getSelectedItem().toString().trim().equals("PDF_417")) {
                        bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.PDF_417, 512, 512);
                    }
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    //imageView.setImageBitmap(bitmap);
                    //newBitMap = bitmap;
                    //test.setText(String.valueOf(bitmap));
                    //Convert to byte array
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                    //test.setText(String.valueOf(byteArray));
                    Intent in1 = new Intent(this, SaveActivity.class);
                    in1.putExtra("image", byteArray);
                    startActivity(in1);
                    finish();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    public void onBackPressed() {
        //startAppAd.onBackPressed();
        super.onBackPressed();
    }


}
