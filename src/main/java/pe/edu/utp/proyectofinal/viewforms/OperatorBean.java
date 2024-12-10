package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.service.Auth;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class OperatorBean implements Serializable {
    private static final Logger logger = Logger.getLogger(OperatorBean.class.getName());

    private List<Trabajador> trabajadores;

    @PostConstruct
    public void init() {
        try {
            trabajadores = Auth.mostrarTrabajador();
        } catch (IOException | SQLException | NamingException e) {
            logger.log(Level.SEVERE, "Error al obtener los trabajadores", e);
        }
    }

    // Getters y Setters
    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }

}