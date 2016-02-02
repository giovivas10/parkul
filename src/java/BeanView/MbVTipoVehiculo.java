/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

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
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVTipoVehiculo {

    /**
     * Creates a new instance of MbVTipoVehiculo
     */
    
    public TipoVehiculo tipoVehiculo;
    public List<TipoVehiculo> listaTipoVehiculo;
    public List<TipoVehiculo> listaTipoVehiculoFiltrada;
    
    public Session session;
    public Transaction transaction;
    
    public MbVTipoVehiculo() {
        this.tipoVehiculo = new TipoVehiculo();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //FUNCIONES
    public void register() throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoTipoVehiculo.register(this.tipoVehiculo,this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado correctamente"));

            this.tipoVehiculo = new TipoVehiculo();

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
    
    public List<TipoVehiculo> getall(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.listaTipoVehiculo = daoTipoVehiculo.getAll(this.session);
            this.transaction.commit();
            
            return listaTipoVehiculo;
            
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
    
    public void cargaEditar(int Id)
    {
        this.session = null;
        this.transaction = null;

        try {

            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.tipoVehiculo = daoTipoVehiculo.getById(this.session, Id);

            RequestContext.getCurrentInstance().update("frmEdita:panelActualizar");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditar').show()");
            
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

            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoTipoVehiculo.update(this.tipoVehiculo, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado correctamente"));

            this.tipoVehiculo = new TipoVehiculo();

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

            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.tipoVehiculo = daoTipoVehiculo.getById(this.session, Id);
            daoTipoVehiculo.delete(this.tipoVehiculo, this.session);

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
    ///////////////////////////////////////////////////////////////////////////

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<TipoVehiculo> getListaTipoVehiculo() {
        return listaTipoVehiculo;
    }

    public void setListaTipoVehiculo(List<TipoVehiculo> listaTipoVehiculo) {
        this.listaTipoVehiculo = listaTipoVehiculo;
    }

    public List<TipoVehiculo> getListaTipoVehiculoFiltrada() {
        return listaTipoVehiculoFiltrada;
    }

    public void setListaTipoVehiculoFiltrada(List<TipoVehiculo> listaTipoVehiculoFiltrada) {
        this.listaTipoVehiculoFiltrada = listaTipoVehiculoFiltrada;
    }
    
    
    
}
