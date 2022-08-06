package dao;

import java.sql.PreparedStatement;
import java.util.List;
import modelo.Comentario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ComentarioImpl extends Conexion {
    
    public void registrar(Comentario comentario) throws Exception {
        String sql = "insert into COMENTARIO (ASUNCOM, MSGCOM, IDCLI) values (?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, comentario.getASUNCOM());
            ps.setString(2, comentario.getMSGCOM());
            ps.setString(3, comentario.getIDCLI());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en Registrar_D " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }
    
    public String obtenerIdCli(String cadenaEMACLI) throws SQLException, Exception {
        String sql = "select IDCLI FROM CLIENTE WHERE EMACLI = ? ";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, cadenaEMACLI);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("IDCLI");
            }
            return rs.getString("IDCLI");
        } catch (Exception e) {
            System.out.println("Error en obtenerIdCli_D " + e.getMessage());
            throw e;
        }
    }
    
    public List listarTodos() throws Exception {
        List<Comentario> listado = null;
        Comentario comentario;
        String sql = "select * from COMENTARIO order by IDCOM desc";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                comentario = new Comentario();
                comentario.setIDCOM(rs.getInt("IDCOM"));
                comentario.setASUNCOM(rs.getString("ASUNCOM"));
                comentario.setMSGCOM(rs.getString("MSGCOM"));
                comentario.setIDCLI(rs.getString("IDCLI"));
                listado.add(comentario);
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
