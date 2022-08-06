package controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "principalC")
//@Dependent
@SessionScoped
public class PrincipalC implements Serializable {
     
    private List<String> images;
    private List<String> imagess;
    private List<String> imagesss;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            images.add("IMG" + i + ".jpg");
        }
        imagess = new ArrayList<String>();
        for (int i = 1; i <= 8; i++) {
            imagess.add("IMG" + i + ".jpg");
        }
        imagesss = new ArrayList<String>();
        for (int i = 1; i <= 15; i++) {
            imagesss.add("IMG" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
    
    public List<String> getImagess() {
        return imagess;
    }
    
    public List<String> getImagesss() {
        return imagesss;
    }
    
}
