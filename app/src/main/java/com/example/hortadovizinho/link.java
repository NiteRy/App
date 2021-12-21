package com.example.hortadovizinho;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class link extends SQLiteOpenHelper {

    private static final String name = "loja.db";
    private static final int version = 1;

    public link(Context context){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id integer primary key autoincrement," +
                "nome varchar(50), " +
                "mail varchar(50)," +
                "morada varchar(50)," +
                "pais varchar(30)," +
                "datanasc integer," +
                "genero varchar(20)," +
                "nif integer," +
                "telefone integer," +
                "password varchar(50)," +
                "password2 varchar(50))");

        db.execSQL("CREATE TABLE item (id integer primary key autoincrement," +
                "nome varchar(50), " +
                "tipo varchar(20), " +
                "quantidade integer, " +
                "preco float)");

        db.execSQL("CREATE TABLE fatura (idf integer primary key autoincrement," +
                "idu integer references user(id)," +
                "idp integer references item(id)," +
                "nome varchar(50), " +
                "nif integer," +
                "produto varchar(20)," +
                "quantidade integer, " +
                "preco float," +
                "total float)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
