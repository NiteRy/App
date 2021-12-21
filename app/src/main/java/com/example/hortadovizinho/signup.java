package com.example.hortadovizinho;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class signup {

    private SQLiteDatabase bd;

    public long insert(user us)
    {
        ContentValues valores = new ContentValues();
        valores.put("nome", us.getNome());
        valores.put("mail", us.getMail());
        valores.put("morada", us.getMorada());
        valores.put("pais", us.getp());
        valores.put("datanasc", us.getData().toString());
        valores.put("genero", us.getGen());
        valores.put("nif", us.getNif());
        valores.put("telefone", us.getTelefone());
        valores.put("password", us.getPassword());
        valores.put("password2", us.getPassword2());
        return bd.insert("user",null, valores);
    }
}
