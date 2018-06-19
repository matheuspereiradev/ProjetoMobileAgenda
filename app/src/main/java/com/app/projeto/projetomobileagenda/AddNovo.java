package com.app.projeto.projetomobileagenda;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class AddNovo extends AppCompatActivity {

    Button salvar;
    EditText EtDescricao, EtTipo, EtData, EtHora;
    Cursor cursor;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_novo);

        EtDescricao = (EditText) findViewById(R.id.ctDescricao);
        EtTipo=(EditText) findViewById(R.id.ctTipo);
        EtData=(EditText) findViewById(R.id.ctData);
        EtHora=(EditText) findViewById(R.id.ctHora);
        salvar = (Button) findViewById(R.id.btSalvar);


        SimpleMaskFormatter mascaraData1 = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mascaraData = new MaskTextWatcher(EtData, mascaraData1);
        EtData.addTextChangedListener(mascaraData);

        SimpleMaskFormatter mascaraHora = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher mascaraHoura = new MaskTextWatcher(EtHora, mascaraHora);
        EtHora.addTextChangedListener(mascaraHoura);

        if (getIntent().getStringExtra("situacao").equals("alterar")){
            Controller controller= new Controller(getBaseContext());
            codigo=getIntent().getStringExtra("codigo");

            cursor= controller.buscaID(Integer.parseInt(codigo));

            EtDescricao.setText(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            EtTipo.setText(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
            EtData.setText(cursor.getString(cursor.getColumnIndexOrThrow("data")));
            EtHora.setText(cursor.getString(cursor.getColumnIndexOrThrow("hora")));
        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controller c = new Controller(getBaseContext());
                String descricao=String.valueOf(EtDescricao.getText());
                String tipo=String.valueOf(EtTipo.getText());
                String data=String.valueOf(EtData.getText());
                String hora=String.valueOf(EtHora.getText());

                String result;
                if (getIntent().getStringExtra("situacao").equals("inserir")){

                    result = c.inserir(descricao,tipo,data,hora);
                }
                else{
                    result= c.alterar(Integer.parseInt(codigo),descricao,tipo,data,hora);
                }

                Toast.makeText(getBaseContext(),result,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(AddNovo.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
