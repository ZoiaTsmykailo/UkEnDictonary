package com.project.zoia.ukendictonary;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


@Root
public class Word implements Parcelable{
    public static final String TABLE_NAME_UA_EN = "WordUaEn";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_UA_WORD = "UaWord";
    public static final String COLUMN_TRANSCRIPTION= "Transcription";
    public static final String COLUMN_EN_WORD = "EnWord";
    public static final String COLUMN_IS_ACTIVE ="IsActiv";

    @Attribute
    public long Id;
    @Attribute
    public String UaWord;
    @Attribute
    public String EnWord;
    @Attribute
    public String Transcription;
    @Attribute
    public int IsActiv = 0;
    public Word(){}
    public Word(long id, String enWord,String transcription, String uaWord, int isActiv  ){
        Id = id;
        EnWord = enWord;
        Transcription = transcription;
        UaWord = uaWord;
        IsActiv = isActiv;
    }

    public Word(String enWord,String transcription, String uaWord ) {

        EnWord = enWord;
        Transcription = transcription;
        UaWord = uaWord;
        this.Id = Id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Id);
        dest.writeString(this.UaWord);
        dest.writeString(this.EnWord);
        dest.writeString(this.Transcription);

    }

    protected Word(Parcel in) {
        this.Id = in.readLong();
        this.UaWord = in.readString();
        this.EnWord = in.readString();
        this.Transcription = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
