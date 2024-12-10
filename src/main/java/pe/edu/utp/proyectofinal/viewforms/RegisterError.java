package pe.edu.utp.proyectofinal.viewforms;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

// IMPORTANTE: import javax.faces.bean.ManagedBean;
@ManagedBean
@RequestScoped
public class RegisterError implements Serializable {
    private String error;

    public RegisterError() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// Nota: Utilice este metodo si se desea recibir el parametro "error" via GET
        String param = facesContext.getExternalContext()
                .getRequestParameterMap().get("error");

		// Si no se desea usar parametros via GET, se puede recibir informacion del error via un atributo de sesion				
        error = (param == null) ? "Unkown" : param;
    }

    public String getError() {
        return error;
    }

}
