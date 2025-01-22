package com.example.agenda;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertarActivity extends AppCompatActivity {

    private EditText et1,et2;
    private ImageView iv;

    BDAgencia admin;
    SQLiteDatabase bd;

    private int[] imagenes = {
            R.drawable.foto1,
            R.drawable.foto2,
            R.drawable.foto3,
            R.drawable.foto4,
            R.drawable.foto5,
            R.drawable.foto6,
            R.drawable.foto7
    };
    // Índice de la imagen actual
    private int indiceActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        et1 = (EditText) findViewById(R.id.editTextNombre);
        et2 = (EditText) findViewById(R.id.editTextIntereses);
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(imagenes[indiceActual]);

        admin = new BDAgencia(this);
        bd = admin.getWritableDatabase();
    }

    public void salirSinGuardar(View v){
        finish();
    }

    public void salvarDatos(View v){
        String nombre = et1.getText().toString();
        String intereses = et2.getText().toString();

        if(nombre.isEmpty() || intereses.isEmpty()){
            Toast.makeText(this, "Falta nombre o intereses por completar", Toast.LENGTH_LONG).show();
            return;
        }else{
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("intereses", intereses);
            registro.put("imagen", imagenes[indiceActual]);

            long resultado = bd.insert("agencia",null,registro);
            if(resultado != -1){
                Toast.makeText(this, "Datos guardados correctamente con codigo: " + resultado, Toast.LENGTH_LONG).show();
                // Finaliza la actividad para volver a la principal
                finish();
            }else{
                Toast.makeText(this, "no se pudo insertar el registro", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cambiarImagen(View v){
        // Incrementar el indice actual y reiniciar si es necesario
        indiceActual = (indiceActual + 1) % imagenes.length;
        // Establecer la nueva imagen
        iv.setImageResource(imagenes[indiceActual]);
    }
}

