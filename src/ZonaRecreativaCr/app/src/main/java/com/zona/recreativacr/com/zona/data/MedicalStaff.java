package com.zona.recreativacr.com.zona.data;

public class MedicalStaff {
    public String descripcion, nombre;
    public int numeroTelefono;

    public MedicalStaff(String descripcion, String nombre, int numeroTelefono) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.numeroTelefono = numeroTelefono;
    }

    public MedicalStaff() {
    }
}
