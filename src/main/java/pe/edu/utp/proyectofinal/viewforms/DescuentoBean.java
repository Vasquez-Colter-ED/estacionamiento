package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.service.Auth;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class DescuentoBean implements Serializable {
    private static final Logger logger = Logger.getLogger(DescuentoBean.class.getName());

    private String nombreConductor;
    private String tipoVehiculo;
    private int cantidadServicios;
    private int mesesRegistrando;
    private String mensajeDescuento;

    @PostConstruct
    public void init() {
        // Inicializa cualquier dato requerido aquí
    }

    public void obtenerDatosServicios() {
        try {
            DatosServicios datos = Auth.obtenerDatosServicios(nombreConductor, tipoVehiculo);
            if (datos != null) {
                this.cantidadServicios = datos.getCantidadServicios();
                this.mesesRegistrando = datos.getMesesRegistrando();
                this.descuento = datos.getDescuento();

                if (this.descuento > 0) {
                    this.mensajeDescuento = "¡Felicidades! Usted ha obtenido un descuento.";
                } else {
                    this.mensajeDescuento = "No hay descuento disponible en este momento.";
                }
            } else {
                this.mensajeDescuento = "No se encontraron datos para el conductor " + nombreConductor + ".";
                logger.log(Level.WARNING, "No se encontraron datos para el conductor {0}", nombreConductor);
            }
        } catch (SQLException | NamingException e) {
            this.mensajeDescuento = "Ocurrió un error al calcular el descuento.";
            logger.log(Level.SEVERE, "Error al recuperar datos de servicios para el conductor {0}. Excepción: {1}",
                    new Object[]{nombreConductor, e.getMessage()});
        }
    }

    private double descuento;

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getCantidadServicios() {
        return cantidadServicios;
    }

    public void setCantidadServicios(int cantidadServicios) {
        this.cantidadServicios = cantidadServicios;
    }

    public int getMesesRegistrando() {
        return mesesRegistrando;
    }

    public void setMesesRegistrando(int mesesRegistrando) {
        this.mesesRegistrando = mesesRegistrando;
    }

    public String getMensajeDescuento() {
        return mensajeDescuento;
    }

    public void setMensajeDescuento(String mensajeDescuento) {
        this.mensajeDescuento = mensajeDescuento;
    }
}