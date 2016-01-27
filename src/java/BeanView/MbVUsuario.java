/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoUsuario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import pojo.Usuario;
import pojo.Rol;
import Clases.EncryptMD5;
import Dao.DaoRol;
import HibernateUtil.HibernateUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVUsuario {
    
    private Session session;
    private Transaction transaction;

    private Usuario usuario;
    private Rol rol;
    private List<Usuario> listaUsuario;
    private List<Usuario> listaUsuarioFiltrado;
    
    private String txtContrasenaRepita;
    private int rolSelect;
    private UploadedFile avatar;

    public MbVUsuario() {
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
    
    public void update() throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            DaoUsuario daoUsuario = new DaoUsuario();
            DaoRol daoRol = new DaoRol();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            ///////////////////////////////////////////////////////////
            //Asignacion del Id del rol al pojo del rol
            this.rol = daoRol.getById(this.session, this.rolSelect);
            this.usuario.setRol(rol);
            //////////////////////////////////////////////////////////

            daoUsuario.update(this.session, this.usuario);

            this.transaction.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Los cambios fueron guardados correctamente"));

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
    
    public void actualizarAvatar()throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        
        try
        {
            if(this.avatar.getSize()<=0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar un archivo de imagen \".png\""));
                return;
            }
            
            /*if(!this.avatar.getFileName().endsWith(".png"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\""));
                return;
            }*/
            
            if(this.avatar.getSize()>2097152)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 2mb"));
                return;
            }
            
            ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaAvatar=(String)servletContext.getRealPath("/avatar");
            
            outputStream=new FileOutputStream(new File(carpetaAvatar+"/"+this.usuario.getId()+".png"));
            inputStream=this.avatar.getInputstream();
            
            int read=0;
            byte[] bytes=new byte[1024];
            
            while((read=inputStream.read(bytes))!=-1)
            {
                outputStream.write(bytes, 0, read);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Avatar actualizado correctamente")); 
        }
        catch(Exception ex)
        {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador")); 
        }
        finally
        {
            if(inputStream!=null){
                inputStream.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }
    
    public void cargaUsuarioEditar(int Id)
    {
        this.session = null;
        this.transaction = null;

        try {

            DaoUsuario daoUsuario = new DaoUsuario();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.usuario = daoUsuario.getByIdUsuario(this.session, Id);

            RequestContext.getCurrentInstance().update("frmEditarUsuario:panelActualizarUsuario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').show()");
            
            this.transaction.commit();
                        
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Los cambios fueron guardados correctamente"));

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
    
    public void delete(int Id) throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            DaoUsuario daoUsuario = new DaoUsuario();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.usuario = daoUsuario.getByIdUsuario(this.session, Id);
            daoUsuario.delete(this.session, this.usuario);

            this.transaction.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Informacion Eliminada"));

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

    public UploadedFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadedFile avatar) {
        this.avatar = avatar;
    }
    
    
    
    

}