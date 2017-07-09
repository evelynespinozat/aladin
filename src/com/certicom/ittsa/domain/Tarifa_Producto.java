package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class Tarifa_Producto {
	
	private Integer idTarifa_Producto;
	private String descripcion;
	private Integer idAgencia;
	private BigDecimal precioEnvio;
	private BigDecimal precioEmbalaje;
	private BigDecimal peso;
	private BigDecimal precioKilo;
	private BigDecimal totalPagar;

	
	
	public Tarifa_Producto(){
		
	}

	public Integer getIdTarifa_Producto() {
		return idTarifa_Producto;
	}

	public void setIdTarifa_Producto(Integer idTarifa_Producto) {
		this.idTarifa_Producto = idTarifa_Producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public BigDecimal getPrecioEnvio() {
		return precioEnvio;
	}

	public void setPrecioEnvio(BigDecimal precioEnvio) {
		this.precioEnvio = precioEnvio;
	}

	public BigDecimal getPrecioEmbalaje() {
		return precioEmbalaje;
	}

	public void setPrecioEmbalaje(BigDecimal precioEmbalaje) {
		this.precioEmbalaje = precioEmbalaje;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPrecioKilo() {
		return precioKilo;
	}

	public void setPrecioKilo(BigDecimal precioKilo) {
		this.precioKilo = precioKilo;
	}

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}




	
    
	
	

}
