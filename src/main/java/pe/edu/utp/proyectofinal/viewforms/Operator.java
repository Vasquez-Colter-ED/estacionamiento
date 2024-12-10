package pe.edu.utp.proyectofinal.viewforms;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.logging.Logger;

// Configurar User como ManageBean y SessionScoped
@ManagedBean
@SessionScoped
public class Operator implements Serializable{
//    private static final Logger logger = Logger.getLogger("pe.edu.utp.ProyectoFinal.formview");
//
//    private String nombres;
//    private String apellidos;
//    private int edad;
//    private String dni;
//    private String telefono;
//    private String correo;
//    private String turno;
//    private boolean worked;
//    private String image;
//
//    public Operator() {
//        // Completar
//        nombres = "";
//        apellidos = "";
//        edad = 0;
//        dni = "";
//        telefono = "";
//        correo = "";
//        turno = "";
//        worked = false;
//        image = null;
//    }
//
//    public Operator(String nombres, String apellidos, int edad, String dni, String telefono, String correo, String turno, boolean worked, String image) {
//        this.nombres = nombres;
//        this.apellidos = apellidos;
//        this.edad = edad;
//        this.dni = dni;
//        this.telefono = telefono;
//        this.correo = correo;
//        this.turno = turno;
//        this.worked = worked;
//        this.image = image;
//    }
//
//    public String getNombres() {
//        return nombres;
//    }
//
//    public void setNombres(String nombres) {
//        this.nombres = nombres;
//    }
//
//    public String getApellidos() {
//        return apellidos;
//    }
//
//    public void setApellidos(String apellidos) {
//        this.apellidos = apellidos;
//    }
//
//    public int getEdad() {
//        return edad;
//    }
//
//    public void setEdad(int edad) {
//        this.edad = edad;
//    }
//
//    public String getDni() {
//        return dni;
//    }
//
//    public void setDni(String dni) {
//        this.dni = dni;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) {
//        this.telefono = telefono;
//    }
//
//    public String getCorreo() {
//        return correo;
//    }
//
//    public void setCorreo(String correo) {
//        this.correo = correo;
//    }
//
//    public String getTurno() {
//        return turno;
//    }
//
//    public void setTurno(String turno) {
//        this.turno = turno;
//    }
//
//    public boolean isWorked() {
//        return worked;
//    }
//
//    public void setWorked(boolean worked) {
//        this.worked = worked;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public void takeWork() {
//        this.worked = true;
//    }
//
//    public void noWorked() {
//        this.worked = false;
//    }
//
//    // metodo makeService
//    public void makeWork(String name){
//        if(!this.worked){
//            takeWork();
//            logger.info(name + " realizo su turno");
//        }else {
//            noWorked();
//            logger.info(name + " no realizo su turno");
//        }
//    }
}
