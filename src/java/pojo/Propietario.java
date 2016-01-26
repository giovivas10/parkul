package pojo;
// Generated 26/01/2016 12:27:20 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Propietario generated by hbm2java
 */
public class Propietario  implements java.io.Serializable {


     private Integer id;
     private TipoUsuario tipoUsuario;
     private TipoVehiculo tipoVehiculo;
     private String documento;
     private String nombres;
     private String apellidos;
     private String telefono;
     private String email;
     private String placa;
     private byte[] fotoPropietario;
     private byte[] fotoVehiculo;
     private Set evaluacionEstadoVehiculars = new HashSet(0);

    public Propietario() {
    }

	
    public Propietario(String documento, String nombres, String apellidos, String telefono, String email, String placa) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.placa = placa;
    }
    public Propietario(TipoUsuario tipoUsuario, TipoVehiculo tipoVehiculo, String documento, String nombres, String apellidos, String telefono, String email, String placa, byte[] fotoPropietario, byte[] fotoVehiculo, Set evaluacionEstadoVehiculars) {
       this.tipoUsuario = tipoUsuario;
       this.tipoVehiculo = tipoVehiculo;
       this.documento = documento;
       this.nombres = nombres;
       this.apellidos = apellidos;
       this.telefono = telefono;
       this.email = email;
       this.placa = placa;
       this.fotoPropietario = fotoPropietario;
       this.fotoVehiculo = fotoVehiculo;
       this.evaluacionEstadoVehiculars = evaluacionEstadoVehiculars;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public TipoUsuario getTipoUsuario() {
        return this.tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public TipoVehiculo getTipoVehiculo() {
        return this.tipoVehiculo;
    }
    
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    public String getDocumento() {
        return this.documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getNombres() {
        return this.nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPlaca() {
        return this.placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public byte[] getFotoPropietario() {
        return this.fotoPropietario;
    }
    
    public void setFotoPropietario(byte[] fotoPropietario) {
        this.fotoPropietario = fotoPropietario;
    }
    public byte[] getFotoVehiculo() {
        return this.fotoVehiculo;
    }
    
    public void setFotoVehiculo(byte[] fotoVehiculo) {
        this.fotoVehiculo = fotoVehiculo;
    }
    public Set getEvaluacionEstadoVehiculars() {
        return this.evaluacionEstadoVehiculars;
    }
    
    public void setEvaluacionEstadoVehiculars(Set evaluacionEstadoVehiculars) {
        this.evaluacionEstadoVehiculars = evaluacionEstadoVehiculars;
    }




}


