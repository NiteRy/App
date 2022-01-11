package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hortadovizinho.clas.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class produtos extends AppCompatActivity implements RecycleViewInterface
{


    ListView txt;

    String p,list="";

    int ad=0;

    float total,somatorio;

    Produto produto;

    Button comp,emite;

    ArrayList<String> arrayList;

    ArrayAdapter arrayAdapter;

    int numC;

    String fat;

    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;

    RecyclerView recyclerView;
    DatabaseReference database;
    prodHolder myAdapter;
    ArrayList<Produto> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        txt=findViewById(R.id.compra);
        comp=findViewById(R.id.comp);
        emite=findViewById(R.id.emite);

        emite.setVisibility(View.GONE);
        comp.setVisibility(View.GONE);


        recyclerView=findViewById(R.id.lis);

        database=FirebaseDatabase.getInstance().getReference("produtos");
        recyclerView.setHasFixedSize(true);

        lista=new ArrayList<>();
        myAdapter=new prodHolder(this,lista,this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(produtos.this, android.R.layout.simple_list_item_1,arrayList);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren())
                {
                    produto=data.getValue(Produto.class);
                    lista.add(produto);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query check;

        check=reference.orderByChild(FirebaseAuth.getInstance().getUid());


        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                p=snapshot.child(FirebaseAuth.getInstance().getUid()).child("nome").getValue(String.class);
                if(p.equals("Nite"))
                {
                    txt.setVisibility(View.GONE);
                    TextView tes=findViewById(R.id.lc);
                    tes.setText("Editar produtos");
                }
                else
                {
                    comp.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if(!list.equals(""))
                            {
                                arrayList.add("" + list + "\n");
                                txt.setAdapter(arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();
                                somatorio += total;
                                ad = 0;
                                emite.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            arrayList.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                            emite.setVisibility(View.GONE);
                            comp.setVisibility(View.GONE);
                            Toast.makeText(produtos.this, "A remover produto "+produto.getNome(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.barra,menu);

        MenuItem item = menu.findItem(R.id.pro);

        if (item.isVisible())
        {
            item.setVisible(false);

        } else
        {
            Log.e(String.valueOf(this),"erro");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i=item.getItemId();
        if(i==R.id.perfil)
        {
            Intent v= new Intent(produtos.this,perfil.class);
            startActivity(v);

        }
        if(i==R.id.exit)
        {

            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();


            editor.apply();

            Intent inte = new Intent(produtos.this, menu.class);
            startActivity(inte);
            finish();
        }
        return super.onOptionsItemSelected(item);
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
    public void tras(View view)
    {
        Intent i= new Intent(produtos.this, welcome.class);
        startActivity(i);
    }

    public void fatura(View view)
    {

        FileOutputStream num = null;
        try {
            num = openFileOutput(String.valueOf(numC), MODE_PRIVATE);
            numC++;
            String val = String.valueOf(numC);
            num.write(val.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (num != null) {
                try {
                    num.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String no = "Fatura de " + p + " ";
        no = no + " " + numC + ".txt";
        Calendar calendar = Calendar.getInstance();
        String data = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        fat = "Horta do vizinho\nData: " + data + "\nNome do cliente: " + p + "\nProdutos: " + arrayList.toString() + "\nTotal: " + somatorio + "€";

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "" + no);

        startActivityForResult(intent, 1);


        arrayList.removeAll(arrayList);
        arrayList.add("");
        txt.setAdapter(arrayAdapter);
        somatorio = 0;
        list="";
        emite.setVisibility(View.GONE);
        comp.setVisibility(View.GONE);
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
                Toast.makeText(this, "A cancelar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(int position)
    {

        produto=lista.get(position);

        if(!p.equals("Nite"))
        {
            comp.setVisibility(View.VISIBLE);
            Toast.makeText(produtos.this, "Adicionando "+produto.getNome(), Toast.LENGTH_SHORT).show();
            ad++;
            total=ad*produto.getPreco();
            list="Produto: "+produto.getNome()+" Quantidade: "+ad+" Preço: "+produto.getPreco()+" Subtotal: "+total+"€";
        }
        else
        {
            Toast.makeText(this, ""+produto.getNome(), Toast.LENGTH_SHORT).show();
        }

    }
}