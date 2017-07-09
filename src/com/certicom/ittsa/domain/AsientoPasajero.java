package com.certicom.ittsa.domain;

public class AsientoPasajero {
	
	private Asiento asiento;
	private AsientoVenta asientoVenta;
	private Persona pasajero;
	private Programacion programacion;
	private Servicio servicio;
	
	public AsientoPasajero(){;}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}

	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
	}

	public Persona getPasajero() {
		return pasajero;
	}

	public void setPasajero(Persona pasajero) {
		this.pasajero = pasajero;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

}
