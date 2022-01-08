package com.example.hortadovizinho;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class prodAd extends ArrayAdapter<layoutProd>{

    private Context mcontext;


    public prodAd(@NonNull Context context, int resource, @NonNull ArrayList<layoutProd> objects)
    {
        super(context, resource, objects);
        this.mcontext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
