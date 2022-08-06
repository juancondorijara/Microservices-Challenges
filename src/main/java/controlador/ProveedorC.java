package controlador;

import dao.ProveedorImpl;
import modelo.Proveedor;
import modelo.Ubigeo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import services.CamelCaseS;
import services.ReniecS;
import services.ReporteS;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "proveedorC")
//@Dependent
@SessionScoped
public class ProveedorC implements Serializable {

    private Proveedor proveedor;
    private ProveedorImpl dao;
    private List<Proveedor> listadoProveedor;
    private Ubigeo ubigeo;
    private int tipo = 1;
    private ExcelOptions xls;
    private PDFOptions pdf;
    private ReniecS reniecs;
    private Boolean disabled = false;

    public ProveedorC() {
        proveedor = new Proveedor();
        dao = new ProveedorImpl();
        listadoProveedor = new ArrayList<>();
        reniecs = new ReniecS();
        customizationOptions();
    }

    public void buscarRUC() throws Exception {
        try {
            reniecs.buscarPorRUC(proveedor);
            if (reniecs.disabled == true) {
                this.disabled = true;
            } else {
                this.disabled = false;
                limpiar();
            }
        } catch (Exception e) {
            System.out.println("Error en buscarPorRUC_C " + e.getMessage());
        }
    }

    public void registrar() throws Exception {
        try {
            if (dao.duplicadoRUC(proveedor, listadoProveedor) == false) {
                proveedor.setNOMPROV(CamelCaseS.camelMayuscula(proveedor.getNOMPROV()));
                proveedor.setDIRPROV(CamelCaseS.camelMayuscula(proveedor.getDIRPROV()));
                proveedor.setCOMPROV(CamelCaseS.camelMayuscula(proveedor.getCOMPROV()));
                proveedor.setEMAPROV(CamelCaseS.camelMinuscula(proveedor.getEMAPROV()));
                dao.registrar(proveedor);
                PrimeFaces.current().ajax().update("form:dlgDatos");
                limpiar();
                listar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El RUC ya Existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Registrar"));
            System.out.println("Error en RegistrarC " + e.getMessage());
        }
        proveedor = new Proveedor();
        listar();
    }

    public void modificar() throws Exception {
        try {
            proveedor.setNOMPROV(CamelCaseS.camelMayuscula(proveedor.getNOMPROV()));
            proveedor.setDIRPROV(CamelCaseS.camelMayuscula(proveedor.getDIRPROV()));
            proveedor.setCOMPROV(CamelCaseS.camelMayuscula(proveedor.getCOMPROV()));
            proveedor.setEMAPROV(CamelCaseS.camelMinuscula(proveedor.getEMAPROV()));
            dao.modificar(proveedor);
            PrimeFaces.current().ajax().update("form3:dlgDatos");
            limpiar();
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Modificado con Éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Modificar"));
            System.out.println("Error en ModificarC " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(proveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "CORRECTO", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(proveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Restaurado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en restaurarEstadoC " + e.getMessage());
        }
    }

    public void eliminarDelete() throws Exception {
        try {
            dao.eliminarDelete(proveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "CORRECTO", "Eliminado Completo"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }

    //REPORTE VISTA PREVIA
    public void reporteProveedor() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Proveedor.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteProveedor_C " + e.getMessage());
        }
    }

    public void limpiar() {
        proveedor = new Proveedor();
    }

    public void listar() {
        try {
            listadoProveedor = dao.listarTodos(tipo);
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
