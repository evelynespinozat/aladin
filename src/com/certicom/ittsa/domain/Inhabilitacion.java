package com.certicom.ittsa.domain;

public class Inhabilitacion {

	private String tipo;
	private String motivo;
	private String serieRelacionado;
	private String numeroRelacionado;
	private String dniPasajeroCambiado;
	
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getSerieRelacionado() {
		return serieRelacionado;
	}
	public void setSerieRelacionado(String serieRelacionado) {
		this.serieRelacionado = serieRelacionado;
	}
	public String getNumeroRelacionado() {
		return numeroRelacionado;
	}
	public void setNumeroRelacionado(String numeroRelacionado) {
		this.numeroRelacionado = numeroRelacionado;
	}
	public String getDniPasajeroCambiado() {
		return dniPasajeroCambiado;
	}
	public void setDniPasajeroCambiado(String dniPasajeroCambiado) {
		this.dniPasajeroCambiado = dniPasajeroCambiado;
	}

	
}
