package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class DetalleNotaCobranza {
	
	private Integer idDetNotaCobranza;
	private Integer idNotaCobranza;
	private String descripcion;
	private BigDecimal peso;
	private Integer cantidad;
	private BigDecimal precioEnvio;
	
	public DetalleNotaCobranza(){
	}

	public Integer getIdDetNotaCobranza() {
		return idDetNotaCobranza;
	}

	public void setIdDetNotaCobranza(Integer idDetNotaCobranza) {
		this.idDetNotaCobranza = idDetNotaCobranza;
	}

	public Integer getIdNotaCobranza() {
		return idNotaCobranza;
	}

	public void setIdNotaCobranza(Integer idNotaCobranza) {
		this.idNotaCobranza = idNotaCobranza;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioEnvio() {
		return precioEnvio;
	}

	public void setPrecioEnvio(BigDecimal precioEnvio) {
		this.precioEnvio = precioEnvio;
	}
	
}
