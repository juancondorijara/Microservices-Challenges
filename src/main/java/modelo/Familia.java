package modelo;

import lombok.Data;

@Data

public class Familia {
    
    private String CODFAM;
    private String NOMFAM;
    private String SUBFAM;
    
    public Familia() {
    }

    public Familia(String CODFAM, String NOMFAM, String SUBFAM) {
        this.CODFAM = CODFAM;
        this.NOMFAM = NOMFAM;
        this.SUBFAM = SUBFAM;
    }
    
}
