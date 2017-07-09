package com.certicom.ittsa.domain;

public class RutaServicio {
	
	private String idRutaDestino;
	private String rutaDestino;
	private String tipoViaje;
	private Boolean estado;
	
	public RutaServicio(){}

	public String getIdRutaDestino() {
		return idRutaDestino;
	}

	public String getRutaDestino() {
		return rutaDestino;
	}

	public String getTipoViaje() {
		return tipoViaje;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setIdRutaDestino(String idRutaDestino) {
		this.idRutaDestino = idRutaDestino;
	}

	public void setRutaDestino(String rutaDestino) {
		this.rutaDestino = rutaDestino.toUpperCase();
	}

	public void setTipoViaje(String tipoViaje) {
		this.tipoViaje = tipoViaje.toUpperCase();
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


}
