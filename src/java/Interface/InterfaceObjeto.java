/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.Objetos;

/**
 *
 * @author Giovanni
 */
public interface InterfaceObjeto {
    public boolean register(Objetos objetos, Session session) throws Exception;
    public List<Objetos> getAll(Session session)throws Exception;
    public Objetos getById(Session session, Integer id) throws Exception;
    public boolean update(Objetos objetos,Session session) throws Exception;
    public boolean delete(Objetos objetos,Session session) throws Exception;
}
