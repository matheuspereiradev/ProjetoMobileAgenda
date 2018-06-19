package com.app.projeto.projetomobileagenda;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.text.SimpleDateFormat;

public class Controller {

    SQLiteDatabase db;
    AdmBanco banco;

    public Controller (Context context){
        banco = new AdmBanco(context);
    }

    public String inserir(String descricao, String tipo, String data, String hora){

        ContentValues v;
        long result;
        db= banco.getWritableDatabase();

        v=new ContentValues();
        v.put("descricao", descricao);
        v.put("tipo", tipo);
        v.put("data", data);
        v.put("hora", hora);
        result = db.insert("compromisso",null,v);
        db.close();
        if (result==-1){
            return "erro ao criar o compromisso";
        }
        else
        {
            return "Compromisso gravado com sucesso";
        }

    }

    public Cursor lista(){
        Cursor cursor;
        String [] campos={"_id","descricao","tipo","data","hora"};
        db=banco.getReadableDatabase();
        cursor=db.query("compromisso",campos,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String verDataDeHoje(){
        long dia = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataDeHoje = simpleDateFormat.format(dia);
        return dataDeHoje;
    }
    public Cursor listarCompromissosDoDia(){
        Cursor cursor;
        String [] campos={"_id","descricao","tipo","data","hora"};
        String dataAtual=verDataDeHoje();
        String where = "data='"+dataAtual+"'";
        db=banco.getReadableDatabase();

        cursor=db.query("compromisso",campos,where,null,null,null,"hora asc");
        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public  Cursor buscaID(int id){
        Cursor cursor;
        String [] campos={"_id","descricao","tipo","data","hora"};
        String where= "_id="+id;
        db=banco.getReadableDatabase();
        cursor = db.query("compromisso",campos,where,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public String alterar(int id, String descricao, String tipo, String data, String hora){
        ContentValues valores;
        String where;

        db=banco.getWritableDatabase();//Writable guarda os metodos que escrevem na tabela

        where= "_id="+id;
        valores= new ContentValues();
        valores.put("_id", id);
        valores.put("descricao",descricao);
        valores.put("tipo",tipo);
        valores.put("data",data);
        valores.put("hora",hora);
        int result = db.update("compromisso",valores,where,null);
        db.close();
        if (result==-1)
            return "Erro ao alterar o compromisso";
        else
            return "Compromisso alterado com sucesso";
    }

    public String apagar(int id){
        String where;
        db=banco.getWritableDatabase();
        where = "_id="+id;
        int result = db.delete("compromisso",where,null);
        db.close();
        if (result == -1){
            return "Erro ao excluir compromisso";
        }else{
            return "Excluido com sucesso";
        }
    }
}
