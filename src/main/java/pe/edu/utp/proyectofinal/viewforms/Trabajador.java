package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.service.Auth;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trabajador {
    private static final Logger logger = Logger.getLogger(Trabajador.class.getName());
    private String nombres;
    private String apellidos;
    private int edad;
    private String dni;
    private String telefono;
    private String email;
    private String turno;
    private boolean worked;

    // Getters y Setters
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public boolean isWorked() {
        return worked;
    }

    public void setWorked(boolean worked) {
        this.worked = worked;
    }

    public void makeWork() {
        this.worked = !this.worked;
        String estado = this.worked ? "trabajo" : "no trabajo";
        try {
            Auth.registrarAsistencia(this.nombres, this.apellidos, estado);
            logger.log(Level.INFO, "Work status changed for {0} {1} to {2}", new Object[]{this.nombres, this.apellidos, estado});
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error changing work status for {0} {1} to {2}", new Object[]{this.nombres, this.apellidos, estado});
            e.printStackTrace();
        }
    }

}