package com.justclack.qrgeneratorandbarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveCodeActivity extends AppCompatActivity implements View.OnClickListener {
    public static SQLiteHelper sqLiteHelper;
    String bar_code = "";
    EditText title;
    TextView code;
    private StartAppAd startAppAd = new StartAppAd(this);
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_code);

        title = findViewById(R.id.title);
        title.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        code = findViewById(R.id.code);
        save = findViewById(R.id.save);

        save.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Save Code");
        sqLiteHelper = new SQLiteHelper(SaveCodeActivity.this);
        sqLiteHelper.createTable(Const.post_table_query);
        bar_code = getIntent().getStringExtra("bar_code");
        code.setText("Code :" + bar_code);
    }

    @Override
    public void onClick(View v) {
        if (v == save) {

            if (TextUtils.isEmpty(title.getText())) {
                title.requestFocus();
                title.setError("Please Enter Some Title");
            } else if (!TextUtils.isEmpty(title.getText())) {
                String date = new SimpleDateFormat("dd-MMM-yyyy  hh:mm a").format(new Date());
                sqLiteHelper.insertData(title.getText().toString().trim(), bar_code.toString().trim(), date);
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                startAppAd.showAd(new AdDisplayListener() {

                    /**
                     * Callback when Ad has been hidden
                     * @param
                     */
                    @Override
                    public void adHidden(Ad ad) {
                        startActivity(new Intent(SaveCodeActivity.this, HistoryActivity.class));
                        finish();
                    }

                    /**
                     * Callback when ad has been displayed
                     * @param
                     */
                    @Override
                    public void adDisplayed(Ad ad) {

                    }

                    /**
                     * Callback when ad has been clicked
                     * @param
                     */
                    @Override
                    public void adClicked(Ad arg0) {

                    }

                    /**
                     * Callback when ad not displayed
                     * @param
                     */
                    @Override
                    public void adNotDisplayed(Ad arg0) {
                        startActivity(new Intent(SaveCodeActivity.this, HistoryActivity.class));
                        finish();
                    }
                });

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