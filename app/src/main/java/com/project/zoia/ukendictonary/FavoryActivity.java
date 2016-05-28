package com.project.zoia.ukendictonary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoryActivity extends AppCompatActivity {
    CastomeAdapter castomeAdapter;
    DataBaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<Word> listWords;
    ListView listV;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favory);


         helper = new DataBaseHelper(this);
        ArrayList<Word> words = helper.getWords();

        castomeAdapter = new CastomeAdapter(this,words);
        listV = (ListView)findViewById(R.id.lVFav);
        listV.setAdapter(castomeAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_button1:

                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.search_button2:

                Intent intent3 = new Intent(this,FavoryActivity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
