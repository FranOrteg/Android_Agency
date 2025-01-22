package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDAgencia extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2; // Versión actual de la base de datos
    private static final String DATABASE_NAME = "administrador"; // Nombre de la base de datos

    // Sentencia para crear la tabla
    private static final String CREAR_TABLA =
            "CREATE TABLE agencia (" +
                    "codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "intereses TEXT, " +
                    "imagen INTEGER)";

    // Constructor
    public BDAgencia(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla
        db.execSQL(CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // Eliminar tabla si existe y crearla nuevamente
            db.execSQL("DROP TABLE IF EXISTS agencia");
            onCreate(db);
        }
    }
}
