package pojo;
// Generated 7/02/2016 12:30:40 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoUsuario generated by hbm2java
 */
public class TipoUsuario  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String descripcion;
     private Set propietarios = new HashSet(0);

    public TipoUsuario() {
    }

	
    public TipoUsuario(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public TipoUsuario(String nombre, String descripcion, Set propietarios) {
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.propietarios = propietarios;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getPropietarios() {
        return this.propietarios;
    }
    
    public void setPropietarios(Set propietarios) {
        this.propietarios = propietarios;
    }




}


