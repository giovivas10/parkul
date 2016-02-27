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
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
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

    private String foto;
    private String rutaFoto;

    private String txtContrasenaRepita;
    private int rolSelect;

    public MbVUsuario() {
        this.usuario = new Usuario();
        this.usuario.setEstado(true);
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("rol") != null) {
            String rol1 = httpSession.getAttribute("rol").toString();
            switch (rol1) {
                case "1":
                    RequestContext.getCurrentInstance().execute("mostrarMenu('liParametros,liUsuario,liPropietarios,liAuxiliar,liInformes')");
                    break;
                case "2":
                    RequestContext.getCurrentInstance().execute("mostrarMenu('liAuxiliar')");
                    break;
                case "3":
                    RequestContext.getCurrentInstance().execute("mostrarMenu('liPropietarios')");
                    break;
            }
        }
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
            this.usuario.setFoto(this.rutaFoto);
            //////////////////////////////////////////////////////////

            this.usuario.setContrasenia(EncryptMD5.encriptaEnMD5(this.usuario.getContrasenia()));
            daoUsuario.register(this.usuario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado el usuario correctamente"));

            //RequestContext.getCurrentInstance().execute("limpiarFomrmulario('frmRegistrarUsuario')");
            this.usuario = new Usuario();
            this.usuario.setEstado(true);
            this.foto = new String();
            this.rutaFoto = new String();
            RequestContext.getCurrentInstance().update("frmRegistrarUsuario");
            

        } catch (ConstraintViolationException ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            for (ConstraintViolation constraint : ex.getConstraintViolations()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", constraint.getPropertyPath() + ": " + constraint.getMessage()));
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

    public List<Usuario> getall() {

        this.session = null;
        this.transaction = null;

        try {
            DaoUsuario daoUsuario = new DaoUsuario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaUsuario = daoUsuario.getAll(this.session);
            this.transaction.commit();

            return listaUsuario;

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

    public Usuario getByUsuario() {

        this.session = null;
        this.transaction = null;

        try {
            DaoUsuario daoUsuario = new DaoUsuario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            HttpSession sessionUsuario = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

            this.usuario = daoUsuario.getByNombreUsuario(this.session, sessionUsuario.getAttribute("usuario").toString());
            this.transaction.commit();

            return usuario;

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
            this.usuario.setFoto(this.rutaFoto);
            //////////////////////////////////////////////////////////

            daoUsuario.update(this.session, this.usuario);

            this.transaction.commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Los cambios fueron guardados correctamente"));
            
            this.usuario = new Usuario();
            this.usuario.setEstado(true);
            this.foto = new String();
            this.rutaFoto = new String();
            RequestContext.getCurrentInstance().update("frmListaUsuario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').hide()");

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

    public void cargaUsuarioEditar(int Id) {
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

    private String getRandomImageName() {
        int i = (int) (Math.random() * 1000000000);

        return String.valueOf(i);
    }

    public void oncapture(CaptureEvent captureEvent) {
        this.foto = getRandomImageName();
        byte[] data = captureEvent.getData();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "usuario" + File.separator + foto + ".png";

        this.rutaFoto = this.foto + ".png";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }

        //RequestContext.getCurrentInstance().update("frmRegistrarUsuario");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
