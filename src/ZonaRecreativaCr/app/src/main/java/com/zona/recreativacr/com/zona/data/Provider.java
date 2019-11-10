package com.zona.recreativacr.com.zona.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Provider implements Parcelable {
    public String descripcion, direccion, email, id, name;
    public String numeroTelefono, tipoDeServicio;

    public Provider() {
    }

    public Provider(String descripcion, String direccion, String email, String id, String name, String numeroTelefono, String tipoDeServicio) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.email = email;
        this.id = id;
        this.name = name;
        this.numeroTelefono = numeroTelefono;
        this.tipoDeServicio = tipoDeServicio;
    }

    public static final Parcelable.Creator<Provider> CREATOR = new Parcelable.Creator<Provider>() {
        public Provider createFromParcel(Parcel in) {
            return new Provider(in);
        }

        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descripcion);
        dest.writeString(direccion);
        dest.writeString(email);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(numeroTelefono);
        dest.writeString(tipoDeServicio);
    }

    private Provider(Parcel in ) {
        descripcion = in.readString();
        direccion = in.readString();
        email = in.readString();
        id = in.readString();
        name = in.readString();
        numeroTelefono = in.readString();
        tipoDeServicio = in.readString();
    }
}
