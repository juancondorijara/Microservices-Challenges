package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import modelo.Cliente;
import modelo.Empleado;

public class UsuarioD extends Conexion {

    public static Boolean logueo = false;
    public static int nivel = 0;
    public static int IDEMP = 0;
    public static String rol = "";

    public Usuario login(Usuario usuario, String PWDUSU) throws Exception {
        Usuario user = new Usuario();
        String sql = "select USUUSU, PWDUSU, NIVUSU from USUARIO where USUUSU=? and PWDUSU=? ";
        String sqll = "select * from vNombreCliente where USUUSU=? and PWDUSU=? ";
        String sqlll = "select * from vNombreEmpleado where USUUSU=? and PWDUSU=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            PreparedStatement pss = this.conectar().prepareStatement(sqll);
            PreparedStatement psss = this.conectar().prepareStatement(sqlll);
            ps.setString(1, usuario.getUSUUSU());
            ps.setString(2, PWDUSU);
            pss.setString(1, usuario.getUSUUSU());
            pss.setString(2, PWDUSU);
            psss.setString(1, usuario.getUSUUSU());
            psss.setString(2, PWDUSU);
            ResultSet rs = ps.executeQuery();
            ResultSet rss = pss.executeQuery();
            ResultSet rsss = psss.executeQuery();
            if (rs.next()) {
                logueo = true;
                nivel = rs.getInt("NIVUSU");
                user.setUSUUSU(rs.getString("USUUSU"));
                user.setPWDUSU(rs.getString("PWDUSU"));
                user.setNIVUSU(rs.getInt("NIVUSU"));
                if (rss.next()) {
                    user.setIDCLI(rss.getInt("IDCLI"));
                    user.setNOMCLI(rss.getString("NOMCLI"));
                    user.setAPECLI(rss.getString("APECLI"));
                    user.setEMACLI(rss.getString("EMACLI"));
                }
                if (rsss.next()) {
                    IDEMP = rsss.getInt("IDEMP");
                    rol = rsss.getString("ROLEMP");
                    user.setIDEMP(rsss.getInt("IDEMP"));
                    user.setNOMEMP(rsss.getString("NOMEMP"));
                    user.setAPEEMP(rsss.getString("APEEMP"));
                    user.setEMAEMP(rsss.getString("EMAEMP"));
                }
            } else {
                logueo = false;
            }
            ps.close();
            pss.close();
            psss.close();
            rs.close();
            rss.close();
            rsss.close();
            return user;
        } catch (Exception e) {
            System.out.println("Errorr en login_D " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public int loginNivel(Usuario usuario) throws Exception {
        String sql = "select USUUSU, PWDUSU, NIVUSU from USUARIO where USUUSU=? and PWDUSU=? ";
        try {
            PreparedStatement ps = (PreparedStatement) this.conectar().prepareStatement(sql);
            ps.setString(1, usuario.getUSUUSU());
            ps.setString(2, usuario.getPWDUSU());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nivel = rs.getInt("NIVUSU");
                logueo = true;
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return nivel;
        } catch (Exception e) {
            System.out.println("Errorr en loginNivel_D " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String loginRol(Usuario usuario) throws Exception {
        String sql = "SELECT USUUSU, PWDUSU, ROLEMP FROM vRoLUsuario WHERE USUUSU=? AND PWDUSU=? ";
        try {
            PreparedStatement ps = (PreparedStatement) this.conectar().prepareStatement(sql);
            ps.setString(1, usuario.getUSUUSU());
            ps.setString(2, usuario.getPWDUSU());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rol = rs.getString("ROLEMP");
                logueo = true;
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return rol;
        } catch (Exception e) {
            System.out.println("Errorr en loginRol_D " + e.getMessage());
            Logger.getGlobal().log(Level.WARNING, "Errorr en loginRol_D {0} ", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void registrarCli(Cliente cliente, int idCli) throws Exception {
        String sql = "insert into USUARIO (USUUSU, PWDUSU, NIVUSU, IDCLI, ESTUSU) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cliente.getDNICLI());
            ps.setString(2, cliente.getPWDCLI());
            ps.setInt(3, 2);
            ps.setInt(4, idCli);
            ps.setString(5, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    public void registrarEmp(Empleado empleado, int idEmp) throws Exception {
        String sql = "insert into USUARIO (USUUSU, PWDUSU, NIVUSU, IDEMP, ESTUSU) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, empleado.getDNIEMP());
            ps.setString(2, empleado.getPWDEMP());
            ps.setInt(3, 1);
            ps.setInt(4, idEmp);
            ps.setString(5, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    //MODIFICAR ROL DEL USUARIO
    public void modificarRolUser(String rol, int id) throws Exception {
        try {
            String sql = "";
            switch (rol) {
                case "A":
                    sql = "update EMPLEADO set ROLEMP='T' where IDEMP=? ";
                    break;
                case "T":
                    sql = "update EMPLEADO set ROLEMP='A' where IDEMP=? ";
                    break;
            }
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en modificarRolUser_D " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int ultimoIdCli() throws Exception {
        int idCli = 0;
        try {
            PreparedStatement ps = this.conectar().prepareStatement("SELECT MAX(C.IDCLI) AS IDCLI FROM CLIENTE C");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idCli = rs.getInt("IDCLI");
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en ultimoIdCli_D " + e.getMessage());
            e.printStackTrace();
        }
        return idCli;
    }

    public int ultimoIdEmp() throws Exception {
        int idEmp = 0;
        try {
            PreparedStatement ps = this.conectar().prepareStatement("SELECT MAX(E.IDEMP) AS IDEMP FROM EMPLEADO E");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idEmp = rs.getInt("IDEMP");
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en ultimoIdEmp_D " + e.getMessage());
            e.printStackTrace();
        }
        return idEmp;
    }

}
