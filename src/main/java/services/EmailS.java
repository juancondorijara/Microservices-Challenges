package services;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.swing.JOptionPane;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import dao.Conexion;
import modelo.Comentario;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Usuario;
// https://www.google.com/intl/es/gmail/about/#
// https://www.google.com/settings/security/lesssecureapps

public class EmailS extends Conexion {

    public static void configuracionEmail(String remitente, String clave, String destinatario, String asunto, String cuerpo) throws Exception {
        // La configuracion para enviar correo
        // Properties props = System.getProperties();
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);

            BodyPart texto = new MimeBodyPart();
            texto.setText(cuerpo);

            //PARA ENVIAR ARCHIVOS ADJUNTOS EN EL CORREO
//            String url = "D:\\JuanCondori\\Documentos\\ALGORITMOS\\AED.jpg";
//            BodyPart adjunto = new MimeBodyPart();
//            adjunto.setDataHandler(new DataHandler(new FileDataSource(url)));
//            adjunto.setFileName("Imagen.jpg");
            MimeMultipart multiParte = new MimeMultipart();
//            multiParte.addBodyPart(adjunto);
            multiParte.addBodyPart(texto);
            message.setContent(multiParte);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            System.out.println("Error en configuracionEmail_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void comentario(Comentario comentario) throws Exception {
        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario varia en la vista
        String destinatario = "juan.condori.jara@vallegrande.edu.pe";

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = comentario.getASUNCOM();
        String cuerpo = comentario.getMSGCOM();

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en comentario_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void enviarPwdCli(Cliente cliente) throws Exception {
        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario varia en la vista
        String destinatario = cliente.getEMACLI();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Envio de tu Contraseña de CONFLOMARK";
        String cuerpo = "BUEN DÍA " + cliente.getNOMCLI() + " " + cliente.getAPECLI() + "\n"
                + "\n Su usuario es: " + cliente.getDNICLI() + "\n Su contraseña es: " + cliente.getPWDCLI() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/Conflomark_ODAO/faces/Login.xhtml" + "\n"
                + "\n Muchas gracias por registrarse en CONFLOMARK";

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarEmail_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void enviarPwdEmp(Empleado empleado) throws Exception {
        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario varia en la vista
        String destinatario = empleado.getEMAEMP();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Envio de tu Contraseña de CONFLOMARK";
        String cuerpo = "BUEN DÍA " + empleado.getNOMEMP() + " " + empleado.getAPEEMP() + "\n"
                + "\n Su usuario es: " + empleado.getDNIEMP() + "\n Su contraseña es: " + empleado.getPWDEMP() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/Conflomark_ODAO/faces/Login.xhtml" + "\n"
                + "\n Muchas gracias por registrarse en CONFLOMARK";

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarEmail_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void notificacionCli(String usu) throws UnknownHostException, Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(C.NOMCLI) AS NOMCLI,\n"
                + "INITCAP(C.APECLI) AS APECLI,\n"
                + "C.EMACLI AS EMACLI\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN CLIENTE C ON\n"
                + "U.IDCLI = C.IDCLI\n"
                + "WHERE \n"
                + "U.USUUSU = '" + usu + "'";
        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNOMCLI(rs.getString("NOMCLI"));
            usuario.setAPECLI(rs.getString("APECLI"));
            usuario.setEMACLI(rs.getString("EMACLI"));
        }
        rs.close();
        st.close();

        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario segun el usuario en el login
        String destinatario = usuario.getEMACLI();

        //IP, fecha y hora
        String ip = InetAddress.getLocalHost().getHostAddress();
        String fecha = new SimpleDateFormat("dd/MMMM/yyyy").format(Calendar.getInstance().getTime());
        String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        //Asunto, cuerpo y la notificacion de inicio de sesion
        String asunto = "Nuevo inicio de sesión detectado de la cuenta CONFLOMARK";
        String cuerpo = "BUEN DÍA " + usuario.getNOMCLI() + " " + usuario.getAPECLI() + "\n"
                + "Recientemente se ha iniciado sesión en la cuenta CONFLOMARK: " + usuario.getUSUUSU() + "\n"
                + "A continuación se muestran algunos detalles que pueden ayudar a garantizar la seguridad: " + "\n"
                + "\n País o región: Perú" + "\n"
                + "\n IP: " + ip + "\n"
                + "\n Fecha: " + fecha + "\n"
                + "\n Hora: " + hora + "\n"
                + "\n Si has sido tú, puedes descartar tranquilamente este correo electrónico." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de CONFLOMARK.";

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarNotificacion_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void notificacionEmp(String usu) throws UnknownHostException, Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(E.NOMEMP) AS NOMEMP,\n"
                + "INITCAP(E.APEEMP) AS APEEMP,\n"
                + "E.EMAEMP AS EMAEMP\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN EMPLEADO E ON\n"
                + "U.IDEMP = E.IDEMP\n"
                + "WHERE USUUSU = '" + usu + "'";
        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNOMEMP(rs.getString("NOMEMP"));
            usuario.setAPEEMP(rs.getString("APEEMP"));
            usuario.setEMAEMP(rs.getString("EMAEMP"));
        }
        rs.close();
        st.close();

        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario segun el usuario en el login
        String destinatario = usuario.getEMAEMP();

        //IP, fecha y hora
        String ip = InetAddress.getLocalHost().getHostAddress();
        String fecha = new SimpleDateFormat("dd/MMMM/yyyy").format(Calendar.getInstance().getTime());
        String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        //Asunto, cuerpo y la notificacion de inicio de sesion
        String asunto = "Nuevo inicio de sesión detectado de la cuenta CONFLOMARK";
        String cuerpo = "BUEN DÍA " + usuario.getNOMEMP() + " " + usuario.getAPEEMP() + "\n"
                + "Recientemente se ha iniciado sesión en la cuenta CONFLOMARK: " + usuario.getUSUUSU() + "\n"
                + "A continuación se muestran algunos detalles que pueden ayudar a garantizar la seguridad: " + "\n"
                + "\n País o región: Perú" + "\n"
                + "\n IP: " + ip + "\n"
                + "\n Fecha: " + fecha + "\n"
                + "\n Hora: " + hora + "\n"
                + "\n Si has sido tú, puedes descartar tranquilamente este correo electrónico." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de CONFLOMARK.";

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarNotificacion_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void restablecerPwdCli(String email) throws Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.IDUSU AS IDUSU,\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(C.NOMCLI) AS NOMCLI,\n"
                + "INITCAP(C.APECLI) AS APECLI,\n"
                + "C.EMACLI AS EMACLI\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN CLIENTE C ON\n"
                + "U.IDCLI = C.IDCLI\n"
                + "WHERE \n"
                + "C.EMACLI = '" + email + "'";

        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setIDUSU(rs.getInt("IDUSU"));
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNOMCLI(rs.getString("NOMCLI"));
            usuario.setAPECLI(rs.getString("APECLI"));
            usuario.setEMAIL(rs.getString("EMACLI"));
        }
        rs.close();
        st.close();

        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario segun el usuario en el login
        String destinatario = usuario.getEMAIL();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Restablecer tu contraseña de la cuenta CONFLOMARK";
        String cuerpo = "BUEN DÍA " + usuario.getNOMCLI() + " " + usuario.getAPECLI() + "\n"
                + "Has indicado que olvidaste tu contraseña en la cuenta CONFLOMARK: " + usuario.getUSUUSU() + "\n"
                + "\n Su nueva contraseña es: " + usuario.getPWDUSU() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/Conflomark_ODAO/faces/Login.xhtml" + "\n"
                + "\n Puedes comunicarte al email: " + remitente + " para mayor ayuda o información." + "\n"
                + "Estaremos encantados de responder las preguntas que puedas tener." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de CONFLOMARK.";

