/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceTipoDanio;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.ParametroEvaluacion;

/**
 *
 * @author Giovanni
 */
public class DaoTipoDanio implements InterfaceTipoDanio{

    @Override
    public List<ParametroEvaluacion> getAll(Session session) throws Exception {
         String hql = "from ParametroEvaluacion where id !=-1";
        Query query = session.createQuery(hql);
        
        List<ParametroEvaluacion> listaParametroEvaluacions = (List<ParametroEvaluacion>) query.list();
        
        return listaParametroEvaluacions;
    }
    
    @Override
    public List<ParametroEvaluacion> cargarSelect(Session session) throws Exception {
         String hql = "from ParametroEvaluacion";
        Query query = session.createQuery(hql);
        
        List<ParametroEvaluacion> listaParametroEvaluacions = (List<ParametroEvaluacion>) query.list();
        
        return listaParametroEvaluacions;
    }

    @Override
    public ParametroEvaluacion getById(Session session, Integer id) throws Exception {
        return (ParametroEvaluacion) session.get(ParametroEvaluacion.class, id);
    }

    @Override
    public boolean register(ParametroEvaluacion parametroEvaluacion, Session session) throws Exception {
        session.save(parametroEvaluacion);
        
        return true;
    }

    @Override
    public boolean update(ParametroEvaluacion parametroEvaluacion, Session session) throws Exception {
        session.update(parametroEvaluacion);
        
        return true;
    }

    @Override
    public boolean delete(ParametroEvaluacion parametroEvaluacion, Session session) throws Exception {
        session.delete(parametroEvaluacion);
        
        return true;
    }
    
}
