package com.example.notitasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class VerNotaActivity extends AppCompatActivity {
    private EditText editText;
    private Button botonVolver;
    private Button botonBorrar;
    private Button botonEditar;
    private String nombreNota;
    private String contenidoCaja;
    private int idNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);

        editText = findViewById(R.id.editText_verNota);
        botonVolver = findViewById(R.id.volverBoton_vernota);
        botonBorrar = findViewById(R.id.borrarBoton_vernota);
        botonEditar = findViewById(R.id.editarBoton_vernota);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("notaData");
            if (bundle != null) {
                nombreNota = bundle.getString("nombre");
                idNota = bundle.getInt("idNota");

                editText.setText(nombreNota);
            }
        }

        contenidoCaja = editText.getText().toString();

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a la actividad anterior
                finish();
            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idNota != -1 && Objects.equals(contenidoCaja, editText.getText().toString())) {
                    // Crear una instancia de la base de datos
                    BaseDeDatos db = new BaseDeDatos(VerNotaActivity.this);

                    // Eliminar la nota
                    boolean eliminado = db.eliminarNota(idNota);

                    if (eliminado) {
                        Toast.makeText(VerNotaActivity.this, "Nota eliminada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerNotaActivity.this, "No se pudo eliminar la nota", Toast.LENGTH_SHORT).show();
                    }

                    // Volver al listado de notas
                    Intent volverAListado = new Intent(VerNotaActivity.this, ListadoActivity.class);
                    startActivity(volverAListado);
                    finish(); // Finalizar la actividad actual
                } else {
                    Toast.makeText(VerNotaActivity.this, "No es posible eliminar la nota", Toast.LENGTH_SHORT).show();
                }
            }
        });
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idNota != -1) {
                    // Crear una instancia de la base de datos
                    BaseDeDatos db = new BaseDeDatos(VerNotaActivity.this);
                    contenidoCaja = editText.getText().toString();
                    // Eliminar la nota
                    boolean editado = db.editarNombreNota(idNota, contenidoCaja);

                    if (editado) {
                        Toast.makeText(VerNotaActivity.this, "Nota editada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerNotaActivity.this, "No se pudo editar la nota", Toast.LENGTH_SHORT).show();
                    }

                    // Volver al listado de notas
                    Intent volverAListado = new Intent(VerNotaActivity.this, ListadoActivity.class);
                    startActivity(volverAListado);
                    finish(); // Finalizar la actividad actual
                }
            }
        });
    }
}
