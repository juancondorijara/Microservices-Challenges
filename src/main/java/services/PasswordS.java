package services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Usuario;
// http://www.forosdelweb.com/f45/obtener-ip-java-1048135/
// https://www.delftstack.com/es/howto/java/how-to-get-the-current-date-time-in-java/

public class PasswordS {

    public static void pwdCliente(Cliente cliente) throws Exception {
        int length = 10;
        String symbol = "-/.^&*_!@%=+>)";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String finalString = upper + lower + numbers + symbol;
        Random random = SecureRandom.getInstanceStrong();
        char[] pass = new char[length];
        for (int i = 0; i < length; i++) {
            pass[i] = finalString.charAt(random.nextInt(finalString.length()));
        }
        String password = String.valueOf(pass);
        cliente.setPWDCLI(password);
        System.out.println(password);
        Logger.getGlobal().log(Level.INFO, password);
    }
    
    public static String pwdUsuario(String password) throws Exception {
        Usuario usuario = new Usuario();
        int length = 10;
        String symbol = "-/.^&*_!@%=+>)";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String finalString = upper + lower + numbers + symbol;
        Random random = SecureRandom.getInstanceStrong();
        char[] pass = new char[length];
        for (int i = 0; i < length; i++) {
            pass[i] = finalString.charAt(random.nextInt(finalString.length()));
        }
        password = String.valueOf(pass);
        usuario.setPWDUSU(password);
        System.out.println(password);
        Logger.getGlobal().log(Level.INFO, password);
        return password;
    }
    
    public static void main(String[] args) throws Exception, UnknownHostException {
        Cliente cliente = new Cliente();
        pwdCliente(cliente);
        Usuario usuario = new Usuario();
        String ip = InetAddress.getLocalHost().getHostAddress();
    }
    
}
