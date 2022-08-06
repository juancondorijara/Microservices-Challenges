package services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Cliente;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
// https://www.altiria.com/api-envio-sms/

public class SmsS {

    public static void enviarSms(Cliente cliente) throws Exception {
        //Tiempo maximo para conectar con el servidor (5000) y tiempo de la respuesta del servidor (60000)
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(60000).build();

        //Se inicia el objeto HTTP
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(config);
        CloseableHttpClient httpClient = builder.build();
        
        //Destinatario varia en la vista
        String celular = cliente.getCELCLI();

        //Se fija la URL sobre la que enviar la petición POST
        HttpPost post = new HttpPost("http://www.altiria.net/api/http");
        String cuerpo = "BUEN DÍA " + cliente.getNOMCLI()+ " " + cliente.getAPECLI() + "\n"
                    + "\n Su usuario es: " + cliente.getDNICLI() + "\n Su contraseña es: " + cliente.getPWDCLI() + "\n"
                    + "\n Muchas gracias por registrarse en CONFLOMARK";

        //Se crea la lista de parámetros a enviar en la peticion POST
        List<NameValuePair> parametersList = new ArrayList<NameValuePair>();
        parametersList.add(new BasicNameValuePair("cmd", "sendsms"));
        parametersList.add(new BasicNameValuePair("login", "juan.condori.jara@vallegrande.edu.pe"));
        parametersList.add(new BasicNameValuePair("passwd", "rya7gmpy"));
        parametersList.add(new BasicNameValuePair("dest", "51" + celular));
        parametersList.add(new BasicNameValuePair("msg", cuerpo));
        try {
        //Se fija la codificacion de caracteres de la peticion POST
            post.setEntity(new UrlEncodedFormEntity(parametersList, "UTF-8"));
        } catch (UnsupportedEncodingException uex) {
            System.out.println("ERROR: codificacion de caracteres no soportada");
        }
        CloseableHttpResponse response = null;
        try {
            System.out.println("Enviando peticion");
            //Se env�a la petición
            response = httpClient.execute(post);
            //Se consigue la respuesta
            String resp = EntityUtils.toString(response.getEntity());
            //Error en la respuesta del servidor
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("ERROR: Codigo de error HTTP:  " + response.getStatusLine().getStatusCode());
                System.out.println("Compruebe que ha configurado correctamente la direccion/url ");
                System.out.println("suministrada por Altiria");
                return;
            } else {
                //Se procesa la respuesta capturada en la cadena 'response'
                if (resp.startsWith("ERROR")) {
                    System.out.println(resp);
                    System.out.println("Codigo de error de Altiria. Compruebe las especificaciones");
                } else {
                    System.out.println(resp);
                }
            }
        } catch (Exception e) {
            System.out.println("Excepcion");
            e.printStackTrace();
            return;
        } finally {
            //En cualquier caso se cierra la conexion
            post.releaseConnection();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    System.out.println("ERROR cerrando recursos");
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Cliente cliente = new Cliente();
            cliente.setNOMCLI("Juan Gabriel");
            cliente.setAPECLI("Condori Jara");
            cliente.setDNICLI("70335061");
            cliente.setCELCLI("940460321");
            SmsS.enviarSms(cliente);
            System.out.println("MENSAJE SMS ENVIADO");
            JOptionPane.showMessageDialog(null, "MENSAJE SMS ENVIADO", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println("ERROR AL ENVIAR MENSAJE SMS");
            System.out.println("Error en enviarSms_S " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "NO SE ENVIO EL CORREO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
