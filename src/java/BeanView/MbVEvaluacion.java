/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoDaniosVehiculo;
import Dao.DaoEvaluacionEstadoVehicular;
import Dao.DaoObjetos;
import Dao.DaoObjetosVehiculos;
import Dao.DaoParteVehiculo;
import Dao.DaoPropietario;
import Dao.DaoTipoDanio;
import HibernateUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import pojo.DaniosVehiculo;
import pojo.EvaluacionEstadoVehicular;
import pojo.Objetos;
import pojo.ObjetosVehiculos;
import pojo.ParametroEvaluacion;
import pojo.ParteVehiculo;
import pojo.Propietario;
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVEvaluacion {

    /**
     * Creates a new instance of MbVEvaluacion
     */
    public EvaluacionEstadoVehicular evaluacionEstadoVehicular;
    public ObjetosVehiculos objetosVehiculos;
    public DaniosVehiculo daniosVehiculo;
    public Propietario propietario;
    public TipoVehiculo tipoVehiculo;
    public ParametroEvaluacion parametroEvaluacion;
    public ParteVehiculo parteVehiculo;
    public Objetos objetos;

    private List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicular;
    private List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicularFiltrada;

    private List<DaniosVehiculo> listaDaniosVehiculos;
    private List<ObjetosVehiculos> listaObjetosVehiculos;
    

    private Session session;
    private Transaction transaction;

    private Boolean flag;

    public MbVEvaluacion() {
        this.evaluacionEstadoVehicular = new EvaluacionEstadoVehicular();
        this.objetosVehiculos = new ObjetosVehiculos();
        this.daniosVehiculo = new DaniosVehiculo();
        this.propietario = new Propietario();
        this.tipoVehiculo = new TipoVehiculo();
        this.parametroEvaluacion = new ParametroEvaluacion();
        this.parteVehiculo = new ParteVehiculo();
        this.objetos = new Objetos();

        java.util.Date fecha = new Date();
        this.evaluacionEstadoVehicular.setFecha(fecha);

        this.evaluacionEstadoVehicular.setPoseeDanios(false);
        this.evaluacionEstadoVehicular.setPoseeObjetos(false);

        this.listaDaniosVehiculos = new ArrayList<>();
        this.listaObjetosVehiculos = new ArrayList<>();
        
        this.flag = false;

    }

    ////////////////////////////////////////////////////////////////////////////
    public void validarPlaca() {
        this.session = null;
        this.transaction = null;

        try {
            DaoPropietario daoPropietario = new DaoPropietario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.propietario = daoPropietario.getByPlaca(this.session, this.propietario.getPlaca());

            if (this.propietario != null) {
                this.tipoVehiculo.setVehiculo(this.propietario.getTipoVehiculo().getVehiculo());
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:panelRegistro");
                this.flag = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No se encontraron datos relacionados a esta placa"));
                this.propietario = new Propietario();
                this.tipoVehiculo = new TipoVehiculo();
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:panelRegistro");
                this.flag = false;
            }
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

    public void agregarListaDanios() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            if (this.parametroEvaluacion.getId() != -1 && this.parteVehiculo.getId() != -1) {

                DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
                DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();

                this.daniosVehiculo.setParametroEvaluacion(daoTipoDanio.getById(this.session, this.parametroEvaluacion.getId()));
                this.daniosVehiculo.setParteVehiculo(daoParteVehiculo.getById(this.session, this.parteVehiculo.getId()));
                
                java.util.Date fecha = new Date();
                this.listaDaniosVehiculos.add(new DaniosVehiculo(null, this.daniosVehiculo.getParametroEvaluacion(), this.daniosVehiculo.getParteVehiculo(), fecha, null));

                this.transaction.commit();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Daño agregado"));

                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");

                this.parametroEvaluacion.setId(-1);
                this.parteVehiculo.setId(-1);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un tipo de daño y una parte del vehiculo"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
            }
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void retirarListaDanios(ParametroEvaluacion pEvaluacion, ParteVehiculo pVehiculo) {
        try {
            int i = 0;

            for (DaniosVehiculo item : this.listaDaniosVehiculos) {
                if (item.getParametroEvaluacion().equals(pEvaluacion) && item.getParteVehiculo().equals(pVehiculo)) {
                    this.listaDaniosVehiculos.remove(i);

                    break;
                }

                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Correcto", "Daño retirado de la lista"));

            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaDaños");
            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divDaños");
            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }
    
    public void agregarListaObjetos() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            if (this.objetos.getId() != -1) {

                DaoObjetos daoObjetos = new DaoObjetos();

                this.objetosVehiculos.setObjetos(daoObjetos.getById(this.session, this.objetos.getId()));
                
                java.util.Date fecha = new Date();
                this.listaObjetosVehiculos.add(new ObjetosVehiculos(null, this.objetosVehiculos.getObjetos(), this.objetosVehiculos.getDescripcion(), null,fecha));

                this.transaction.commit();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Objeto agregado"));

                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaObjetos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divObejtos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");

                this.objetos.setId(-1);
                this.objetosVehiculos.setDescripcion("");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un tipo de daño y una parte del vehiculo"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaObjetos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divObejtos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
            }
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
    
    public void registrar()
    {
        this.session=null;
        this.transaction=null;
        
        try
        {
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaction=this.session.beginTransaction();
            
            if(this.propietario.getPlaca()!=null && this.flag == true) {
                
                DaoPropietario daoPropietario = new DaoPropietario();
                DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();
                DaoDaniosVehiculo daoDaniosVehiculo = new DaoDaniosVehiculo();
                DaoObjetosVehiculos daoObjetosVehiculos = new DaoObjetosVehiculos();
                java.util.Date fecha = new Date();
                
                this.propietario = daoPropietario.getByPlaca(this.session, this.propietario.getPlaca());
                this.evaluacionEstadoVehicular.setPropietario(this.propietario);
                this.evaluacionEstadoVehicular.setFecha(fecha);
                this.evaluacionEstadoVehicular.setHora(fecha);
                this.evaluacionEstadoVehicular.setPlaca(this.propietario.getPlaca());
                
                HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                this.evaluacionEstadoVehicular.setUsuario(httpSession.getAttribute("usuario").toString());
                
                daoEvaluacionEstadoVehicular.register(this.evaluacionEstadoVehicular, this.session);
                this.evaluacionEstadoVehicular = daoEvaluacionEstadoVehicular.getUltimoRegistro(this.session);
                
                for(DaniosVehiculo item : this.listaDaniosVehiculos)
                {
                    item.setEvaluacionEstadoVehicular(this.evaluacionEstadoVehicular);
                    daoDaniosVehiculo.register(this.session, item);
                }
                
                for(ObjetosVehiculos item : this.listaObjetosVehiculos)
                {
                    item.setEvaluacionEstadoVehicular(this.evaluacionEstadoVehicular);
                    daoObjetosVehiculos.register(this.session, item);
                }
                
                this.transaction.commit();
                
                this.evaluacionEstadoVehicular = new EvaluacionEstadoVehicular();
                this.objetosVehiculos = new ObjetosVehiculos();
                this.daniosVehiculo = new DaniosVehiculo();
                this.propietario = new Propietario();
                this.tipoVehiculo = new TipoVehiculo();
                this.parametroEvaluacion = new ParametroEvaluacion();
                this.parteVehiculo = new ParteVehiculo();
                this.objetos = new Objetos();
                this.evaluacionEstadoVehicular.setPoseeDanios(false);
                this.evaluacionEstadoVehicular.setPoseeObjetos(false);
                this.listaDaniosVehiculos = new ArrayList<>();
                this.listaObjetosVehiculos = new ArrayList<>();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Evaluacion realizada correctamente"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe validar la placa del vehiculo"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
            }
                
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
        
    }
    
    public List<EvaluacionEstadoVehicular> getall(){
        
        this.session = null;
        this.transaction = null;
        
        try
        {
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            java.util.Date fecha = new Date();
            
            
            this.listaEvaluacionEstadoVehicular = daoEvaluacionEstadoVehicular.getall(this.session,fecha,httpSession.getAttribute("usuario").toString());
            this.transaction.commit();
            
            return listaEvaluacionEstadoVehicular;
            
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
    
    public void mosrarDetalle(int Id)
    {
        this.session = null;
        this.transaction = null;

        try {

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            DaoPropietario daoPropietario = new DaoPropietario();
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();
            DaoDaniosVehiculo daoDaniosVehiculo = new DaoDaniosVehiculo();
            DaoObjetosVehiculos daoObjetosVehiculos = new DaoObjetosVehiculos();
            
            this.evaluacionEstadoVehicular = daoEvaluacionEstadoVehicular.getById(this.session, Id);
            this.propietario = daoPropietario.getByIdPropietario(this.session, this.evaluacionEstadoVehicular.getPropietario().getId());
            this.tipoVehiculo.setVehiculo(this.propietario.getTipoVehiculo().getVehiculo());
            
            this.listaDaniosVehiculos = daoDaniosVehiculo.getAllByIdEvaluacion(this.session,Id);
            this.listaObjetosVehiculos = daoObjetosVehiculos.getAllByIdEvaluacion(this.session, Id);

            RequestContext.getCurrentInstance().update("frmDetalle:panelDetalle");
            RequestContext.getCurrentInstance().update("frmDetalle:panelListaDanios");
            RequestContext.getCurrentInstance().update("frmDetalle:panelListaObjetos");
            RequestContext.getCurrentInstance().execute("PF('dialogoDetalle').show()");
            
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
    
    public String retornaNombreDanio(int id) throws Exception{
        
        this.session = null;
        this.transaction = null;
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            this.parametroEvaluacion = daoTipoDanio.getById(this.session, id); 
            this.transaction.commit();
            return parametroEvaluacion.getNombre();
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
    
    public String retornaNombreParteVehiculo(int id) throws Exception{
        
        this.session = null;
        this.transaction = null;
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            this.parteVehiculo = daoParteVehiculo.getById(this.session, id); 
            this.transaction.commit();
            return parteVehiculo.getNombre();
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
    
    public String retornaNombreObejto(int id) throws Exception{
        
        this.session = null;
        this.transaction = null;
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoObjetos daoObjetos = new DaoObjetos();
            this.objetos = daoObjetos.getById(this.session, id); 
            this.transaction.commit();
            return objetos.getNombre();
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
    ////////////////////////////////////////////////////////////////////////////

    public EvaluacionEstadoVehicular getEvaluacionEstadoVehicular() {
        return evaluacionEstadoVehicular;
    }

    public void setEvaluacionEstadoVehicular(EvaluacionEstadoVehicular evaluacionEstadoVehicular) {
        this.evaluacionEstadoVehicular = evaluacionEstadoVehicular;
    }

    public ObjetosVehiculos getObjetosVehiculos() {
        return objetosVehiculos;
    }

    public void setObjetosVehiculos(ObjetosVehiculos objetosVehiculos) {
        this.objetosVehiculos = objetosVehiculos;
    }

    public DaniosVehiculo getDaniosVehiculo() {
        return daniosVehiculo;
    }

    public void setDaniosVehiculo(DaniosVehiculo daniosVehiculo) {
        this.daniosVehiculo = daniosVehiculo;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<EvaluacionEstadoVehicular> getListaEvaluacionEstadoVehicular() {
        return listaEvaluacionEstadoVehicular;
    }

    public void setListaEvaluacionEstadoVehicular(List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicular) {
        this.listaEvaluacionEstadoVehicular = listaEvaluacionEstadoVehicular;
    }

    public List<EvaluacionEstadoVehicular> getListaEvaluacionEstadoVehicularFiltrada() {
        return listaEvaluacionEstadoVehicularFiltrada;
    }

    public void setListaEvaluacionEstadoVehicularFiltrada(List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicularFiltrada) {
        this.listaEvaluacionEstadoVehicularFiltrada = listaEvaluacionEstadoVehicularFiltrada;
    }

    public List<DaniosVehiculo> getListaDaniosVehiculos() {
        return listaDaniosVehiculos;
    }

    public void setListaDaniosVehiculos(List<DaniosVehiculo> listaDaniosVehiculos) {
        this.listaDaniosVehiculos = listaDaniosVehiculos;
    }

    public ParametroEvaluacion getParametroEvaluacion() {
        return parametroEvaluacion;
    }

    public void setParametroEvaluacion(ParametroEvaluacion parametroEvaluacion) {
        this.parametroEvaluacion = parametroEvaluacion;
    }

    public ParteVehiculo getParteVehiculo() {
        return parteVehiculo;
    }

    public void setParteVehiculo(ParteVehiculo parteVehiculo) {
        this.parteVehiculo = parteVehiculo;
    }

    public Objetos getObjetos() {
        return objetos;
    }

    public void setObjetos(Objetos objetos) {
        this.objetos = objetos;
    }

    public List<ObjetosVehiculos> getListaObjetosVehiculos() {
        return listaObjetosVehiculos;
    }

    public void setListaObjetosVehiculos(List<ObjetosVehiculos> listaObjetosVehiculos) {
        this.listaObjetosVehiculos = listaObjetosVehiculos;
    }

    
}
