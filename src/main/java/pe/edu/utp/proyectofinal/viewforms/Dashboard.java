package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.service.Auth;
import pe.edu.utp.proyectofinal.util.LogFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Logger;

// IMPORTANTE: import javax.faces.bean.ManagedBean;
@ManagedBean
@RequestScoped
public class Dashboard implements Serializable {
    private String sessionId;
    private String tipoVehiculo;
    private String numeroPlaca;
    private String fechaIngreso;
    private String fechaSalida;
    private String nombreConductor;
    private String dniConductor;
    private double montoCobrar;
    private String observaciones;
    private boolean lavado;

    private static final Logger logger = Logger.getLogger("pe.edu.utp.ProyectoFinal.formview");

    public Dashboard() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        sessionId = (String) externalContext.getSessionMap().get("sessionId");

        // Si no se recibio el sessionId se redirije a index.xhtml
        // -- Colocar codigo aqui --
        if (sessionId == null) {
            logger.info("Se intento ingresar a dashboard sin iniciar sesion");
            externalContext.redirect(externalContext.getRequestContextPath()+"/index.xhtml");
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String logout(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.getSessionMap().remove("sessionId");
        return "index.xhtml?faces-redirect=true";
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getDniConductor() {
        return dniConductor;
    }

    public void setDniConductor(String dniConductor) {
        this.dniConductor = dniConductor;
    }

    public double getMontoCobrar() {
        return montoCobrar;
    }

    public void setMontoCobrar(double montoCobrar) {
        this.montoCobrar = montoCobrar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isLavado() {
        return lavado;
    }

    public void setLavado(boolean lavado) {
        this.lavado = lavado;
    }

//    public String save() throws IOException {
//        if (tipoVehiculo == null || tipoVehiculo.isEmpty()) {
//            logger.info("Tipo de vehículo es requerido");
//            return "error.xhtml?faces-redirect=true";
//        }
//        if (numeroPlaca == null || numeroPlaca.isEmpty()) {
//            logger.info("Número de placa es requerido");
//            return "error.xhtml?faces-redirect=true";
//        }
//        if (fechaIngreso == null || fechaIngreso.isEmpty()) {
//            logger.info("Fecha de ingreso es requerida");
//            return "error.xhtml?faces-redirect=true";
//        }
//        if (nombreConductor == null || nombreConductor.isEmpty()) {
//            logger.info("Nombre del conductor es requerido");
//            return "error.xhtml?faces-redirect=true";
//        }
//        if (dniConductor == null || dniConductor.isEmpty()) {
//            logger.info("DNI del conductor es requerido");
//            return "error.xhtml?faces-redirect=true";
//        }
//        if (montoCobrar <= 0) {
//            logger.info("Monto a cobrar debe ser mayor a cero");
//            return "error.xhtml?faces-redirect=true";
//        }
//
//        try {
//            Auth.saveDashboardInfo(tipoVehiculo, numeroPlaca, fechaIngreso, fechaSalida, nombreConductor, dniConductor, montoCobrar, observaciones, lavado);
//            return "dashboard.xhtml?faces-redirect=true";
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return "error.xhtml?faces-redirect=true";
//        } catch (NamingException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
