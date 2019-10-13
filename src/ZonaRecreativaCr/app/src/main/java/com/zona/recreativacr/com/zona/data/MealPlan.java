package com.zona.recreativacr.com.zona.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MealPlan implements Parcelable {
    public String almuerzo, cena, desayuno, meriendaAlmuerzo, meriendaCena, meriendaDesayuno, nombre, id;

    public MealPlan(String almuerzo, String cena, String desayuno, String meriendaAlmuerzo, String meriendaCena, String meriendaDesayuno, String nombre, String id) {
        this.almuerzo = almuerzo;
        this.cena = cena;
        this.desayuno = desayuno;
        this.meriendaAlmuerzo = meriendaAlmuerzo;
        this.meriendaCena = meriendaCena;
        this.meriendaDesayuno = meriendaDesayuno;
        this.nombre = nombre;
        this.id = id;
    }

    public MealPlan() {
    }

    public static final Parcelable.Creator<MealPlan> CREATOR = new Parcelable.Creator<MealPlan>() {
        public MealPlan createFromParcel(Parcel in) {
            return new MealPlan(in);
        }

        public MealPlan[] newArray(int size) {
            return new MealPlan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(almuerzo);
        dest.writeString(cena);
        dest.writeString(desayuno);
        dest.writeString(meriendaAlmuerzo);
        dest.writeString(meriendaCena);
        dest.writeString(meriendaDesayuno);
        dest.writeString(nombre);
        dest.writeString(id);
    }

    private MealPlan(Parcel in) {
        almuerzo = in.readString();;
        cena = in.readString();;
        desayuno = in.readString();;
        meriendaAlmuerzo = in.readString();;
        meriendaCena = in.readString();;
        meriendaDesayuno = in.readString();;
        nombre = in.readString();;
        id = in.readString();;
    }
}
