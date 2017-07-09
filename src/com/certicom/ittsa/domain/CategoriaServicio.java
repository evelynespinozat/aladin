package com.certicom.ittsa.domain;

public class CategoriaServicio {

	private Integer idCatServicio;
	private String Descripcion;
	private boolean estado;

	public CategoriaServicio() {
	}

	public Integer getIdCatServicio() {
		return idCatServicio;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setIdCatServicio(Integer idCatServicio) {
		this.idCatServicio = idCatServicio;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

}
