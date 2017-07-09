package com.certicom.ittsa.domain;

import java.util.Date;

public class Embarque {
	
	private Integer embarqueId;
	private String descripcion;
	//INICIO PISCOYA
	private String nombrePasajero;
	private String apellidoPasajero;
	private String dniPasajero;
	private Date fIncidencia;
	private String oficina;
	//FIN PISCOYA
	
	public Embarque(){
	}

	public Integer getEmbarqueId() {
		return embarqueId;
	}

	public void setEmbarqueId(Integer embarqueId) {
		this.embarqueId = embarqueId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombrePasajero() {
		return nombrePasajero;
	}

	public void setNombrePasajero(String nombrePasajero) {
		this.nombrePasajero = nombrePasajero;
	}

	public String getApellidoPasajero() {
		return apellidoPasajero;
	}

	public void setApellidoPasajero(String apellidoPasajero) {
		this.apellidoPasajero = apellidoPasajero;
	}

	public String getDniPasajero() {
		return dniPasajero;
	}

	public void setDniPasajero(String dniPasajero) {
		this.dniPasajero = dniPasajero;
	}
	
	public Date getfIncidencia() {
		return fIncidencia;
	}

	public void setfIncidencia(Date fIncidencia) {
		this.fIncidencia = fIncidencia;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	
	
	
	
}
