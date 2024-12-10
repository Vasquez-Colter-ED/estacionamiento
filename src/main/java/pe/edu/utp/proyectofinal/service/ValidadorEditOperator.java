package pe.edu.utp.proyectofinal.service;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;

@FacesValidator("pe.edu.utp.proyectofinal.ValidadorEditOperator")
public class ValidadorEditOperator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
            throws ValidatorException {
        // Obtener atributo del core tag
        String username = o.toString();

        // Validar si el usuario tiene elevados privilegios
        boolean is_privileged_user = username.equals("root")
                || username.equals("admin") || username.equals("wheel");

        // Crear Validador
//        if(is_privileged_user) {
//            String error_msg = "Usuario invalido: El usuario '%s' es un usuario privilegiado";
//            String error_detail = "El usuario no puede ser root, admin o sys";
//            FacesMessage message = new FacesMessage(String.format(error_msg, username),
//                    error_detail);
//            message.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(message);
//        }

        if (is_privileged_user) {
            ResourceBundle bundle = facesContext.getApplication()
                    .getResourceBundle(facesContext, "bundle");

            String error_msg = bundle.getString("backup.usuarioPriviligiado");
            String error_detail = bundle.getString("backup.usuarioDetalle");

            FacesMessage message = new FacesMessage(String.format(error_msg, username),
                    error_detail);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
}
