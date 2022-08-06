package controlador;

import dao.FamiliaD;
import modelo.Familia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "familiaC")
//@Dependent
@SessionScoped
public class FamiliaC implements Serializable {

    private Familia familia;
    private FamiliaD dao;
    private List<Familia> listadoFamilia;

    public FamiliaC() {
        familia = new Familia();
        dao = new FamiliaD();
        listadoFamilia = new ArrayList<>();
    }
    
    public void listar() {
        try {
            listadoFamilia = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
        }
    }
    
    @PostConstruct
    public void construir() {
        listar();
    }

}
