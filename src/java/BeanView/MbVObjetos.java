/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoObjetos;
import HibernateUtil.HibernateUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import pojo.Objetos;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVObjetos {

    /**
     * Creates a new instance of MbVObjetos
     */
    private Objetos objetos;
    private List<Objetos> listaObjetos;
    private List<Objetos> listaObjetosFiltrada;
    
    private Session session;
    private Transaction transaction;
    
    public MbVObjetos() {
        this.objetos = new Objetos();
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

            DaoObjetos daoObjetos = new DaoObjetos();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoObjetos.register(this.objetos,this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado el Objeto correctamente"));

            this.objetos = new Objetos();

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
    
    public List<Objetos> getall(){
        
        this.session = null;
        this.transaction = null;
        
        
        try
        {
            DaoObjetos daoObjetos = new DaoObjetos();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.listaObjetos = daoObjetos.getAll(this.session);
            this.transaction.commit();
            
            return listaObjetos;
            
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

            DaoObjetos daoObjetos = new DaoObjetos();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.objetos = daoObjetos.getById(this.session, Id);

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

            DaoObjetos daoObjetos = new DaoObjetos();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoObjetos.update(this.objetos, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado correctamente"));

            this.objetos = new Objetos();
            
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

            DaoObjetos daoObjetos = new DaoObjetos();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            this.objetos = daoObjetos.getById(this.session, Id);
            daoObjetos.delete(this.objetos, this.session);

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

    public Objetos getObjetos() {
        return objetos;
    }

    public void setObjetos(Objetos objetos) {
        this.objetos = objetos;
    }

    public List<Objetos> getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(List<Objetos> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<Objetos> getListaObjetosFiltrada() {
        return listaObjetosFiltrada;
    }

    public void setListaObjetosFiltrada(List<Objetos> listaObjetosFiltrada) {
        this.listaObjetosFiltrada = listaObjetosFiltrada;
    }
    
    
    
}
