package com.juanjiga.feeds;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends BaseAdapter {

    private Context context;
    private List entrada;

    public Adapter (Context context, List<String> entrada){
         this.context = context;
         this.entrada = entrada;
    }



    @Override
    public int getCount() {
        return entrada.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return (position);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
