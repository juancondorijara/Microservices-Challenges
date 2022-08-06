package controlador;

import com.google.gson.JsonSyntaxException;
import dao.EmpleadoImpl;
import dao.UsuarioD;
import modelo.Empleado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import modelo.Usuario;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import services.EncriptarS;
import services.CamelCaseS;
import services.EmailS;
import services.ReniecS;
import services.ReporteS;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "empleadoC")
//@Dependent
@SessionScoped
public class EmpleadoC implements Serializable {

    private Empleado empleado;
    private EmpleadoImpl dao;
    private List<Empleado> listadoEmpleado;
    private Usuario usuario;
    private UsuarioD daoUser;
    private ExcelOptions xls;
    private PDFOptions pdf;
    private int tipo = 1;
    private ReniecS reniecs;
    private Boolean disabled = false;

    public EmpleadoC() {
        empleado = new Empleado();
        dao = new EmpleadoImpl();
        listadoEmpleado = new ArrayList<>();
        usuario = new Usuario();
        daoUser = new UsuarioD();
        reniecs = new ReniecS();
        customizationOptions();
    }
    
    public void buscarPorDNI() throws Exception {
        try {
            reniecs.buscarPorDNI(empleado);
            if (reniecs.disabled == true) {
                this.disabled = true;
            } else {
                this.disabled = false;
                limpiar();
            }
        } catch (Exception e) {
            System.out.println("Error en buscarPorDNI_C " + e.getMessage());
        }
    }

    public void registrar() throws Exception {
        try {
            if (dao.duplicadoDNI(empleado, listadoEmpleado) == false) {
                empleado.setNOMEMP(CamelCaseS.camelMayuscula(empleado.getNOMEMP()));
                empleado.setAPEEMP(CamelCaseS.camelMayuscula(empleado.getAPEEMP()));
                empleado.setEMAEMP(CamelCaseS.camelMinuscula(empleado.getEMAEMP()));
                dao.registrar(empleado);
                EmailS.enviarPwdEmp(empleado);
                int idEmp = daoUser.ultimoIdEmp();
                empleado.setPWDEMP(EncriptarS.encriptar(empleado.getPWDEMP()));
                daoUser.registrarEmp(empleado, idEmp);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
                PrimeFaces.current().ajax().update("form:dlgDatos");
                limpiar();
                listar();
            } else if (dao.duplicadoDNI(empleado, listadoEmpleado) == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El DNI ya Existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Registrar"));
            System.out.println("Error en registrar_C " + e.getMessage());
        }
        empleado = new Empleado();
        listar();
    }
    
    public void modificar() throws Exception {
        try {
            empleado.setNOMEMP(CamelCaseS.camelMayuscula(empleado.getNOMEMP()));
            empleado.setAPEEMP(CamelCaseS.camelMayuscula(empleado.getAPEEMP()));
            empleado.setEMAEMP(CamelCaseS.camelMinuscula(empleado.getEMAEMP()));
            dao.modificar(empleado);
            PrimeFaces.current().ajax().update("form3:dlgDatos");
        } catch (Exception e) {
            System.out.println("Error en modificar_C " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(empleado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "CORRECTO", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminar_C " + e.getMessage());
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(empleado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Restaurado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en restaurar_C " + e.getMessage());
        }
    }
    
    //REPORTE VISTA PREVIA
    public void reporteEmpleado() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Trabajador.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteEmpleado_C " + e.getMessage());
        }
    }
    
    //REPORTE VISTA PREVIA
    public void ventaTrabajador() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/VxTrabajador.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en ventaTrabajador_C " + e.getMessage());
        }
    }
    
    public void limpiar() {
        try {
            empleado = new Empleado();
        } catch (Exception e) {
        }
    }
    
    public void listar() {
        try {
            listadoEmpleado = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en Listar_C " + e.getMessage());
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
