package com.project.zoia.ukendictonary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MShowResultActivity extends AppCompatActivity {
    public static final String EXTRA_WORD_FAV="com.project.zoia.ukendictonary.WORDFAV";
    TextView enWord;
    TextView transcr;
    TextView uaWord;
    Button btn;
    public  ArrayList<String> kayWord ;
    DataBaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mshow_result);
        Intent intent = getIntent();
        final Word word = intent.getParcelableExtra(MainActivity.EXTRA_WORD);
        enWord = (TextView)findViewById(R.id.tWEn);
        transcr = (TextView)findViewById(R.id.tWTr);
        uaWord = (TextView)findViewById(R.id.tWUa);
        enWord.setText(word.EnWord);
        transcr.setText(word.Transcription);
        uaWord.setText(word.UaWord);
        kayWord = new ArrayList<>();
        btn = (Button)findViewById(R.id.btFav);
        helper = new DataBaseHelper(MShowResultActivity.this);
        db = helper.getWritableDatabase();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               long result =  helper.updateValue(word);

            }
        });

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
