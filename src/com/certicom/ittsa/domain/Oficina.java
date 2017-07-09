package com.certicom.ittsa.domain;

public class Oficina {
	
	private Integer idOficina;
	private Integer idAgencia;
	private String tipo;
	private String nombre;
	private String direccion;
	private String telefono2;
	private String telefono1;
	private String email;
	private String serieBoletoManual;
	private String ultimoNumero;
	private boolean estado;
	private String prefijo;
	private Integer ultimoGiroTel;
	private Integer idUsuarioAdm;
	private Integer esTerminal;
	private String desAgencia;
		
	
	public Oficina(){
		
	}


	public Integer getIdOficina() {
		return idOficina;
	}


	public Integer getIdAgencia() {
		return idAgencia;
	}


	public String getTipo() {
		return tipo;
	}


	public String getNombre() {
		return nombre;
	}


	public String getDireccion() {
		return direccion;
	}


	public String getTelefono2() {
		return telefono2;
	}


	public String getTelefono1() {
		return telefono1;
	}


	public String getEmail() {
		return email;
	}


	public String getSerieBoletoManual() {
		return serieBoletoManual;
	}


	public String getUltimoNumero() {
		return ultimoNumero;
	}


	public boolean isEstado() {
		return estado;
	}


	public String getPrefijo() {
		return prefijo;
	}


	public Integer getUltimoGiroTel() {
		return ultimoGiroTel;
	}


	public Integer getIdUsuarioAdm() {
		return idUsuarioAdm;
	}


	public Integer getEsTerminal() {
		return esTerminal;
	}


	public String getDesAgencia() {
		return desAgencia;
	}


	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}


	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo.toUpperCase();
	}


	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion.toUpperCase();
	}


	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2.toUpperCase();
	}


	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1.toUpperCase();
	}


	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}


	public void setSerieBoletoManual(String serieBoletoManual) {
		this.serieBoletoManual = serieBoletoManual.toUpperCase();
	}


	public void setUltimoNumero(String ultimoNumero) {
		this.ultimoNumero = ultimoNumero.toUpperCase();
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo.toUpperCase();
	}


	public void setUltimoGiroTel(Integer ultimoGiroTel) {
		this.ultimoGiroTel = ultimoGiroTel;
	}


	public void setIdUsuarioAdm(Integer idUsuarioAdm) {
		this.idUsuarioAdm = idUsuarioAdm;
	}


	public void setEsTerminal(Integer esTerminal) {
		this.esTerminal = esTerminal;
	}


	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia.toUpperCase();
	}
	


}
