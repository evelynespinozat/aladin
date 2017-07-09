package com.certicom.ittsa.domain;

public class Automotor {
	
	private Integer idAutomotor;
	private String descripcion;
	private boolean estado;
	
	public Automotor(){
	}

	public Integer getIdAutomotor() {
		return idAutomotor;
	}

	public void setIdAutomotor(Integer idAutomotor) {
		this.idAutomotor = idAutomotor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	

}
