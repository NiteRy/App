package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText user,pass;
    String val,val2;

    FirebaseAuth a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        a= FirebaseAuth.getInstance();
    }

    public void login(View v)
    {
        val = user.getText().toString();
        val2 = pass.getText().toString();
        if (val.matches("")&&val2.matches(""))
        {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(val2.length()<6)
        {
            pass.setText("");
            Toast.makeText(this, "A password tem de ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
        }
        else
            {
              a.signInWithEmailAndPassword(val,val2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(login.this, "Bem vindo", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(getApplicationContext(),welcome.class));
                      }
                      else
                      {
                          Toast.makeText(login.this, "Erro: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }

                  }
              });
            }
    }
}