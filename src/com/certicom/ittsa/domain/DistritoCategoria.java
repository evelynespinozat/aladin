package com.certicom.ittsa.domain;

public class DistritoCategoria {
	private Integer id;
	private String sid_ubigeo;
	private String descripcion;
	private double valor;
	private double costoServicio;
	
	public Integer getId() {
		return id;
	}
	
	public double getCostoServicio() {
		return costoServicio;
	}

	public void setCostoServicio(double costoServicio) {
		this.costoServicio = costoServicio;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSid_ubigeo() {
		return sid_ubigeo;
	}
	
	public void setSid_ubigeo(String sid_ubigeo) {
		this.sid_ubigeo = sid_ubigeo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	

}
