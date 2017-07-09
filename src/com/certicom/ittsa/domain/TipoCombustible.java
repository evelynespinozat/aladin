package com.certicom.ittsa.domain;

public class TipoCombustible {
	
	private Integer idTipoCombustible;
	private String tipo;
	private String descripcion;
	private boolean estado;
	
	public TipoCombustible(){
		
	}

	public Integer getIdTipoCombustible() {
		return idTipoCombustible;
	}

	public void setIdTipoCombustible(Integer idTipoCombustible) {
		this.idTipoCombustible = idTipoCombustible;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo.toUpperCase();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
