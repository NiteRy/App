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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hortadovizinho.clas.Produto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class insert extends AppCompatActivity {

    Button btn1,btn2;

    TextInputLayout t1,t2,t3,t4,t5,t6,t7,t8,unit,sec;

    String s1,s4;

    DatePickerDialog.OnDateSetListener setListener;

    String nome,descricao,datafixado,datalimite,foto,slug;

    float preco,promo;

    int stock;

    TextInputEditText txt,txt2,ednome,edd,edpre,edpro,edst,edsl;

    Calendar calendar = Calendar.getInstance();
    String data;

    DatabaseReference db;

    AutoCompleteTextView c1,c2;
    ArrayAdapter<String> adapter,ad4;


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    ActivityResultLauncher<String> mGetContent;

    ImageView fotografia;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        t1=findViewById(R.id.nome);
        t2=findViewById(R.id.descricao);
        t3=findViewById(R.id.preco);
        t4=findViewById(R.id.promo);

        unit=findViewById(R.id.unit);
        sec=findViewById(R.id.sec);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        txt=findViewById(R.id.edt);
        txt2=findViewById(R.id.edl);

        t5=findViewById(R.id.dataFixado);
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
        txt2.setOnClickListener(new View.OnClickListener() {
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

        t8=findViewById(R.id.slug);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("images");

        db= FirebaseDatabase.getInstance().getReference("produtos");

        c1=findViewById(R.id.unidade);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.unidade));
        c1.setAdapter(adapter);
        c1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                s1 = parent.getItemAtPosition(position).toString();
            }
        });


        c2=findViewById(R.id.seccao);
        ad4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.seccao));
        c2.setAdapter(ad4);
        c2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                s4 = parent.getItemAtPosition(position).toString();
            }
        });

        ednome=findViewById(R.id.ednome);
        edd=findViewById(R.id.edescricao);
        edst=findViewById(R.id.edstock);
        edsl=findViewById(R.id.edstock);
        edpre=findViewById(R.id.edpreco);
        edpro=findViewById(R.id.edpromo);

        btn1=findViewById(R.id.btn);

        btn2=findViewById(R.id.btn2);

        t2.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        t4.setVisibility(View.GONE);
        t5.setVisibility(View.GONE);
        t6.setVisibility(View.GONE);
        t7.setVisibility(View.GONE);
        t8.setVisibility(View.GONE);
        unit.setVisibility(View.GONE);
        sec.setVisibility(View.GONE);
        btn1.setVisibility(View.GONE);



        ednome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=3 )
                {
                    t2.setVisibility(View.VISIBLE);
                }

                else
                {
                    t2.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > t1.getCounterMaxLength())
                {
                    t1.setError("Tamanho máximo: " + t1.getCounterMaxLength());
                    t2.setVisibility(View.GONE);
                }

                else
                {
                    t1.setError(null);
                }
            }
        });

        edd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=20)
                {
                    unit.setVisibility(View.VISIBLE);
                }

                else
                {
                    unit.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > t2.getCounterMaxLength())
                {
                    t2.setError("Tamanho máximo: " + t2.getCounterMaxLength());
                    unit.setVisibility(View.GONE);
                }

                else
                {
                    t2.setError(null);
                }
            }
        });

        c1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >1)
                {
                    t3.setVisibility(View.VISIBLE);
                }

                else
                {
                    t3.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() < 1)
                {
                    t3.setVisibility(View.GONE);
                }
            }
        });

        edpre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edpro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
        txt2.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

        edst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=20)
                {
                    t3.setVisibility(View.VISIBLE);
                }

                else
                {
                    t3.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > t2.getCounterMaxLength())
                {
                    t2.setError("Tamanho máximo: " + t2.getCounterMaxLength());
                    t3.setVisibility(View.GONE);
                }

                else
                {
                    t2.setError(null);
                }
            }
        });

        edsl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        c2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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
            AlertDialog.Builder net=new AlertDialog.Builder(insert.this);
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
                    insert.this.finishAffinity();
                }
            });
            net.show();
        }

        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>()
        {
            @Override
            public void onActivityResult(Uri result)
            {
                fotografia=findViewById(R.id.fotografia);
                fotografia.setImageURI(result);
                final ProgressDialog pd=new ProgressDialog(insert.this);
                pd.setTitle("A atualizar");
                pd.show();
                StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(result));
                storageReference2.putFile(result)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                Task<Uri> u=taskSnapshot.getStorage().getDownloadUrl();
                                u.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        foto = uri.toString();
                                        Toast.makeText(getApplicationContext(), "Imagem Inserida ", Toast.LENGTH_LONG).show();
                                        @SuppressWarnings("VisibleForTests")
                                        Produto imageProdutoInfo = new Produto(""+nome,
                                                ""+descricao,
                                                ""+s1,
                                                preco,
                                                promo,
                                                ""+datafixado,
                                                ""+datalimite,
                                                stock,
                                                ""+foto,
                                                ""+slug,
                                                ""+data,
                                                ""+s4);
                                        db.child(slug).setValue(imageProdutoInfo);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        pd.dismiss();
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
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    private boolean teste(insert insert)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) insert.getSystemService(Context.CONNECTIVITY_SERVICE);

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
        t1.getEditText().setText("");
        t2.getEditText().setText("");
        t3.getEditText().setText("");
        t4.getEditText().setText("");
        t5.getEditText().setText("");
        t6.getEditText().setText("");
        t7.getEditText().setText("");
        t8.getEditText().setText("");
        c1.setText("");
        c2.setText("");
    }
    private void btnClick()
    {

        nome=t1.getEditText().getText().toString();
        descricao=t2.getEditText().getText().toString();
        preco= Float.parseFloat(t3.getEditText().getText().toString());
        promo= Float.parseFloat(t4.getEditText().getText().toString());
        datafixado=t5.getEditText().getText().toString();
        datalimite=t6.getEditText().getText().toString();
        stock=Integer.parseInt(t7.getEditText().getText().toString());
        slug=t8.getEditText().getText().toString();

        mGetContent.launch("image/*");

        data = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        t1.getEditText().setText("");
        t2.getEditText().setText("");
        t3.getEditText().setText("");
        t4.getEditText().setText("");
        t5.getEditText().setText("");
        t6.getEditText().setText("");
        t7.getEditText().setText("");
        t8.getEditText().setText("");
        c1.setText("");
        c2.setText("");

        Toast.makeText(this, "Registo criado", Toast.LENGTH_SHORT).show();

    }
    public void tras(View view)
    {
        Intent i= new Intent(insert.this, welcome.class);
        startActivity(i);
    }


}