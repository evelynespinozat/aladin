package com.certicom.ittsa.domain;

import java.awt.Image;
import java.math.BigDecimal;

public class DetalleEncomienda {

	private Integer idDetalle;
	private Integer idEncomienda;
	private String tipoEnvio;
	private BigDecimal peso;
	private BigDecimal importe;
	private Integer cantidad;
	private String descripcion;
	private BigDecimal precioKilo;
	private byte[] codigoBarras;
	private String codigoBarrasString;

	// //
	private Integer nroOrden;
	private Integer basePago2;
	private BigDecimal precioEnvio;
	


	private Double precioEnvioNormal;
	private boolean tipoReparto;

	public DetalleEncomienda() {
	}
		
	public boolean isTipoReparto() {
		return tipoReparto;
	}

	public void setTipoReparto(boolean tipoReparto) {
		this.tipoReparto = tipoReparto;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Integer getIdEncomienda() {
		return idEncomienda;
	}

	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecioKilo() {
		return precioKilo;
	}

	public void setPrecioKilo(BigDecimal precioKilo) {
		this.precioKilo = precioKilo;
	}

	public byte[] getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(byte[] codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getCodigoBarrasString() {
		return codigoBarrasString;
	}

	public void setCodigoBarrasString(String codigoBarrasString) {
		this.codigoBarrasString = codigoBarrasString;
	}

	public Integer getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(Integer nroOrden) {
		this.nroOrden = nroOrden;
	}

	public Integer getBasePago2() {
		return basePago2;
	}

	public void setBasePago2(Integer basePago2) {
		this.basePago2 = basePago2;
	}

	public BigDecimal getPrecioEnvio() {
		return precioEnvio;
	}

	public void setPrecioEnvio(BigDecimal precioEnvio) {
		this.precioEnvio = precioEnvio;
	}

	public Double getPrecioEnvioNormal() {
		return precioEnvioNormal;
	}

	public void setPrecioEnvioNormal(Double precioEnvioNormal) {
		this.precioEnvioNormal = precioEnvioNormal;
	}


	
	
	

}
