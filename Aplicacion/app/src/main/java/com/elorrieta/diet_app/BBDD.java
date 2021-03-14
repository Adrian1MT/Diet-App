package com.elorrieta.diet_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BBDD extends SQLiteOpenHelper {
    public BBDD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table dieta(nomDieta varchar(10) primary key);");
        db.execSQL("create table contiene(cantidad integer, nomDieta varchar(10), id integer, foreign key(nomDieta) references dieta(nomDieta), foreign key(id) references receta(id), primary key(id), primary key(nomDieta))");
        db.execSQL("create table receta(id integer primary key, nombre varchar(10), elaboracion varchar(150), foto varchar(50), tiempo integer, dificultad varchar(10), tipo varchar(10), origen varchar(10));");
        db.execSQL("create table nComensales(numComensales integer, id integer, primary key(numComensales), primary key(id), foreign key(id) references receta(id));");
        db.execSQL("create table tiene(numComensales integer, nomIngrediente varchar(10), cantidad integer, unidad varchar(10), foreign key(numComensales) references nComensales(numComensales), foreign key(nomIngrediente) references ingrediente(nomIngrediente), primary key(nomIngrediente), primary key(numComensales))");
        db.execSQL("create table ingrediente(nomIngrediente varchar(10) primary key, foto varchar(50), precio float)");
        db.execSQL("create table hay(nomAlmacen varchar(20), nomIngrediente varchar(10), unidad varchar(10), cantidad integer, foreign key(nomIngrediente) references ingrediente(nomIngrediente), foreign key(nomAlmacen) references almacen(nomAlmacen), primary key(nomAlmacen), primary key(nomIngrediente))");
        db.execSQL("create table almacen(nomAlmacen varchar(20) primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
