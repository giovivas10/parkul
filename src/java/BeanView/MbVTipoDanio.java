/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoTipoDanio;
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
import pojo.ParametroEvaluacion;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVTipoDanio {

    /**
     * Creates a new instance of MbVTipoDanio
     */
    public ParametroEvaluacion tipoDanio;
    public List<ParametroEvaluacion> listaParametroEvaluacion;
    public List<ParametroEvaluacion> listaParametroEvaluacionFiltrada;
    
    public Session session;
    public Transaction transaction;
    
    public MbVTipoDanio() {
        this.tipoDanio = new ParametroEvaluacion();
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

            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoTipoDanio.register(this.tipoDanio,this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado correctamente"));

            this.tipoDanio = new ParametroEvaluacion();

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
    
    public List<ParametroEvaluacion> getall(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.listaParametroEvaluacion = daoTipoDanio.getAll(this.session);
            this.transaction.commit();
            
            return listaParametroEvaluacion;
            
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

            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.tipoDanio = daoTipoDanio.getById(this.session, Id);

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

            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoTipoDanio.update(this.tipoDanio, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado correctamente"));

            this.tipoDanio = new ParametroEvaluacion();

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

            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.tipoDanio = daoTipoDanio.getById(this.session, Id);
            daoTipoDanio.delete(this.tipoDanio, this.session);

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

    public ParametroEvaluacion getTipoDanio() {
        return tipoDanio;
    }

    public void setTipoDanio(ParametroEvaluacion tipoDanio) {
        this.tipoDanio = tipoDanio;
    }

    public List<ParametroEvaluacion> getListaParametroEvaluacion() {
        return listaParametroEvaluacion;
    }

    public void setListaParametroEvaluacion(List<ParametroEvaluacion> listaParametroEvaluacion) {
        this.listaParametroEvaluacion = listaParametroEvaluacion;
    }

    public List<ParametroEvaluacion> getListaParametroEvaluacionFiltrada() {
        return listaParametroEvaluacionFiltrada;
    }

    public void setListaParametroEvaluacionFiltrada(List<ParametroEvaluacion> listaParametroEvaluacionFiltrada) {
        this.listaParametroEvaluacionFiltrada = listaParametroEvaluacionFiltrada;
    }
    
}
