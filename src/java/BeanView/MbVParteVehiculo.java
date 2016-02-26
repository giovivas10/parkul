/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoParteVehiculo;
import HibernateUtil.HibernateUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import pojo.ParteVehiculo;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVParteVehiculo {

    
    private ParteVehiculo parteVehiculo;
    private List<ParteVehiculo> listaParteVehiculo;
    private List<ParteVehiculo> listaParteVehiculoFiltrada;
    
    private Session session;
    private Transaction transaction;
    
    public MbVParteVehiculo() {
        this.parteVehiculo = new ParteVehiculo();
        
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("rol") != null) {
            String rol = httpSession.getAttribute("rol").toString();
            switch (rol) {
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
    
    ///////////////////////////////////////////////////////////////////////////
    //FUNCIONES
    public void register() throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoParteVehiculo.register(this.parteVehiculo,this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado correctamente"));

            this.parteVehiculo = new ParteVehiculo();

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
    
    public List<ParteVehiculo> getall(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.listaParteVehiculo = daoParteVehiculo.getAll(this.session);
            this.transaction.commit();
            
            return listaParteVehiculo;
            
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

            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.parteVehiculo = daoParteVehiculo.getById(this.session, Id);

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

            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoParteVehiculo.update(this.parteVehiculo, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado correctamente"));

            this.parteVehiculo = new ParteVehiculo();
            RequestContext.getCurrentInstance().execute("PF('dialogoEditar').hide()");

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

            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.parteVehiculo = daoParteVehiculo.getById(this.session, Id);
            daoParteVehiculo.delete(this.parteVehiculo, this.session);

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

    public ParteVehiculo getParteVehiculo() {
        return parteVehiculo;
    }

    public void setParteVehiculo(ParteVehiculo parteVehiculo) {
        this.parteVehiculo = parteVehiculo;
    }

    public List<ParteVehiculo> getListaParteVehiculo() {
        return listaParteVehiculo;
    }

    public void setListaParteVehiculo(List<ParteVehiculo> listaParteVehiculo) {
        this.listaParteVehiculo = listaParteVehiculo;
    }

    public List<ParteVehiculo> getListaParteVehiculoFiltrada() {
        return listaParteVehiculoFiltrada;
    }

    public void setListaParteVehiculoFiltrada(List<ParteVehiculo> listaParteVehiculoFiltrada) {
        this.listaParteVehiculoFiltrada = listaParteVehiculoFiltrada;
    }
    
    
}
