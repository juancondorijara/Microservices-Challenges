package services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import javax.swing.JOptionPane;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Proveedor;

public class ReniecS {
    
    public static Boolean disabled = true;
    
    public static void main(String[] args) throws Exception {
        Empleado cliente = new Empleado();
//        cliente.setDNICLI("70335061");
//        buscarDNI(cliente);
//        cliente.setDNIEMP("73020978");
//        buscarPorDNI(cliente);
//        cliente.setDNICLI("20491265737");
//        buscarPorRUC(cliente);
//        15428960  |  62734368 FALLA
//        20491265737 https://dniruc.apisperu.com/api/v1/ruc/20491265737
    }

    public static void buscarPorDNI(Cliente cliente) throws Exception {
        String leerdni = cliente.getDNICLI();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/" + leerdni + token;  // + token
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                disabled = true;
                JsonObject rootobj = root.getAsJsonObject();
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
                cliente.setNOMCLI(CamelCaseS.camelMayuscula(nombres));
                cliente.setAPECLI(CamelCaseS.camelMayuscula(apellido_paterno + " " + apellido_materno));
                cliente.setDIRCLI("");
                cliente.setEMACLI("");
                cliente.setCELCLI("");
                cliente.setCODUBI("Seleccione");
                System.out.println("RESULTADO:\n");
                System.out.println(nombres + "\n" + apellido_paterno + "\n" + apellido_materno + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BUSQUEDA", "DNI Exitoso"));
            } else {
                disabled = false;
                cliente.setNOMCLI("");
                cliente.setAPECLI("");
                cliente.setDIRCLI("");
                cliente.setEMACLI("");
                cliente.setCELCLI("");
                cliente.setCODUBI("Seleccione");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            }
        } catch (NullPointerException e) {
            disabled = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            System.out.println("Error en buscarPorDNI_S " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            disabled = false;
        }
    }
    
    public static void buscarPorDNI(Empleado empleado) throws Exception {
        String leerdni = empleado.getDNIEMP();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/" + leerdni + token;  // + token
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                disabled = true;
                JsonObject rootobj = root.getAsJsonObject();
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
                empleado.setNOMEMP(CamelCaseS.camelMayuscula(nombres));
                empleado.setAPEEMP(CamelCaseS.camelMayuscula(apellido_paterno + " " + apellido_materno));
                empleado.setEMAEMP("");
                empleado.setCELEMP("");
                empleado.setROLEMP("Seleccione");
                System.out.println("RESULTADO:\n");
                System.out.println(nombres + "\n" + apellido_paterno + "\n" + apellido_materno + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BUSQUEDA", "DNI Exitoso"));
            } else {
                disabled = false;
                empleado.setNOMEMP("");
                empleado.setAPEEMP("");
                empleado.setEMAEMP("");
                empleado.setCELEMP("");
                empleado.setROLEMP("Seleccione");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            }
        } catch (NullPointerException e) {
            disabled = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            System.out.println("Error en buscarPorDNI_S " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            disabled = false;
        }
    }
    
    public static void buscarPorRUC(Proveedor proveedor) throws Exception {
        String leerruc = proveedor.getRUCPROV();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/ruc/" + leerruc + token;
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                disabled = true;
                JsonObject rootobj = root.getAsJsonObject();
                String razon = rootobj.get("razonSocial").getAsString();
                String direccion = rootobj.get("direccion").getAsString();
                String ubigeo = rootobj.get("ubigeo").getAsString();
                proveedor.setNOMPROV(CamelCaseS.camelMayuscula(razon));
                proveedor.setDIRPROV(CamelCaseS.camelMayuscula(direccion));
                proveedor.setCODUBI(ubigeo);
                proveedor.setCOMPROV("");
                proveedor.setEMAPROV("");
                proveedor.setCELPROV("");
                System.out.println("RESULTADO:\n");
                System.out.println(razon + "\n" + direccion + "\n" + ubigeo + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BUSQUEDA", "RUC Exitoso"));
            } else {
                disabled = false;
                proveedor.setNOMPROV("");
                proveedor.setCOMPROV("");
                proveedor.setDIRPROV("");
                proveedor.setEMAPROV("");
                proveedor.setCELPROV("");
                proveedor.setCODUBI("Seleccione");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "RUC no Encontrado o el servidor falló"));
            }
        } catch (NullPointerException e) {
            disabled = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "RUC no Encontrado o el servidor falló"));
            System.out.println("Error en buscarRUC_S " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            disabled = false;
        }
    }
    
    public static void buscarDniReniec(Cliente cliente) throws Exception {
        String dni = cliente.getDNICLI();
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url("https://apiperu.dev/api/dni/" + dni)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer 6c345b3dcf9970c97f1f22c585d9eab356dbdf3219923af2d09e6a14308606a0")
//                .addHeader("Authorization", "Bearer e73333617bf9d09b54f5528fc96ea728cdf777b477be38cae45d113c9ce1f477")  //MIO
                .build();
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            System.out.println(json);
            JsonElement root = JsonParser.parseString​(json).getAsJsonObject();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            if (root.isJsonObject()) {
                disabled = true;
                JsonObject rootobj = root.getAsJsonObject().getAsJsonObject("data");
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellido_paterno").getAsString();
                String apellido_materno = rootobj.get("apellido_materno").getAsString();
                cliente.setNOMCLI(nombres);
                cliente.setAPECLI(apellido_paterno + " " + apellido_materno);
                cliente.setDIRCLI("");
                cliente.setEMACLI("");
                cliente.setCELCLI("");
                cliente.setCODUBI("Seleccione");
                System.out.println("RESULTADO:\n");
                System.out.println(nombres + "\n" + apellido_paterno + "\n" + apellido_materno + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BUSQUEDA", "DNI Exitoso"));
            } else {
                disabled = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            }
        }
        catch (NullPointerException e) {
            disabled = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no Encontrado o el servidor falló"));
            System.out.println("Error en buscarDNI_S " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            disabled = false;
        }
    }
    
}
