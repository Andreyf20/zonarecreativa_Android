package com.zona.recreativacr.com.zona.data;

import java.util.Date;

public class Employee {
    public String nombre, numeroSeguro;
    public int cedula;
    public Date vige, vence;

    public Employee(String nombre, String numeroSeguro, int cedula, Date vige, Date vence) {
        this.nombre = nombre;
        this.numeroSeguro = numeroSeguro;
        this.cedula = cedula;
        this.vige = vige;
        this.vence = vence;
    }

    public Employee() {
    }
}
