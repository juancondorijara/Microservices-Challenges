package controlador;

import dao.ProductoImpl;
import dao.VentaDetalleImpl;
import modelo.Producto;
import modelo.Venta;
import modelo.VentaDetalle;
import modelo.TempVta;
import services.ReporteS;
import java.io.Serializable;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import java.util.Date;
import java.util.Calendar;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "ventadetalleC")
//@Dependent
@SessionScoped
public class VentaDetalleC implements Serializable {

    private Date INICIO;
    private Date FIN;
    private int IDVEN;

    double precio = 0.0, monto = 0.0;
    Integer stockPro = 0, cantPed = 1;
//    String nombre="", marca="", familia="", descripcion="", proveedor="", cadenaPro = "", cadenaCli = "";
    String cadenaPro = "", cadenaCli = "";
    ProductoImpl daoPro;
    List<TempVta> productos;
    List<TempVta> selectedProduct;
    TempVta tempVta;

    private Producto producto;
    private Venta venta;
    private VentaDetalle ventadetalle;

    private VentaDetalleImpl dao;
    private List<Venta> listadoVentaRealizada;
    private List<VentaDetalle> listadoDetalle;
    private List<VentaDetalle> listadoVentaDetalle;
    private List<VentaDetalle> listadoVentaDetalleFinal;
    private List<Producto> listadoProducto;

    public VentaDetalleC() {
        producto = new Producto();
        venta = new Venta();
        ventadetalle = new VentaDetalle();
        dao = new VentaDetalleImpl();
        listadoVentaRealizada = new ArrayList<>();
        listadoDetalle = new ArrayList<>();
        listadoVentaDetalle = new ArrayList<>();
        listadoVentaDetalleFinal = new ArrayList<>();
        listadoProducto = new ArrayList<>();
        venta.setFECVEN(GregorianCalendar.getInstance().getTime());
        productos = new ArrayList();
    }
    
