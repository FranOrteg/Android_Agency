package com.example.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends BaseAdapter {

    private Context contexto;
    private ArrayList<Contact> listaUsers;

    public Adaptador(Context contexto, List<Contact> listaUsers){
        this.contexto = contexto;
        this.listaUsers = (ArrayList<Contact>) listaUsers;
    }

    @Override
    public int getCount() {
        return listaUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int posicion, View vista, ViewGroup padre){
        ViewHolder holder;

        if(vista == null){
            LayoutInflater inflater = LayoutInflater.from(contexto);
            vista = inflater.inflate(R.layout.itemlistview, padre, false);

            // Crear un nuevo ViewHolder
            holder = new ViewHolder();
            holder.nombre = (TextView) vista.findViewById(R.id.editTextNombre);
            holder.intereses = (TextView) vista.findViewById(R.id.editTextIntereses);
            holder.imagen = (ImageView) vista.findViewById(R.id.imageView);

            vista.setTag(holder);
        }else{
            holder = (ViewHolder) vista.getTag();
        }
        Contact contact = listaUsers.get(posicion);
        holder.nombre.setText(contact.getNombre());
        holder.intereses.setText(contact.getIntereses());
        holder.imagen.setImageResource(contact.getImagen());

        return vista;
    }

    static class ViewHolder{
        TextView nombre;
        TextView intereses;
        ImageView imagen;
    }
}
