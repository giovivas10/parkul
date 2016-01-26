package pojo;
// Generated 26/01/2016 12:27:20 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * ParteVehiculo generated by hbm2java
 */
public class ParteVehiculo  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private Set daniosVehiculos = new HashSet(0);

    public ParteVehiculo() {
    }

	
    public ParteVehiculo(String nombre) {
        this.nombre = nombre;
    }
    public ParteVehiculo(String nombre, Set daniosVehiculos) {
       this.nombre = nombre;
       this.daniosVehiculos = daniosVehiculos;
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
    public Set getDaniosVehiculos() {
        return this.daniosVehiculos;
    }
    
    public void setDaniosVehiculos(Set daniosVehiculos) {
        this.daniosVehiculos = daniosVehiculos;
    }




}


