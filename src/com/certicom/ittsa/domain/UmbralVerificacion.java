package com.certicom.ittsa.domain;

public class UmbralVerificacion {
	private int id_verificacion;
	private String descripcion;
	private boolean estado;
	public int getId_verificacion() {
		return id_verificacion;
	}
	public void setId_verificacion(int id_verificacion) {
		this.id_verificacion = id_verificacion;
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
