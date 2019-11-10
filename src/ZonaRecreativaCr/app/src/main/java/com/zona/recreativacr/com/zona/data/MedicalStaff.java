package com.zona.recreativacr.com.zona.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MedicalStaff implements Parcelable {
    public String descripcion, nombre, id, numeroTelefono, provincia;

    public MedicalStaff(String descripcion, String nombre, String id, String numeroTelefono, String provincia) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.id = id;
        this.numeroTelefono = numeroTelefono;
        this.provincia = provincia;
    }

    public MedicalStaff() {
    }

    public static final Parcelable.Creator<MedicalStaff> CREATOR = new Parcelable.Creator<MedicalStaff>() {
        public MedicalStaff createFromParcel(Parcel in) {
            return new MedicalStaff(in);
        }

        public MedicalStaff[] newArray(int size) {
            return new MedicalStaff[size];
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
        dest.writeString(provincia);
    }

    private MedicalStaff(Parcel in) {
        descripcion = in.readString();
        id = in.readString();
        nombre = in.readString();
        numeroTelefono = in.readString();
        provincia = in.readString();
    }
}
