package pe.edu.utp.proyectofinal.viewforms;

public class DatosServicios {
    private int cantidadServicios;
    private int mesesRegistrando;
    private double descuento;

    public DatosServicios(int cantidadServicios, int mesesRegistrando, double descuento) {
        this.cantidadServicios = cantidadServicios;
        this.mesesRegistrando = mesesRegistrando;
        this.descuento = descuento;
    }

    public int getCantidadServicios() {
        return cantidadServicios;
    }

    public int getMesesRegistrando() {
        return mesesRegistrando;
    }

    public double getDescuento() {
        return descuento;
    }
}
