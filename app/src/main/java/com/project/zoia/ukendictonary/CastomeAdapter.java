package com.project.zoia.ukendictonary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CastomeAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Word> objects;

    public CastomeAdapter(Context ctx, ArrayList<Word> objects) {
        this.ctx = ctx;
        this.objects = objects;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = lInflater.inflate(R.layout.listw, parent, false);

        ((TextView) convertView.findViewById(R.id.tvEW)).setText(objects.get(position).EnWord);
        ((TextView) convertView.findViewById(R.id.tvTranscr)).setText(objects.get(position).Transcription);
        ((TextView) convertView.findViewById(R.id.tvUaW)).setText(objects.get(position).UaWord);

        return convertView;
    }
}
