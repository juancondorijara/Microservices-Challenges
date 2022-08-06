package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.Familia;
import modelo.Producto;
import modelo.Venta;
import modelo.VentaDetalle;
import modelo.DetalleVen;

public class VentaDetalleImpl extends Conexion {

    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    public VentaDetalle agregarFila(int idpro) throws SQLException, Exception {
        VentaDetalle ventadetalle = null;
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = " + idpro;
//        String sql = "SELECT IDPRO, NOMPRO, MARPRO, DESPRO, PRESPRO, STOPRO, PREPRO FROM PRODUCTO "
//                + "WHERE IDPRO = " + idpro;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ventadetalle = new VentaDetalle();
                Producto producto = new Producto();
                ventadetalle.setIDPRO(rs.getInt("IDPRO"));
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setMARPRO(rs.getString("MARPRO"));
                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setPRESPRO(rs.getString("PRESPRO"));
                producto.setSTOPRO(rs.getInt("STOPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                ventadetalle.setProducto(producto);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en agregarFila_D " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
            return ventadetalle;
        }
    }

    public void registroMultiple(List<VentaDetalle> listaVentaDetalle, int idVenta) throws Exception {
        String sql = "INSERT INTO VENTA_DETALLE (CANVENDET,PREVENDET,IDPRO,IDVEN,ESTVENDET) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            for (VentaDetalle ventadetalle : listaVentaDetalle) {
                ps.setInt(1, ventadetalle.getCANVENDET());
                ps.setDouble(2, ventadetalle.getPREVENDET());
                ps.setInt(3, ventadetalle.getIDPRO());
                ps.setInt(4, idVenta);
                ps.setString(5, "A");
                ps.executeUpdate();
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registroMultipleDAO " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    public void registrar(Venta venta) throws Exception {
        UsuarioD dao = new UsuarioD();
        String sql = "INSERT INTO VENTA (FECVEN,IDCLI,IDEMP,TOTVEN,ESTVEN) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, formato.format(venta.getFECVEN()));
            ps.setInt(2, venta.getCliente().getIDCLI());
            ps.setInt(3, dao.IDEMP);
//            ps.setInt(3, venta.getEmpleado().getIDEMP());
            ps.setDouble(4, venta.getTOTVEN());
            ps.setString(5, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registrar_D VENTA " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    public int obtenerUltimoId() {
        try {
            PreparedStatement ps1 = this.conectar().prepareStatement("SELECT MAX(v.idven) as IDVEN FROM VENTA v");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDVEN");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en obtenerUltimoIdDAO" + e.getMessage());
        }
        return -1;
    }

    //LISTA DE VENTAS
    public List<Venta> listar() throws Exception {
        List<Venta> listado = new ArrayList<>();
        Cliente cliente;
        Venta venta;
        String sql = "SELECT\n"
                + "    VENTA.IDVEN,\n"
                + "    VENTA.FECVEN,\n"
                + "    VENTA.IDCLI,\n"
                + "    (UPPER(CLIENTE.APECLI) || ', ' || CLIENTE.NOMCLI) AS CLIENTE,\n"
                + "    CLIENTE.DNICLI,\n"
                + "    VENTA.TOTVEN\n"
                + "FROM VENTA\n"
                + "    INNER JOIN CLIENTE\n"
                + "    ON VENTA.IDCLI = CLIENTE.IDCLI\n"
                + "ORDER BY VENTA.FECVEN DESC";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                venta = new Venta();
                venta.setIDVEN(rs.getInt("IDVEN"));
                venta.setFECVEN(rs.getDate("FECVEN"));
                cliente = new Cliente();
                cliente.setIDCLI(rs.getInt("IDCLI"));
                cliente.setNOMCLI(rs.getString("CLIENTE"));
                cliente.setDNICLI(rs.getString("DNICLI"));
                venta.setCliente(cliente);
                listado.add(venta);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en el listadoDaoVenta " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    //LISTA DEL DETALLE DE LA VENTA
    public List listarDetalle(int id) throws Exception {
        List<DetalleVen> listado = null;
        DetalleVen detalle;
        String sql = "SELECT * FROM vVentaDetalle WHERE IDVEN = '" + id + "'";
        try {
            listado = new ArrayList();
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                detalle = new DetalleVen();
                detalle.setIdVen(rs.getInt("IDVEN"));
                detalle.setIdCli(rs.getInt("IDCLI"));
                detalle.setIdPro(rs.getInt("IDPRO"));
                detalle.setFecVen(rs.getDate("FECVEN"));
                detalle.setCliente(rs.getString("CLIENTE"));
                detalle.setDniCli(rs.getString("DNICLI"));
                detalle.setNomPro(rs.getString("NOMPRO"));
                detalle.setPrePro(rs.getDouble("PREPRO"));
                detalle.setCentVentDet(rs.getInt("SUBTOTAL"));
                detalle.setTotal(rs.getDouble("TOTVEN"));
                listado.add(detalle);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en listarPorId: " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }
    
//    public void enviarContraseñaCorreo() {
//        String pss = usuario.getNOMUSU();
//        pss = MD5.encriptar(pss);
//        System.out.println(pss);
//    }

    //FALLA
    public List listarDetalleee(int IDVEN) throws Exception {  //public List<VentaDetalle> listarDetalle(int idVen) throws Exception {
        List<VentaDetalle> listado = null;
        VentaDetalle ventaDetalle;
        Producto producto;
        Venta venta;
        String sql = "SELECT * FROM vVentaDetalle WHERE IDVEN =? ";
//        String sql = "SELECT * FROM vVentaDetalle WHERE IDVEN = '" + 8 + "'";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, IDVEN);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                venta = new Venta();
                venta.setIDVEN(rs.getInt("IDVEN"));
                venta.setTOTVEN(rs.getDouble("TOTVEN"));
                ventaDetalle = new VentaDetalle();
                ventaDetalle.setCANVENDET(rs.getInt("CANVENDET"));
                ventaDetalle.setPREVENDET(rs.getDouble("SUBTOTAL"));
                producto = new Producto();
                producto.setNOMPRO(rs.getString("NOMPRO"));
//                producto.setMARPRO(rs.getString("MARPRO"));
//                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
//                producto.setVENPRO(rs.getDate("VENPRO"));
                ventaDetalle.setProducto(producto);
                ventaDetalle.setVenta(venta);
                listado.add(ventaDetalle);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en el listadoDaoDetalle " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    public List listaProducto() throws Exception {
        List<Producto> listado = null;
        Producto producto;
        String sql = "SELECT * FROM vProductoA";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                producto = new Producto();
                producto.setIDPRO(rs.getInt("IDPRO"));
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setMARPRO(rs.getString("MARPRO"));
                producto.setCODFAM(rs.getString("SUBFAM"));
                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                producto.setSTOPRO(rs.getInt("STOPRO"));
                producto.setVENPRO(rs.getDate("VENPRO"));
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

    public VentaDetalle obtenerDatos(int idpro) throws SQLException, Exception {
        VentaDetalle ventadetalle = null;
        String sql = "SELECT P.NOMPRO, P.MARPRO, F.NOMFAM, P.PREPRO, P.IDPRO, F.CODFAM \n"
                + "FROM PRODUCTO P\n"
                + "INNER JOIN FAMILIA F ON\n"
                + "P.CODFAM = F.CODFAM\n"
                + "WHERE P.IDPRO = " + idpro;
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ventadetalle = new VentaDetalle();
                ventadetalle.setIDPRO(rs.getInt("IDPRO"));
                Producto producto = new Producto();
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setMARPRO(rs.getString("MARPRO"));
                producto.setCODFAM(rs.getString("CODFAM"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                Familia familia = new Familia();
                familia.setNOMFAM(rs.getString("NOMFAM"));
                producto.setFamilia(familia);
                ventadetalle.setProducto(producto);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en obtenerDatos " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
        return ventadetalle;
    }

    public boolean duplicadoProducto(VentaDetalle ventadetalle, List<VentaDetalle> ventaDet) {
        for (VentaDetalle ven : ventaDet) {
            if (ventadetalle.getNOMPRO().equals(ven.getNOMPRO())) {
                return true;
            }
        }
        return false;
    }

}
