package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
//https://www.youtube.com/watch?v=VyG7sWephmI

@FacesValidator(value = "nombresV")
public class NombresV implements Validator {

//    private static final String NOMBRES_PATTERN = "^([A-Z_Á_É_Í_Ó_Ú]{1}[a-z_á_é_í_ó_ú]+[ ]*){1,2}$";  //Formato: Juan || Juan Gabriel
    private static final String NOMBRES_PATTERN = "^([a-z_á_é_í_ó_ú]{0}[a-z_á_é_í_ó_ú]+[ ]*){1,2}$";  //Formato: juan || juan gabriel

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile(NOMBRES_PATTERN);
        String nombres = ((String) value).trim();
        if (nombres.isEmpty()) {
        } else {
            Matcher matcher = pattern.matcher(nombres);
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage("Formato ejemplo: Juan Gabriel");
                throw new ValidatorException(msg);
            }
        }
    }
    
}

