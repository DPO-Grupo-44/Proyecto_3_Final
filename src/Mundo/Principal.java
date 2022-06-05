package Mundo;

import Interfaz.VentanaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    Laboratorio lab = new Laboratorio();
    CargaDeDatos cargar = new CargaDeDatos();

    public static void main(String [] args) {

        Principal Consola = new Principal();
        //VentanaPrincipal Ventana = new VentanaPrincipal();
        //Ventana.setVisible(true);
        Consola.ejecutarAplicacion();


    }
    public void ejecutarAplicacion() {
        boolean continuar = true;
        while(continuar) {
            try {
                mostrarMenu();
                int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opciÃ³n"));
                ArrayList<Proyecto> proyectosdisp = lab.getProyectos();
                if (opcion_seleccionada == 1) {
                    cargar.leerArchivo_proyectos(lab);
                    cargar.leerArchivo_proyectos(lab);
                }
                else if (opcion_seleccionada == 2)
                {

                    crearnuevoProyecto();
                }
                else if (opcion_seleccionada == 3)
                {
                    if (proyectosdisp.size() < 1) {
                        System.out.println("No hay proyectos disponibles, cree uno primero");
                    }
                    else {
                        int pos = mostrarProyectos(proyectosdisp);
                        Proyecto ubicacion = proyectosdisp.get(pos);
                        agregarMasParticipantes(ubicacion);
                    }
                }
                else if (opcion_seleccionada == 4)
                {
                    if (proyectosdisp.size() < 1) {
                        System.out.println("No hay proyectos disponibles, cree uno primero");
                    }
                    else {
                        int pos = mostrarProyectos(proyectosdisp);
                        Proyecto ubicacion = proyectosdisp.get(pos);
                        agregarActividades(ubicacion);
                    }
                }
                else if (opcion_seleccionada == 5) {
                    int pos = mostrarProyectos(proyectosdisp);
                    Proyecto ubicacion = proyectosdisp.get(pos);
                    ArrayList<Actividad>lista = ubicacion.getActividades();
                    if (lista.size() < 1) {
                        System.out.println("No hay actividades disponibles, cree una primero");
                    }
                    else {
                        int posact = mostrarActividades(lista);
                        Actividad ubact = lista.get(posact);
                        String res = input("Seleccione la hora");
                        ubact.setHora(res);
                        System.out.println(ubact.calcularDuracion());
                    }


                }
                else if (opcion_seleccionada == 6) {
                    int pos = mostrarProyectos(proyectosdisp);
                    Proyecto ubicacion = proyectosdisp.get(pos);
                    ArrayList<Actividad> lista = ubicacion.getActividades();
                    crearCalendario(lista);
                }

                else if (opcion_seleccionada == 7) {
                    int pos = mostrarProyectos(proyectosdisp);
                    Proyecto ubicacion = proyectosdisp.get(pos);
                    Paquete pack = ubicacion.getRamas().get(0);
                    String modificar = input("Desea modificar un paquete o una tarea?");
                    if (modificar.toLowerCase().equals("paquete")) {
                        String cadena = input("Nombre del paquete");
                        Paquete res2 = navegarArbolPack(pack, cadena);
                    }
                    else if (modificar.toLowerCase().equals("tarea")) {
                        String cadena = input("Nombre de la tarea");
                        Tarea res1 = navegarArbolTarea(pack, cadena);
                    }

                }

                else if (opcion_seleccionada == 8) {
                    continuar = false;
                }

                else {
                    System.out.println("Opcion no valida, por favor seleccione otra");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Debe seleccionar uno de los números de las opciones.");
            }
        }
    }

    public void menuModificacionesPack() {
        System.out.println("\nQue desea modificar?n\n");
        System.out.println("1. Nombre");
        System.out.println("2. Descripcion");
        System.out.println("3. Subpaquetes");
        System.out.println("4. Tareas");

    }

    public void realizarModificacionesPack(Paquete pack, int opcion_seleccionada) {
        if (opcion_seleccionada == 1) {

        }
        else if (opcion_seleccionada == 2)
        {

            crearnuevoProyecto();
        }
        else if (opcion_seleccionada == 3)
        {

        }
    }

    public void mostrarMenu()
    {
        System.out.println("\nOpciones de la aplicación\n");
        System.out.println("1. Cargar Un Archivo");
        System.out.println("2. Crear Un Proyecto");
        System.out.println("3. Agregar Un Participante");
        System.out.println("4. Crear Una Actividad");
        System.out.println("5. Modificar Una Actividad");
        System.out.println("6. Calendario");
        System.out.println("7. Modificar Plan");
        System.out.println("8. Salir de la Aplicacion");
    }

    public void crearnuevoProyecto() {
        String nombreproy = input("Ingrese nombre de proyecto");
        String descrproy = input("Ingrese la descripcion del proyecto");
        Proyecto proyectoNuevo = new Proyecto(nombreproy, descrproy, LocalDateTime.now(),
                null, LocalDateTime.now());
        lab.agregarProyectos(proyectoNuevo);
        System.out.println("Agregue un due�o a su proyecto");
        Participante dueño = crearnuevoParticipante();
        proyectoNuevo.setDueño(dueño);
        proyectoNuevo.agregarParticipantes(dueño);
        agregarTipos(proyectoNuevo);
        crearPlan(proyectoNuevo);


    }

    public void crearPlan(Proyecto proy) {
        System.out.println("Ahora es momento de definir la WBS");
        String nombrePack = input("Ingrese el nombre del paquete principal");
        String descrPack = input("Ingrese la descripcion");
        Paquete nuevoPaquete = new Paquete(nombrePack, descrPack);
        subPaquetes(proy, nuevoPaquete);
        proy.agregarPacks(nuevoPaquete);

    }

    public void subPaquetes(Proyecto proy, Paquete pack) {
        String subs = input("Hay subpaquetes? (Si/No)");
        if (subs.equals("Si")){
            int cant = Integer.parseInt(input("Ingrese la cantidad de subpaquetes"));
            for (int i=0; i< cant; i++) {
                String nuevoNom = input("Ingrese el nombre del paquete " + (i+1) + " que pertenece al paquete " + pack.getNombre());
                String nuevoDes = input("Ingrese la descripcion");
                Paquete subPaquete = new Paquete(nuevoNom, nuevoDes);
                pack.AgregarPacks(subPaquete);
                subPaquetes(proy, subPaquete);
            }
        }
        else if (subs.equals("No")) {
            String subs2 = input("Hay tareas para agregar? (Si/No)");
            if (subs2.equals("Si")){
                String nomTarea = input("Ingrese el nombre de la tarea que pertenece a " + pack.getNombre());
                String descr = input("Ingrese la descripcion");
                String tipo = noEstaTipo(proy);
                DateTimeFormatter formateadort = DateTimeFormatter.ofPattern("HH:mm");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                String tiempo = input("Ingrese un tiempo estimado");
                String fecha = input("Ingrese una fecha estimada de finalizacion");
                LocalTime time = LocalTime.parse(tiempo, formateadort);
                LocalDate date = LocalDate.parse(fecha, formatter);
                Tarea nuevaTarea = new Tarea(nomTarea, descr, tipo, time, date);
                pack.AgregarTareas(nuevaTarea);
            }
        }
    }

    public void agregarMasParticipantes(Proyecto tproyecto) {
        boolean continuar = true;
        while (continuar) {
            String res = input("Desea agregar mas participantes? (1- Si | 2- No");
            int resint = Integer.parseInt(res);
            if (resint == 1) {
                Participante nuevoParticipante = crearnuevoParticipante();
                boolean yaesta = lab.buscarParticipante(nuevoParticipante.getNombre(), tproyecto);
                if (yaesta == false){
                    tproyecto.agregarParticipantes(nuevoParticipante);
                }
                else {
                    System.out.println("El participante ya se encuentra agregado al proyecto");
                }
            }
            else {
                continuar = false;
            }

        }
    }

    public void agregarTipos(Proyecto tproyecto) {
        boolean continuar = true;
        while (continuar) {
            String res = input("Desea agregar un tipo? 1- Si | 2- No");
            int resint = Integer.parseInt(res);
            if (resint == 1) {
                String nuevoTipo = input("Ingrese el nombre del nuevo tipo de actividad");
                boolean yaesta = tproyecto.buscarTipo(nuevoTipo);
                if (yaesta){
                    System.out.println("El tipo ya ha sido agregado previamente");
                }
                else {
                    tproyecto.agregarTipos(nuevoTipo);
                }

            }
            else {
                continuar = false;
            }
        }
    }

    public void agregarActividades(Proyecto tproyecto) {
        boolean continuar = true;
        while (continuar) {
            String res = input("Desea agregar una actividad? 1- Si | 2- No");
            int resint = Integer.parseInt(res);
            if (resint == 1) {
                crearnuevaActividad(tproyecto);
            }
            else {
                continuar = false;
            }
        }
    }



    public Participante crearnuevoParticipante() {
        String nombreest = input("Ingrese su nombre");
        String correoest = input("Ingrese su correo");
        Participante nuevoParticipante = new Participante(nombreest, correoest);
        return nuevoParticipante;
    }

    public void crearnuevaActividad(Proyecto tproyecto) {
        LocalTime horai = LocalTime.now();
        String titulo = input("Titulo");
        String descripcion = input("Descripcion");
        String tipo = noEstaTipo(tproyecto);
        LocalDate fecha = LocalDate.now();
        LocalDate fechaf = LocalDate.now();
        LocalTime horaf = LocalTime.now();
        Participante encargado = noEstaPar(tproyecto);
        Actividad nuevaActividad = new Actividad(titulo, descripcion, tipo, fecha, fechaf, horai,
                horaf, encargado);
        tproyecto.agregarActividades(nuevaActividad);
        System.out.println("Actividad agregada de forma exitosa, pulse enter");
        Scanner teclado = new Scanner(System.in);
        teclado.nextLine();
        System.out.println("\n\t\tSe esta tomando el tiempo de la actividad, presione ENTER para continuar..."); //Mensaje en pantalla
        teclado.nextLine();
        try
        {
            nuevaActividad.setHoraf(LocalTime.now());
            String duracion = nuevaActividad.calcularDuracion();
            System.out.println("La duracion fue de " + duracion);
        }
        catch(Exception e)
        {}

    }

    public Participante estaParticipante(String nombre, Proyecto tproyecto) {

        Participante res = null;
        ArrayList<Participante> listaparticipantes = tproyecto.getParticipantes();
        for (int j=0;j<listaparticipantes.size();j++) {
            String nombreencon = listaparticipantes.get(j).getNombre();
            if (nombre.equals(nombreencon)) {
                res = listaparticipantes.get(j);
            }
        }
        return res;

    }

    public Participante noEstaPar(Proyecto tproyecto) {
        Participante encargado = null;
        boolean incorrecto = true;
        while (incorrecto) {
            String nombre = input("Nombre del encargado");
            encargado = estaParticipante(nombre, tproyecto);
            if (encargado == null) {
                System.out.println("El estudiante no hace parte del proyecto, por favor seleccione otro");
                System.out.println("Tipos validos: \n");

                for (int j=0;j<tproyecto.getParticipantes().size();j++) {
                    System.out.println(tproyecto.getParticipantes().get(j).getNombre());
                }
            }
            else {
                incorrecto = false;
            }
        }
        return encargado;

    }


    public String noEstaTipo(Proyecto tproyecto) {
        String tipo = null;
        boolean incorrecto = true;
        while (incorrecto) {
            tipo = input("Tipo");
            boolean yaesta = tproyecto.buscarTipo(tipo);
            if (yaesta) {
                incorrecto = false;

            }
            else {
                System.out.println("El tipo no hace parte del proyecto, por favor seleccione otro");
                System.out.println("Tipos validos: \n");
                for (int j=0;j<tproyecto.getTipos().size();j++) {
                    System.out.println(tproyecto.getTipos().get(j));
                }
            }
        }
        return tipo;

    }

    public ArrayList<String> buscarProyectos (Participante nombreparticipante){

        return null;
    }


    public String input(String mensaje)
    {
        try
        {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        }
        catch (IOException e)
        {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }
    public int mostrarProyectos(ArrayList<Proyecto> proyectosdisp) {
        System.out.println("Seleccione el proyecto del que quiere hacer las modificaciones");
        for (int j=0;j<proyectosdisp.size();j++) {
            System.out.println(j+"-" + " " + proyectosdisp.get(j).getNombre());
        }
        int respuesta = Integer.parseInt(input("Numero"));
        return respuesta;
    }

    public int mostrarActividades(ArrayList<Actividad> actividadesdisp) {
        System.out.println("Seleccione la actividad de la que va a realizar cambios");
        for (int j=0;j<actividadesdisp.size();j++) {
            System.out.println(j+"-" + " " + actividadesdisp.get(j).getNombre());
        }
        int respuesta = Integer.parseInt(input("Numero"));
        return respuesta;
    }

    public Tarea navegarArbolTarea(Paquete pack, String cadena) {
        Tarea res = null;
        ArrayList<Tarea> canttars = pack.getTareas();
        ArrayList<Paquete> cantsubs = pack.getPaquetes();
        if (cantsubs.size() > 0) {
            for (int i = 0; i< cantsubs.size(); i++) {
                Tarea resprov = navegarArbolTarea(cantsubs.get(i), cadena);
                if (resprov != null) {
                    res = resprov;
                }

            }
        }
        else if (canttars.size() > 0) {
            for (int j = 0; j < canttars.size(); j++) {
                if (canttars.get(j).getNombre().equals(cadena)) {
                    res = canttars.get(j);

                }
            }
        }

        return res;
    }


    public Paquete navegarArbolPack(Paquete pack, String cadena) {
        Paquete res = null;

        ArrayList<Paquete> cantsubs = pack.getPaquetes();
        if (cantsubs.size() > 0) {
            for (int i = 0; i< cantsubs.size(); i++) {
                if (cantsubs.get(i).getNombre().equals(cadena)) {
                    res = cantsubs.get(i);
                }
                else {
                    Paquete resprov = navegarArbolPack(cantsubs.get(i), cadena);
                    if (resprov != null) {
                        res = resprov;
                    }
                }
            }
        }

        return res;
    }

    public void crearCalendario(ArrayList<Actividad> actividades) {
        int ano = 2022;
        JPanel calendario = new JPanel();
        calendario.setBackground(Color.WHITE);

        for(int i = 1; i <= 12; i++) {

            System.out.println("\nMes: "+ i );
            System.out.println("Dom\tLun\tMar\tMier\tJue\tVie\tSab");

            int dias = diasMes(ano, i);
            int conDia = 0;
            int z = zeller(ano, i);
            for(int k = 0; k < z; k++) {
                conDia++;
                System.out.print("\t");
            }


            for(int j =1; j <= dias; j++) {
                String res = j + "\t";
                int l = 1;
                for(l= 1; l <= actividades.size(); l++) {
                    int fecha = actividades.get(l-1).getFecha().getMonthValue();
                    int dia = actividades.get(l-1).getFecha().getDayOfMonth();
                    if((fecha == i)&&(dia == j)) {
                        res = j+ "*" + "\t";
                    }
                }
                System.out.print(res);
                conDia++;
                if(conDia == 7) {
                    System.out.println();
                    conDia = 0;
                }
            }
        }

    }

    private static int zeller(int ano, int mes) {
        int a = (14 - mes) / 12;
        int y = ano - a;
        int m = mes + 12 * a - 2;
        int dia = 1, d;
        d = (dia + y + y / 4 - y / 100 + y / 400 + (31 * m) / 12) % 7;
        return (d);
    }

    public static int diasMes(int ano, int mes) {
        if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || 	mes == 10 || mes == 12) {
            return 31;
        }
        else if(mes == 2) {
            return 28;
        }
        else {
            return 30;
        }


    }



}