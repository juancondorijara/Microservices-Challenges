package dao;

import java.sql.PreparedStatement;
import java.util.List;
import modelo.Producto;
import modelo.Compra;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CompraImpl extends Conexion {
    
    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    
    public void registrar(Compra compra) throws Exception {
        String sql = "insert into COMPRA (IDPROV, IDPRO, CANCOM, TOTCOM, FECCOM, ESTCOM) values (?,?,?,?,?,?)";
        try {
            Producto producto = new Producto();
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, compra.getProveedor().getIDPROV());
            ps.setInt(2, compra.getProducto().getIDPRO());
            ps.setInt(3, compra.getCANCOM());
            ps.setDouble(4, compra.getTOTCOM());
//            ps.setDouble(4, compra.setTOTCOM(compra.getCANCOM() * producto.getPREPRO()));
            ps.setString(5, formato.format(compra.getFECCOM()));
            ps.setString(6, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    public List listarTodos() throws Exception {
        List<Compra> listado = null;
        Compra compra;
        String sql = "select * from vCompra";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                compra = new Compra();
                compra.setIDCOM(rs.getInt("IDCOM"));
                compra.setNOMPROV(rs.getString("NOMPROV"));
                compra.setNOMPRO(rs.getString("NOMPRO"));
                compra.setMARPRO(rs.getString("MARPRO"));
                compra.setCANCOM(rs.getInt("CANCOM"));
                compra.setTOTCOM(rs.getDouble("TOTCOM"));
                compra.setFECCOM(rs.getDate("FECCOM"));
                listado.add(compra);
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
    
}
