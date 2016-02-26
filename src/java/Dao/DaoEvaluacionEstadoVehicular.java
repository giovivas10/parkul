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

    @Override
    public List<EvaluacionEstadoVehicular> listadoInforme(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        String hql="from EvaluacionEstadoVehicular where fecha BETWEEN :fechaInicio AND :fechaFin";
        Query query = session.createQuery(hql);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        
        List<EvaluacionEstadoVehicular> listaEvaluacion =  query.list();
        
        return listaEvaluacion;
    }

    @Override
    public List<EvaluacionEstadoVehicular> listadoInformePorPlaca(Session session, String placa) throws Exception {
        String hql="from EvaluacionEstadoVehicular where placa=:placa";
        Query query = session.createQuery(hql);
        query.setParameter("placa", placa);
        
        List<EvaluacionEstadoVehicular> listaEvaluacion =  query.list();
        
        return listaEvaluacion;
    }

    @Override
    public List<EvaluacionEstadoVehicular> listadoInformePorAuxiliar(Session session, String auxiliar) throws Exception {
        String hql="from EvaluacionEstadoVehicular where usuario=:auxiliar";
        Query query = session.createQuery(hql);
        query.setParameter("auxiliar", auxiliar);
        
        List<EvaluacionEstadoVehicular> listaEvaluacion =  query.list();
        
        return listaEvaluacion;
    }
    
    
}
