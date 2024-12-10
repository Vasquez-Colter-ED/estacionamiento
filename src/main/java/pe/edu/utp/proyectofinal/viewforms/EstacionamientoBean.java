package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.service.Auth;
import pe.edu.utp.proyectofinal.service.Validador;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
public class EstacionamientoBean {
    private static final Logger logger = Logger.getLogger("pe.edu.utp.ProyectoFinal.formview");
    private String tipoVehiculo;
    private String numeroPlaca;
    private int cantidadHoras;
    private String nombreConductor;
    private String dniConductor;
    private String montoCobrarStr;
    private String observaciones;
    private Boolean lavado;
    private String qrImagePath;


    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
        calcularCosto();
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
        calcularCosto();
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

    public String getMontoCobrarStr() {
        return montoCobrarStr;
    }

    public void setMontoCobrarStr(String montoCobrarStr) {
        this.montoCobrarStr = montoCobrarStr;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean isLavado() {
        return lavado;
    }

    public Boolean getLavado() {
        return lavado;
    }

    public void setLavado(Boolean lavado) {
        this.lavado = lavado;
    }

    public String getQrImagePath() {
        return qrImagePath;
    }

    public void setQrImagePath(String qrImagePath) {
        this.qrImagePath = qrImagePath;
    }

    public void calcularCosto() {
        double tarifaPorHora;
        switch (tipoVehiculo) {
            case "Auto":
                tarifaPorHora = 5.0;
                break;
            case "Moto":
                tarifaPorHora = 3.0;
                break;
            case "Bicicleta":
                tarifaPorHora = 2.0;
                break;
            case "Camión":
                tarifaPorHora = 10.0;
                break;
            default:
                tarifaPorHora = 4.0;
                break;
        }
        double costo = tarifaPorHora * cantidadHoras;
        this.montoCobrarStr = String.format("%.2f", costo);
    }

    public void registrarServicio() throws IOException {
        logger.info("Valor de lavado recibido: " + lavado);
        boolean incluyelavado = lavado != null && lavado;

        Timestamp fechaIngreso;
        Timestamp fechaSalida;
        double montoCobrar;

        try {
            LocalDateTime fechaIngresoStr = LocalDateTime.now();
            LocalDateTime fechaSalidaStr = fechaIngresoStr.plusHours(cantidadHoras);

            Validador.validarConductor(nombreConductor, dniConductor);
            Validador.validarVehiculo(numeroPlaca);

            fechaIngreso = Timestamp.valueOf(fechaIngresoStr);
            fechaSalida = Timestamp.valueOf(fechaSalidaStr);
            montoCobrar = Double.parseDouble(montoCobrarStr);

            int idConductor = Auth.obtenerIdConductor(dniConductor);
            if (idConductor <= 0) {
                Auth.insertarConductor(nombreConductor, dniConductor);
                idConductor = Auth.obtenerIdConductor(dniConductor);
            }

            Vehiculo vehiculoExistente = Auth.seleccionarVehiculo(Auth.obtenerIdVehiculo(numeroPlaca));
            if (vehiculoExistente != null) {
                Vehiculo vehiculoClonado = vehiculoExistente.clone();
                vehiculoClonado.setPlaca(numeroPlaca);
                Auth.insertarVehiculo(vehiculoClonado.getPlaca(), vehiculoClonado.getTipo(), vehiculoClonado.getIdConductor());
            } else {
                Auth.insertarVehiculo(numeroPlaca, tipoVehiculo, idConductor);
            }

            Servicio nuevoServicio;
            if (incluyelavado) {
                nuevoServicio = crearServicioLavado(fechaIngreso, fechaSalida, montoCobrar, observaciones, incluyelavado);
            } else {
                nuevoServicio = crearServicio(fechaIngreso, fechaSalida, montoCobrar, observaciones);
            }

            Auth.insertarServicio(nuevoServicio.getFechaHoraIngreso(), nuevoServicio.getFechaHoraSalida(), nuevoServicio.getMontoCobro(), nuevoServicio.getComentario(), Auth.obtenerIdVehiculo(numeroPlaca), incluyelavado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "El servicio se registró correctamente."));

            String qrContent = "http://136.248.115.139:8080/ProyectoFinal-1.0-SNAPSHOT/boleta.xhtml?" +
                    "tipoVehiculo=" + tipoVehiculo +
                    "&numeroPlaca=" + numeroPlaca +
                    "&cantidadHoras=" + cantidadHoras +
                    "&nombreConductor=" + nombreConductor +
                    "&dniConductor=" + dniConductor +
                    "&montoCobrar=" + montoCobrarStr +
                    "&observaciones=" + observaciones +
                    "&lavado=" + lavado;

            // Generar QR en formato Base64
            qrImagePath = QRService.generateQRBase64(qrContent, 300, 300);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "QR generado", "El QR se generó correctamente y se muestra en la página."));
        } catch (Exception e) {
            String errorMessage = "Error al generar el QR: " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new IOException(errorMessage, e);
        }
    }

    public Servicio crearServicio(Timestamp ingreso, Timestamp salida, double monto, String comentario) {
        return new Servicio.Builder(ingreso, salida, monto, comentario)
                .build(); // Aquí se asegura que `lavado` se establece en `false` por defecto en el builder
    }

    public Servicio crearServicioLavado(Timestamp ingreso, Timestamp salida, double monto, String comentario, boolean lavado) {
        return new Servicio.Builder(ingreso, salida, monto, comentario).withLavado().build();
    }
}