package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class perfil extends AppCompatActivity {

    TextView name,mail,apelido,cp,che;

    TextInputLayout edname,edmail,edapelido,edcp;

    String a,b,c,d;

    Button b1,b2,b3,resend;

    DatabaseReference reference;

    Query check;

    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        name=findViewById(R.id.uname);
        mail=findViewById(R.id.umail);
        apelido=findViewById(R.id.usur);
        cp=findViewById(R.id.ucp);

        edname=findViewById(R.id.edName);
        edmail=findViewById(R.id.edMail);
        edapelido=findViewById(R.id.edSurn);
        edcp=findViewById(R.id.edCp);

        b1=findViewById(R.id.edit);
        b2=findViewById(R.id.cancel);
        b3=findViewById(R.id.ok);

        edname.setVisibility(View.GONE);
        edmail.setVisibility(View.GONE);
        edapelido.setVisibility(View.GONE);
        edcp.setVisibility(View.GONE);

        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);

        resend=findViewById(R.id.verif);
        che=findViewById(R.id.che);

        FirebaseAuth auth=FirebaseAuth.getInstance();

        FirebaseUser user= auth.getCurrentUser();
        if(!user.isEmailVerified())
        {
            resend.setVisibility(View.VISIBLE);
            che.setVisibility(View.VISIBLE);
            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    FirebaseUser fu=auth.getCurrentUser();
                    fu.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Resources res = getResources();
                            String [] txt = res.getStringArray(R.array.verif);
                            Toast.makeText(perfil.this, ""+txt[6], Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(perfil.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


        reference= FirebaseDatabase.getInstance().getReference("users");

        check=reference.orderByChild(FirebaseAuth.getInstance().getUid());

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                a=snapshot.child(FirebaseAuth.getInstance().getUid()).child("nome").getValue(String.class);
                b=snapshot.child(FirebaseAuth.getInstance().getUid()).child("apl").getValue(String.class);
                c=snapshot.child(FirebaseAuth.getInstance().getUid()).child("mail").getValue(String.class);
                d=snapshot.child(FirebaseAuth.getInstance().getUid()).child("codP").getValue(String.class);

                name.setText("Nome de utilizador: "+a);
                apelido.setText("apelido de utilizador: "+b);
                mail.setText("Email de utilizador: "+c);
                cp.setText("Código-Postal "+d);

                edname.getEditText().setText(""+a);
                edapelido.getEditText().setText(""+b);
                edmail.getEditText().setText(""+c);
                edcp.getEditText().setText(""+d);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        cp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cp.length()==4){
                    cp.append("-");
                }
            }
        });
        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(perfil.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),perfil.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    perfil.this.finishAffinity();
                }
            });
            net.show();
        }
    }

    private boolean teste(perfil perfil)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) perfil.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    public void edit(View view)
    {
        edname.setVisibility(View.VISIBLE);
        edmail.setVisibility(View.VISIBLE);
        edapelido.setVisibility(View.VISIBLE);
        edcp.setVisibility(View.VISIBLE);

        name.setVisibility(View.GONE);
        mail.setVisibility(View.GONE);
        apelido.setVisibility(View.GONE);
        cp.setVisibility(View.GONE);


        b1.setVisibility(View.GONE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
    }
    public void cancel(View view)
    {
        edname.setVisibility(View.GONE);
        edmail.setVisibility(View.GONE);
        edapelido.setVisibility(View.GONE);
        edcp.setVisibility(View.GONE);

        name.setVisibility(View.VISIBLE);
        mail.setVisibility(View.VISIBLE);
        apelido.setVisibility(View.VISIBLE);
        cp.setVisibility(View.VISIBLE);

        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
    }

    public void editar(View view)
    {
        if(!a.equals(edname.getEditText().getText().toString()))
        {
            reference.child(FirebaseAuth.getInstance().getUid()).child("nome").setValue(edname.getEditText().getText().toString());
        }
        else
        {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }

        if(!b.equals(edname.getEditText().getText().toString()))
        {
            reference.child(FirebaseAuth.getInstance().getUid()).child("apl").setValue(edapelido.getEditText().getText().toString());
        }
        else
        {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }

        if(!c.equals(edname.getEditText().getText().toString()))
        {
            reference.child(FirebaseAuth.getInstance().getUid()).child("mail").setValue(edmail.getEditText().getText().toString());
        }
        else
        {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }

        if(!d.equals(edname.getEditText().getText().toString()))
        {
            reference.child(FirebaseAuth.getInstance().getUid()).child("codP").setValue(edcp.getEditText().getText().toString());
        }
        else
        {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.barra,menu);

        MenuItem item = menu.findItem(R.id.perfil);

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
        if(i==R.id.pro)
        {
            Intent v= new Intent(perfil.this,produtos.class);
            startActivity(v);

        }
        if(i==R.id.exit)
        {

            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();


            editor.apply();

            Intent inte = new Intent(perfil.this, menu.class);
            startActivity(inte);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}