package pojo;
// Generated 4/03/2016 10:14:53 AM by Hibernate Tools 4.3.1



/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private Integer id;
     private Rol rol;
     private String usuario;
     private String nombre;
     private String documento;
     private String contrasenia;
     private String email;
     private String telefono;
     private String direccion;
     private Boolean estado;
     private String foto;

    public Usuario() {
    }

	
    public Usuario(String usuario, String nombre, String documento, String contrasenia, String email, String telefono, String direccion) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.documento = documento;
        this.contrasenia = contrasenia;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    public Usuario(Rol rol, String usuario, String nombre, String documento, String contrasenia, String email, String telefono, String direccion, Boolean estado, String foto) {
       this.rol = rol;
       this.usuario = usuario;
       this.nombre = nombre;
       this.documento = documento;
       this.contrasenia = contrasenia;
       this.email = email;
       this.telefono = telefono;
       this.direccion = direccion;
       this.estado = estado;
       this.foto = foto;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Rol getRol() {
        return this.rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDocumento() {
        return this.documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public String getFoto() {
        return this.foto;
    }
    
    public void setFoto(String foto) {
        this.foto = foto;
    }




}


