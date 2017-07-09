package com.certicom.ittsa.domain;

public class Frecuencia {
	
	private Integer idFrecuencia;
	private String descripcion;
	private boolean estado;
	
	public Frecuencia(){
		
	}

	public Integer getIdFrecuencia() {
		return idFrecuencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setIdFrecuencia(Integer idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}



}
