package com.certicom.ittsa.domain;

import java.util.Date;

public class CompraDetalle {

	private Integer IdCompDet;
	private Integer IdCompra;
	private Integer IdProducto;
	private Integer cantIngreso;
	private Date fRegistro;
	private Integer idProveedor;
	
	private String desProducto;
	private String desMedida;
	private Double costoUni;
	
	public Integer getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}
	public Integer getIdCompDet() {
		return IdCompDet;
	}
	public Integer getIdCompra() {
		return IdCompra;
	}
	public Integer getIdProducto() {
		return IdProducto;
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
	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
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
	public Double getCostoUni() {
		return costoUni;
	}
	public void setCostoUni(Double costoUni) {
		this.costoUni = costoUni;
	}
	
	
}
