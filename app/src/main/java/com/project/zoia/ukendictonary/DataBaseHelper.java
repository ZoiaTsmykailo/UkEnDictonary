package com.project.zoia.ukendictonary;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.UserDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public DataBaseHelper(Context context) {
        super(context, "DictonaryDB4.db", null, 1);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Word.TABLE_NAME_UA_EN + "(" +
                Word.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Word.COLUMN_UA_WORD + " TEXT NOT NULL, " +
                Word.COLUMN_TRANSCRIPTION + " TEXT NOT NULL, " +
                Word.COLUMN_EN_WORD + " TEXT NOT NULL, " +
                Word.COLUMN_IS_ACTIVE + " INTEGER DEFAULT 0);");

        ArrayList<Word> line = getStringFromAssetFile(mContext);
        for (Word word1 : line) {
            insertWord(word1, db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ArrayList<Word> getStringFromAssetFile(Context context) {
        BufferedReader reader = null;
        ArrayList<Word> listWords = new ArrayList<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("Text.txt")));
            String mLine = "";
            String englW = "", transcr = "", ukrW = "";
            while ((mLine = reader.readLine()) != null) {
                String[] words = mLine.split(",");
                for (int i = 0; i < words.length; i++) {
                    englW = words[0];
                    transcr = words[1];
                    ukrW = words[2];
                }
                Word word = new Word(englW, transcr, ukrW);
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

    public long insertWord(Word word, SQLiteDatabase db) {
        long id = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("UaWord", word.UaWord);
            values.put("Transcription", word.Transcription);
            values.put("EnWord", word.EnWord);
            values.put("IsActiv", word.IsActiv);
            db.insert("WordUaEn", null, values);


        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return id;
    }

    public long updateValue(Word word) {
        long id = word.Id;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Word.COLUMN_IS_ACTIVE, 1);
            db.update(Word.TABLE_NAME_UA_EN, contentValues, Word.COLUMN_EN_WORD + " =?", new String[]{word.EnWord});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("Select * from " + Word.TABLE_NAME_UA_EN + " where " +
                    Word.COLUMN_IS_ACTIVE + " = 1", null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    String eWord = cursor.getString(cursor.getColumnIndex(Word.COLUMN_EN_WORD));

                    String uaWord = cursor.getString(cursor.getColumnIndex(Word.COLUMN_UA_WORD));
                    String transcr = cursor.getString(cursor.getColumnIndex(Word.COLUMN_TRANSCRIPTION));
                    long idWord = cursor.getLong(cursor.getColumnIndex(Word.COLUMN_ID));
                    int isAct = cursor.getInt(cursor.getColumnIndex(Word.COLUMN_IS_ACTIVE));

                    Word mWord = new Word(idWord, eWord, transcr, uaWord, isAct);
                    words.add(mWord);

                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return words;
    }

    /*public Word getEnWord(String enword){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = null;
        try{
            cursor = db.query(Word.TABLE_NAME_UA_EN, null, null, null, null, null, null);

            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Word word = new Word();
                    word.Id = cursor.getLong(cursor.getColumnIndex(Word.COLUMN_ID));
                    word.EnWord = cursor.getString(cursor.getColumnIndex(Word.COLUMN_EN_WORD));
                    word.Transcription = cursor.getString(cursor.getColumnIndex(Word.COLUMN_TRANSCRIPTION));
                    word.UaWord = cursor.getString(cursor.getColumnIndex(Word.COLUMN_UA_WORD));
                    if (enword.equals(word.UaWord)||enword.equals(word.EnWord)){
                        return word;


                    }
                    cursor.moveToNext();
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
        }
        return null;
    }*/
}
