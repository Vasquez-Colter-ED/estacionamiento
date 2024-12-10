package pe.edu.utp.proyectofinal.viewforms;

public class Vehiculo implements VehiculoPrototype {
    private int idVehiculo;
    private String placa;
    private String tipo;
    private int idConductor;

    @Override
    public Vehiculo clone() throws CloneNotSupportedException {
        try{
            return (Vehiculo) super.clone();
        }catch (CloneNotSupportedException e){
            return new Vehiculo(this.idVehiculo, this.placa, this.tipo, this.idConductor);
        }
    }

    public Vehiculo() {
        idVehiculo = 0;
        placa = "";
        tipo = "";
        idConductor = 0;
    }

    public Vehiculo(int idVehiculo, String placa, String tipo, int idConductor) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.tipo = tipo;
        this.idConductor = idConductor;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", placa='" + placa + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idConductor=" + idConductor +
                '}';
    }
}
