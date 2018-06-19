package com.app.projeto.projetomobileagenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListarCompromissos extends AppCompatActivity {


    ListView listaCompleta;
    Cursor cursor;
    String cd;
    AlertDialog alerta,conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_compromissos);

        Controller c= new Controller(getBaseContext());
        cursor=c.lista();
        String[] campos=new String[]{"_id","descricao","tipo", "data", "hora"};
        int[]idViews=new int[]{R.id.id,R.id.nome,R.id.tipo,R.id.data,R.id.hora};
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getBaseContext(),R.layout.tabela,cursor,campos,idViews,0);

        listaCompleta=(ListView)findViewById(R.id.listaTodosCompromissos);
        listaCompleta.setAdapter(simpleCursorAdapter);
        listaCompleta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                cursor.moveToPosition(position);
                cd=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                AlertDialog.Builder builder=new AlertDialog.Builder(ListarCompromissos.this);
                final AlertDialog.Builder confirmar=new AlertDialog.Builder(ListarCompromissos.this);
                confirmar.setMessage("Deseja realmente excluir o compromisso?");
                builder.setMessage("o que deseja fazer?");
                builder.setPositiveButton("alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(ListarCompromissos.this,AddNovo.class);

                        i.putExtra("situacao","alterar");//manda o que fazer
                        i.putExtra("codigo",cd);//manda o codigo q pegou no click
                        startActivity(i);
                        finish();
                    }
                });
                builder.setNegativeButton("excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmar.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Controller c = new Controller(getBaseContext());
                                String resultado;

                                resultado=c.apagar(Integer.parseInt(cd));
                                Toast.makeText(getBaseContext(),resultado,Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());

                            }
                        });
                        confirmar.setNegativeButton("não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        conf=confirmar.create();
                        conf.show();

                    }
                });
                alerta=builder.create();
                alerta.show();
            }
        });
    }

}
