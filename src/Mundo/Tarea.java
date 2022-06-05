package Mundo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Tarea {
    private String nombre;
    private String descr;
    private String tipo;
    private LocalTime tiempo;
    private LocalDate fecha;
    private ArrayList<Participante> encargados;
    private ArrayList<Actividad> asociadas;
    private boolean terminada;

    public Tarea(String tnombre, String tdescr, String ttipo, LocalTime ttiempo, LocalDate tfecha) {
        nombre = tnombre;
        descr = tdescr;
        tipo = ttipo;
        tiempo = ttiempo;
        fecha = tfecha;
        encargados = new ArrayList<Participante>();
        asociadas = new ArrayList<Actividad>();
        terminada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalTime getTiempo() {
        return tiempo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public ArrayList<Participante> getParts(){
        return encargados;
    }

    public ArrayList<Actividad> getActs(){
        return asociadas;
    }

    public boolean getProgreso() {
        return terminada;
    }

    public void setProgreso() {
        terminada = true;
    }
}