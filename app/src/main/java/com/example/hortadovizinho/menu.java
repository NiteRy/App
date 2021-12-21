package com.example.hortadovizinho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void sign(View v)
    {
        Intent i= new Intent(menu.this, conta.class);
        startActivity(i);
    }

    public void log(View v)
    {
        Intent i= new Intent(menu.this, login.class);
        startActivity(i);
    }

    public void exit(View v)
    {
        menu.this.finishAffinity();
    }

    public void prod(View v) {

        Intent i= new Intent(menu.this, produtos.class);
        startActivity(i);

    }
}