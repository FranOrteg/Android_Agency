package com.example.agenda;

public class Contact {

    private int codigo;
    private String nombre;
    private String intereses;
    private int imagen;

    public Contact(String pNombre, String pIntereses, int pImagen){
        this.nombre = pNombre;
        this.intereses = pIntereses;
        this.imagen = pImagen;
    }

    public Contact(int pCodigo, String pNombre, String pIntereses, int pImagen){
        this.codigo = pCodigo;
        this.nombre = pNombre;
        this.intereses = pIntereses;
        this.imagen = pImagen;
    }

    // Constructor por defecto
    public Contact(){}

    // Getters && Setters

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIntereses() {
        return intereses;
    }

    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
