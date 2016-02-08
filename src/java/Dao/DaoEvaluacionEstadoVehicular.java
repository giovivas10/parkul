/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceEvaluacionEstadoVehicular;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.EvaluacionEstadoVehicular;

/**
 *
 * @author Giovanni
 */
public class DaoEvaluacionEstadoVehicular implements InterfaceEvaluacionEstadoVehicular{

    @Override
    public boolean register(EvaluacionEstadoVehicular estadoVehicular, Session session) throws Exception {
        session.save(estadoVehicular);
        
        return true;
    }

    @Override
    public EvaluacionEstadoVehicular getUltimoRegistro(Session session) throws Exception {
        String hql="from EvaluacionEstadoVehicular order by id desc";
        Query query=session.createQuery(hql).setMaxResults(1);
        
        return (EvaluacionEstadoVehicular) query.uniqueResult();
    }

    @Override
    public List<EvaluacionEstadoVehicular> getall(Session session, Date fechaDia, String usser) throws Exception {
        String hql="from EvaluacionEstadoVehicular where usuario=:usser and fecha=:fechaDia";
        Query query = session.createQuery(hql);
        query.setParameter("fechaDia", fechaDia);
        query.setParameter("usser", usser);
        
        List<EvaluacionEstadoVehicular> listaEvaluacion =  query.list();
        
        return listaEvaluacion;
        
    }

    @Override
    public EvaluacionEstadoVehicular getById(Session session, int Id) throws Exception {
        return (EvaluacionEstadoVehicular) session.get(EvaluacionEstadoVehicular.class, Id);
    }
    
    
}
