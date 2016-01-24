/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hibernate.Session;
import pojo.Usuario;

/**
 *
 * @author Giovanni
 */
public interface InterfaceUsuario {
   public boolean register(Usuario usuario,Session session ) throws Exception, ConstraintViolationException;
   public List<Usuario> getAll(Session session) throws Exception;
   public Usuario getByCodigoUsuario(Session session,String documento) throws Exception;
   public boolean update(Session session,Usuario usuario) throws Exception;
   public Usuario getByCorreoElectronico(Session session, String correoElectronico) throws Exception;
   public Usuario getByNombreUsuario(Session session, String usuario) throws Exception;
}
