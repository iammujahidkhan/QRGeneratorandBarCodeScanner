package com.justclack.qrgeneratorandbarcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.startapp.android.publish.adsCommon.StartAppAd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    Button save;
    private StartAppAd startAppAd = new StartAppAd(this);
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        image = findViewById(R.id.image);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bmp);
    }

    @Override
    public void onClick(View v) {
        if (v == save) {
            saveImage(bmp);
        }
    }

    private void saveImage(Bitmap data) {
        File myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name));
        myDir.mkdirs();
        File file = new File(myDir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            data.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Image Saved Successfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SaveActivity.this, AActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
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