/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.Propietario;

/**
 *
 * @author Giovanni
 */
public interface InterfacePropietario {
    public boolean register(Propietario propietario,Session session ) throws Exception;
    public List<Propietario> getAll(Session session) throws Exception;
    public Propietario getByIdPropietario(Session session,int id) throws Exception;
    public Propietario getByPlaca(Session session,String Placa) throws Exception;
    public boolean update(Propietario propietario,Session session ) throws Exception;
    public boolean delete(Propietario propietario,Session session ) throws Exception;
}
