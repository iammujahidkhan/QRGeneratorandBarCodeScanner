package com.justclack.qrgeneratorandbarcodescanner;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<CategoryModelClass> list = new ArrayList<>();
    public static SQLiteHelper sqLiteHelper;
    LinearLayoutManager manager;
    PapersAdapter papersAdapter;
    private StartAppAd startAppAd = new StartAppAd(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");
        sqLiteHelper = new SQLiteHelper(HistoryActivity.this);
        sqLiteHelper.createTable(Const.post_table_query);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(HistoryActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        papersAdapter = new PapersAdapter(HistoryActivity.this, list);
        recyclerView.setAdapter(papersAdapter);
        loadData();

       /* recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(HistoryActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        CategoryModelClass data = list.get(position);
                        if (Patterns.WEB_URL.matcher(data.getBar_code()).matches()) {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getBar_code()));
                                startActivity(browserIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(HistoryActivity.this, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
        );*/
        //adsLoad();
    }

    private void loadData() {
        //get data from SQLite
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM " + Const.POST_TABLE_NAME + " order by id desc");
        list.clear();
        while (cursor.moveToNext()) {
            //int id = cursor.getInt(0);

            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String code = cursor.getString(2);
            String date = cursor.getString(3);

            list.add(new CategoryModelClass(id, title, code, date));
            Log.e("SQL VALUES", title);
        }
        papersAdapter.notifyDataSetChanged();
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
