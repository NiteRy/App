package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.icu.text.Edits;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    TextInputLayout user,pass;

    String username,passw;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=findViewById(R.id.email);
        pass=findViewById(R.id.password);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        username = sharedpreferences.getString(EMAIL_KEY, null);
        passw = sharedpreferences.getString(PASSWORD_KEY, null);

        auth= FirebaseAuth.getInstance();

        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(login.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),login.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    login.this.finishAffinity();
                }
            });
            net.show();
        }

    }

    private boolean teste(login login)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    public void login(View v)
    {

        if (TextUtils.isEmpty(user.getEditText().getText().toString()) && TextUtils.isEmpty(pass.getEditText().getText().toString()))
        {
            Toast.makeText(login.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }
        else
            {
                username = user.getEditText().getText().toString().trim();
                passw = pass.getEditText().getText().toString().trim();

                auth.signInWithEmailAndPassword(username,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(EMAIL_KEY, user.getEditText().getText().toString());
                            editor.putString(PASSWORD_KEY, pass.getEditText().getText().toString());

                            editor.apply();

                            Intent i = new Intent(login.this, welcome.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
    }

    public void recover(View view)
    {
        Resources res = getResources();
        EditText text=new EditText(view.getContext());
        String [] txt = res.getStringArray(R.array.verif);
        AlertDialog.Builder recupera=new AlertDialog.Builder(view.getContext());
        recupera.setCancelable(false);
        recupera.setTitle(txt[0]);
        recupera.setMessage(txt[1]);
        recupera.setView(text);
        recupera.setPositiveButton(txt[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String mail= text.getText().toString();
                auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        Toast.makeText(login.this, txt[4], Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, txt[5]+" "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recupera.setNegativeButton(txt[3], new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        recupera.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (username != null && passw != null) {
            Intent i = new Intent(login.this, welcome.class);
            startActivity(i);
        }
    }
}