package modelo;

import lombok.Data;

@Data

public class Proveedor {
    
    private Integer IDPROV;
    private String NOMPROV;
    private String DIRPROV;
    private String CELPROV;
    private String RUCPROV;
    private String COMPROV;
    private String EMAPROV;
    private String CODUBI;
    private String DISUBI;
    private String PROUBI;
    private String DEPUBI;
    private String ESTPROV;
    private Ubigeo ubigeo = new Ubigeo();
    
    public Proveedor() {
    }
    
    public Proveedor(Integer IDPROV, String NOMPROV, String DIRPROV, String CELPROV, String RUCPROV, String COMPROV, String EMAPROV, String CODUBI, String DISUBI, String PROUBI, String DEPUBI, String ESTPROV, Ubigeo ubigeo) {
        this.IDPROV = IDPROV;
        this.NOMPROV = NOMPROV;
        this.DIRPROV = DIRPROV;
        this.CELPROV = CELPROV;
        this.RUCPROV = RUCPROV;
        this.COMPROV = COMPROV;
        this.EMAPROV = EMAPROV;
        this.CODUBI = CODUBI;
        this.DISUBI = DISUBI;
        this.PROUBI = PROUBI;
        this.DEPUBI = DEPUBI;
        this.ESTPROV = ESTPROV;
        this.ubigeo = ubigeo;
    }
    
}
