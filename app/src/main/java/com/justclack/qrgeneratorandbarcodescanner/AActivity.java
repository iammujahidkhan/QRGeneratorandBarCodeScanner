package com.justclack.qrgeneratorandbarcodescanner;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;


import com.startapp.android.publish.adsCommon.StartAppAd;

import java.io.File;
import java.util.ArrayList;

public class AActivity extends AppCompatActivity {
    RecyclerView listview;
    String appDirectoryName;
    StableArrayAdapter adapter;
    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txt = findViewById(R.id.filelist);
        listview = findViewById(R.id.datalist);
        listview.hasFixedSize();
        listview.setHasFixedSize(true);
        listview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        appDirectoryName = getResources().getString(R.string.app_name);
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name));
        directory.mkdirs();
        File[] fList = directory.listFiles();
        int a = 1;
        for (int x = 0; x < fList.length; x++) {

            txt.setText("You Have Capture " + String.valueOf(a) + " Photos");
            a++;
        }
        ArrayList<CategoryModelClass> list = new ArrayList<>();
        //get all the files from a directory
        for (File file : fList) {
            if (file.isFile()) {
                list.add(new CategoryModelClass(file.getName(), file.getName(), file.getPath(), file.getAbsolutePath()));
            }
        }
        adapter = new StableArrayAdapter(AActivity.this, list);
        listview.setAdapter(adapter);
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