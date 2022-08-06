package dao;

import modelo.Producto;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProductoImpl extends Conexion implements ICRUD<Producto> {

    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void registrar(Producto producto) throws Exception {
        String sql = "insert into PRODUCTO (NOMPRO, MARPRO, CODFAM, PRESPRO, DESPRO, PREPRO, STOPRO, VENPRO, IDPROV, ESTPRO) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setString(2, producto.getMARPRO());
            ps.setString(3, producto.getCODFAM());
            ps.setString(4, producto.getPRESPRO());
            ps.setString(5, producto.getDESPRO());
            ps.setDouble(6, producto.getPREPRO());
            ps.setInt(7, producto.getSTOPRO());
            ps.setString(8, formato.format(producto.getVENPRO()));
            ps.setInt(9, producto.getIDPROV());
            ps.setString(10, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Producto producto) throws Exception {
        String sql = "update PRODUCTO set NOMPRO=?, MARPRO=?, CODFAM=?, PRESPRO=?, DESPRO=?, PREPRO=?, STOPRO=?, VENPRO=?, IDPROV=?, ESTPRO=? where IDPRO=?"; //where IDPRO like?
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setString(2, producto.getMARPRO());
            ps.setString(3, producto.getCODFAM());
            ps.setString(4, producto.getPRESPRO());
            ps.setString(5, producto.getDESPRO());
            ps.setDouble(6, producto.getPREPRO());
            ps.setInt(7, producto.getSTOPRO());
            ps.setString(8, formato.format(producto.getVENPRO()));
            ps.setInt(9, producto.getIDPROV());
            ps.setString(10, "A");
            ps.setInt(11, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ModificarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Producto> listado = null;
        Producto producto;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vProductoT WHERE ESTPRO = 'A'";
                break;
            case 2:
                sql = "SELECT * FROM vProductoT WHERE ESTPRO = 'I'";
                break;
            case 3:
                sql = "SELECT * FROM vProductoT";
                break;
        }
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                producto = new Producto();
                producto.setIDPRO(rs.getInt("IDPRO"));
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setMARPRO(rs.getString("MARPRO"));
                producto.setCODFAM(rs.getString("CODFAM"));
                producto.setSUBFAM(rs.getString("SUBFAM"));
                producto.setNOMFAM(rs.getString("NOMFAM"));
                producto.setPRESPRO(rs.getString("PRESPRO"));
                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                producto.setSTOPRO(rs.getInt("STOPRO"));
                producto.setVENPRO(rs.getDate("VENPRO"));
                producto.setIDPROV(rs.getInt("IDPROV"));
                producto.setNOMPROV(rs.getString("NOMPROV"));
                listado.add(producto);
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
    public void eliminar(Producto producto) throws Exception {
        String sql = "update PRODUCTO set ESTPRO='I' where IDPRO=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    @Override
    public void restaurar(Producto producto) throws Exception {
        String sql = "update PRODUCTO set ESTPRO='A' where IDPRO=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RestaurarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    public boolean duplicadoDescripcion(Producto producto, List<Producto> listaProducto) {
        for (Producto pro : listaProducto) {
            if (producto.getDESPRO().equals(pro.getDESPRO())) {
                return true;
            }
        }
        return false;
    }
    
    public Double precio=0.0;
    public Integer stockPro = 0;
    public String nombre="", marca="", familia="", descripcion="", proveedor = "";
    
    public Integer obtenerIdProducto(String cadenaPro) throws SQLException, Exception {
        Integer idProducto = 0;
        try {
            CallableStatement ps = this.conectar().prepareCall("{call spDatosAutoCompletPro(?)}");
            ps.setString(1, cadenaPro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idProducto = rs.getInt("IDPRO");
                nombre = rs.getString("NOMPRO");
                marca = rs.getString("MARPRO");
                familia = rs.getString("SUBFAM");
                descripcion = rs.getString("DESPRO");
                precio = rs.getDouble("PREPRO");
                stockPro = rs.getInt("STOPRO");
                proveedor = rs.getString("NOMPROV");
            }            
        } catch (Exception e) {
            System.out.println("Error en obteneridProducto " + e.getMessage());            
        }
        return idProducto;
    }
    
}
