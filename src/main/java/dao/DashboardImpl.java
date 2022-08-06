package dao;

import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Dashboard;
import modelo.Usuario;

public class DashboardImpl extends Conexion {
    
    public List listarUserEmpleado() throws Exception {
        List<Usuario> listado = null;
        Usuario usuario;
        String sql = "select * from vUsuarioEmpleado";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setIDUSU(rs.getInt("IDUSU"));
                usuario.setIDEMP(rs.getInt("IDEMP"));
                usuario.setUSUUSU(rs.getString("USUUSU"));
                usuario.setNOMEMP(rs.getString("NOMEMP"));
                usuario.setAPEEMP(rs.getString("APEEMP"));
                usuario.setROLUSU(rs.getString("ROLEMP"));
                listado.add(usuario);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en listarUserEmpleado_D " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }
    
    public List<Number> dashboardCliente() throws Exception {
        this.conectar();
        List<Number> lista = new ArrayList();
        try {
            String sql = "SELECT COUNT(CASE ESTCLI WHEN 'A' THEN 'A' END) AS ACTIVO , COUNT(CASE ESTCLI WHEN 'I' THEN 'I' END) AS INACTIVO FROM CLIENTE";
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Existen datos");
                lista.add(rs.getInt("ACTIVO"));
                lista.add(rs.getInt("INACTIVO"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en dashboardClienteD " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public List<Number> dashboardEmpleado() throws Exception {
        this.conectar();
        List<Number> lista = new ArrayList<>();
        try {
            String sql = "SELECT COUNT(CASE ROLEMP WHEN 'A' THEN 'A' END) AS ADMINISTRADOR , COUNT(CASE ROLEMP WHEN 'T' THEN 'T' END) AS TRABAJADOR FROM EMPLEADO";
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Existe la cantidad de empleados");
                lista.add(rs.getInt("ADMINISTRADOR"));
                lista.add(rs.getInt("TRABAJADOR"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en el dashboardEmpleadoD "+ e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public List<Dashboard> dashboardVenta() throws Exception{
        List<Dashboard> lista = null;
        Dashboard venta;
        String sql = "SELECT * FROM chartBar ORDER BY chartBar.mes DESC";
        try {
            lista = new ArrayList<>();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                venta = new Dashboard();
                venta.setMES(rs.getString("MES"));
                venta.setTOTAL(rs.getDouble("TOTAL"));
                lista.add(venta);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en chatBar: "+ e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
}
