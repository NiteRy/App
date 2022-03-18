package com.example.hortadovizinho;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hortadovizinho.clas.Produto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;

public class alterProd extends AppCompatActivity {

    Button btn1,btn2;

    TextInputLayout anome,adesc,aunit,apreco,apromo,adataFix,adataLim,astock,aslug,asec;

    String nome="",descricao="",uni="",datafixado="",datalimite="",foto="",slug="",sec="",sub="";

    TextInputEditText altDataFix,altDataLim,altnome,altdesc,altpreco,altpromo,altstock,altslug;

    DatePickerDialog.OnDateSetListener setListener;

    float preco=0,promo=0;

    int stock=0;


    Calendar calendar = Calendar.getInstance();
    String data;

    DatabaseReference db;

    AutoCompleteTextView altun,altsel;
    ArrayAdapter<String> adapter,ad4;


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    ActivityResultLauncher<String> mGetContent;

    ImageView fotografia;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_prod);

        anome=findViewById(R.id.anome);
        adesc=findViewById(R.id.adescricao);
        apreco=findViewById(R.id.apreco);
        apromo=findViewById(R.id.apromo);

        aunit=findViewById(R.id.aunit);
        asec=findViewById(R.id.asec);

        astock=findViewById(R.id.astock);
        aslug=findViewById(R.id.aslug);

        fotografia=findViewById(R.id.altfotografia);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        altDataFix=findViewById(R.id.altdataFix);
        altDataLim=findViewById(R.id.altdataLim);

        adataFix=findViewById(R.id.adataFixado);
        adataFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        alterProd.this, android.R.style.ThemeOverlay_Material_Dark_ActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=day+"-"+month+"-"+year;
                adataFix.getEditText().setText(date);
            }
        };
        altDataFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        alterProd.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month+=1;
                        String date=day+"-"+month+"-"+year;
                        adataFix.getEditText().setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        adataLim=findViewById(R.id.adataLimite);
        adataLim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        alterProd.this, android.R.style.ThemeOverlay_Material_Dark_ActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=day+"-"+month+"-"+year;
                adataLim.getEditText().setText(date);
            }
        };
        altDataLim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        alterProd.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month+=1;
                        String date=day+"-"+month+"-"+year;
                        adataLim.getEditText().setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("images");


            Bundle extras = getIntent().getExtras();
            slug= extras.getString("slug");



        db= FirebaseDatabase.getInstance().getReference("produtos");

        Query check=db.orderByChild(slug);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String d=snapshot.child(slug).child("seccao").getValue(String.class);

                nome=snapshot.child(slug).child("nome").getValue(String.class);
                descricao=snapshot.child(slug).child("descricao").getValue(String.class);
                uni=snapshot.child(slug).child("unidade").getValue(String.class);
                preco= snapshot.child(slug).child("preco").getValue(Float.class);
                promo=snapshot.child(slug).child("preco_promo").getValue(Float.class);
                datafixado=snapshot.child(slug).child("data_precopromo_fixado").getValue(String.class);
                datalimite=snapshot.child(slug).child("data_precopromo_limite").getValue(String.class);
                foto=snapshot.child(slug).child("foto").getValue(String.class);
                stock=snapshot.child(slug).child("stock").getValue(Integer.class);
                slug=snapshot.child(slug).child("slug").getValue(String.class);


                Glide.with(alterProd.this).load(foto).into(fotografia);

                anome.getEditText().setText(nome);
                adesc.getEditText().setText(descricao);
                apreco.getEditText().setText(""+preco);
                apromo.getEditText().setText(""+promo);
                adataFix.getEditText().setText(datafixado);
                adataLim.getEditText().setText(datalimite);
                aslug.getEditText().setText(slug);
                astock.getEditText().setText(""+stock);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        altun=findViewById(R.id.altunidade);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.unidade));
        altun.setAdapter(adapter);
        altun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                uni = parent.getItemAtPosition(position).toString();
            }
        });


        altsel=findViewById(R.id.altseccao);
        ad4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.seccao));
        altsel.setAdapter(ad4);
        altsel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                sec = parent.getItemAtPosition(position).toString();
            }
        });

        altnome=findViewById(R.id.altnome);
        altdesc=findViewById(R.id.altescricao);
        altstock=findViewById(R.id.altstock);
        altslug=findViewById(R.id.altslug);
        altpreco=findViewById(R.id.altpreco);
        altpromo=findViewById(R.id.altpromo);

        btn1=findViewById(R.id.altProd);

        btn2=findViewById(R.id.Del);




        altnome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=3 )
                {
                    adesc.setVisibility(View.VISIBLE);
                }

                else
                {
                    adesc.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > anome.getCounterMaxLength())
                {
                    anome.setError("Tamanho máximo: " + anome.getCounterMaxLength());
                    adesc.setVisibility(View.GONE);
                }

                else
                {
                    anome.setError(null);
                }
            }
        });

        altdesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=10)
                {
                    aunit.setVisibility(View.VISIBLE);
                }

                else
                {
                    aunit.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > adesc.getCounterMaxLength())
                {
                    adesc.setError("Tamanho máximo: " + adesc.getCounterMaxLength());
                    aunit.setVisibility(View.GONE);
                }

                else
                {
                    adesc.setError(null);
                }
            }
        });

        altun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >1)
                {
                    apreco.setVisibility(View.VISIBLE);
                }

                else
                {
                    apreco.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() < 1)
                {
                    apreco.setVisibility(View.GONE);
                }
            }
        });

        altpreco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >0)
                {
                    apromo.setVisibility(View.VISIBLE);
                }

                else
                {
                    apromo.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() < 0)
                {
                    apromo.setVisibility(View.GONE);
                }
            }
        });

        altpromo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >0)
                {
                    adataFix.setVisibility(View.VISIBLE);
                }

                else
                {
                    adataFix.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <0)
                {
                    adataFix.setVisibility(View.GONE);
                }
            }
        });


        altDataFix.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=8)
                {
                    adataLim.setVisibility(View.VISIBLE);
                }

                else
                {
                    adataLim.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <=8)
                {
                    adataLim.setVisibility(View.GONE);
                }
            }
        });
        altDataLim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=8)
                {
                    astock.setVisibility(View.VISIBLE);
                }

                else
                {
                    astock.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <=8)
                {
                    astock.setVisibility(View.GONE);
                }
            }
        });

        altstock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >0)
                {
                    aslug.setVisibility(View.VISIBLE);
                }

                else
                {
                    aslug.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <0)
                {
                    aslug.setVisibility(View.GONE);
                }
            }
        });

        altslug.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=5)
                {
                    asec.setVisibility(View.VISIBLE);
                }

                else
                {
                    asec.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > aslug.getCounterMaxLength())
                {
                    aslug.setError("Tamanho máximo: " + aslug.getCounterMaxLength());
                    asec.setVisibility(View.GONE);
                }

                else
                {
                    aslug.setError(null);
                }
            }
        });

        altsel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >0)
                {
                    btn1.setVisibility(View.VISIBLE);
                }

                else
                {
                    btn1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <0)
                {
                    btn1.setVisibility(View.GONE);
                }
            }
        });


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


        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            android.app.AlertDialog.Builder net=new AlertDialog.Builder(alterProd.this);
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

        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>()
        {
            @Override
            public void onActivityResult(Uri result)
            {
                fotografia.setImageURI(result);
                final ProgressDialog pd=new ProgressDialog(alterProd.this);
                pd.setTitle("A atualizar");
                pd.show();
                StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(result));
                storageReference2.putFile(result)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                Log.e(String.valueOf(this),""+sub);
                                Log.e(String.valueOf(this),""+slug);
                                Task<Uri> u=taskSnapshot.getStorage().getDownloadUrl();
                                u.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        foto = uri.toString();
                                        Toast.makeText(getApplicationContext(), "Imagem Inserida ", Toast.LENGTH_LONG).show();
                                        @SuppressWarnings("VisibleForTests")
                                        Produto imageProdutoInfo = new Produto(""+nome,
                                                ""+descricao,
                                                ""+uni,
                                                preco,
                                                promo,
                                                ""+datafixado,
                                                ""+datalimite,
                                                stock,
                                                ""+foto,
                                                ""+slug,
                                                ""+data,
                                                ""+sec);
                                        db.child(slug).setValue(imageProdutoInfo);
                                    }
                                });

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = ref.child("produtos").orderByChild("slug").equalTo(sub);

                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren())
                                        {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                                Intent i= new Intent(alterProd.this, welcome.class);
                                startActivity(i);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        pd.dismiss();
                        Intent i= new Intent(alterProd.this, welcome.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Erro ao inserir a imagem", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double prog=(100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentagem:"+(int)prog+"%");
                    }
                });
            }

        });

        sub=aslug.getEditText().getText().toString();
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if(contentResolver.getType(uri).equals(""))
        {
            return mimeTypeMap.getExtensionFromMimeType("") ;
        }
        else
        {
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
        }

    }

    private boolean teste(alterProd alt)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) alt.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private void clean()
    {

        DatabaseReference del=FirebaseDatabase.getInstance().getReference("produtos").child(slug);
        del.removeValue();
        Toast.makeText(alterProd.this, "Registo eliminado", Toast.LENGTH_SHORT).show();
        Intent i= new Intent(alterProd.this, welcome.class);
        startActivity(i);

    }
    private void btnClick()
    {

        fotografia.setVisibility(View.VISIBLE);

        nome=anome.getEditText().getText().toString();
        descricao=adesc.getEditText().getText().toString();
        preco= Float.parseFloat(apreco.getEditText().getText().toString());
        promo= Float.parseFloat(apromo.getEditText().getText().toString());
        datafixado=adataFix.getEditText().getText().toString();
        datalimite=adataLim.getEditText().getText().toString();
        stock=Integer.parseInt(astock.getEditText().getText().toString());
        slug=aslug.getEditText().getText().toString();

        mGetContent.launch("image/*");

        data = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        anome.getEditText().setText("");
        adesc.getEditText().setText("");
        apreco.getEditText().setText("");
        apromo.getEditText().setText("");
        adataFix.getEditText().setText("");
        adataLim.getEditText().setText("");
        astock.getEditText().setText("");
        aslug.getEditText().setText("");
        altun.setText("");
        altsel.setText("");
        fotografia.setVisibility(View.GONE);

        Toast.makeText(this, "Registo alterado", Toast.LENGTH_SHORT).show();


    }
    public void tras(View view)
    {
        Intent i= new Intent(alterProd.this, welcome.class);
        startActivity(i);
    }
}