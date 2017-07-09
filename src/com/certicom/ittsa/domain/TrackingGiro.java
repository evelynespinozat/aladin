package com.certicom.ittsa.domain;

public class TrackingGiro {
	
	private Integer idTracking;
	private Integer idGiro;
	private Integer idEstado;
	private Boolean estadoActual;

	
	public TrackingGiro(){
		
	}


	public Integer getIdTracking() {
		return idTracking;
	}


	public void setIdTracking(Integer idTracking) {
		this.idTracking = idTracking;
	}

	public Integer getIdGiro() {
		return idGiro;
	}


	public void setIdGiro(Integer idGiro) {
		this.idGiro = idGiro;
	}


	public Integer getIdEstado() {
		return idEstado;
	}


	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}


	public Boolean getEstadoActual() {
		return estadoActual;
	}


	public void setEstadoActual(Boolean estadoActual) {
		this.estadoActual = estadoActual;
	}
	
	

}
