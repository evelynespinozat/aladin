package com.certicom.ittsa.domain;

public class TipoDocumento {

	private int idtipodoc;
	private String descripcion;
	private int longitud;

	public int getIdtipodoc() {
		return idtipodoc;
	}

	public void setIdtipodoc(int idtipodoc) {
		this.idtipodoc = idtipodoc;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	
	

}
