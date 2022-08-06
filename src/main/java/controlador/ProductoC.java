package controlador;

import dao.ProductoImpl;
import modelo.Producto;
import modelo.Familia;
import modelo.Proveedor;
import services.ReporteS;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.HashMap;
import javax.enterprise.context.SessionScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.CamelCaseS;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "productoC")
//@Dependent
@SessionScoped
public class ProductoC implements Serializable {

    private Producto producto;
    private ProductoImpl dao;
    private List<Producto> listadoProducto;
    private Familia familia;
    private Proveedor proveedor;
    private int tipo = 1;
    private ExcelOptions xls;
    private PDFOptions pdf;

    public ProductoC() {
        producto = new Producto();
        dao = new ProductoImpl();
        listadoProducto = new ArrayList<>();
        familia = new Familia();
        proveedor = new Proveedor();
        customizationOptions();
    }

    public void registrar() throws Exception {
        try {
            if (dao.duplicadoDescripcion(producto, listadoProducto) == false) {
                producto.setNOMPRO(CamelCaseS.camelMayuscula(producto.getNOMPRO()));
                producto.setMARPRO(CamelCaseS.camelMayuscula(producto.getMARPRO()));
                producto.setPRESPRO(CamelCaseS.camelMayuscula(producto.getPRESPRO()));
                producto.setDESPRO(CamelCaseS.camelMayuscula(producto.getDESPRO()));
                dao.registrar(producto);
                PrimeFaces.current().ajax().update("form:dlgDatos");
                limpiar();
                listar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "La Descripción ya existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Registrar"));
            System.out.println("Error en RegistrarC " + e.getMessage());
        }
        producto = new Producto();
        listar();
    }

    public void modificar() throws Exception {
        try {
            producto.setNOMPRO(CamelCaseS.camelMayuscula(producto.getNOMPRO()));
            producto.setMARPRO(CamelCaseS.camelMayuscula(producto.getMARPRO()));
            producto.setPRESPRO(CamelCaseS.camelMayuscula(producto.getPRESPRO()));
            producto.setDESPRO(CamelCaseS.camelMayuscula(producto.getDESPRO()));
            dao.modificar(producto);
            PrimeFaces.current().ajax().update("form3:dlgDatos");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Modificar"));
            System.out.println("Error en modificarC " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(producto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "CORRECTO", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(producto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Restaurado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en restaurarEstadoC " + e.getMessage());
        }
    }
    
    //REPORTE VISTA PREVIA
    public void reporteProducto() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Pro.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteClienteC " + e.getMessage());
        }
    }
    
    //REPORTE VISTA PREVIA
    public void productoMasVendido() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/MProducto.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteClienteC " + e.getMessage());
        }
    }

    public void limpiar() {
        producto = new Producto();
    }

    public void listar() {
        try {
            listadoProducto = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en listarC " + e.getMessage());
        }
    }

    public void customizationOptions() {
        xls = new ExcelOptions();
        xls.setFacetBgColor("#19C7FF");
        xls.setFacetFontSize("10");
        xls.setFacetFontColor("#FFFFFF");
        xls.setFacetFontStyle("BOLD");
        xls.setCellFontColor("#000000");
        xls.setCellFontSize("8");
        xls.setFontName("Verdana");

        pdf = new PDFOptions();
        pdf.setFacetBgColor("#19C7FF");
        pdf.setFacetFontColor("#000000");
        pdf.setFacetFontStyle("BOLD");
        pdf.setCellFontSize("12");
        pdf.setFontName("Courier");
        pdf.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @PostConstruct
    public void construir() {
        listar();
    }

}
