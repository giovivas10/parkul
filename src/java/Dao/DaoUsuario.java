/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import HibernateUtil.HibernateUtil;
import Interface.InterfaceUsuario;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Usuario;

/**
 *
 * @author Giovanni
 */
public class DaoUsuario implements InterfaceUsuario{
    //private Session session;

    @Override
    public boolean register(Usuario usuario, Session session) throws Exception, ConstraintViolationException {
        //session = HibernateUtil.getSessionFactory().openSession();
        //Transaction transaction = session.beginTransaction();
        session.save(usuario);
        //transaction.commit();
        //session.close();
        
        return true;
    }

    @Override
    public List<Usuario> getAll(Session session) throws Exception {
        String hql = "from Usuario";
        Query query = session.createQuery(hql);
        
        List<Usuario> listaUsuario = (List<Usuario>) query.list();
        
        return listaUsuario;
    }

    @Override
    public Usuario getByIdUsuario(Session session,int id) throws Exception {
        /*String hql = "from Usuario where id:=id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        
        Usuario usuario = (Usuario) query.uniqueResult();
        
        return usuario;*/
        
        return (Usuario) session.get(Usuario.class, id);
    }

    @Override
    public boolean update(Session session,Usuario usuario) throws Exception {
        session.update(usuario);
        
        return true;
    }
    
    @Override
    public boolean delete(Session session,Usuario usuario) throws Exception{
        session.delete(usuario);
        
        return true;
    }
    
    @Override
    public Usuario getByCorreoElectronico(Session session,String correoElectronico) throws Exception {
        String hql = "from Usuario where email=:correoElectronico";
        Query query = session.createQuery(hql);
        query.setParameter("correoElectronico", correoElectronico);
        
        Usuario usuario = (Usuario) query.uniqueResult();
        
        return usuario;
    }
    
    @Override
    public Usuario getByNombreUsuario(Session session,String usuario) throws Exception {
        String hql = "from Usuario where usuario=:usser";
        Query query = session.createQuery(hql);
        query.setParameter("usser", usuario);
        
        Usuario usuarioP = (Usuario) query.uniqueResult();
        
        return usuarioP;
    }
    
}
