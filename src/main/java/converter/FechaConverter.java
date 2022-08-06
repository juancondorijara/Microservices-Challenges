package converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("fechaConverter")
public class FechaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = new Date(sdf.parse(value).getTime());
                return date;
            } catch (ParseException ex) {
                Logger.getLogger(FechaConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Date) {
            Calendar fechaCompara = Calendar.getInstance();
            fechaCompara.setTime((Date) value);
            if (fechaCompara.get(Calendar.DATE) == 31 && fechaCompara.get(Calendar.MONTH) == 11 && fechaCompara.get(Calendar.YEAR) == 3000) {
                return null;
            }
            Date fecha = (Date) value;
            return new SimpleDateFormat("dd-MMM-yyyy").format(fecha);
        } else {
            return null;
        }
    }

}
