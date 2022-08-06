package controlador;

import dao.ComentarioImpl;
import modelo.Comentario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import services.EmailS;
import org.primefaces.event.RateEvent;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "comentarioC")
//@Dependent
@SessionScoped
public class ComentarioC implements Serializable {

    private Comentario comentario;
    private ComentarioImpl dao;
    private List<Comentario> listadoComentario;
    private Integer estrellas;

    public ComentarioC() {
        comentario = new Comentario();
        dao = new ComentarioImpl();
        listadoComentario = new ArrayList<>();
    }

    public void registrar() throws Exception {
        try {
            comentario.setIDCLI(dao.obtenerIdCli(comentario.getIDCLI()));
            dao.registrar(comentario);
            EmailS.comentario(comentario);
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Mensaje Enviado"));
        } catch (Exception e) {
            System.out.println("Error en registrar_C " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void activarStar(RateEvent<Integer> rateEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ACTIVADO", rateEvent.getRating() + " Estrellas"));
    }

    public void desactivarStar() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DESACTIVADO", "Sin Estrellas"));
    }

    public void limpiar() {
        comentario = new Comentario();
    }

    public void listar() {
        try {
            listadoComentario = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
        }
    }

    @PostConstruct
    public void construir() {
        try {
            listar();
        } catch (Exception e) {
        }
    }

}
