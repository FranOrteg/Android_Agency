package com.example.agenda;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    ArrayList<Contact> listaUsers;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv = (ListView) findViewById(R.id.listView);

        listContacts();
        registerForContextMenu(lv);
    }

    // recargar los datos cuando vuelva a la actividad principal y se muestren los nuevos contactos añadidos
    @Override
    protected void onResume(){
        super.onResume();
        listContacts();
    }

    // Cerrar la aplicación
    public void closeApp(View v){
        finish();
    }

    public void addContact(View v){
        Intent i = new Intent(getApplicationContext(), InsertarActivity.class);
        startActivity(i);
    }

    public void listContacts() {
        // Lista para almacenar los datos de la consulta
        listaUsers = new ArrayList<>();

        // Abrir la base de datos
        BDAgencia admin = new BDAgencia(this);
        SQLiteDatabase bd = admin.getReadableDatabase();

        // Realizar la consulta SELECT
        Cursor cursor = bd.rawQuery("SELECT codigo,nombre, intereses, imagen FROM agencia", null);

        // Recorrer los resultados y llenar la lista
        if (cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String intereses = cursor.getString(2);
                int imagen = cursor.getInt(3); // Índice de la imagen

                // Crear un objeto Contact y agregarlo a la lista
                listaUsers.add(new Contact(codigo,nombre, intereses, imagen));
            } while (cursor.moveToNext());
        }

        cursor.close(); // Cerrar el cursor
        bd.close(); // Cerrar la base de datos

        // Crear el adaptador y asignarlo al ListView
        adaptador = new Adaptador(this, listaUsers);
        lv.setAdapter(adaptador);
    }

    // Crear el menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu); // Inflar el menú desde XML
    }


    // Manejar las opciones seleccionadas del menú contextual
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Obtener la posición del contacto seleccionado
        int posicion = info.position;
        Contact selectedContact = listaUsers.get(posicion);

        if (item.getItemId() == R.id.eliminar) {
            eliminarContacto(selectedContact.getCodigo());
            // Actualizar la lista de contactos
            listContacts();
            return true;
        } else if (item.getItemId() == R.id.editar) {
            editarContacto(selectedContact);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    // Metodo para eliminar un contacto
    public void eliminarContacto(int codigo){

        BDAgencia admin = new BDAgencia(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String query = "DELETE FROM agencia WHERE codigo = " + codigo;

        bd.execSQL(query);

        bd.close();
    }

    // Metodo para iniciar la actividad de edición
    private void editarContacto(Contact contact){
        Intent intent = new Intent(this, EditarActivity.class);
        intent.putExtra("codigo", contact.getCodigo());
        intent.putExtra("nombre", contact.getNombre());
        intent.putExtra("intereses", contact.getIntereses());
        intent.putExtra("imagen", contact.getImagen());
        startActivity(intent);
    }

}