package dao;

import java.util.List;
import modelo.Familia;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FamiliaD extends Conexion {
    
    public List listarTodos() throws Exception {
        List<Familia> listado = null;
        Familia familia;
        String sql = "select * from FAMILIA";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                familia = new Familia();
                familia.setCODFAM(rs.getString("CODFAM"));
                familia.setNOMFAM(rs.getString("NOMFAM"));
                familia.setSUBFAM(rs.getString("SUBFAM"));
                listado.add(familia);
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
