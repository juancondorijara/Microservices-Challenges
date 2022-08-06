package modelo;

import lombok.Data;

@Data

public class Dashboard {
    
    //DASHBOARD CLIENTE---------------------------------------------------------
    private int ACTIVO;
    private int INACTIVO;
    
    public int Dashboard() {
        return ACTIVO;
    }
    //DASHBOARD CLIENTE---------------------------------------------------------
    
    
    //DASHBOARD EMPLEADO--------------------------------------------------------
    private int ADMINISTRADOR;
    private int TRABAJADOR;
    //DASHBOARD EMPLEADO---------------------------------------------------------
    
    
    //DASHBOARD VENTA-----------------------------------------------------------
    private double TOTAL;
    private String MES;
    //DASHBOARD VENTA-----------------------------------------------------------
    
    public Dashboard() {
    }
    
    @Override
    public String toString() {
        return "Dashboard{" + "ACTIVO=" + ACTIVO + ", INACTIVO=" + INACTIVO + ", ADMINISTRADOR=" + ADMINISTRADOR + ", TRABAJADOR=" + TRABAJADOR + ", TOTAL=" + TOTAL + ", MES=" + MES + '}';
    }
    
}