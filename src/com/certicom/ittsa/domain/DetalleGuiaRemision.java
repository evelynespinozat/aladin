package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class DetalleGuiaRemision {

	private Integer idDetGuiaRemision;
	private Integer idGuiaRemision;
	private String descripcion;
	private BigDecimal peso;
	private Integer cantidad;
	private BigDecimal precioEnvio;

	public DetalleGuiaRemision(){
	}

	public Integer getIdDetGuiaRemision() {
		return idDetGuiaRemision;
	}

	public void setIdDetGuiaRemision(Integer idDetGuiaRemision) {
		this.idDetGuiaRemision = idDetGuiaRemision;
	}

	public Integer getIdGuiaRemision() {
		return idGuiaRemision;
	}

	public void setIdGuiaRemision(Integer idGuiaRemision) {
		this.idGuiaRemision = idGuiaRemision;
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
