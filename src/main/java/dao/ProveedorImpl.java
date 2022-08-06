package dao;

import modelo.Proveedor;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ProveedorImpl extends Conexion implements ICRUD<Proveedor>  {
    
    @Override
    public void registrar(Proveedor proveedor) throws Exception {
        String sql = "insert into PROVEEDOR (NOMPROV, DIRPROV, CELPROV, RUCPROV, COMPROV, EMAPROV, CODUBI, ESTPROV) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getNOMPROV());
            ps.setString(2, proveedor.getDIRPROV());
            ps.setString(3, proveedor.getCELPROV());
            ps.setString(4, proveedor.getRUCPROV());
            ps.setString(5, proveedor.getCOMPROV());
            ps.setString(6, proveedor.getEMAPROV());
            ps.setString(7, proveedor.getCODUBI());
            ps.setString(8, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Proveedor proveedor) throws Exception {
        String sql = "update PROVEEDOR set NOMPROV=?, DIRPROV=?, CELPROV=?, RUCPROV=?, COMPROV=?, EMAPROV=?, CODUBI=?, ESTPROV=? where IDPROV=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getNOMPROV());
            ps.setString(2, proveedor.getDIRPROV());
            ps.setString(3, proveedor.getCELPROV());
            ps.setString(4, proveedor.getRUCPROV());
            ps.setString(5, proveedor.getCOMPROV());
            ps.setString(6, proveedor.getEMAPROV());
            ps.setString(7, proveedor.getCODUBI());
            ps.setString(8, "A");
            ps.setInt(9, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Proveedor> listado = null;
        Proveedor proveedor;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "select * from vProveedorT where ESTPROV='A'";
                break;
            case 2:
                sql = "select * from vProveedorT where ESTPROV='I'";
                break;
            case 3:
                sql = "select * from vProveedorT";
                break;
        }
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setIDPROV(rs.getInt("IDPROV"));
                proveedor.setNOMPROV(rs.getString("NOMPROV"));
                proveedor.setDIRPROV(rs.getString("DIRPROV"));
                proveedor.setCELPROV(rs.getString("CELPROV"));
                proveedor.setRUCPROV(rs.getString("RUCPROV"));
                proveedor.setCOMPROV(rs.getString("COMPROV"));
                proveedor.setEMAPROV(rs.getString("EMAPROV"));
                proveedor.setCODUBI(rs.getString("CODUBI"));
                proveedor.setDISUBI(rs.getString("DISUBI"));
                proveedor.setPROUBI(rs.getString("PROUBI"));
                proveedor.setDEPUBI(rs.getString("DEPUBI"));
                listado.add(proveedor);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en ListarTodosD" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }
    
    @Override
    public void eliminar(Proveedor proveedor) throws Exception {
        String sql = "update PROVEEDOR set ESTPROV='I' where IDPROV=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void restaurar(Proveedor proveedor) throws Exception {
        String sql = "update PROVEEDOR set ESTPROV='A' where IDPROV=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RestaurarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    public void eliminarDelete(Proveedor proveedor) throws Exception {
        String sql = "delete from PROVEEDOR where IDPROV=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    public boolean duplicadoRUC(Proveedor proveedor, List<Proveedor> listaProveedor) {
        for (Proveedor prov : listaProveedor) {
            if (proveedor.getRUCPROV().equals(prov.getRUCPROV())) {
                return true;
            }
        }
        return false;
    }
    
}
