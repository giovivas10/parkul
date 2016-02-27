/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanRequest;

import Dao.DaoObjetos;
import Dao.DaoParteVehiculo;
import Dao.DaoTipoDanio;
import Dao.DaoTipoUsuario;
import Dao.DaoTipoVehiculo;
import Dao.DaoUsuario;
import HibernateUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Objetos;
import pojo.ParametroEvaluacion;
import pojo.ParteVehiculo;
import pojo.TipoUsuario;
import pojo.TipoVehiculo;
import pojo.Usuario;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@RequestScoped
public class MbRCargarSelects {

    /**
     * Creates a new instance of MbVTipoUsuario
     */
    private Session session;
    private Transaction transaction;

    private List<SelectItem> listaTipoUsuario;
    private TipoUsuario tipoUsuario;
    
    private List<SelectItem> listaTipoVehiculo;
    private TipoVehiculo tipoVehiculo;
    
    private List<SelectItem> listaTipoDanio;
    private ParametroEvaluacion tipoDanio;
    
    private List<SelectItem> listaParteVehiculo;
    private ParteVehiculo parteVehiculo;
    
    private List<SelectItem> listaObjetos;
    private Objetos objetos;
    
    private List<SelectItem> listaUsuarios;
    private Usuario usuario;

    public MbRCargarSelects() {
        this.tipoUsuario = new TipoUsuario();
        this.tipoVehiculo = new TipoVehiculo();
        this.tipoDanio = new ParametroEvaluacion();
        this.parteVehiculo = new ParteVehiculo();
        this.objetos = new Objetos();
    }

    public List<SelectItem> getListaTipoUsuario() throws Exception {
        this.session = null;
        this.transaction = null;

        try 
        {
            this.listaTipoUsuario = new ArrayList<>();
            DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<TipoUsuario> lTipoUsuario = daoTipoUsuario.cargarSelect(this.session);
            this.transaction.commit();
            
            for (TipoUsuario tUsuario : lTipoUsuario) {
                SelectItem tUsuarioItem = new SelectItem(tUsuario.getId(),tUsuario.getNombre());
                this.listaTipoUsuario.add(tUsuarioItem);
            }
            
            return listaTipoUsuario;
        } 
        catch (Exception ex) 
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
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
    
    public List<SelectItem> getListaTipoVehiculo() {
        this.session = null;
        this.transaction = null;

        try 
        {
            this.listaTipoVehiculo = new ArrayList<>();
            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<TipoVehiculo> lTipoVehiculo = daoTipoVehiculo.cargarSelect(this.session);
            this.transaction.commit();
            
            for (TipoVehiculo tVehiculo : lTipoVehiculo) {
                SelectItem tVehiculoItem = new SelectItem(tVehiculo.getId(),tVehiculo.getVehiculo());
                this.listaTipoVehiculo.add(tVehiculoItem);
            }
            
            return listaTipoVehiculo;
        } 
        catch (Exception ex) 
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
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
    
    public List<SelectItem> getListaTipoDanio() {
        this.session = null;
        this.transaction = null;

        try 
        {
            this.listaTipoDanio = new ArrayList<>();
            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<ParametroEvaluacion> lista = daoTipoDanio.cargarSelect(this.session);
            this.transaction.commit();
            
            for (ParametroEvaluacion var : lista) {
                SelectItem items = new SelectItem(var.getId(),var.getNombre());
                this.listaTipoDanio.add(items);
            }
            
            return listaTipoDanio;
        } 
        catch (Exception ex) 
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
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
    
    public List<SelectItem> getListaParteVehiculo() {
        this.session = null;
        this.transaction = null;

        try 
        {
            this.listaParteVehiculo = new ArrayList<>();
            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<ParteVehiculo> lista = daoParteVehiculo.cargarSelect(this.session);
            this.transaction.commit();
            
            for (ParteVehiculo var : lista) {
                SelectItem items = new SelectItem(var.getId(),var.getNombre());
                this.listaParteVehiculo.add(items);
            }
            
            return listaParteVehiculo;
        } 
        catch (Exception ex) 
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
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
    
    public List<SelectItem> getListaObjetos() {
        this.session = null;
        this.transaction = null;

        try 
        {
            this.listaObjetos = new ArrayList<>();
            DaoObjetos daoObjetos = new DaoObjetos();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<Objetos> lista = daoObjetos.cargarSelect(this.session);
            this.transaction.commit();
            
            for (Objetos var : lista) {
                SelectItem items = new SelectItem(var.getId(),var.getNombre());
                this.listaObjetos.add(items);
            }
            
            return listaObjetos;
        } 
        catch (Exception ex) 
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
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
    
    public List<SelectItem> getListaUsuarios() {
        this.session = null;
        this.transaction = null;

        try 
        {
            this.listaUsuarios = new ArrayList<>();
            DaoUsuario daoUsuario = new DaoUsuario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<Usuario> lista = daoUsuario.getAll(this.session);
            this.transaction.commit();
            
            SelectItem items = new SelectItem("-1","Seleccione...");
            this.listaUsuarios.add(items);
            
            for (Usuario var : lista) {
                items = new SelectItem(var.getUsuario(),var.getNombre());
                this.listaUsuarios.add(items);
            }
            
            return listaUsuarios;
        } 
        catch (Exception ex) 
        {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
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
    
    ///////////////////////////////////////////////////////////////////////////////////////
    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setListaTipoUsuario(List<SelectItem> listaTipoUsuario) {
        this.listaTipoUsuario = listaTipoUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setListaTipoVehiculo(List<SelectItem> listaTipoVehiculo) {
        this.listaTipoVehiculo = listaTipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public void setListaTipoDanio(List<SelectItem> listaTipoDanio) {
        this.listaTipoDanio = listaTipoDanio;
    }

    public ParametroEvaluacion getTipoDanio() {
        return tipoDanio;
    }

    public void setTipoDanio(ParametroEvaluacion tipoDanio) {
        this.tipoDanio = tipoDanio;
    }

    public void setListaParteVehiculo(List<SelectItem> listaParteVehiculo) {
        this.listaParteVehiculo = listaParteVehiculo;
    }

    public ParteVehiculo getParteVehiculo() {
        return parteVehiculo;
    }

    public void setParteVehiculo(ParteVehiculo parteVehiculo) {
        this.parteVehiculo = parteVehiculo;
    }

    public void setListaObjetos(List<SelectItem> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public Objetos getObjetos() {
        return objetos;
    }

    public void setObjetos(Objetos objetos) {
        this.objetos = objetos;
    }

    

    public void setListaUsuarios(List<SelectItem> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
 }
