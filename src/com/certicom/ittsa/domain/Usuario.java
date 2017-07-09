package com.certicom.ittsa.domain;

import java.util.Date;
import java.util.List;


public class Usuario implements java.io.Serializable {
	
    private static final long serialVersionUID = 1L;
	public int idusuario;
	private String dni;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String login;
    private String password;
    private String email;
    private String direccion;
    private Boolean estado;
    private Date fecha_acceso;

    
    private Date fecha_registro;
    private Date fecha_mod;
    private int cod_perfil;
    private Boolean es_nuevo;
    
    private Date fecha_cambio_password;
    private String fecha_cambio_pass;
    
    /*Agencia y oficina*/
    private int idagencia;
    private String descagencia;
    private int idoficina;
    private String descoficina;
    private int idpuntoventa;
    private String descpventa;
    private int idtipodoc;
    
    
    /*Para ONP*/
    
    private String nombre_completo;
    private String nombre_centro_atencion;
    private int nro_impresiones;
    private int nro_consultas;
    private List<DNI> listDNI;
    
    public Usuario() {
	}

	public Usuario(Integer idusuario) {
		this.idusuario = idusuario;
	}
	
	public Usuario(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getNombre_completo() {
		nombre_completo = getNombre() + " " + getApellido_paterno() + " "+getApellido_materno();
		return nombre_completo;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public String getApellido_paterno() {
		return apellido_paterno;
	}

	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno.toUpperCase();
	}

	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno.toUpperCase();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFecha_acceso() {
		return fecha_acceso;
	}

	public void setFecha_acceso(Date fecha_acceso) {
		this.fecha_acceso = fecha_acceso;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Date getFecha_mod() {
		return fecha_mod;
	}

	public void setFecha_mod(Date fecha_mod) {
		this.fecha_mod = fecha_mod;
	}

	public int getCod_perfil() {
		return cod_perfil;
	}

	public void setCod_perfil(int cod_perfil) {
		this.cod_perfil = cod_perfil;
	}

	public Boolean getEs_nuevo() {
		return es_nuevo;
	}

	public void setEs_nuevo(Boolean es_nuevo) {
		this.es_nuevo = es_nuevo;
	}

	public Date getFecha_cambio_password() {
		return fecha_cambio_password;
	}

	public void setFecha_cambio_password(Date fecha_cambio_password) {
		this.fecha_cambio_password = fecha_cambio_password;
	}

	public String getFecha_cambio_pass() {
		return fecha_cambio_pass;
	}

	public void setFecha_cambio_pass(String fecha_cambio_pass) {
		this.fecha_cambio_pass = fecha_cambio_pass;
	}

	public int getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(int idagencia) {
		this.idagencia = idagencia;
	}

	public String getDescagencia() {
		return descagencia;
	}

	public void setDescagencia(String descagencia) {
		this.descagencia = descagencia;
	}

	public int getIdoficina() {
		return idoficina;
	}

	public void setIdoficina(int idoficina) {
		this.idoficina = idoficina;
	}

	public String getDescoficina() {
		return descoficina;
	}

	public void setDescoficina(String descoficina) {
		this.descoficina = descoficina;
	}

	public String getDescpventa() {
		return descpventa;
	}

	public void setDescpventa(String descpventa) {
		this.descpventa = descpventa;
	}

	public int getIdtipodoc() {
		return idtipodoc;
	}

	public void setIdtipodoc(int idtipodoc) {
		this.idtipodoc = idtipodoc;
	}

	public String getNombre_centro_atencion() {
		return nombre_centro_atencion;
	}

	public void setNombre_centro_atencion(String nombre_centro_atencion) {
		this.nombre_centro_atencion = nombre_centro_atencion;
	}

	public int getNro_impresiones() {
		return nro_impresiones;
	}

	public void setNro_impresiones(int nro_impresiones) {
		this.nro_impresiones = nro_impresiones;
	}

	public int getNro_consultas() {
		return nro_consultas;
	}

	public void setNro_consultas(int nro_consultas) {
		this.nro_consultas = nro_consultas;
	}

	public List<DNI> getListDNI() {
		return listDNI;
	}

	public void setListDNI(List<DNI> listDNI) {
		this.listDNI = listDNI;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}
	/*public String getNombre_completo() {
		String nomb = getNombre() + " " + getApellido_paterno() + " "+getApellido_materno(); 
		return nomb;
	}*/

	public int getIdpuntoventa() {
		return idpuntoventa;
	}

	public void setIdpuntoventa(int idpuntoventa) {
		this.idpuntoventa = idpuntoventa;
	}
	
   
   
}
