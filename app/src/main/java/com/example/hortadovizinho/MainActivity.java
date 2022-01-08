package com.example.hortadovizinho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private int t=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(MainActivity.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setIcon(android.R.drawable.ic_menu_info_details);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    MainActivity.this.finishAffinity();
                }
            });
            net.show();
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    opcao();
                }
            },t);
        }

    }

    private boolean teste(MainActivity main)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) main.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo conn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifi!=null && wifi.isConnected()) || (conn!=null && conn.isConnected()))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private void opcao()
    {
        Resources res = getResources();
        String [] txt = res.getStringArray(R.array.ver);
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle(""+txt[0]);
        msg.setMessage(""+txt[1]);
        msg.setIcon(android.R.drawable.ic_menu_info_details);

        msg.setPositiveButton(""+txt[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(welcome.EMAIL_KEY!=null)
                {
                    Intent i= new Intent(MainActivity.this, welcome.class);
                    startActivity(i);
                }
                else
                {
                    Intent i= new Intent(MainActivity.this, menu.class);
                    startActivity(i);
                }

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