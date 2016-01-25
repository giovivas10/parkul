/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import Clases.EncryptMD5;
import Dao.DaoUsuario;
import HibernateUtil.HibernateUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Usuario;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@Named(value = "mbSLogin")
@SessionScoped
public class MbSLogin {

    /**
     * Creates a new instance of MbSLogin
     */
    private String usuario;
    private String contrasenia;

    private Session session;
    private Transaction transaction;

    public MbSLogin() 
    {
        HttpSession miSession =(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        miSession.setMaxInactiveInterval(5000);
    }

    public String login() {
        this.session = null;
        this.transaction = null;

        try {
            DaoUsuario daoUsuario = new DaoUsuario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            Usuario usuarioConsulta = daoUsuario.getByNombreUsuario(this.session, this.usuario);
            //int rol = usuarioConsulta.getRol().getId();

            if (usuarioConsulta != null) {
                if (usuarioConsulta.getContrasenia().equals(EncryptMD5.encriptaEnMD5(this.contrasenia))) {
                    //if(rol==1){
                        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                        httpSession.setAttribute("usuario", this.usuario);
                        
                        return "/usuario/verTodos";
                    //}
                    
                }
            }

            this.transaction.commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de acceso ", " Usuario o contraseña incorrecto"));
            this.usuario = null;
            this.contrasenia = null;

            return "/index";

        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));

            return null;
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }
    }

    public String cerrarSesion() {
        this.usuario = null;
        this.contrasenia = null;
        
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.invalidate();

        return "/index";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
