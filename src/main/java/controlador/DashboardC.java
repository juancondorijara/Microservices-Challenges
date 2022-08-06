package controlador;

import dao.DashboardImpl;
import modelo.Dashboard;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartDataSet;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import services.ReporteS;
import modelo.Usuario;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "dashboardC")
//@Dependent
@SessionScoped
public class DashboardC implements Serializable {

    private Date INICIO;
    private Date FIN;

    private Usuario usuario;
    private List<Usuario> listaUserEmpleado;

    private PieChartModel dashboardCliente;
    private List<Number> listaCliente;

    private DashboardImpl dao;

    private PieChartModel dashboardEmpleado;
    private List<Number> listaEmpleado;

    private BarChartModel barVenta;
    private Dashboard dashboardVenta;
    private List<Dashboard> listaVenta;

    public DashboardC() {
        dao = new DashboardImpl();
        dashboardCliente = new PieChartModel();
        dashboardEmpleado = new PieChartModel();
        listaVenta = new ArrayList<>();
        dashboardVenta = new Dashboard();
    }

    public void listar() {
        try {
            listaUserEmpleado = dao.listarUserEmpleado();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
        }
    }

    //REPORTE VISTA PREVIA CON 2 PARAMETROS DE FECHA
    public void rangoFecha() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        try {
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
        } catch (Exception e) {
            System.out.println("Error en rangoFecha_C " + e.getMessage());
        }
    }

    private void dashboardCliente() throws Exception {
        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = listaCliente;
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("CLIENTES ACTIVOS");
        labels.add("CLIENTES INACTIVOS");
        data.setLabels(labels);
        dashboardCliente.setData(data);
    }

    private void dashboardEmpleado() throws Exception {
        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = listaEmpleado;
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("ADMINISTRADORES");
        labels.add("TRABAJADORES");
        data.setLabels(labels);
        dashboardEmpleado.setData(data);
    }

    public void dashboardVenta() throws Exception {
        listaVenta = dao.dashboardVenta();
        barVenta = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet bardataSet = new BarChartDataSet();
        bardataSet.setLabel("VENTAS REALIZADAS");

        List<String> labels = new ArrayList<>();
        List<Number> valores = new ArrayList<>();

        for (Dashboard barra : listaVenta) {
            labels.add(barra.getMES());
            valores.add(barra.getTOTAL());
        }

        data.setLabels(labels);
        bardataSet.setData(valores);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bardataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 159, 64)");
        bardataSet.setBorderColor(borderColor);
        bardataSet.setBorderWidth(1);

        data.addChartDataSet(bardataSet);
        barVenta.setData(data);
    }
    
    @PostConstruct
    public void construir() {
        try {
            listaCliente = dao.dashboardCliente();
            dashboardCliente();

            listaEmpleado = dao.dashboardEmpleado();
            dashboardEmpleado();

            dashboardVenta();
            listar();
        } catch (Exception ex) {
            Logger.getLogger(DashboardC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
