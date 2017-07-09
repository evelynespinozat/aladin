package com.certicom.ittsa.domain;

import java.util.Date;

public class AgenciaAutoparte {

	public Integer Id;
	public Integer Idagencia;
	public Integer IdOficina;
	public Integer IdAlmacen;
	public Integer IdAutoparte;
	public Integer Stock;
	public Date Fecha;

	public String desAgencia;
	public String desOficina;
	public String desAlmacen;
	public String desProducto;
	public String medida;
	public String desMedida;
	public Double precioCompra;
	
	
	public double precioUni;
	// atributo para ingresar valor
	private Integer cantIngreso =0;

	public Integer getId() {
		return Id;
	}

	public Integer getIdagencia() {
		return Idagencia;
	}

	public Integer getIdOficina() {
		return IdOficina;
	}

	public Integer getIdAlmacen() {
		return IdAlmacen;
	}


	public Integer getStock() {
		return Stock;
	}

	public Date getFecha() {
		return Fecha;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}

	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		IdAlmacen = idAlmacen;
	}

	public void setStock(Integer stock) {
		Stock = stock;
	}

	public void setFecha(Date fecha) {
		Fecha = fecha;
	}

	public String getDesAgencia() {
		return desAgencia;
	}

	public String getDesOficina() {
		return desOficina;
	}

	public String getDesAlmacen() {
		return desAlmacen;
	}

	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia;
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}

	public void setDesAlmacen(String desAlmacen) {
		this.desAlmacen = desAlmacen;
	}

	public String getDesProducto() {
		return desProducto;
	}

	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public String getDesMedida() {
		return desMedida;
	}

	public void setDesMedida(String desMedida) {
		this.desMedida = desMedida;
	}

	public Integer getCantIngreso() {
		return cantIngreso;
	}

	public void setCantIngreso(Integer cantIngreso) {
		this.cantIngreso = cantIngreso;
	}

	public Integer getIdAutoparte() {
		return IdAutoparte;
	}

	public void setIdAutoparte(Integer idAutoparte) {
		IdAutoparte = idAutoparte;
	}

	public double getPrecioUni() {
		return precioUni;
	}

	public void setPrecioUni(double precioUni) {
		this.precioUni = precioUni;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}
	
	

}
