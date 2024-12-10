package pe.edu.utp.proyectofinal.viewforms;

import java.sql.Timestamp;

public class Servicio {
    private Timestamp fechaHoraIngreso;
    private Timestamp fechaHoraSalida;
    private double montoCobro;
    private String comentario;
    private boolean lavado;

    private Servicio(Builder builder) {
        this.fechaHoraIngreso = builder.fechaHoraIngreso;
        this.fechaHoraSalida = builder.fechaHoraSalida;
        this.montoCobro = builder.montoCobro;
        this.comentario = builder.comentario;
        this.lavado = builder.lavado;
    }

    public static class Builder {
        private Timestamp fechaHoraIngreso;
        private Timestamp fechaHoraSalida;
        private double montoCobro;
        private String comentario;
        private boolean lavado;

        public Builder(Timestamp fechaHoraIngreso, Timestamp fechaHoraSalida, double montoCobro, String comentario) {
            this.fechaHoraIngreso = fechaHoraIngreso;
            this.fechaHoraSalida = fechaHoraSalida;
            this.montoCobro = montoCobro;
            this.comentario = comentario;
        }

        public Builder withLavado() {
            this.lavado = true;
            return this;
        }

        public Servicio build() {
            return new Servicio(this);
        }
    }

    public Timestamp getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(Timestamp fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public Timestamp getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(Timestamp fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public double getMontoCobro() {
        return montoCobro;
    }

    public void setMontoCobro(double montoCobro) {
        this.montoCobro = montoCobro;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isLavado() {
        return lavado;
    }

    public void setLavado(boolean lavado) {
        this.lavado = lavado;
    }
}
