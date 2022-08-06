package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "apellidosV")
public class ApellidosV implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent arg1, Object value) throws ValidatorException {
        int cantidadDeEspacios = 0;
        // Recorremos la cadena:
        for (int i = 0; i < ((String) value).length(); i++) {
            // Si el carácter en [i] es un espacio (' ') aumentamos el contador 
            if (((String) value).charAt(i) == ' ') {
                cantidadDeEspacios++;
            }
        }
        if (cantidadDeEspacios == 0) {
            throw new ValidatorException(new FacesMessage("Complete sus 2 Apellidos"));
        }
    }
    
}
