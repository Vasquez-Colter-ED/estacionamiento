package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.service.Auth;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class TrabajadorBean {
    private static final Logger logger = Logger.getLogger(TrabajadorBean.class.getName());
    private List<Trabajador> trabajadores;
//    private Trabajador selectedTrabajador;

    @PostConstruct
    public void init() {
        try {
            trabajadores = Auth.mostrarTrabajador();
        } catch (IOException | SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error initializing TrabajadorBean: {0}", e.getMessage());
        }
    }

    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

//    public Trabajador getSelectedTrabajador() {
//        return selectedTrabajador;
//    }
//
//    public void setSelectedTrabajador(Trabajador selectedTrabajador) {
//        this.selectedTrabajador = selectedTrabajador;
//    }

    public void editarTrabajador(Trabajador trabajador, String oldNombres, String oldApellidos) {
        try {
            Auth.editarTrabajador(oldNombres, oldApellidos, trabajador.getNombres(), trabajador.getApellidos(),
                    trabajador.getEdad(), trabajador.getDni(), trabajador.getTelefono(), trabajador.getEmail(), trabajador.getTurno());

            logger.log(Level.INFO, "Trabajador actualizado: {0}", trabajador);
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error al actualizar el trabajador", e);
        }
    }






    public void eliminarTrabajador(Trabajador trabajador) {
        try {
            Auth.eliminarTrabajador(trabajador.getNombres(), trabajador.getApellidos());
            trabajadores.remove(trabajador);
            logger.log(Level.INFO, "Trabajador eliminado: {0}", trabajador);
        } catch (SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error al eliminar el trabajador", e);
        }
    }
}