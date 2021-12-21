package com.example.hortadovizinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
        if(i==R.id.produto)
        {
            Intent v= new Intent(welcome.this,produto.class);
            startActivity(v);

        }
        if(i==R.id.perfil)
        {
            Intent v= new Intent(welcome.this,perfil.class);
            startActivity(v);

        }
        if(i==R.id.carro)
        {
            Intent v= new Intent(welcome.this,carrinho.class);
            startActivity(v);

        }
        if(i==R.id.exit)
        {
            Intent v= new Intent(welcome.this,menu.class);
            startActivity(v);
        }
        return super.onOptionsItemSelected(item);
    }
}