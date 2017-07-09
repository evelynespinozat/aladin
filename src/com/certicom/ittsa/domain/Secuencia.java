package com.certicom.ittsa.domain; 

import java.util.Date;

public class Secuencia implements java.io.Serializable 
{ 
	private static final long serialVersionUID = 1L; 
	private int idsecuencia; 
	private String descripcion; 
	private int secuencia; 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getIdsecuencia() {
		return idsecuencia;
	}


	public void setIdsecuencia(int idsecuencia) {
		this.idsecuencia = idsecuencia;
	}


	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}

	public int getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}
	
	
} 
