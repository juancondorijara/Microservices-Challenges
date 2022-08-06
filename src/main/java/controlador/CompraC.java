package controlador;

import dao.CompraImpl;
import modelo.Compra;
import modelo.Proveedor;
import modelo.Producto;
import services.ReporteS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "compraC")
//@Dependent
@SessionScoped
public class CompraC implements Serializable {

    private Compra compra;
    private CompraImpl dao;
    private List<Compra> listadoCompra;
    private Proveedor proveedor;
    private Producto producto;
    private int tipo = 1;
    private ExcelOptions xls;
    private PDFOptions pdf;

    public CompraC() {
        compra = new Compra();
        dao = new CompraImpl();
        listadoCompra = new ArrayList<>();
        customizationOptions();
    }
    
    public void registrar() throws Exception {
        try {
            dao.registrar(compra);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }
    
    //REPORTE VISTA PREVIA
    public void reporteCompra() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Com.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteCompraC " + e.getMessage());
        }
    }
    
    public void limpiar() {
        compra = new Compra();
    }
    
    public void listar() {
        try {
            listadoCompra = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
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
