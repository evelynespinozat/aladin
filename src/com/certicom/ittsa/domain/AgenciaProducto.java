package com.certicom.ittsa.domain;

import java.util.Date;

public class AgenciaProducto {

	public Integer Id;
	public Integer Idagencia;
	public Integer IdOficina;
	public Integer IdAlmacen;
	public Integer IdProducto;
	public Integer Stock;
	public Date Fecha;
	private double costoUni; 
	private double costo;
	private Integer cantDist;
	private double costoxPasj;
	private String disgregacion;
	

	public String desAgencia;
	public String desOficina;
	public String desAlmacen;
	public String desProducto;
	public String medida;
	public String desMedida;
	
	
	// atributo para ingresar valor
	private Integer cantIngreso =0;
	private Integer cantSalidaDefecto =0;

	public double getCostoUni() {
		return costoUni;
	}

	public void setCostoUni(double costoUni) {
		this.costoUni = costoUni;
	}

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

	public Integer getIdProducto() {
		return IdProducto;
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

	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
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

	public Integer getCantSalidaDefecto() {
		return cantSalidaDefecto;
	}

	public void setCantSalidaDefecto(Integer cantSalidaDefecto) {
		this.cantSalidaDefecto = cantSalidaDefecto;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Integer getCantDist() {
		return cantDist;
	}

	public void setCantDist(Integer cantDist) {
		this.cantDist = cantDist;
	}

	public double getCostoxPasj() {
		return costoxPasj;
	}

	public void setCostoxPasj(double costoxPasj) {
		this.costoxPasj = costoxPasj;
	}

	public String getDisgregacion() {
		return disgregacion;
	}

	public void setDisgregacion(String disgregacion) {
		this.disgregacion = disgregacion;
	}
	
	
	
}
