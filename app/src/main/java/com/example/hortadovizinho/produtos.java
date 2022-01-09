package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


public class produtos extends AppCompatActivity
{
    String result="";

    String id,nome,descricao,unidade,preco,promo,dataFix,dataLim,foto,stock,slug,timestamp,publicado,categoria,sub,seccao;


    String[] str;


    String[] ident;
    String[] name;
    String[] desc;
    String[] unit;
    String[] pri;
    String[] priPromo;
    String[] dtPromoFix;
    String[] dtPromoLim;
    String[] ft;
    String[] st;
    String[] cSlug;
    String[] time;
    String[] pub;
    String[] cat;
    String[] subCat;
    String[] section;

    String url = "https://api.npoint.io/b8b2f5d8ddbc3beefa4e";

    ListView listView,txt;

    String p,list;

    int i,ad=0;

    float total,somatorio;


    Button comp,clear;

    ArrayList<String> arrayList;

    ArrayAdapter arrayAdapter;

    int numC;

    String fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);


        txt=findViewById(R.id.compra);
        comp=findViewById(R.id.comp);
        clear=findViewById(R.id.emite);

        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(produtos.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),produtos.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    produtos.this.finishAffinity();
                }
            });
            net.show();
        }

        listView=(ListView) findViewById(R.id.lis);
        new json().execute();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query check;

        check=reference.orderByChild(FirebaseAuth.getInstance().getUid());


        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                p=snapshot.child(FirebaseAuth.getInstance().getUid()).child("nome").getValue(String.class);
                if(p.equals("Nit"))
                {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i= new Intent(getApplicationContext(), prod.class);
                            i.putExtra("id",ident[position]);
                            i.putExtra("name",name[position]);
                            i.putExtra("descr",desc[position]);
                            i.putExtra("unid",unit[position]);
                            i.putExtra("preco",pri[position]);
                            i.putExtra("promo",priPromo[position]);
                            i.putExtra("fix",dtPromoFix[position]);
                            i.putExtra("lim",dtPromoLim[position]);
                            i.putExtra("foto",ft[position]);
                            i.putExtra("stock",st[position]);
                            i.putExtra("slug",cSlug[position]);
                            i.putExtra("time",time[position]);
                            i.putExtra("pub",pub[position]);
                            i.putExtra("cat",cat[position]);
                            i.putExtra("subcat",subCat[position]);
                            i.putExtra("sec",section[position]);
                            startActivity(i);
                        }
                    });
                }

                else
                {
                    arrayList=new ArrayList<>();
                    arrayAdapter=new ArrayAdapter(produtos.this, android.R.layout.simple_list_item_1,arrayList);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {

                            Toast.makeText(produtos.this, "Adicionando "+name[position], Toast.LENGTH_SHORT).show();
                            ad++;
                            total=ad*Float.parseFloat(pri[position]);
                            list="Produto: "+name[position]+" Quantidade: "+ad+" Preço: "+pri[position]+" Subtotal: "+total+"€";

                            comp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {

                                    arrayList.add(""+list+"\n");
                                    txt.setAdapter(arrayAdapter);
                                    arrayAdapter.notifyDataSetChanged();
                                    somatorio+=total;
                                    ad=0;
                                }
                            });
                        }
                    });

                    txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            arrayList.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean teste(produtos produtos)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) produtos.getSystemService(Context.CONNECTIVITY_SERVICE);

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

                str=new String[jsonArray.length()];

                ident=new String[jsonArray.length()];
                name=new String[jsonArray.length()];
                desc=new String[jsonArray.length()];
                unit=new String[jsonArray.length()];
                pri=new String[jsonArray.length()];
                priPromo=new String[jsonArray.length()];
                dtPromoFix=new String[jsonArray.length()];
                dtPromoLim=new String[jsonArray.length()];
                st=new String[jsonArray.length()];
                ft=new String[jsonArray.length()];
                cSlug=new String[jsonArray.length()];
                time=new String[jsonArray.length()];
                pub=new String[jsonArray.length()];
                cat=new String[jsonArray.length()];
                subCat=new String[jsonArray.length()];
                section=new String[jsonArray.length()];

                for(i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    id=jsonObject.getString("id")+"\n";
                    nome=jsonObject.getString("nome")+"\n";
                    descricao=jsonObject.getString("descricao")+"\n";
                    unidade=jsonObject.getString("unidade")+"\n";
                    preco=jsonObject.getString("preco")+"\n";
                    promo=jsonObject.getString("preco_promo")+"\n";
                    dataFix=jsonObject.getString("data_precopromo_fixado")+"\n";
                    dataLim=jsonObject.getString("data_precopromo_limite")+"\n";
                    foto=jsonObject.getString("foto")+"\n";
                    stock=jsonObject.getString("stock")+"\n";
                    slug=jsonObject.getString("campo_slug")+"\n";;
                    timestamp=jsonObject.getString("timestamp_alterado")+"\n";
                    publicado=jsonObject.getString("publicado")+"\n";
                    categoria=jsonObject.getString("categoria")+"\n";
                    sub=jsonObject.getString("sub_categoria")+"\n";
                    seccao=jsonObject.getString("seccao")+"\n";

                    str[i]="Nome: "+nome+"Descrição: "+descricao+"Unidade: "+unidade+"Preco: "+preco+"Quantidade em stock: "+stock;

                    ident[i]=""+id;
                    name[i]=""+nome;
                    desc[i]=""+descricao;
                    unit[i]=""+unidade;
                    pri[i]=""+preco;
                    priPromo[i]=""+promo;
                    dtPromoFix[i]=""+dataFix;
                    dtPromoLim[i]=""+dataLim;
                    ft[i]=""+foto;
                    st[i]=""+stock;
                    cSlug[i]=""+slug;
                    time[i]=""+timestamp;
                    pub[i]=""+publicado;
                    cat[i]=""+categoria;
                    subCat[i]=""+sub;
                    section[i]=""+seccao;

                }
                produto produto=new produto(getApplicationContext(),str,ft);
                listView.setAdapter(produto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void tras(View view)
    {
        Intent i= new Intent(produtos.this, welcome.class);
        startActivity(i);
    }

    public void fatura(View view)
    {
            FileOutputStream num=null;
            try
            {
                num=openFileOutput(String.valueOf(numC),MODE_PRIVATE);
                numC++;
                String val= String.valueOf(numC);
                num.write(val.getBytes());
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(num!=null)
                {
                    try
                    {
                        num.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            String no="Fatura de "+p+" ";
            no=no+" "+numC+".txt";
            Calendar calendar=Calendar.getInstance();
            String data= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            fat="Horta do vizinho\nData: "+data+"\nNome do cliente: "+p+"\nProdutos: "+arrayList.toString()+"\nTotal: "+somatorio+"€";

        Intent intent=new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE,""+no);

        startActivityForResult(intent,1);


            arrayList.removeAll(arrayList);
            arrayList.add("");
            txt.setAdapter(arrayAdapter);
            somatorio=0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                Uri uri=data.getData();
                try
                {
                    OutputStream outputStream=getContentResolver().openOutputStream(uri);
                    outputStream.write(fat.getBytes());
                    outputStream.close();
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            }
        }
    }

}