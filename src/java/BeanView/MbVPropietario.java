/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoPropietario;
import Dao.DaoTipoUsuario;
import Dao.DaoTipoVehiculo;
import HibernateUtil.HibernateUtil;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import pojo.Propietario;
import pojo.TipoUsuario;
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVPropietario {

    /**
     * Creates a new instance of MbVPropietario
     */
    private Session session;
    private Transaction transaction;
    
    
    private Propietario propietario;
    private TipoUsuario tipoUsuario;
    private TipoVehiculo tipoVehiculo;
    
    private List<Propietario> listaPropietario;
    private List<Propietario> listaPropietarioFiltrado;
    
    private String foto;
    private String rutaFoto;
    
    public MbVPropietario() {
        this.propietario = new Propietario();
        this.tipoUsuario = new TipoUsuario();
        this.tipoVehiculo = new TipoVehiculo();
        
        Calendar fecha = Calendar.getInstance();
        this.propietario.setModelo(fecha.getWeekYear());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //FUNCIONES
    public void register() throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            DaoPropietario daoPropietario = new DaoPropietario();
            DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuario();
            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            ///////////////////////////////////////////////////////////
            //Asignacion del Id del rol al pojo del rol
            this.propietario.setTipoUsuario(daoTipoUsuario.getById(this.session, this.tipoUsuario.getId()));
            this.propietario.setTipoVehiculo(daoTipoVehiculo.getById(this.session, this.tipoVehiculo.getId()));
            this.propietario.setFotoPropietario(this.rutaFoto);
            //////////////////////////////////////////////////////////

            daoPropietario.register(this.propietario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado el propietario correctamente"));

            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);
            Calendar fecha = Calendar.getInstance();
            this.propietario.setModelo(fecha.getWeekYear());
            this.rutaFoto = new String();

        } 
        catch (Exception ex) {
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
    
    public List<Propietario> getall(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoPropietario daoPropietario = new DaoPropietario();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.listaPropietario = daoPropietario.getAll(this.session);
            this.transaction.commit();
            
            return listaPropietario;
            
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
    
    public void cargaUsuarioEditar(int Id)
    {
        this.session = null;
        this.transaction = null;

        try {

            DaoPropietario daoPropietario = new DaoPropietario();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.propietario = daoPropietario.getByIdPropietario(this.session, Id);
            this.tipoUsuario.setId(this.propietario.getTipoUsuario().getId());
            this.tipoVehiculo.setId(this.propietario.getTipoVehiculo().getId());

            RequestContext.getCurrentInstance().update("frmEditarPropietario:panelActualizarPropietario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarPropietario').show()");
            
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
    
    public void update() throws Exception
    {
        this.session = null;
        this.transaction = null;

        try {

            DaoPropietario daoPropietario = new DaoPropietario();
            DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuario();
            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            ///////////////////////////////////////////////////////////
            //Asignacion del Id del rol al pojo del rol
            this.propietario.setTipoUsuario(daoTipoUsuario.getById(this.session, this.tipoUsuario.getId()));
            this.propietario.setTipoVehiculo(daoTipoVehiculo.getById(this.session, this.tipoVehiculo.getId()));
            this.propietario.setFotoPropietario(this.rutaFoto);
            //////////////////////////////////////////////////////////

            daoPropietario.update(this.propietario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado el propietario correctamente"));

            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);
            Calendar fecha = Calendar.getInstance();
            this.propietario.setModelo(fecha.getWeekYear());
            this.rutaFoto = new String();

        } 
        catch (Exception ex) {
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

            DaoPropietario daoPropietario = new DaoPropietario();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.propietario = daoPropietario.getByIdPropietario(this.session, Id);
            daoPropietario.delete(this.propietario, this.session);

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
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images"+ File.separator + "propietario" + File.separator + foto + ".png";
        
        this.rutaFoto = this.foto+".png";
         
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
        
        //RequestContext.getCurrentInstance().update("frmRegistrarUsuario:modalDialog2");
        //RequestContext.getCurrentInstance().execute("PF('dlg3').show()");
        
    }
    //////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    
    public List<Propietario> getListaPropietario() {
        return listaPropietario;
    }

    public void setListaPropietario(List<Propietario> listaPropietario) {
        this.listaPropietario = listaPropietario;
    }
    
    public List<Propietario> getListaPropietarioFiltrado() {
        return listaPropietarioFiltrado;
    }

    public void setListaPropietarioFiltrado(List<Propietario> listaPropietarioFiltrado) {
        this.listaPropietarioFiltrado = listaPropietarioFiltrado;
    }
    
    
    //////////////////////////////////////////////////////////////////////////

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
       
}
