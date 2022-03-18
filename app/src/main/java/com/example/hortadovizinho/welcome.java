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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class welcome extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String email,password;

    String p;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn=findViewById(R.id.insert);
        btn.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        if(sharedpreferences==null)
        {
            Intent i= new Intent(welcome.this, menu.class);
            startActivity(i);
        }

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query check;

        if(email==null)
        {
            Intent i= new Intent(welcome.this, menu.class);
            startActivity(i);
        }
        else {

            check = reference.orderByChild(FirebaseAuth.getInstance().getUid());

            check.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(!password.equals(snapshot.child(FirebaseAuth.getInstance().getUid()).child("password").getValue(String.class)))
                    {
                        reference.child(FirebaseAuth.getInstance().getUid()).child("password").setValue(password);
                        reference.child(FirebaseAuth.getInstance().getUid()).child("password2").setValue(password);
                    }
                    p = snapshot.child(FirebaseAuth.getInstance().getUid()).child("nome").getValue(String.class);
                    TextView welcomeTV = findViewById(R.id.alt);
                    welcomeTV.setText("Bem vindo " + p);
                    if (p.equals("Nite")) {
                        btn.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        if(!teste(this))
        {
            Resources res = getResources();
            String [] txt = res.getStringArray(R.array.con);
            AlertDialog.Builder net=new AlertDialog.Builder(welcome.this);
            net.setCancelable(false);
            net.setTitle(txt[0]);
            net.setPositiveButton(txt[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(getApplicationContext(),welcome.class));
                }
            });
            net.setNegativeButton(txt[2], new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    welcome.this.finishAffinity();
                }
            });
            net.show();
        }


    }

    private boolean teste(welcome welcome)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) welcome.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.barra,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i=item.getItemId();
        if(i==R.id.pro)
        {
            Intent v= new Intent(welcome.this,produtos.class);
            startActivity(v);

        }
        if(i==R.id.perfil)
        {
            Intent v= new Intent(welcome.this,perfil.class);
            startActivity(v);

        }
        if(i==R.id.exit)
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();


            editor.apply();


            Intent inte = new Intent(welcome.this, menu.class);
            startActivity(inte);
            finish();


        }
        return super.onOptionsItemSelected(item);
    }

    public void teste(View v)
    {

        Intent i= new Intent(welcome.this, insert.class);
        startActivity(i);

    }
}