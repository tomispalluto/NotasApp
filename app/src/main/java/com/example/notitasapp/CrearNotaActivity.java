package com.example.notitasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CrearNotaActivity extends AppCompatActivity {

    private EditText caja1;

    private Button boton1;
    private Button boton2;


    private ArrayList<String> notas= new ArrayList<String>();
    private ArrayAdapter<String> adaptador;

    private String contenidoCaja1="";
    private String contenidoItem="";

    private BaseDeDatos db;

    private Intent pasarPantalla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);

        caja1 = (EditText) findViewById(R.id.caja_crear);
        boton1 = (Button) findViewById(R.id.botonVolver_crear);
        boton2 = (Button) findViewById(R.id.botonCrear_crear);

        db = new BaseDeDatos(this);


        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(CrearNotaActivity.this, ListadoActivity.class);
                startActivity(pasarPantalla);

            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contenidoCaja1= caja1.getText().toString();
                if (contenidoCaja1.equals(""))
                {
                    Toast.makeText(CrearNotaActivity.this, "NOTA OBLIGATORIA", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CrearNotaActivity.this, "Nota creada correctamente", Toast.LENGTH_SHORT).show();
                    db.insertarNota(contenidoCaja1);
                    pasarPantalla = new Intent(CrearNotaActivity.this, ListadoActivity.class);
                    startActivity(pasarPantalla);

                }

            }
        });
    }
}