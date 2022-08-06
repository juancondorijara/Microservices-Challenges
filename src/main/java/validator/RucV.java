package validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import dao.Conexion;

@FacesValidator(value = "rucV")
public class RucV extends Conexion implements Validator {

    public static boolean duplicadoRUC(String ruc) {
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement("SELECT RUCPROV FROM PROVEEDOR WHERE RUCPROV = '" + ruc + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en duplicadoRUC " + e.getMessage());
        }
        return false;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String numero = value.toString().trim();
        if (numero.length() != 0 && numero.length() < 11) {
            String formato = "^d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d$";
            boolean val = Pattern.matches(formato, numero);
            if (!val) {
                throw new ValidatorException(new FacesMessage("El formato es ###########"));
            }
        }
        String ruc = (String) value;
        if (duplicadoRUC(ruc) == true) {
            throw new ValidatorException(new FacesMessage("Duplicado, el RUC ya existe"));
        }
    }
    
}