    //LISTA DEL DETALLE DE LA VENTA
    public void listarDetalle(Venta venta) {
        try {
            IDVEN = venta.getIDVEN();
            listadoDetalle = dao.listarDetalleee(IDVEN);
            System.out.println("ID ES: " + IDVEN);
        } catch (Exception e) {
            System.out.println("Error en ListarDetalle_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<TempVta> agregarTmp() throws Exception {
        try {
            boolean repetido = false;
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getIdpro().equals(daoPro.obtenerIdProducto(cadenaPro))) {
                    repetido = true;
                    cadenaPro = "";
                    break;
                }
            }
            if (repetido == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "REPETIDO", "El producto ya esta en la compra"));
            } else {
                tempVta = new TempVta();
                tempVta.setIdpro(daoPro.obtenerIdProducto(cadenaPro));
                tempVta.setNombre(daoPro.nombre);
                tempVta.setMarca(daoPro.marca);
                tempVta.setFamilia(daoPro.familia);
                tempVta.setDescripcion(daoPro.descripcion);
                tempVta.setPrecio(daoPro.precio);
                tempVta.setStockPro(daoPro.stockPro);
                tempVta.setCantidad(cantPed);
                tempVta.setSubTotal((double) (tempVta.getPrecio() * tempVta.getCantidad()));
                this.productos.add(tempVta);
                monto += tempVta.getSubTotal();
                limpiarCampos();
            }
        } catch (Exception e) {
            System.out.println("Error en agregarFilaDAO " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    public void limpiarListaTemp() throws Exception {
        productos.clear();
        monto = 0.0;
    }

    public void limpiarCampos() throws Exception {
        cadenaPro = "";
        cantPed = 1;
    }

    public void anularTmp() throws Exception {
        limpiarCampos();
        productos.clear();
    }

    public void eliminarFilaTmp(TempVta tempVta) throws Exception {
        try {
            productos.remove(tempVta);
            sumarMontoTmp();
        } catch (Exception e) {
            System.out.println("Error en VentasC/eliminarFilaTmp " + e.getMessage());
        }
    }

    private void sumarMontoTmp() {
        for (TempVta tempVta : productos) {
            monto += tempVta.getSubTotal();
        }
    }

    public void agregarFila() {
        try {
//            if (dao.duplicadoProducto(ventadetalle, listadoVentaDetalle) == false) {
            VentaDetalle ventadet = dao.agregarFila(ventadetalle.getProducto().getIDPRO());
            ventadet.setIDPRO(this.ventadetalle.getProducto().getIDPRO());
            ventadet.setCANVENDET(this.ventadetalle.getCANVENDET());
            ventadet.setPREPRO(ventadet.getProducto().getPREPRO());  //extra
            ventadet.setPREVENDET(ventadet.getProducto().getPREPRO() * this.ventadetalle.getCANVENDET());
            ventadet.setNOMPRO(ventadet.getProducto().getNOMPRO());
            ventadet.setMARPRO(ventadet.getProducto().getMARPRO());
            ventadet.setDESPRO(ventadet.getProducto().getDESPRO());
            ventadet.setPRESPRO(ventadet.getProducto().getPRESPRO());
            ventadet.setSTOPRO(ventadet.getProducto().getSTOPRO());
            this.listadoVentaDetalle.add(ventadet);
            ventadetalle = new VentaDetalle();
            for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
                System.out.println(ventaDetalle);
            }
            sumar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Producto Agregado"));
//            } else {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El Producto ya está en la compra"));
//            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El Producto ya está en la compra"));
            System.out.println("Error en agregarFilaDAO " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarFila(VentaDetalle ventadetalle) {
        try {
            listadoVentaDetalle.remove(ventadetalle);
            sumar();
        } catch (Exception e) {
            System.out.println("Error en eliminarFilaDAO " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registrarVenta() {
        try {
            dao.registrar(venta);
            int idven = dao.obtenerUltimoId();
            dao.registroMultiple(listadoVentaDetalle, idven);
            listadoVentaDetalle.clear();
            listar();
            venta = new Venta();
        } catch (Exception e) {
            System.out.println("Error en registrarVentaC " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void limpiar() {
        this.ventadetalle = new VentaDetalle();
        this.venta = new Venta();
    }

    public void anular() throws Exception {
        limpiar();
        listadoVentaDetalle.clear();
    }

    //LISTA DE VENTAS
    public void listar() {
        try {
            listadoVentaRealizada = dao.listar();
        } catch (Exception e) {
            System.out.println("Error en Listar_C " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void listarProducto() {
        try {
            listadoProducto = dao.listaProducto();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
            e.printStackTrace();
        }
    }

    //REPORTE VISTA PREVIA CON PARAMETRO ID VENTA
    public void ticketVenta(Venta venta) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("/reports/TicketVenta.jasper");
            String id = String.valueOf(venta.getIDVEN());
            reporte.ticketVenta(root, id);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            System.out.println("Error en ticketVentaC " + e.getMessage());
        }
    }

    //REPORTE VISTA PREVIA
    public void ventaPorMes() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/RVxMes.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteClienteC " + e.getMessage());
        }
    }

    //REPORTE VISTA PREVIA CON 2 PARAMETROS DE FECHA
    public void rangoFecha() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        try {
            if (INICIO == null || FIN == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Falta rellenar una fecha en el reporte"));
            }
            if (INICIO != null && FIN != null) {
                if (INICIO.after(FIN)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fecha de inicio es mayor a la salida en el reporte"));
                } else {
                    ReporteS reporte = new ReporteS();
                    FacesContext facescontext = FacesContext.getCurrentInstance();
                    ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
                    String root = servletcontext.getRealPath("/reports/VxFecha.jasper");
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
                    String fecha1 = formato.format(INICIO);
                    String fecha2 = formato.format(FIN);
                    System.out.println("Fecha Inicio: " + formato.format(INICIO) + "   Fecha Final: " + formato.format(FIN));
                    reporte.rangoFecha(root, fecha1, fecha2);
                    FacesContext.getCurrentInstance().responseComplete();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en rangoFecha_C " + e.getMessage());
        }
    }

    @PostConstruct
    public void start() {
        listar();
        daoPro = new ProductoImpl();
    }

    public void addMessage() {
        String summary = venta.getTOGGLESWICHT() ? "Activado" : "Desactivado";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void sumar() {
        double precioTotal = 0.0;
        for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
            precioTotal += ventaDetalle.getPREVENDET();
        }
        System.out.println(precioTotal);
        venta.setTOTVEN(precioTotal);
    }

    public void agregar() {
        try {
            VentaDetalle vd = new VentaDetalle();
            vd = dao.obtenerDatos(ventadetalle.getProducto().getIDPRO());
            listadoVentaDetalle.add(vd);
        } catch (Exception e) {
            System.out.println("Error en agregarFilaDAO " + e.getMessage());
            e.printStackTrace();
        }
    }

}
