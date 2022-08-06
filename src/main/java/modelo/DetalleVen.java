package modelo;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data

public class DetalleVen {

    private int idVen;
    private int idCli;
    private int idPro;
    private Date fecVen = GregorianCalendar.getInstance().getTime();
    private String cliente;
    private String dniCli;
    private String nomPro;
    private double prePro;
    private int centVentDet;
    private double subtotal;
    private double total;

    public DetalleVen() {
    }
    
    public DetalleVen(int idVen, int idCli, int idPro, String cliente, String dniCli, String nomPro, double prePro, int centVentDet, double subtotal, double total) {
        this.idVen = idVen;
        this.idCli = idCli;
        this.idPro = idPro;
        this.cliente = cliente;
        this.dniCli = dniCli;
        this.nomPro = nomPro;
        this.prePro = prePro;
        this.centVentDet = centVentDet;
        this.subtotal = subtotal;
        this.total = total;
    }
    
}
