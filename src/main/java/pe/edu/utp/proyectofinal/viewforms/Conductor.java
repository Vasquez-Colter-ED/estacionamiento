package pe.edu.utp.proyectofinal.viewforms;

import java.io.Serializable;

public class Conductor implements Serializable {
    private int idConductor;
    private String nombre;
    private String dni;

    public Conductor() {
        idConductor = 0;
        nombre = "";
        dni = "";
    }

    public Conductor(int idConductor, String nombre, String dni) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.dni = dni;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
