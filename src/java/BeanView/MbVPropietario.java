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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
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
    
    public MbVPropietario() {
        this.propietario = new Propietario();
        this.tipoUsuario = new TipoUsuario();
        this.tipoVehiculo = new TipoVehiculo();
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
            //////////////////////////////////////////////////////////

            daoPropietario.register(this.propietario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado el propietario correctamente"));

            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);

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
            //////////////////////////////////////////////////////////

            daoPropietario.update(this.propietario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado el propietario correctamente"));

            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);

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

    
}
