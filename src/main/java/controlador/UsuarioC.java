package controlador;

import dao.UsuarioD;
import modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
//import org.primefaces.context.PrimeFacesContext;
import services.EmailS;
import services.EncriptarS;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "usuarioC")
//@Dependent
@SessionScoped
public class UsuarioC implements Serializable {

    private Usuario usuario;
    private UsuarioD dao;
    private int captcha = 0;
    private int intentos = 0;
    private int tiempo = 20;
    private boolean bloquear = false;
    String PWDUSU;

    public UsuarioC() {
        usuario = new Usuario();
        dao = new UsuarioD();
    }

    public void login() throws Exception {
        try {
            dao.login(usuario, PWDUSU);
        } catch (Exception e) {
            System.out.println("Error en login_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loginNivel() throws Exception {
        try {
            dao.loginNivel(usuario);
        } catch (Exception e) {
            System.out.println("Error en loginNivel_C" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loginRol() throws Exception {
        try {
            dao.loginRol(usuario);
        } catch (Exception e) {
            System.out.println("Error en loginNivel_C" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void iniciarSesion() throws Exception {
        try {
            PWDUSU = EncriptarS.encriptar(usuario.getPWDUSU());
            usuario = dao.login(usuario, PWDUSU);
            this.login();
            if (dao.logueo == true) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoUsuario", usuario);
                if (dao.nivel == 2) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Conflomark_ODAO/faces/vistaCliente/Productos.xhtml");
                    String usu = usuario.getUSUUSU();
                    EmailS.notificacionCli(usu);
                }
                if (dao.rol != null) {
                    switch (dao.rol) {
                        case "A":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/Conflomark_ODAO/faces/vistas/Dashboard.xhtml");
                            break;
                        case "T":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/Conflomark_ODAO/faces/vistaTrabajador/Ventas.xhtml");
                            break;
                        default:
                            break;
                    }
                    String usu = usuario.getUSUUSU();
                    EmailS.notificacionEmp(usu);
                }
            } else {
                intentos++;
                limpiar();
                if (intentos == 1) {
                    setIntentos(1);
                    setCaptcha(0);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "1 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 2 INTENTOS", ""));
                }
                if (intentos == 2) {
                    setIntentos(2);
                    setCaptcha(0);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "2 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDA 1 INTENTO", ""));
                }
                if (intentos == 3) {
                    setIntentos(3);
                    setCaptcha(1);
//                    bloquear = true;
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "3 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VUELVE A INTENTARLO", ""));
                }
                if (intentos == 4) {
                    setIntentos(4);
                    setCaptcha(1);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "1 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 2 INTENTOS", ""));
                }
                if (intentos == 5) {
                    setIntentos(5);
                    setCaptcha(1);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "2 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDA 1 INTENTO", ""));
                }
                if (intentos == 6) {
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "3 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "BLOQUEO DE SEGURIDAD", ""));
                    setIntentos(6);
                    bloquear = true;
                    if (bloquear) {
                        delaySegundo();
                    }
                    if (intentos == 6) {
                        setIntentos(0);
                        setCaptcha(0);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error en iniciarSesion_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delaySegundo() {  //private nuevo
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Error en delaySegundo_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void modificarRolUser(String rol, int id) throws Exception {
        try {
            System.out.println(rol + "   " + id);
            dao.modificarRolUser(rol, id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Se Cambio el ROL"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "No Se Cambio el ROL"));
            System.out.println("Error en modificarPwdGeneral_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void modificarPwdGeneral() throws Exception {
        try {
            modificarPwdCli();
            modificarPwdEmp();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REVISE SU CORREO", "Se Envio su Contraseña"));
        } catch (Exception e) {
            System.out.println("Error en modificarPwdGeneral_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void modificarPwdCli() throws Exception {
        try {
            String email = usuario.getEMAIL();
            EmailS.restablecerPwdCli(email);
            limpiar();
        } catch (Exception e) {
            System.out.println("Error en modificarPwdCli_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void modificarPwdEmp() throws Exception {
        try {
            String email = usuario.getEMAIL();
            EmailS.restablecerPwdEmp(email);
            limpiar();
        } catch (Exception e) {
            System.out.println("Error en modificarPwdEmp_C " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void limpiar() {
        usuario = new Usuario();
    }
    
    public void activarSesion() {
        tiempo = 20;
    }
    
    // Obtener el objeto de la sesión activa
    public static Usuario obtenerObjetoSesion() {
        return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("objetoUsuario");
    }

    // Si la sesión no está iniciada no permitirá entrar a otra vista de la aplicación y por tanto llevara al Login
    public void seguridadSesion() throws IOException {
        if (obtenerObjetoSesion() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Conflomark_ODAO/faces/Login.xhtml");
        }
    }

    // Cerrar y limpiar la sesión y direccionar al xhtml inicial del proyecto
    public void cerrarSesion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Conflomark_ODAO/faces/Login.xhtml");
    }
    
    // Si durante 20 segundos no hay actividad del usuario, se cerrará la sesión
    public void tiempoSesion() throws IOException {
        try {
            if (tiempo > 0) {
                tiempo--;
            } else if (tiempo == 0) {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Conflomark_ODAO/faces/Login.xhtml");
                System.out.println("SESIÓN CERRADA, TIEMPO SIN ACTIVIDAD");
            }
        } catch (Exception e) {
            System.out.println("Error en tiempoSesion_C " + e.getMessage());
        }
    }
    
}
