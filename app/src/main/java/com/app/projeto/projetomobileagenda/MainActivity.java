package com.app.projeto.projetomobileagenda;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    Button btNovo, btAgenda;
    ListView lista;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller c = new Controller(getBaseContext());
        cursor = c.listarCompromissosDoDia();
        String[] campos = new String[]{"_id", "descricao", "tipo", "data", "hora"};
        int[] idViews = new int[]{R.id.id, R.id.nome, R.id.tipo, R.id.data, R.id.hora};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.tabela, cursor, campos, idViews, 0);


        lista = (ListView) findViewById(R.id.compromissosHoje);
        lista.setAdapter(simpleCursorAdapter);

        btNovo = (Button) findViewById(R.id.btNovoCompromisso);
        btAgenda = (Button) findViewById(R.id.btAgenda);

        btAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarCompromissos.class);

                startActivity(intent);
                finish();
            }
        });
        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNovo.class);
                intent.putExtra("situacao", "inserir");
                startActivity(intent);
                finish();
            }
        });
    }
}
