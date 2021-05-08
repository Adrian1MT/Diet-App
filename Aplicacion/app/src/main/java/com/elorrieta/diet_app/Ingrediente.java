package com.elorrieta.diet_app;

public class Ingrediente {
    String nombre;
    Float cantidad;
    String unidad;

    //Constructor lleno
    Ingrediente(String nombre, Float cantidad, String unidad){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    //Constructor vacio
    Ingrediente(){
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
