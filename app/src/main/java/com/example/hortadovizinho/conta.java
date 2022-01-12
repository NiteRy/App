package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hortadovizinho.clas.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class conta extends AppCompatActivity {

    EditText a,b,c,d,e,f,g;
    String t1,t2,t3,t4,t5,t6,t7;


    String mTextValue;
    Character mLastChar = '\0'; // init with empty character
    int mKeyDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        a=findViewById(R.id.nome);
        b=findViewById(R.id.apelido);
        c=findViewById(R.id.mail);
        d=findViewById(R.id.morada);
        e=findViewById(R.id.codP);
        f=findViewById(R.id.pass);
        g=findViewById(R.id.pass2);

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if (s.length()>0) {// save the last char value
                    mLastChar = s.charAt(s.length() - 1);
                } else {
                    mLastChar = '\0';
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean flag = true;
                String eachBlock[] = e.getText().toString().split("-");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {

                    e.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                mKeyDel = 1;
                            return false;
                        }
                    });

                    if (mKeyDel == 0) {

                        if (e.getText().length() == 4) {
                            e.setText(e.getText() + "-");
                            e.setSelection(e.getText().length());
                        }
                        mTextValue = e.getText().toString();
                    } else {
                        mTextValue = e.getText().toString();
                        if (mLastChar.equals('-')) {
                            mTextValue = mTextValue.substring(0, mTextValue.length() - 1);
                            e.setText(mTextValue);
                            e.setSelection(mTextValue.length());
                        }
                        mKeyDel = 0;
                    }

                } else {
                    e.setText(mTextValue);
                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(conta.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),conta.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    conta.this.finishAffinity();
                }
            });
            net.show();
        }

    }

    private boolean teste(conta conta)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) conta.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    public void Signup(View v)
    {

        t1=a.getText().toString();
        t2=b.getText().toString();
        t3=c.getText().toString();
        t4=d.getText().toString();
        t5=e.getText().toString();
        t6=f.getText().toString();
        t7=g.getText().toString();

        if(t1.matches("") || t2.matches("") || t3.matches("") || t4.matches("") || t5.matches("") || t6.matches("") || t7.matches(""))
        {
            Toast.makeText(this, "Valores nulos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!(t6.equalsIgnoreCase(t7)))
            {
                f.setText("");
                g.setText("");
                Toast.makeText(this, "As passwords não coexistem", Toast.LENGTH_SHORT).show();
            }
            else if(t6.length()<6)
            {
                f.setText("");
                Toast.makeText(this, "A password tem de ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
            else if(t7.length()<6)
            {
                g.setText("");
                Toast.makeText(this, "A password tem de ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
            else
            {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(t3,t6).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            FirebaseUser fu=auth.getCurrentUser();
                            fu.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    Resources res = getResources();
                                    String [] txt = res.getStringArray(R.array.verif);
                                    Toast.makeText(conta.this, ""+txt[6], Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(conta.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            user user = new user(t1,t2,t3,t4,t5,t6,t7);
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                                    .setValue(user);

                            Intent i= new Intent(conta.this, menu.class);
                            startActivity(i);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(conta.this, "Erro: "+e, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }
    }

    public void Limpa(View v)
    {
        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        e.setText("");
        f.setText("");
        g.setText("");
    }

    public void atras(View v)
    {
        Intent i= new Intent(conta.this, menu.class);
        startActivity(i);
    }

}