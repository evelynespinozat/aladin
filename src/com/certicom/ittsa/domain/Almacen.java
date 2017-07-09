package com.certicom.ittsa.domain;

public class Almacen {
	public int IdAlmacen;
	public String Descripcion;
	public boolean  Estado;
	public boolean  asignado;
	//juancito mano rapida
	
	public int getIdAlmacen() {
		return IdAlmacen;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public boolean isEstado() {
		return Estado;
	}
	public void setIdAlmacen(int idAlmacen) {
		IdAlmacen = idAlmacen;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	public boolean isAsignado() {
		return asignado;
	}
	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}
	
	
}
