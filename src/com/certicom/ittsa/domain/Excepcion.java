package com.certicom.ittsa.domain;

import java.util.Date;

public class Excepcion {
	public int idexcepcion;
	public String descripcion;
	public boolean estado;
	public Date fRegistro;
	public Date fecha;
	
	public int getIdexcepcion() {
		return idexcepcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public boolean isEstado() {
		return estado;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setIdexcepcion(int idexcepcion) {
		this.idexcepcion = idexcepcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	
}
