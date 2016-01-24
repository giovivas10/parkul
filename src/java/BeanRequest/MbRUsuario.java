/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanRequest;

import Dao.DaoUsuario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pojo.Usuario;
import pojo.Rol;
import Clases.EncryptMD5;
import Dao.DaoRol;
import HibernateUtil.HibernateUtil;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@RequestScoped
public class MbRUsuario {
    
    private Session session;
    private Transaction transaction;

    private Usuario usuario;
    private Rol rol;
    private List<Usuario> listaUsuario;
    private List<Usuario> listaUsuarioFiltrado;
    
    private String txtContrasenaRepita;
    private int rolSelect;

    public MbRUsuario() {
        this.usuario = new Usuario();
        this.usuario.setEstado(true);
    }

    public void register() throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            if (!(this.usuario.getContrasenia().equals(this.txtContrasenaRepita))) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no conciden"));
                return;
            }

            DaoUsuario daoUsuario = new DaoUsuario();
            DaoRol daoRol = new DaoRol();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            if (daoUsuario.getByNombreUsuario(this.session, this.usuario.getUsuario()) != null) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Este nombre de usaurio ya se encuentra registrado en el sistema"));
                return;
            }

            if (daoUsuario.getByCorreoElectronico(this.session, this.usuario.getEmail()) != null) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Este correo ya se encuentra registrado en el sistema"));
                return;
            }

            ///////////////////////////////////////////////////////////
            //Asignacion del Id del rol al pojo del rol
            this.rol = daoRol.getById(this.session, this.rolSelect);
            this.usuario.setRol(rol);
            //////////////////////////////////////////////////////////

            this.usuario.setContrasenia(EncryptMD5.encriptaEnMD5(this.usuario.getContrasenia()));
            daoUsuario.register(this.usuario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado el usuario correctamente"));

            //RequestContext.getCurrentInstance().execute("limpiarFomrmulario('frmRegistrarUsuario')");
            this.usuario = new Usuario();
            this.usuario.setEstado(true);

        } catch (ConstraintViolationException ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            
            for(ConstraintViolation constraint:ex.getConstraintViolations()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", constraint.getPropertyPath()+": " + constraint.getMessage()));
            }
            
        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }

    }
    
    public List<Usuario> getall(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoUsuario daoUsuario = new DaoUsuario();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.listaUsuario = daoUsuario.getAll(this.session);
            this.transaction.commit();
            
            return listaUsuario;
            
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
            
            return null;
        }
        finally
        {
            if(this.transaction!=null)
            {
                this.session.close();
            }
        }
    }
    
    public Usuario getByUsuario(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoUsuario daoUsuario = new DaoUsuario();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            HttpSession sessionUsuario = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            
            this.usuario = daoUsuario.getByNombreUsuario(this.session,sessionUsuario.getAttribute("usuario").toString());
            this.transaction.commit();
            
            return usuario;
            
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
            
            return null;
        }
        finally
        {
            if(this.transaction!=null)
            {
                this.session.close();
            }
        }
    }
    
    public void prueba()throws Exception{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Prueba", "Esto es un mensajo de prueba"));
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public String getTxtContrasenaRepita() {
        return txtContrasenaRepita;
    }

    public void setTxtContrasenaRepita(String txtContrasenaRepita) {
        this.txtContrasenaRepita = txtContrasenaRepita;
    }

    public int getRolSelect() {
        return rolSelect;
    }

    public void setRolSelect(int rolSelect) {
        this.rolSelect = rolSelect;
    }

    public List<Usuario> getListaUsuarioFiltrado() {
        return listaUsuarioFiltrado;
    }

    public void setListaUsuarioFiltrado(List<Usuario> listaUsuarioFiltrado) {
        this.listaUsuarioFiltrado = listaUsuarioFiltrado;
    }
    
    

}
