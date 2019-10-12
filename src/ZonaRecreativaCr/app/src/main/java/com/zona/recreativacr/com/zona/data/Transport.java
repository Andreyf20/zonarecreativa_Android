package com.zona.recreativacr.com.zona.data;

public class Transport {
    public String nombre, descripcion, id, numeroTelefono;
    public int precio;

    public Transport(String nombre, String descripcion, String id, int precio, String numeroTelefono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.precio = precio;
        this.numeroTelefono = numeroTelefono;
    }

    public Transport() {
    }
}
