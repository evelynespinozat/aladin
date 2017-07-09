package com.certicom.ittsa.domain;

import java.awt.Image;

public class Producto_DetalleEnc {
	
	private Integer idProducto_detalleEnc;
	private Integer idDetalle;
	private Integer idEncomienda;
	private String codigoBarrasString;
	private byte[] codigoBarras;
	private Integer estado;
	private Integer codigoControl;
	private Integer IdCompuesto;
	private String descripcion;
	private String desProducto;
	private String tipoDocumento;

	
	public Producto_DetalleEnc(){
		
	}

	public Integer getIdProducto_detalleEnc() {
		return idProducto_detalleEnc;
	}

	public void setIdProducto_detalleEnc(Integer idProducto_detalleEnc) {
		this.idProducto_detalleEnc = idProducto_detalleEnc;
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

	
	public String getCodigoBarrasString() {
		return codigoBarrasString;
	}

	public void setCodigoBarrasString(String codigoBarrasString) {
		this.codigoBarrasString = codigoBarrasString;
	}

	public byte[] getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(byte[] codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getCodigoControl() {
		return codigoControl;
	}

	public void setCodigoControl(Integer codigoControl) {
		this.codigoControl = codigoControl;
	}

	public Integer getIdCompuesto() {
		return IdCompuesto;
	}

	public void setIdCompuesto(Integer idCompuesto) {
		IdCompuesto = idCompuesto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDesProducto() {
		return desProducto;
	}

	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	
	
}
