/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoEvaluacionEstadoVehicular;
import Dao.DaoPropietario;
import HibernateUtil.HibernateUtil;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import pojo.EvaluacionEstadoVehicular;
import pojo.Propietario;

/**
 *
 * @author Giovanni
 */
@Named(value = "mbVInforme")
@ManagedBean
@ViewScoped
public class MbVInforme {

    /**
     * Creates a new instance of MbVInforme
     */
    public Date fechaInicio;
    public Date fechaFin;
    public Propietario propietario;
    public List<EvaluacionEstadoVehicular> listaEvaluacionVehiciluar;
    public List<EvaluacionEstadoVehicular> listaEvaluacionVehiciluarFiltrada;
    
    public String placa;
    public String auxiliar;

    private Session session;
    private Transaction transaction;

    public MbVInforme() {
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

    public List<EvaluacionEstadoVehicular> listarPorFechas() {
        this.session = null;
        this.transaction = null;

        try {
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaEvaluacionVehiciluar = daoEvaluacionEstadoVehicular.listadoInforme(this.session, this.fechaInicio, this.fechaFin);
            this.transaction.commit();

            RequestContext.getCurrentInstance().update("frmListaObjetos:tbl");

            return listaEvaluacionVehiciluar;

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

    public void limpiarPorFechas() {
        this.fechaInicio = null;
        this.fechaFin = null;
        listarPorFechas();
    }
    
    public List<EvaluacionEstadoVehicular> listarPorPlaca() {
        this.session = null;
        this.transaction = null;

        try {
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaEvaluacionVehiciluar = daoEvaluacionEstadoVehicular.listadoInformePorPlaca(this.session, this.placa);
            this.transaction.commit();

            RequestContext.getCurrentInstance().update("frmListaObjetos:tbl");

            return listaEvaluacionVehiciluar;

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
    
    public void limpiarPorPlaca() {
        this.placa = null;
        listarPorPlaca();
    }
    
    public List<EvaluacionEstadoVehicular> listarPorAuxliar() {
        this.session = null;
        this.transaction = null;

        try {
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaEvaluacionVehiciluar = daoEvaluacionEstadoVehicular.listadoInformePorAuxiliar(this.session, this.auxiliar);
            this.transaction.commit();

            RequestContext.getCurrentInstance().update("frmListaObjetos:tbl");

            return listaEvaluacionVehiciluar;

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
    
    public void limpiarPorAuxiliar() {
        this.auxiliar = null;
        listarPorAuxliar();
    }

    public String retornaNombrePropietario(int id) throws Exception {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            DaoPropietario daoPropietario = new DaoPropietario();
            this.propietario = daoPropietario.getByIdPropietario(this.session, id);
            this.transaction.commit();

            return propietario.getNombres() + " " + propietario.getApellidos();
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

    public String retornaTelefono(int id) throws Exception {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            DaoPropietario daoPropietario = new DaoPropietario();
            this.propietario = daoPropietario.getByIdPropietario(this.session, id);
            this.transaction.commit();

            return propietario.getTelefono();
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
    
    public String randomName() {
        int i = (int) (Math.random() * 10000);

        return String.valueOf(i);
    }
    //////////////////////////////////////////////////////////////////////////

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<EvaluacionEstadoVehicular> getListaEvaluacionVehiciluar() {
        return listaEvaluacionVehiciluar;
    }

    public void setListaEvaluacionVehiciluar(List<EvaluacionEstadoVehicular> listaEvaluacionVehiciluar) {
        this.listaEvaluacionVehiciluar = listaEvaluacionVehiciluar;
    }

    public List<EvaluacionEstadoVehicular> getListaEvaluacionVehiciluarFiltrada() {
        return listaEvaluacionVehiciluarFiltrada;
    }

    public void setListaEvaluacionVehiciluarFiltrada(List<EvaluacionEstadoVehicular> listaEvaluacionVehiciluarFiltrada) {
        this.listaEvaluacionVehiciluarFiltrada = listaEvaluacionVehiciluarFiltrada;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }
    
    

}
