/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanRequest;

import Dao.DaoTipoUsuario;
import Dao.DaoTipoVehiculo;
import HibernateUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.TipoUsuario;
import pojo.TipoVehiculo;

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

    public MbRCargarSelects() {
        this.tipoUsuario = new TipoUsuario();
        this.tipoVehiculo = new TipoVehiculo();
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

            List<TipoUsuario> lTipoUsuario = daoTipoUsuario.getAll(this.session);
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

            List<TipoVehiculo> lTipoVehiculo = daoTipoVehiculo.getAll(this.session);
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

}
