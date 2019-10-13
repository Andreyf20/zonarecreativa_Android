package com.zona.recreativacr.com.zona.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Employee implements Parcelable {
    public String nombre, numeroSeguro, id, cedula;
    public Date vige, vence;

    public Employee(String nombre, String numeroSeguro, String id, String cedula, Date vige, Date vence) {
        this.nombre = nombre;
        this.numeroSeguro = numeroSeguro;
        this.id = id;
        this.cedula = cedula;
        this.vige = vige;
        this.vence = vence;
    }

    public Employee() {
    }

    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(numeroSeguro);
        dest.writeString(id);
        dest.writeString(cedula);
        dest.writeLong(vence.getTime());
        dest.writeLong(vige.getTime());
    }

    private Employee(Parcel in ) {
        nombre = in .readString();
        numeroSeguro  = in .readString();
        id   = in .readString();
        cedula = in .readString();
        vence = new Date(in.readLong());
        vige = new Date(in.readLong());
    }
}
