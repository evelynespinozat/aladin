package com.certicom.ittsa.domain;

public class Personal {
	
	private Integer idPersonal;
	private String appaterno;
	private String apmaterno;
	private String nombres;
	private String dni;
	private String tipo;
	
	private boolean estado;
	
	private String nomcompleto;
	
	public Integer getIdPersonal() {
		return idPersonal;
	}
	public String getAppaterno() {
		return appaterno;
	}
	public String getApmaterno() {
		return apmaterno;
	}
	public String getNombres() {
		return nombres;
	}
	public String getDni() {
		return dni;
	}
	public String getTipo() {
		return tipo;
	}
	public void setIdPersonal(Integer idPersonal) {
		this.idPersonal = idPersonal;
	}
	public void setAppaterno(String appaterno) {
		this.appaterno = appaterno;
	}
	public void setApmaterno(String apmaterno) {
		this.apmaterno = apmaterno;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getNomcompleto() {
		return  nomcompleto = getAppaterno() +" " + getApmaterno() + " " + getNombres();
	}
	public void setNomcompleto(String nomcompleto) {
		this.nomcompleto = nomcompleto;
	}

	
	
	
	
	
}
