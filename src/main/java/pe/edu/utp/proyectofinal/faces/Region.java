package pe.edu.utp.proyectofinal.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class Region implements Serializable {
    private String locale;

    public Region() {
        locale = "es"; // Locale por defecto
    }

    public Region(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
