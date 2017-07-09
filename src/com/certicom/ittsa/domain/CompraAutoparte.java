package com.certicom.ittsa.domain;

import java.util.Date;

public class CompraAutoparte {
	
	private Integer IdCompra;
	private String ordenCompra;
	private Integer Idagencia;
	private Integer IdOficina;
	private Integer IdProveedor;
	private Date fRegistro;
	private Date fechaCompra;
	private String tipDocumento;
	private String moneda;
	private double tipoCambio;
	
	private String desAgencia;
	private String desOficina;
	private String desProveedor;
	
	public Integer getIdCompra() {
		return IdCompra;
	}
	public String getOrdenCompra() {
		return ordenCompra;
	}
	public Integer getIdagencia() {
		return Idagencia;
	}
	public Integer getIdOficina() {
		return IdOficina;
	}
	public Integer getIdProveedor() {
		return IdProveedor;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public String getTipDocumento() {
		return tipDocumento;
	}
	public void setIdCompra(Integer idCompra) {
		IdCompra = idCompra;
	}
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}
	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}
	public void setIdProveedor(Integer idProveedor) {
		IdProveedor = idProveedor;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public void setTipDocumento(String tipDocumento) {
		this.tipDocumento = tipDocumento;
	}
	public String getDesAgencia() {
		return desAgencia;
	}
	public String getDesOficina() {
		return desOficina;
	}
	public String getDesProveedor() {
		return desProveedor;
	}
	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia;
	}
	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}
	public void setDesProveedor(String desProveedor) {
		this.desProveedor = desProveedor;
	}
	public String getMoneda() {
		return moneda;
	}
	public double getTipoCambio() {
		return tipoCambio;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public void setTipoCambio(double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	
	
	
	
	
}
