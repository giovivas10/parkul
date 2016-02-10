/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanAplication;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ApplicationScoped
public class MbAPrueba {

    private Boolean mostrar;
    private final String rol;
    public MbAPrueba() {
        rol = "1";
        /*HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.rol = httpSession.getAttribute("rol").toString();
        if(this.rol.equals("1")){
            mostrar = true;
        }
        else{
            mostrar = false;
        }*/
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }
    
    
}
