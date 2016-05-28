package com.project.zoia.ukendictonary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_WORD="com.project.zoia.ukendictonary.WORD";
    public static final int REQUEST_CODE = 2;
    DataBaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<String> words;
    EditText et;
    ListView list;
    ArrayAdapter<String> adapter;
    ArrayList<Word> listWords;
    Word wordDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         helper = new DataBaseHelper(this);
         db = helper.getWritableDatabase();
        list = (ListView)findViewById(R.id.listView);

        /*ArrayList<Word> line =getStringFromAssetFile( this);
        for (Word word1: line){
            helper.insertWord(word1);
        }*/

         et = (EditText)findViewById(R.id.text);


        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String word = et.getText().toString();
                if (word.length() >= 3) {
                    Log.d("SQLITE",Word.COLUMN_EN_WORD);
                    try {
                        cursor = db.rawQuery("Select * from " + Word.TABLE_NAME_UA_EN + " where " +
                                Word.COLUMN_EN_WORD + " like '" + word + "%' ", null);
                        words = new ArrayList<String>();
                        listWords = new ArrayList<Word>();
                        if (cursor.moveToFirst()) {
                            while (!cursor.isAfterLast()) {

                                String eWord = cursor.getString(cursor.getColumnIndex(Word.COLUMN_EN_WORD));

                                String uaWord = cursor.getString(cursor.getColumnIndex(Word.COLUMN_UA_WORD));
                                String transcr = cursor.getString(cursor.getColumnIndex(Word.COLUMN_TRANSCRIPTION));
                                long idWord = cursor.getLong(cursor.getColumnIndex(Word.COLUMN_ID));

                                Word mWord = new Word(eWord,transcr,uaWord);
                                listWords.add(mWord);
                                words.add(eWord);


                                cursor.moveToNext();
                            }
                        }

                         adapter = new ArrayAdapter<String>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                android.R.id.text1,
                                words
                        );
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String wordInList = parent.getItemAtPosition(position).toString();
                               for (int i=0;i<listWords.size();i++){
                                   if (wordInList.equals(listWords.get(i).EnWord)||wordInList.equals(listWords.get(i).UaWord)){
                                        wordDone = new Word(listWords.get(i).EnWord,listWords.get(i).Transcription,listWords.get(i).UaWord);
                                       Intent intent = new Intent(MainActivity.this,MShowResultActivity.class);
                                       intent.putExtra(EXTRA_WORD, wordDone);
                                       startActivity(intent);
                                      // startActivityForResult(intent,REQUEST_CODE);
                                   }
                               }
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    ArrayList<Word> getStringFromAssetFile(MainActivity activity){
        BufferedReader reader = null;
        ArrayList<Word> listWords = new ArrayList<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.getAssets().open("Text.txt")));
            String mLine= "" ;
            String englW="", transcr="",ukrW="";
            while ((mLine = reader.readLine()) != null) {
                String []words =mLine.split(",");
                for (int i=0;i<words.length;i++){
                    englW = words[0];
                    transcr = words[1];
                    ukrW = words[2];
                }
                Word word= new Word(englW,transcr,ukrW);
                listWords.add(word);
            }
            return listWords;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    return listWords;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

}
