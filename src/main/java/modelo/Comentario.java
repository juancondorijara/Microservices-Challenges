package modelo;

import lombok.Data;

@Data

public class Comentario {
    
    private int IDCOM;
    private String ASUNCOM;
    private String MSGCOM;
    private String IDCLI;
    
    public Comentario() {
    }

    public Comentario(int IDCOM, String ASUNCOM, String MSGCOM, String IDCLI) {
        this.IDCOM = IDCOM;
        this.ASUNCOM = ASUNCOM;
        this.MSGCOM = MSGCOM;
        this.IDCLI = IDCLI;
    }
    
}
