package com.certicom.ittsa.domain;

import java.util.Date;

public class CompraDetAutoparte {

	private Integer IdCompDet;
	private Integer IdCompra;
	private Integer IdAutoparte;
	private Integer cantIngreso;
	private Date fRegistro;
	private double precio;
	
	private String desProducto;
	private String desMedida;
	
	private double subtotal;
	public Integer getIdCompDet() {
		return IdCompDet;
	}
	public Integer getIdCompra() {
		return IdCompra;
	}
	public Integer getCantIngreso() {
		return cantIngreso;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public String getDesProducto() {
		return desProducto;
	}
	public String getDesMedida() {
		return desMedida;
	}
	public void setIdCompDet(Integer idCompDet) {
		IdCompDet = idCompDet;
	}
	public void setIdCompra(Integer idCompra) {
		IdCompra = idCompra;
	}
	public void setCantIngreso(Integer cantIngreso) {
		this.cantIngreso = cantIngreso;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}
	public void setDesMedida(String desMedida) {
		this.desMedida = desMedida;
	}
	public Integer getIdAutoparte() {
		return IdAutoparte;
	}
	public double getPrecio() {
		return precio;
	}
	public void setIdAutoparte(Integer idAutoparte) {
		IdAutoparte = idAutoparte;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	
	
}
