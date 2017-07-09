package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class CopiaBoleto {

	private String dniPasajero;
	private String nombrePasajero;
	private String numeroBoleto;
	private String numeroAsiento;
	private String origen;
	private String destino;
	private String fechaSalida;
	private String horaSalida;
	private String rucPasajero;
	private String razonSocialPasajero;
	private String oficinaVenta;
	private String responsableVenta;
	private BigDecimal valorCopia;
	private String concepto;
	
	public CopiaBoleto(){;}

	public String getDniPasajero() {
		return dniPasajero;
	}

	public String getNombrePasajero() {
		return nombrePasajero;
	}

	public String getNumeroBoleto() {
		return numeroBoleto;
	}

	public String getNumeroAsiento() {
		return numeroAsiento;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public String getRucPasajero() {
		return rucPasajero;
	}

	public String getRazonSocialPasajero() {
		return razonSocialPasajero;
	}

	public String getOficinaVenta() {
		return oficinaVenta;
	}

	public String getResponsableVenta() {
		return responsableVenta;
	}

	public BigDecimal getValorCopia() {
		return valorCopia;
	}

	public void setDniPasajero(String dniPasajero) {
		this.dniPasajero = dniPasajero;
	}

	public void setNombrePasajero(String nombrePasajero) {
		this.nombrePasajero = nombrePasajero;
	}

	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}

	public void setNumeroAsiento(String numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public void setRucPasajero(String rucPasajero) {
		this.rucPasajero = rucPasajero;
	}

	public void setRazonSocialPasajero(String razonSocialPasajero) {
		this.razonSocialPasajero = razonSocialPasajero;
	}

	public void setOficinaVenta(String oficinaVenta) {
		this.oficinaVenta = oficinaVenta;
	}

	public void setResponsableVenta(String responsableVenta) {
		this.responsableVenta = responsableVenta;
	}

	public void setValorCopia(BigDecimal valorCopia) {
		this.valorCopia = valorCopia;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	
}
