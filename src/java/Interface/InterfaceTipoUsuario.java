/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.TipoUsuario;

/**
 *
 * @author Giovanni
 */
public interface InterfaceTipoUsuario {
    public List<TipoUsuario> getAll(Session session) throws Exception;
    public List<TipoUsuario> cargarSelect(Session session) throws Exception;
    public TipoUsuario getById(Session session, Integer id) throws Exception;
    public boolean register(TipoUsuario objetos, Session session) throws Exception;
    public boolean update(TipoUsuario objetos,Session session) throws Exception;
    public boolean delete(TipoUsuario objetos,Session session) throws Exception;
}
