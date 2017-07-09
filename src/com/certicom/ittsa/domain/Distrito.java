package com.certicom.ittsa.domain;

public class Distrito {
	
	private Integer idDistrito;
	private String descripcion;
	private Boolean estado;
	private Integer idAgencia;

	
	public Distrito(){
		
	}


	public Integer getIdDistrito() {
		return idDistrito;
	}


	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Boolean getEstado() {
		return estado;
	}


	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


	public Integer getIdAgencia() {
		return idAgencia;
	}


	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
	
	
	
	

}
