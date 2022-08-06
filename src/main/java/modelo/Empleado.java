package modelo;

import java.util.UUID;
import lombok.Data;

@Data

public class Empleado {
    
    String pass1 = UUID.randomUUID().toString().toUpperCase().substring(2, 5);
    String pass2 = UUID.randomUUID().toString().toLowerCase().substring(2, 5);
    String pass3 = "#+";
    String password = pass1 + pass2 + pass3;
    
    private Integer IDEMP;
    private String NOMEMP;
    private String APEEMP;
    private String EMAEMP;
    private String DNIEMP;
    private String CELEMP;
    private String ROLEMP;
    private String ESTEMP;
    private String PWDEMP = password;
    
    public Empleado() {
    }

    public Empleado(Integer IDEMP, String NOMEMP, String APEEMP, String EMAEMP, String DNIEMP, String CELEMP, String ROLEMP, String ESTEMP, String PWDEMP) {
        this.IDEMP = IDEMP;
        this.NOMEMP = NOMEMP;
        this.APEEMP = APEEMP;
        this.EMAEMP = EMAEMP;
        this.DNIEMP = DNIEMP;
        this.CELEMP = CELEMP;
        this.ROLEMP = ROLEMP;
        this.ESTEMP = ESTEMP;
        this.PWDEMP = PWDEMP;
    }
    
}
