package com.example.hortadovizinho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class produto extends BaseAdapter
{
    Context context;
    String pr[],ft[];
    LayoutInflater inflater;

    public produto(Context context,String[] pr,String[] ft)
    {
    this.context=context;
    this.pr=pr;
    this.ft=ft;
    inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return pr.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.activity_prod,null);
        TextView textView=(TextView)convertView.findViewById(R.id.txtP);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.imgP);
        textView.setText(pr[position]);
        Glide.with(convertView).load(ft[position]).into(imageView);
        return convertView;
    }
}
