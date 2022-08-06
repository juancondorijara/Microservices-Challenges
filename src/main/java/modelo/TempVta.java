package modelo;

import lombok.Data;

@Data

public class TempVta {
    Integer idpro, cantidad, stockPro;
    double precio,subTotal;
    String nombre, marca, familia, descripcion, proveedor;
    
    public TempVta() {
    }
    
}
