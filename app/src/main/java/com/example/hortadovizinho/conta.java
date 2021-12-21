package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class conta extends AppCompatActivity {

    FirebaseAuth sign;

    EditText a,b,c,d,e,f,g,h;
    ListView t;
    Spinner s1,s2;
    String str1,str2,t1,t2,t3,t4,t5,t6,t7,t8,txt1,txt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        sign= FirebaseAuth.getInstance();

        t=findViewById(R.id.caixa);

        a=findViewById(R.id.nome);
        b=findViewById(R.id.mail);
        c=findViewById(R.id.morada);
        d=findViewById(R.id.data);
        e=findViewById(R.id.nif);
        f=findViewById(R.id.tel);
        g=findViewById(R.id.pass);
        h=findViewById(R.id.pass2);

        s1=findViewById(R.id.pais);
        s2=findViewById(R.id.genero);

        ArrayAdapter<CharSequence> ad;
        ad = ArrayAdapter.createFromResource(this,R.array.gen, android.R.layout.simple_spinner_item);
        s1.setAdapter(ad);
        ArrayAdapter<CharSequence> ad2;
        ad2 = ArrayAdapter.createFromResource(this,R.array.pais, android.R.layout.simple_spinner_item);
        s2.setAdapter(ad2);

    }

    public void Signup(View v)
    {
        user u = new user();

        t1=a.getText().toString().trim();
        t2=b.getText().toString().trim();
        t3=c.getText().toString().trim();
        t4=d.getText().toString().trim();
        t5=e.getText().toString().trim();
        t6=f.getText().toString().trim();
        t7=g.getText().toString().trim();
        t8=h.getText().toString().trim();

        str1 = String.valueOf(s1.getSelectedItem());
        str2 = String.valueOf(s2.getSelectedItem());

        if(t1.matches("") || t2.matches("") || t3.matches("") || t4.matches("") || t5.matches("") || t6.matches("") || t7.matches("") || t8.matches(""))
        {
            Toast.makeText(this, "Valores nulos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            u.setNome(t1);
            u.setMail(t2);
            u.setMorada(t3);
            u.setData(Integer.valueOf(t4));
            u.setNif(Integer.valueOf(t5));
            u.setTelefone(Integer.valueOf(t6));
            u.setPassword(t7);
            u.setPassword2(t8);
            u.setGen(str1);
            u.setp(str2);


            if (!(t7.equalsIgnoreCase(t8)))
            {
                g.setText("");
                h.setText("");
                Toast.makeText(this, "As passwords não coexistem", Toast.LENGTH_SHORT).show();
            }
            else if(t7.length()<6)
            {
                g.setText("");
                Toast.makeText(this, "A password tem de ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
            else if(t8.length()<6)
            {
                h.setText("");
                Toast.makeText(this, "A password tem de ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
            else
            {
                sign.createUserWithEmailAndPassword(t2,t7).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(conta.this, "Registo com sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),menu.class));
                        }
                        else
                        {
                            Toast.makeText(conta.this, "Erro"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
        h.setText("");
    }

    public void atras(View v)
    {
        Intent i= new Intent(conta.this, menu.class);
        startActivity(i);
    }

}