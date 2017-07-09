package com.certicom.ittsa.domain;

public class TrackingEncomienda {

	private Integer idTracking;
	private Integer idEncomienda;
	private Integer idEstado;
	private Boolean estadoActual;

	// 19/03/2014
	private Integer idBus;
	private Integer idProgramacion;

	public TrackingEncomienda() {

	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Integer getIdTracking() {
		return idTracking;
	}

	public void setIdTracking(Integer idTracking) {
		this.idTracking = idTracking;
	}

	public Integer getIdEncomienda() {
		return idEncomienda;
	}

	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
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

	public Integer getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}
	
}
