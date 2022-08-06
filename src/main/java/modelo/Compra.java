package modelo;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data

public class Compra {
    
    private Integer IDCOM;
    private Integer IDPROV;
    private String NOMPROV;
    private Integer IDPRO;
    private String NOMPRO;
    private String MARPRO;
    private String DESPRO;
    private Integer CANCOM;
    private Double TOTCOM;
    private Date FECCOM = GregorianCalendar.getInstance().getTime();
    private String ESTCOM;
    private Proveedor proveedor = new Proveedor();
    private Producto producto = new Producto();
    
    public Compra() {
    }

    public Compra(Integer IDCOM, Integer IDPROV, String NOMPROV, Integer IDPRO, String NOMPRO, String MARPRO, String DESPRO, Integer CANCOM, Double TOTCOM, String ESTCOM) {
        this.IDCOM = IDCOM;
        this.IDPROV = IDPROV;
        this.NOMPROV = NOMPROV;
        this.IDPRO = IDPRO;
        this.NOMPRO = NOMPRO;
        this.MARPRO = MARPRO;
        this.DESPRO = DESPRO;
        this.CANCOM = CANCOM;
        this.TOTCOM = TOTCOM;
        this.ESTCOM = ESTCOM;
    }
    
}
