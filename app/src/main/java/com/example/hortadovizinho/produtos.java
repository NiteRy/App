package com.example.hortadovizinho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class produtos extends AppCompatActivity
{
    String result="";
    String mostra="";
    String peso="";
    String url = "https://j6ninhas.eu.pythonanywhere.com/api/produtos/";
    TextView textView,textView2;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        new json().execute();
        textView=findViewById(R.id.pro);
        textView2=findViewById(R.id.pro2);
        imageView=findViewById(R.id.imageView);
    }

    class json extends AsyncTask<Void,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL link=new URL(url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) link.openConnection();
                InputStreamReader streamReader=new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader=new BufferedReader(streamReader);
                StringBuilder builder=new StringBuilder();
                String linha;
                while ((linha= bufferedReader.readLine())!=null)
                {
                    builder.append(linha);
                }
                result=builder.toString();
                Log.e("Json ",builder.toString());
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONArray jsonArray= new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    Log.e("Json Object ",jsonObject.getString("nome"));
                    Log.e("Json foto ",jsonObject.getString("foto"));
                    mostra=mostra+jsonObject.getString("nome")+"\n";
                    peso=peso+jsonObject.getString("preco")+"\n";
                    textView.setText(mostra);
                    textView2.setText(peso);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}