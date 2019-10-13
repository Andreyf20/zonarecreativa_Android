package com.zona.recreativacr.com.zona.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Transport implements Parcelable{
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

    public static final Parcelable.Creator<Transport> CREATOR = new Parcelable.Creator<Transport>() {
        public Transport createFromParcel(Parcel in) {
            return new Transport(in);
        }

        public Transport[] newArray(int size) {
            return new Transport[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descripcion);
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(numeroTelefono);
        dest.writeInt(precio);
    }

    private Transport(Parcel in) {
        descripcion = in.readString();
        id = in.readString();
        nombre = in.readString();
        numeroTelefono = in.readString();
        precio = in.readInt();
    }
}
