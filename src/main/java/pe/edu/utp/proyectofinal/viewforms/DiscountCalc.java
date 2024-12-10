package pe.edu.utp.proyectofinal.viewforms;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class DiscountCalc {

    private static final Logger logger = Logger.getLogger("pe.edu.utp.ProyectoFinal.formview");

    private int month;
    private int service;
    private String vehicle;
    private float discount;


    public DiscountCalc() {
        this.month = 1;
        this.service = 1;
        this.vehicle = "vehicle";
        logger.info("Cloud Calc constructor is used");
    }

    public DiscountCalc(int month, int service, String vehicle) {
        this.month = month;
        this.service = service;
        this.vehicle = vehicle;
        discount = calculateDiscount();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    private float calculateDiscount(){
        discount = (float) ((month*service)*0.1);
        return discount;
    }

    public String calcDiscount(){
        calculateDiscount();
        return null;
    }

}
