package com.example.notitasapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaseDeDatos extends SQLiteOpenHelper {

    // Atributos de la clase BaseDeDatos
    private static final String TABLE_NOTAS = "notas";
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";

    public BaseDeDatos(@Nullable Context context) {
        super(context, "notitas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NOTAS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOMBRE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTAS);
        this.onCreate(db);
    }
    public boolean insertarNota(String nombreNota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nombreNota);

        long resultado = db.insert(TABLE_NOTAS, null, values);
        db.close();

        return resultado != -1;
    }


    // Método para obtener todas las notas
    @SuppressLint("Range")
    public ArrayList<Note> getAllNotas() {
        ArrayList<Note> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTAS + " ORDER BY id ASC", null);

        if (cursor.moveToFirst()) {
            do {
                 int id = cursor.getInt(cursor.getColumnIndex("id"));
                 String nombre = cursor.getString(cursor.getColumnIndex("nombre"));

                // Crear objeto Audio con los datos obtenidos de la base de datos
                Note nota = new Note(id, nombre);
                lista.add(nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
        }
        public boolean eliminarNota(int idNota) {
        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(TABLE_NOTAS, KEY_ID + "=?", new String[] { String.valueOf(idNota) });

        db.close();

        // Retorna true si se eliminó al menos una fila, de lo contrario retorna false
        return resultado != 0;
        }
        public boolean editarNombreNota(int idNota, String nuevoNombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nuevoNombre);

        // Actualizar el nombre de la nota en la base de datos
        int filasActualizadas = db.update(TABLE_NOTAS, values, KEY_ID + "=?", new String[]{String.valueOf(idNota)});
        db.close();

        // Retorna true si se actualizó al menos una fila, de lo contrario retorna false
        return filasActualizadas > 0;
    }


}
