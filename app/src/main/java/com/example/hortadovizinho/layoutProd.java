package com.example.hortadovizinho;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class layoutProd {

        private String pr;
        private String foto;

    public layoutProd(String pr, String foto) {
        this.pr = pr;
        this.foto = foto;
    }
    public String pr(){return pr;}

    public void spr(String pr)
    {this.pr=pr;}

    public String foto(){return foto;}

    public void sfoto(String foto)
    {this.foto=foto;}
}
