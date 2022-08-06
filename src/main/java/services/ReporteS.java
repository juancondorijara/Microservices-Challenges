package services;

import dao.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

public class ReporteS extends Conexion {
    
    //REPORTE VISTA PREVIA
    public void ReportePdf(String root) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        try {
            File reportfile = new File(root);
            Map<String, Object> parameter = new HashMap<String, Object>();
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), parameter, this.conectar());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setContentLength(bytes.length);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error en ReportePdf_S " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //REPORTE DESCARGA
    public void exportarPDFGlobal(Map parameters, String url, String nomPDF) throws JRException, IOException, Exception {
        this.conectar();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/" + url + ""));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=" + nomPDF + "");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | JRException e) {
            System.out.println("Error en exportarPDFGlobal_S " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //REPORTE VISTA PREVIA CON PARAMETRO
    public void ticketVenta(String root, String numero) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        try {
            File reportfile = new File(root);
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("param", numero);
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), parameter, Conexion.conectar());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setContentLength(bytes.length);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error en ticketVenta_S " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void rangoFecha(String root, String fecha1, String fecha2) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        try {
            File reportfile = new File(root);
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("Parameter1", new String(fecha1));
            parameter.put("Parameter2", new String(fecha2));
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), parameter, Conexion.conectar());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setContentLength(bytes.length);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
