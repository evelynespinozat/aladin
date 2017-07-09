package com.certicom.ittsa.domain;

import java.util.Date;

public class Persona {

	private String Dni;
	private String tipodocumento;
	private String APaterno;
	private String AMaterno;
	private String Nombres;
	private String sexo;
	private String Nacimiento;
	private String Ubigeo;
	private String Grupo;
	private String Digito;
	private String Dato;
	private String Verificador;
	private String email;
	private String frecuente;
	//INICIO PISCOYA
	private String telefonoMovil;
	private Date fNacimiento;
	//FIN PISCOYA
	private Integer edad = 0;
	private Date fnac = new Date();
	private Boolean ubicado = Boolean.FALSE;

	/***** Para encomiendas ******/
	private String tipoPersona;
	private String apellidos;
	private Empresa empresa;
	private String telefono;
	private String direccion;
	private Integer idtipodoc;
	private boolean flgbuscado;
	
	private String trama;
	private byte[] imagen;

	public Persona() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return Dni;
	}

	public void setDni(String dni) {
		Dni = dni;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getAPaterno() {
		return APaterno;
	}

	public void setAPaterno(String aPaterno) {
		APaterno = aPaterno;
	}

	public String getAMaterno() {
		return AMaterno;
	}

	public void setAMaterno(String aMaterno) {
		AMaterno = aMaterno;
	}

	public String getNombres() {
		return Nombres;
	}

	public void setNombres(String nombres) {
		Nombres = nombres;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getUbigeo() {
		return Ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		Ubigeo = ubigeo;
	}

	public String getGrupo() {
		return Grupo;
	}

	public void setGrupo(String grupo) {
		Grupo = grupo;
	}

	public String getDigito() {
		return Digito;
	}

	public void setDigito(String digito) {
		Digito = digito;
	}

	public String getDato() {
		return Dato;
	}

	public void setDato(String dato) {
		Dato = dato;
	}

	public String getVerificador() {
		return Verificador;
	}

	public void setVerificador(String verificador) {
		Verificador = verificador;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getIdtipodoc() {
		return idtipodoc;
	}

	public void setIdtipodoc(Integer idtipodoc) {
		this.idtipodoc = idtipodoc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getNacimiento() {
		return Nacimiento;
	}

	public void setNacimiento(String nacimiento) {
		Nacimiento = nacimiento;
	}

	public Date getFnac() {
		return fnac;
	}

	public void setFnac(Date fnac) {
		this.fnac = fnac;
	}

	public Boolean getUbicado() {
		return ubicado;
	}

	public void setUbicado(Boolean ubicado) {
		this.ubicado = ubicado;
	}

	public String getTrama() {
		return trama;
	}

	public void setTrama(String trama) {
		this.trama = trama;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getFrecuente() {
		return frecuente;
	}

	public void setFrecuente(String frecuente) {
		this.frecuente = frecuente;
	}

	public boolean isFlgbuscado() {
		return flgbuscado;
	}

	public void setFlgbuscado(boolean flgbuscado) {
		this.flgbuscado = flgbuscado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public Date getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(Date fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	
}
