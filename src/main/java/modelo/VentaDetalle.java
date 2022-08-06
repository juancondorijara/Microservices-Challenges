package modelo;

import java.util.Date;
import lombok.Data;

@Data

public class VentaDetalle {
    
    private Integer CANVENDET = 1;
    private Double PREVENDET;
    private String MARPRO;
    private String DESPRO;
    private String PRESPRO;
    private Integer STOPRO;
    private String NOMCLI;
    private Producto producto = new Producto();
    private Venta venta = new Venta();
    private String ESTVENDET;
    
    //PARA EL DETALLE DE LA VENTA
    private int IDVEN;
    private int IDVENDET;
    private int IDCLI;
    private int IDPRO;
    private Date FECVEN;
    private String CLIENTE;
    private String NOMPRO;
    private Double PREPRO;
    private int CANDET;
    private double SUBDET;
    private double TOTDET;

    public VentaDetalle() {
    }

    public VentaDetalle(Double PREVENDET, String MARPRO, String DESPRO, String PRESPRO, Integer STOPRO, String NOMCLI, String ESTVENDET, int IDVEN, int IDVENDET, int IDCLI, int IDPRO, Date FECVEN, String CLIENTE, String NOMPRO, Double PREPRO, int CANDET, double SUBDET, double TOTDET) {
        this.PREVENDET = PREVENDET;
        this.MARPRO = MARPRO;
        this.DESPRO = DESPRO;
        this.PRESPRO = PRESPRO;
        this.STOPRO = STOPRO;
        this.NOMCLI = NOMCLI;
        this.ESTVENDET = ESTVENDET;
        this.IDVEN = IDVEN;
        this.IDVENDET = IDVENDET;
        this.IDCLI = IDCLI;
        this.IDPRO = IDPRO;
        this.FECVEN = FECVEN;
        this.CLIENTE = CLIENTE;
        this.NOMPRO = NOMPRO;
        this.PREPRO = PREPRO;
        this.CANDET = CANDET;
        this.SUBDET = SUBDET;
        this.TOTDET = TOTDET;
    }
    
}