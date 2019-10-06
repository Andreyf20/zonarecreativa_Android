package com.zona.recreativacr.com.zona.data;

public class Transport {
    public String nombre, descripcion;
    public int precio, numeroTelefono;

    public Transport(String nombre, String descripcion, int precio, int numeroTelefono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.numeroTelefono = numeroTelefono;
    }

    public Transport() {
    }
}