        String sqll = "update USUARIO set PWDUSU=? where IDUSU=? ";
        usuario.setPWDUSU(EncriptarS.encriptar(usuario.getPWDUSU()));
        try {
            PreparedStatement ps = conectar().prepareStatement(sqll);
            ps.setString(1, usuario.getPWDUSU());
            ps.setInt(2, usuario.getIDUSU());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en restablecerPwdCli_S " + e.getMessage());
            e.printStackTrace();
        }

        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en restablecerPwdCli_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void restablecerPwdEmp(String email) throws Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.IDUSU AS IDUSU,\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(E.NOMEMP) AS NOMEMP,\n"
                + "INITCAP(E.APEEMP) AS APEEMP,\n"
                + "E.EMAEMP AS EMAEMP\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN EMPLEADO E ON\n"
                + "U.IDEMP = E.IDEMP\n"
                + "WHERE \n"
                + "E.EMAEMP = '" + email + "'";

        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setIDUSU(rs.getInt("IDUSU"));
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNOMEMP(rs.getString("NOMEMP"));
            usuario.setAPEEMP(rs.getString("APEEMP"));
            usuario.setEMAIL(rs.getString("EMAEMP"));
        }
        rs.close();
        st.close();
        
        // El correo gmail de envio
        String remitente = "juangabrielcondorijara@gmail.com";
        String clave = "jara2020";

        //Destinatario segun el usuario en el login
        String destinatario = usuario.getEMAIL();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Restablecer tu contraseña de la cuenta CONFLOMARK";
        String cuerpo = "BUEN DÍA " + usuario.getNOMEMP() + " " + usuario.getAPEEMP() + "\n"
                + "Has indicado que olvidaste tu contraseña en la cuenta CONFLOMARK: " + usuario.getUSUUSU() + "\n"
                + "\n Su nueva contraseña es: " + usuario.getPWDUSU() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/Conflomark_ODAO/faces/Login.xhtml" + "\n"
                + "\n Puedes comunicarte al email: " + remitente + " para mayor ayuda o información." + "\n"
                + "Estaremos encantados de responder las preguntas que puedas tener." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de CONFLOMARK.";

        String sqll = "update USUARIO set PWDUSU=? where IDUSU=? ";
        usuario.setPWDUSU(EncriptarS.encriptar(usuario.getPWDUSU()));
        try {
            PreparedStatement ps = conectar().prepareStatement(sqll);
            ps.setString(1, usuario.getPWDUSU());
            ps.setInt(2, usuario.getIDUSU());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en restablecerPwdEmp_S " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            EmailS.configuracionEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en restablecerPwdEmp_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //ENVIO DE USUARIO Y CONTRASEÑA
//        try {
//            Cliente cliente = new Cliente();
//            cliente.setNOMCLI("Juan Gabriel");
//            cliente.setAPECLI("Condori Jara");
//            cliente.setDNICLI("70335061");
//            cliente.setEMACLI("juan.condori.jara@vallegrande.edu.pe");
//            EmailS.enviarEmail(cliente);
//            System.out.println("CORREO ENVIADO");
//            JOptionPane.showMessageDialog(null, "CORREO ENVIADO", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
//        } catch (Exception ex) {
//            System.out.println("ERROR AL ENVIAR CORREO");
//            System.out.println("Error en Email_S " + ex.getMessage());
//            JOptionPane.showMessageDialog(null, "NO SE ENVIO EL CORREO", "ERROR", JOptionPane.ERROR_MESSAGE);
//        }

        //ENVIO DE NOTIFICACION AL INICIAR SESION
//        try {
//            String usu = "70335061";
//            EmailS.notificacionCli(usu);
//            System.out.println("EXITO");
//        } catch (Exception ex) {
//            System.out.println("FALLA" + ex.getMessage());
//        }
        //ENVIO PARA RESTABLECER CONTRASEÑA
        try {
            String email = "juan.condori.jara@vallegrande.edu.pe";
            EmailS.restablecerPwdCli(email);
//            EmailS.restablecerPwdEmp(email);
            System.out.println("EXITO");
        } catch (Exception ex) {
            System.out.println("FALLA" + ex.getMessage());
        }
    }

}
