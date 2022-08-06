package modelo;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data

public class Venta {
    private int IDVEN;
    private Date FECVEN = GregorianCalendar.getInstance().getTime();
    private Cliente cliente = new Cliente();
    private Empleado empleado = new Empleado();
    private String IDEMP;
    private Double TOTVEN;
    private String ESTVEN;
    private Boolean TOGGLESWICHT = false;
    
    private Date INICIO;
    private Date FIN;

    public Venta() {
    }

    public Venta(int IDVEN, Date FECVEN, String IDEMP, Double TOTVEN, String ESTVEN, Cliente cliente, Empleado empleado) {
        this.IDVEN = IDVEN;
        this.FECVEN = FECVEN;
        this.IDEMP = IDEMP;
        this.TOTVEN = TOTVEN;
        this.ESTVEN = ESTVEN;
        this.cliente = cliente;
        this.empleado = empleado;
    }
    
}