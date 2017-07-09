package com.certicom.ittsa.domain;

public class TipoAsiento {

	private Integer idtipoasiento;
	private String nombre;
	private Boolean estado;
	
	public TipoAsiento(){;}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


	public Integer getIdtipoasiento() {
		return idtipoasiento;
	}


	public void setIdtipoasiento(Integer idtipoasiento) {
		this.idtipoasiento = idtipoasiento;
	}
	
	
	
}
