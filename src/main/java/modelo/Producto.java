package modelo;

import java.util.Date;
import lombok.Data;

@Data

public class Producto {
    
    private Integer IDPRO;
    private String NOMPRO;
    private String MARPRO;
    private String CODFAM;
    private String SUBFAM;
    private String NOMFAM;
    private String PRESPRO;
    private String DESPRO;
    private Double PREPRO;
    private Integer STOPRO;
    private Date VENPRO;
    private Integer IDPROV;
    private String NOMPROV;
    private String ESTPRO;
    private Familia familia = new Familia();
    private Proveedor proveedor = new Proveedor();
    
    public Producto() {
    }
    
    public Producto(Integer IDPRO, String NOMPRO, String MARPRO, String CODFAM, String SUBFAM, String NOMFAM, String PRESPRO, String DESPRO, Double PREPRO, Integer STOPRO, Date VENPRO, Integer IDPROV, String NOMPROV, String ESTPRO, Familia familia, Proveedor proveedor) {
        this.IDPRO = IDPRO;
        this.NOMPRO = NOMPRO;
        this.MARPRO = MARPRO;
        this.CODFAM = CODFAM;
        this.SUBFAM = SUBFAM;
        this.NOMFAM = NOMFAM;
        this.PRESPRO = PRESPRO;
        this.DESPRO = DESPRO;
        this.PREPRO = PREPRO;
        this.STOPRO = STOPRO;
        this.VENPRO = VENPRO;
        this.IDPROV = IDPROV;
        this.NOMPROV = NOMPROV;
        this.ESTPRO = ESTPRO;
        this.familia = familia;
        this.proveedor = proveedor;
    }
    
}
