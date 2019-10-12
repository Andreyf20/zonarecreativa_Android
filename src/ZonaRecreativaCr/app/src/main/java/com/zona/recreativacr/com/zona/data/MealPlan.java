package com.zona.recreativacr.com.zona.data;

public class MealPlan {
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
}
