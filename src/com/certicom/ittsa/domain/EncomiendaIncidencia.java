package com.certicom.ittsa.domain;

public class EncomiendaIncidencia {
	
	private Integer idIncidencia;
	private Integer idEncomienda;
	private Integer idUsuario;
	private String  observacion;
	private Integer idAgencia;
	private Integer idOficina;
	private String tipo;
	
	public Integer getIdIncidencia() {
		return idIncidencia;
	}
	public Integer getIdEncomienda() {
		return idEncomienda;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public Integer getIdAgencia() {
		return idAgencia;
	}
	public Integer getIdOficina() {
		return idOficina;
	}
	public void setIdIncidencia(Integer idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
