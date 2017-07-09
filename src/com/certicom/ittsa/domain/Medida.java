package com.certicom.ittsa.domain;

public class Medida {
	
	private String CodigoMedida;
	private String Descripcion;
	private boolean Estado;
	public String getCodigoMedida() {
		return CodigoMedida;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public boolean isEstado() {
		return Estado;
	}
	public void setCodigoMedida(String codigoMedida) {
		CodigoMedida = codigoMedida.toUpperCase();
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}
	public void setEstado(boolean estado) {
		Estado = estado;
	}
	
	

}
