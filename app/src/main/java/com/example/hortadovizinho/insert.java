package com.example.hortadovizinho;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hortadovizinho.put.Interface;
import com.example.hortadovizinho.put.Produto;
import com.example.hortadovizinho.put.retrofit;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class insert extends AppCompatActivity {

    Button btn1,btn2;

    CheckBox check;

    TextInputLayout t1,t2,t3,t4,t5,t6,t7,t8,t9;

    boolean pub;

    String s1,s2,s3,s4;

    DatePickerDialog.OnDateSetListener setListener;

    String nome,descricao,preco,promo,datafixado,datalimite,stock,foto,slug;

    TextInputEditText txt,txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        t1=findViewById(R.id.nome);
        t2=findViewById(R.id.descricao);
        t3=findViewById(R.id.preco);
        t4=findViewById(R.id.promo);

        t5=findViewById(R.id.dataFixado);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        txt=findViewById(R.id.pr);
        txt2=findViewById(R.id.pr2);

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        insert.this, android.R.style.ThemeOverlay_Material_Dark_ActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=day+"-"+month+"-"+year;
                t5.getEditText().setText(date);
            }
        };
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        insert.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month+=1;
                        String date=day+"-"+month+"-"+year;
                        t5.getEditText().setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        t6=findViewById(R.id.dataLimite);
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        insert.this, android.R.style.ThemeOverlay_Material_Dark_ActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=day+"-"+month+"-"+year;
                t6.getEditText().setText(date);
            }
        };
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        insert.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month+=1;
                        String date=day+"-"+month+"-"+year;
                        t6.getEditText().setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        t7=findViewById(R.id.stock);
        t8=findViewById(R.id.foto);
        t9=findViewById(R.id.slug);

        nome=t1.getEditText().toString();
        descricao=t2.getEditText().toString();

        btn1=findViewById(R.id.btn);

        btn2=findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });

        AutoCompleteTextView c1=findViewById(R.id.unidade);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.unidade));
        c1.setAdapter(adapter);
        c1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                s1 = parent.getItemAtPosition(position).toString();
            }
        });


        AutoCompleteTextView c2=findViewById(R.id.categoria);
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categoria));
        c2.setAdapter(ad2);
        c2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                s2 = parent.getItemAtPosition(position).toString();
            }
        });


        AutoCompleteTextView c3=findViewById(R.id.subcat);
        ArrayAdapter<String> ad3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.subcat));
        c3.setAdapter(ad3);
        c3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                s3=parent.getItemAtPosition(position).toString();
            }
        });


        AutoCompleteTextView c4=findViewById(R.id.seccao);
        ArrayAdapter<String> ad4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.seccao));
        c4.setAdapter(ad4);
        c4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                s4 = parent.getItemAtPosition(position).toString();
            }
        });

        check=findViewById(R.id.pub);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.isChecked())
                {
                    pub=true;
                }
                else
                {
                    pub=false;
                }
            }
        });

    }

    private void clean()
    {

    }
    private void btnClick()
    {

        nome=t1.getEditText().getText().toString();
        descricao=t2.getEditText().getText().toString();
        preco=t3.getEditText().getText().toString();
        promo=t4.getEditText().getText().toString();
        datafixado=t5.getEditText().getText().toString();
        datalimite=t6.getEditText().getText().toString();
        stock=t7.getEditText().getText().toString();
        foto=t8.getEditText().getText().toString();
        slug=t9.getEditText().getText().toString();

        Interface inter= retrofit.getRetrofit().create(Interface.class);
        Call<Produto> call=inter.getUserInformation(
                ""+nome,
                ""+descricao,
                ""+s1,
                preco,
                promo,
                ""+datafixado,
                ""+datalimite,
                stock,
                "",
                ""+slug,
                pub,
                "",
                "",
                "");


        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                Log.e(TAG, "onResponse: "+response.code());
                Log.e(TAG, "onResponse id: "+response.body().getId());
                Log.e(TAG, "onResponse nome: "+response.body().getNome());
                Log.e(TAG, "onResponse descricao: "+response.body().getDescricao());
                Log.e(TAG, "onResponse unidade: "+response.body().getUnidade());
                Log.e(TAG, "onResponse preco: "+response.body().getPreco());
                Log.e(TAG, "onResponse preco_promo: "+response.body().getPreco_promo());
                Log.e(TAG, "onResponse data promo fixado: "+response.body().getData_precopromo_fixado());
                Log.e(TAG, "onResponse data promo limite: "+response.body().getData_precopromo_limite());
                Log.e(TAG, "onResponse stock: "+response.body().getStock());
                Log.e(TAG, "onResponse foto: "+response.body().getFoto());
                Log.e(TAG, "onResponse slug: "+response.body().getCampo_slug());
                Log.e(TAG, "onResponse timestamp: "+response.body().getTimestamp_alterado());
                Log.e(TAG, "onResponse publicado: "+response.body().getPublicado());
                Log.e(TAG, "onResponse categoria: "+response.body().getCategoria());
                Log.e(TAG, "onResponse subcategoria: "+response.body().getSub_categoria());
                Log.e(TAG, "onResponse seccao: "+response.body().getSeccao());
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t)
            {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}