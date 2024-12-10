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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class ReporteBean implements Serializable {
    private static final Logger logger = Logger.getLogger(ReporteBean.class.getName());

    private String tipoVehiculo;
    private List<Map<String, Object>> reporteList;

    @PostConstruct
    public void init() {
        // Inicializar cualquier dato necesario
    }

    public void aplicarFiltros() {
        try {
            if (tipoVehiculo != null && !tipoVehiculo.isEmpty()) {
                reporteList = Auth.obtenerReporteServicios(tipoVehiculo);
            } else {
                // Manejar el caso cuando no se selecciona ningún tipo de vehículo
                reporteList = Auth.obtenerReporteServicios("");
            }
        } catch (SQLException | NamingException | IOException e) {
            logger.log(Level.SEVERE, "Error obteniendo el reporte", e);
        }
    }

    // Getters y Setters
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<Map<String, Object>> getReporteList() {
        return reporteList;
    }

    public void setReporteList(List<Map<String, Object>> reporteList) {
        this.reporteList = reporteList;
    }
}