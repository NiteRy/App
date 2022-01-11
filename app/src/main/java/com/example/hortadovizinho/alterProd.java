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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class alterProd extends AppCompatActivity {

    EditText a,b,c,dd,e,f,g,h,i,j,k,l,m,n,o;
    TextView cod;

    String url = "https://j6ninhas.eu.pythonanywhere.com/api/produtos/";


    String id="",no="",d="",u="",p="",pp="",df="",dl="",fo="",s="",cs="",t="",pu="",ca="",sc="",se="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_prod);

        a=findViewById(R.id.txt1);
        b=findViewById(R.id.txt2);
        c=findViewById(R.id.txt3);
        dd=findViewById(R.id.txt4);
        e=findViewById(R.id.txt5);
        f=findViewById(R.id.txt6);
        g=findViewById(R.id.txt7);
        h=findViewById(R.id.txt8);
        i=findViewById(R.id.txt9);
        j=findViewById(R.id.txt10);
        k=findViewById(R.id.txt11);
        l=findViewById(R.id.txt12);
        m=findViewById(R.id.txt13);
        n=findViewById(R.id.txt14);
        o=findViewById(R.id.txt15);
        cod=findViewById(R.id.sid);

        Bundle ex=getIntent().getExtras();
        if(ex!=null)
        {
            id=ex.getString("id");
            no=ex.getString("name");
            d=ex.getString("descr");
            u=ex.getString("unid");
            p=ex.getString("preco");
            pp=ex.getString("promo");
            df=ex.getString("fix");
            dl=ex.getString("lim");
            fo=ex.getString("foto");
            s=ex.getString("stock");
            cs=ex.getString("slug");
            t=ex.getString("time");
            pu=ex.getString("pub");
            ca=ex.getString("cat");
            sc=ex.getString("subcat");
            se=ex.getString("sec");
        }
        cod.setText(id);
        a.setText(no);
        b.setText(d);
        c.setText(u);
        dd.setText(p);
        e.setText(pp);
        f.setText(df);
        g.setText(dl);
        h.setText(fo);
        i.setText(s);
        j.setText(cs);
        k.setText(t);
        l.setText(pu);
        m.setText(ca);
        n.setText(sc);
        o.setText(se);

        Button button = findViewById(R.id.conf);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                try
                {
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("POST");

                    JSONObject cred = new JSONObject();
                    cred.put("nome",""+no);
                }

                catch (MalformedURLException malformedURLException)
                {
                    malformedURLException.printStackTrace();
                }

                catch (UnsupportedEncodingException unsupportedEncodingException)
                {
                    unsupportedEncodingException.printStackTrace();
                }

                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }

                catch (JSONException jsonException)
                {
                    jsonException.printStackTrace();
                }

            }
        });

        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(alterProd.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),alterProd.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    alterProd.this.finishAffinity();
                }
            });
            net.show();
        }
    }

    private boolean teste(alterProd prod)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) prod.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    public void back(View v)
    {
        Intent i= new Intent(alterProd.this, welcome.class);
        startActivity(i);
    }
}