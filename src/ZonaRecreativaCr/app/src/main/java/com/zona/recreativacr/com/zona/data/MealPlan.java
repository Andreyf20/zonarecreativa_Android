package com.zona.recreativacr.com.zona.data;

public class MealPlan {
    public String almuerzo, cena, desayuno, meriendaAlmuerzo, meriendaCena, meriendaDesayuno, nombre;

    public MealPlan(String almuerzo, String cena, String desayuno, String meriendaAlmuerzo, String meriendaCena, String meriendaDesayuno, String nombre) {
        this.almuerzo = almuerzo;
        this.cena = cena;
        this.desayuno = desayuno;
        this.meriendaAlmuerzo = meriendaAlmuerzo;
        this.meriendaCena = meriendaCena;
        this.meriendaDesayuno = meriendaDesayuno;
        this.nombre = nombre;
    }

    public MealPlan() {
    }
}
