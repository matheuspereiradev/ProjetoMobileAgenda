package com.app.projeto.projetomobileagenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdmBanco extends SQLiteOpenHelper {
    public AdmBanco(Context context){
        super(context, "agenda.bd",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE compromisso (" +
                "_id integer primary key autoincrement not null," +
                "descricao text," +
                "tipo text," +
                "data text," +
                "hora text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
