package Mundo;

import java.util.ArrayList;

public class Paquete {
    private String nombre;
    private String descr;
    private ArrayList<Tarea> listatars;
    private ArrayList<Paquete> subpacks;

    public Paquete(String tnombre, String tdescr) {
        nombre = tnombre;
        descr = tdescr;
        listatars = new ArrayList<Tarea>();
        subpacks = new ArrayList<Paquete>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String tnombre) {
        nombre = tnombre;
    }

    public void setDescr(String tdescr) {
        descr = tdescr;
    }

    public ArrayList<Tarea> getTareas(){
        return listatars;
    }

    public ArrayList<Paquete> getPaquetes(){
        return subpacks;
    }

    public void AgregarTareas(Tarea tar) {
        listatars.add(tar);
    }

    public void AgregarPacks(Paquete pack) {
        subpacks.add(pack);
    }

    public void EliminarTareas(Tarea tar) {
        listatars.remove(tar);
    }

    public void EliminarPacks(Paquete pack) {
        subpacks.remove(pack);
    }
}

