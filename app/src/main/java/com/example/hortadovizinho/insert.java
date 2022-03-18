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

    TextInputLayout inome,idesc,ipreco,ipromo,idataFix,idataLim,istock,islug,unit,sec;

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

        inome=findViewById(R.id.nome);
        idesc=findViewById(R.id.descricao);
        ipreco=findViewById(R.id.preco);
        ipromo=findViewById(R.id.promo);

        unit=findViewById(R.id.unit);
        sec=findViewById(R.id.sec);

        fotografia=findViewById(R.id.fotografia);
        fotografia.setVisibility(View.GONE);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        txt=findViewById(R.id.edt);
        txt2=findViewById(R.id.edl);

        idataFix=findViewById(R.id.dataFixado);
        idataFix.setOnClickListener(new View.OnClickListener() {
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
                idataFix.getEditText().setText(date);
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
                        idataFix.getEditText().setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        idataLim=findViewById(R.id.dataLimite);
        idataLim.setOnClickListener(new View.OnClickListener() {
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
                idataLim.getEditText().setText(date);
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
                        idataLim.getEditText().setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        istock=findViewById(R.id.stock);

        islug=findViewById(R.id.slug);

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
        edsl=findViewById(R.id.edslug);
        edpre=findViewById(R.id.edpreco);
        edpro=findViewById(R.id.edpromo);

        btn1=findViewById(R.id.btn);

        btn2=findViewById(R.id.btn2);

        idesc.setVisibility(View.GONE);
        unit.setVisibility(View.GONE);
        ipreco.setVisibility(View.GONE);
        ipromo.setVisibility(View.GONE);
        idataFix.setVisibility(View.GONE);
        idataLim.setVisibility(View.GONE);
        istock.setVisibility(View.GONE);
        islug.setVisibility(View.GONE);
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
                    idesc.setVisibility(View.VISIBLE);
                }

                else
                {
                    idesc.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > inome.getCounterMaxLength())
                {
                    inome.setError("Tamanho máximo: " + inome.getCounterMaxLength());
                    idesc.setVisibility(View.GONE);
                }

                else
                {
                    inome.setError(null);
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
                if (s.length() >=10)
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

                if (s.length() > idesc.getCounterMaxLength())
                {
                    idesc.setError("Tamanho máximo: " + idesc.getCounterMaxLength());
                    unit.setVisibility(View.GONE);
                }

                else
                {
                    idesc.setError(null);
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
                    ipreco.setVisibility(View.VISIBLE);
                }

                else
                {
                    ipreco.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() < 1)
                {
                    ipreco.setVisibility(View.GONE);
                }
            }
        });

        edpre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >0)
                {
                    ipromo.setVisibility(View.VISIBLE);
                }

                else
                {
                    ipromo.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() < 0)
                {
                    ipromo.setVisibility(View.GONE);
                }
            }
        });

        edpro.addTextChangedListener(new TextWatcher()
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
                    idataFix.setVisibility(View.VISIBLE);
                }

                else
                {
                    idataFix.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <0)
                {
                    idataFix.setVisibility(View.GONE);
                }
            }
        });


        txt.addTextChangedListener(new TextWatcher()
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
                    idataLim.setVisibility(View.VISIBLE);
                }

                else
                {
                    idataLim.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <=8)
                {
                    idataLim.setVisibility(View.GONE);
                }
            }
    });
        txt2.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=8)
                {
                    istock.setVisibility(View.VISIBLE);
                }

                else
                {
                    istock.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <=8)
                {
                    istock.setVisibility(View.GONE);
                }
            }
    });

        edst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >0)
                {
                    islug.setVisibility(View.VISIBLE);
                }

                else
                {
                    islug.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <0)
                {
                    islug.setVisibility(View.GONE);
                }
            }
        });

        edsl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >=5)
                {
                    sec.setVisibility(View.VISIBLE);
                }

                else
                {
                    sec.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > islug.getCounterMaxLength())
                {
                    islug.setError("Tamanho máximo: " + islug.getCounterMaxLength());
                    sec.setVisibility(View.GONE);
                }

                else
                {
                    islug.setError(null);
                }
            }
        });

        c2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() >1)
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
                if (s.length() < 1)
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
                        Intent i= new Intent(insert.this, welcome.class);
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
        inome.getEditText().setText("");
        idesc.getEditText().setText("");
        ipreco.getEditText().setText("");
        ipromo.getEditText().setText("");
        idataFix.getEditText().setText("");
        idataLim.getEditText().setText("");
        istock.getEditText().setText("");
        islug.getEditText().setText("");
        c1.setText("");
        c2.setText("");
    }
    private void btnClick()
    {

        fotografia.setVisibility(View.VISIBLE);

        nome=inome.getEditText().getText().toString();
        descricao=idesc.getEditText().getText().toString();
        preco= Float.parseFloat(ipreco.getEditText().getText().toString());
        promo= Float.parseFloat(ipromo.getEditText().getText().toString());
        datafixado=idataFix.getEditText().getText().toString();
        datalimite=idataLim.getEditText().getText().toString();
        stock=Integer.parseInt(istock.getEditText().getText().toString());
        slug=islug.getEditText().getText().toString();

        mGetContent.launch("image/*");

        data = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        inome.getEditText().setText("");
        idesc.getEditText().setText("");
        ipreco.getEditText().setText("");
        ipromo.getEditText().setText("");
        idataFix.getEditText().setText("");
        idataLim.getEditText().setText("");
        istock.getEditText().setText("");
        islug.getEditText().setText("");
        c1.setText("");
        c2.setText("");
        fotografia.setVisibility(View.GONE);

        Toast.makeText(this, "Registo criado", Toast.LENGTH_SHORT).show();

    }
    public void tras(View view)
    {
        Intent i= new Intent(insert.this, welcome.class);
        startActivity(i);
    }


}