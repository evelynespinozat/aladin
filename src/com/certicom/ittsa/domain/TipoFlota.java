package com.certicom.ittsa.domain;

public class TipoFlota {
	
	private Integer idTipoFlota;
	private String descripcion;
	private boolean Estado;
	
	public TipoFlota(){
		
	}

	public Integer getIdTipoFlota() {
		return idTipoFlota;
	}

	public void setIdTipoFlota(Integer idTipoFlota) {
		this.idTipoFlota = idTipoFlota;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}

	public boolean isEstado() {
		return Estado;
	}

	public void setEstado(boolean estado) {
		Estado = estado;
	}

}
