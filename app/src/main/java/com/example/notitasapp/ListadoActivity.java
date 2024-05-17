package com.example.notitasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity {


    private TextView textView1;
    private ListView lista1;

    private ArrayList<Note> notas= new ArrayList<Note>();
    private ArrayAdapter<Note> adaptador;

    private String contenidoCaja1="";
    private String contenidoItem="";

    private BaseDeDatos db;

    private Intent pasarPantalla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        textView1=(TextView) findViewById(R.id.textView1);
        lista1= (ListView) findViewById(R.id.listView);

        db= new BaseDeDatos(this);


        notas = db.getAllNotas();


        // Inicializar el adaptador con los datos obtenidos de la base de datos
        adaptador = new ArrayAdapter<Note>(ListadoActivity.this, android.R.layout.simple_list_item_1, notas) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);

                // Obtener el objeto Audio en la posición actual
                Note nota = getItem(position);

                // Mostrar el título del audio en el TextView
                textView.setText(nota.getNombre());

                return view;
            }
        };

        lista1.setAdapter(adaptador);

        // Recuperar la información enviada desde CrearActivity
        Intent intent = getIntent();
        if (intent != null) {
            String contenidoCaja1 = intent.getStringExtra("contenidoCaja1");

            // Añadir la información al array "audios"
            if (contenidoCaja1 != null) {
                db.insertarNota(contenidoCaja1);

                // Actualizar la lista de audios
                notas.clear();
                notas.addAll(db.getAllNotas());
                adaptador.notifyDataSetChanged();
            }
        }

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener el elemento seleccionado de la ListView
                Note noteSelect = notas.get(i);

                // Crear un Intent para abrir la actividad ReproducirActivity
                Intent intent = new Intent(ListadoActivity.this, VerNotaActivity.class);

                // Crear un Bundle para almacenar los datos del audio
                Bundle bundle = new Bundle();
                bundle.putString("nombre", noteSelect.getNombre());
                bundle.putInt("idNota", noteSelect.getId());

                // Pasar el Bundle como extra al Intent
                intent.putExtra("notaData", bundle);

                // Iniciar la actividad ReproducirActivity con el Intent
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refrescar la lista de audios cuando la actividad se reanuda
        notas.clear();
        notas.addAll(db.getAllNotas());
        adaptador.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        int id = item.getItemId();
        if (id == R.id.item_crear){
            pasarPantalla = new Intent(ListadoActivity.this, CrearNotaActivity.class);
            startActivity(pasarPantalla);
        }
        return true;
    }

}