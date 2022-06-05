package Mundo;

public class Participante {

    private String nombre;
    private String correo;

    public Participante(String tnombre, String tcorreo) {
        nombre = tnombre;
        correo = tcorreo;

    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

}
