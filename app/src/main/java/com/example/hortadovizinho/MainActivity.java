package com.example.hortadovizinho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private int t=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                opcao();
            }
        },t);
    }

    private void opcao()
    {
        Resources res = getResources();
        String [] txt = res.getStringArray(R.array.verif);
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle(""+txt[0]);
        msg.setMessage(""+txt[1]);
        msg.setIcon(android.R.drawable.ic_menu_info_details);

        msg.setPositiveButton(""+txt[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i= new Intent(MainActivity.this, menu.class);
                startActivity(i);
            }
        });
        msg.setNegativeButton(""+txt[3], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity.this.finish();
            }
        });
        msg.setCancelable(false);
        msg.show();
    }
}