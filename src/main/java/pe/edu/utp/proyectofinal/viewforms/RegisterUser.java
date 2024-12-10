package pe.edu.utp.proyectofinal.viewforms;

import pe.edu.utp.proyectofinal.business.UserMgmt;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.IOException;
import java.io.Serializable;

// IMPORTANTE: import javax.faces.bean.ManagedBean;
@ManagedBean
@RequestScoped
public class RegisterUser implements Serializable {
    private String login;
    private String fullName;
    private String email;
    private String password;

    public RegisterUser() {
        login = "";
        fullName = "";
        email = "";
        password = "";
    }

    // Crear constructor completo


    public RegisterUser(String login, String fullName, String email, String password) {
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String newUser() throws IOException {
        try {
            UserMgmt.newUser(this);
			// Retornar la página register_ok con redireccion a true
            return "register_ok.xhtml?faces-redirect=true";
        } catch (IOException | IllegalStateException e) {
            // Guardar mensaje de error en LogFile
            // Devolver el outcome: register_error.xhtml?faces-redirect=true&error=detalle_del_error
            return "register_error.xhtml?faces-redirect=true&error=detalle_del_error";
        }
    }
}
