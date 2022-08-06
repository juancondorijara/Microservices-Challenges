package controlador;

import com.google.gson.JsonSyntaxException;
import dao.ClienteImpl;
import dao.UsuarioD;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Ubigeo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import services.EncriptarS;
import services.CamelCaseS;
import services.ReporteS;
import services.ReniecS;
import services.EmailS;
import services.SmsS;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "clienteC")
//@Dependent
@SessionScoped
public class ClienteC implements Serializable {

    private Cliente cliente;
    private ClienteImpl dao;
    private List<Cliente> listadoCliente;
    private Ubigeo ubigeo;
    private int tipo = 1;
    private ExcelOptions xls;
    private PDFOptions pdf;
    private Usuario usuario;
    private UsuarioD daousu;
    private ReniecS reniecs;
    private Boolean disabled = false;

    public ClienteC() {
        cliente = new Cliente();
        dao = new ClienteImpl();
        listadoCliente = new ArrayList<>();
        ubigeo = new Ubigeo();
        usuario = new Usuario();
        daousu = new UsuarioD();
        reniecs = new ReniecS();
        customizationOptions();
    }

    public void buscarPorDNI() throws Exception {
        try {
            reniecs.buscarPorDNI(cliente);
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
            if (dao.duplicadoDNI(cliente, listadoCliente) == false) {
                cliente.setNOMCLI(CamelCaseS.camelMayuscula(cliente.getNOMCLI()));
                cliente.setAPECLI(CamelCaseS.camelMayuscula(cliente.getAPECLI()));
                cliente.setDIRCLI(CamelCaseS.camelMayuscula(cliente.getDIRCLI()));
                cliente.setEMACLI(CamelCaseS.camelMinuscula(cliente.getEMACLI()));
                dao.registrar(cliente);
                EmailS.enviarPwdCli(cliente);
                int idCli = daousu.ultimoIdCli();
                cliente.setPWDCLI(EncriptarS.encriptar(cliente.getPWDCLI()));
                daousu.registrarCli(cliente, idCli);
//                SmsS.enviarSms(cliente);   //Descomentar para demos, hay un limite de saldo mensajes
                PrimeFaces.current().ajax().update("form:dlgDatos");
                limpiar();
                listar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REVISE SU CORREO", "Se Envio su Contraseña"));
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REVISE SU CELULAR", "Se Envio su Contraseña"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El DNI ya Existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Registrar"));
            System.out.println("Error en registrar_C " + e.getMessage());
        }
        cliente = new Cliente();
        listar();
    }

    public void modificar() throws Exception {
        try {
            cliente.setNOMCLI(CamelCaseS.camelMayuscula(cliente.getNOMCLI()));
            cliente.setAPECLI(CamelCaseS.camelMayuscula(cliente.getAPECLI()));
            cliente.setDIRCLI(CamelCaseS.camelMayuscula(cliente.getDIRCLI()));
            cliente.setEMACLI(CamelCaseS.camelMinuscula(cliente.getEMACLI()));
            dao.modificar(cliente);
            PrimeFaces.current().ajax().update("form3:dlgDatos");
            limpiar();
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Modificado con Éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Modificar"));
            System.out.println("Error en modificar_C " + e.getMessage());
        }
        cliente = new Cliente();
        listar();
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "CORRECTO", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminar_C " + e.getMessage());
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Restaurado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en restaurar_C " + e.getMessage());
        }
    }

    //REPORTE VISTA PREVIA
    public void reporteCliente() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/Cli.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteCliente_C " + e.getMessage());
        }
    }

    //REPORTE VISTA PREVIA
    public void ventaCliente() throws Exception {
        try {
            ReporteS reporte = new ReporteS();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reports/VxCliente.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en ventaCliente_C " + e.getMessage());
        }
    }

    public void limpiar() {
        cliente = new Cliente();
    }

    public void listar() {
        try {
            listadoCliente = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en Listar_C " + e.getMessage());
        }
    }

    public void validar() throws Exception, Throwable {
        try {
            String NOMCLI = cliente.getNOMCLI();
            String APECLI = cliente.getAPECLI();
            String DIRCLI = cliente.getDIRCLI();
            String EMACLI = cliente.getEMACLI();
            String DNICLI = cliente.getDNICLI();
            String CELCLI = cliente.getCELCLI();
            dao.validarCliente(cliente);
            if (!"".equals(cliente.getNOMCLI()) || !"".equals(cliente.getAPECLI()) || !"".equals(cliente.getDIRCLI()) || !"".equals(cliente.getDNICLI()) || !"".equals(cliente.getCELCLI())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "AVISO", "El Cliente ya Existe"));
            } else if (cliente.getNOMCLI() == "" || cliente.getAPECLI() == "" || cliente.getDIRCLI() == "" || cliente.getEMACLI() == "" || cliente.getDNICLI() == "" || cliente.getCELCLI() == "") {
                cliente.setNOMCLI(NOMCLI);
                cliente.setAPECLI(APECLI);
                cliente.setDIRCLI(DIRCLI);
                cliente.setEMACLI(EMACLI);
                cliente.setDNICLI(DNICLI);
                cliente.setCELCLI(CELCLI);
                registrar();
            }
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en Validar_C " + e.getMessage());
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
