package dao;

import java.sql.PreparedStatement;
import java.util.List;
import modelo.Empleado;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EmpleadoImpl extends Conexion implements ICRUD<Empleado> {
    
    @Override
    public void registrar(Empleado emp) throws Exception {
        String sql = "INSERT INTO EMPLEADO (NOMEMP,APEEMP,EMAEMP,DNIEMP,CELEMP,ROLEMP,ESTEMP) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setString(1, emp.getNOMEMP());
            ps.setString(2, emp.getAPEEMP());
            ps.setString(3, emp.getEMAEMP());
            ps.setString(4, emp.getDNIEMP());
            ps.setString(5, emp.getCELEMP());
            ps.setString(6, emp.getROLEMP());
            ps.setString(7, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarEmp: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Empleado emp) throws Exception {
        String sql = "update EMPLEADO set NOMEMP=?, APEEMP=?, EMAEMP=?, DNIEMP=?, CELEMP=?, ROLEMP=? where IDEMP=?  ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, emp.getNOMEMP());
            ps.setString(2, emp.getAPEEMP());
            ps.setString(3, emp.getEMAEMP());
            ps.setString(4, emp.getDNIEMP());
            ps.setString(5, emp.getCELEMP());
            ps.setString(6, emp.getROLEMP());
            ps.setInt(7, emp.getIDEMP());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en modificar Empleado: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void eliminar(Empleado emp) throws Exception {
        String sql = "update EMPLEADO set ESTEMP='I' where IDEMP=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, emp.getIDEMP());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminarEmpleado: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void restaurar(Empleado emp) throws Exception {
        String sql = "UPDATE EMPLEADO SET ESTEMP='A' WHERE IDEMP=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, emp.getIDEMP());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en restaurarEmpleado: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List<Empleado> listarTodos(int tipo) throws Exception {
        List<Empleado> listado = null;
        Empleado empleado;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vEmpleadoT WHERE ESTEMP='A' ";
                break;
            case 2:
                sql = "SELECT * FROM vEmpleadoT WHERE ESTEMP='I' ";
                break;
            case 3:
                sql = "SELECT * FROM vEmpleadoT";
                break;
        }
        try {
            listado = new ArrayList<>();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                empleado = new Empleado();
                empleado.setIDEMP(rs.getInt("IDEMP"));
                empleado.setNOMEMP(rs.getString("NOMEMP"));
                empleado.setAPEEMP(rs.getString("APEEMP"));
                empleado.setEMAEMP(rs.getString("EMAEMP"));
                empleado.setDNIEMP(rs.getString("DNIEMP"));
                empleado.setCELEMP(rs.getString("CELEMP"));
                empleado.setROLEMP(rs.getString("ROLEMP"));
                listado.add(empleado);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en listarEmpleado: " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    public boolean duplicadoDNI(Empleado empleado, List<Empleado> listaEmpleado) {
        for (Empleado emp : listaEmpleado) {
            if (empleado.getDNIEMP().equals(emp.getDNIEMP())) {
                return true;
            }
        }
        return false;
    }

    public void validarEmpleado(Empleado empleado) throws Exception {
        String NOMEMP = empleado.getNOMEMP();
        String APEEMP = empleado.getAPEEMP();
        String EMAEMP = empleado.getEMAEMP();
        String DNIEMP = empleado.getDNIEMP();
        String CELEMP = empleado.getCELEMP();
        String ROLEMP = empleado.getROLEMP();
        String sql = "SELECT NOMEMP, APEEMP, EMAEMP, DNIEMP, CELEMP FROM EMPLEADO "
                + "WHERE NOMEMP='" + NOMEMP + "', APEEMP='" + APEEMP + "', EMAEMP='" + EMAEMP + "', DNIEMP='" + DNIEMP + "', CELEMP='" + CELEMP + "', ROLEMP='" + ROLEMP + "' ";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                empleado.setNOMEMP(NOMEMP);
                empleado.setAPEEMP(APEEMP);
                empleado.setEMAEMP(EMAEMP);
                empleado.setDNIEMP(DNIEMP);
                empleado.setCELEMP(CELEMP);
                empleado.setROLEMP(ROLEMP);
            } else {
                empleado.setNOMEMP("");
                empleado.setAPEEMP("");
                empleado.setEMAEMP("");
                empleado.setDNIEMP("");
                empleado.setCELEMP("");
                empleado.setROLEMP("");
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en validar empleado: " + e.getMessage());
        }

    }
}
