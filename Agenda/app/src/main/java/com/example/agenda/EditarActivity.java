package com.example.agenda;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarActivity extends AppCompatActivity {

    private EditText et1, et2;
    private ImageView iv;
    private int codigo;
    private int imagenActual;

    private int[] imagenes = {
            R.drawable.foto1,
            R.drawable.foto2,
            R.drawable.foto3,
            R.drawable.foto4,
            R.drawable.foto5,
            R.drawable.foto6,
            R.drawable.foto7
    };

    BDAgencia admin;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        et1 = findViewById(R.id.editTextNombre);
        et2 = findViewById(R.id.editTextIntereses);
        iv = findViewById(R.id.imageView);

        // Recuperar los datos enviados desde MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            codigo = extras.getInt("codigo");
            String nombre = extras.getString("nombre");
            String intereses = extras.getString("intereses");
            int imagen = extras.getInt("imagen");

            // Determinar el índice del recurso en el arreglo de imágenes
            imagenActual = findImageIndex(imagen);

            // Mostrar los datos en la interfaz
            et1.setText(nombre);
            et2.setText(intereses);
            iv.setImageResource(imagenes[imagenActual]);
        }

        admin = new BDAgencia(this);
        bd = admin.getWritableDatabase();
    }

    private int findImageIndex(int resourceId) {
        for (int i = 0; i < imagenes.length; i++) {
            if (imagenes[i] == resourceId) {
                return i;
            }
        }
        // Si no se encuentra el recurso, devolver el índice 0 como predeterminado
        return 0;
    }

    public void salirSinGuardar(View v) {
        finish();
    }

    public void salvarDatos(View v) {
        String nombre = et1.getText().toString();
        String intereses = et2.getText().toString();

        if (nombre.isEmpty() || intereses.isEmpty()) {
            Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_LONG).show();
            return;
        }

        String query = "UPDATE agencia SET nombre = '" + nombre + "', intereses = '" + intereses + "', imagen = " + imagenes[imagenActual] + " WHERE codigo = " + codigo;

        try {
            // Ejecutar la consulta
            bd.execSQL(query);
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Volver a la actividad anterior
        } catch (Exception e) {
            // Manejar errores
            Toast.makeText(this, "Error al actualizar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cambiarImagen(View v) {
        // Incrementar el índice actual y reiniciar si es necesario
        imagenActual = (imagenActual + 1) % imagenes.length;
        // Establecer la nueva imagen
        iv.setImageResource(imagenes[imagenActual]);
    }
}
