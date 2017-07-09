package com.certicom.ittsa.domain;

import java.util.Date;

public class SalidaDetalle {
	
	private Integer IDSalDet;
	private Integer IdSalida;
	private Integer IdProducto;
	private Integer cantSalida;
	private Integer stockAnt;
	private Date FRegistro;
	private String flagEntrega;
	private Integer cantConsumida=0;
	
	private Integer IdProgramacion;
	private String desProducto;
	private String Medida;
	private Integer cantLLegada=0;
	private double precioCosto;
	private double precioUni;
	private double costoIndiviual;
	private String desgregacion;
	
	private double costoUni; 
	private double costo;
	private String disgregacion;
	private Integer cantDist;
	private double costoxPasj;
	
	
	
	public Integer getIDSalDet() {
		return IDSalDet;
	}
	public Integer getIdSalida() {
		return IdSalida;
	}
	public Integer getIdProducto() {
		return IdProducto;
	}
	public Integer getCantSalida() {
		return cantSalida;
	}
	public Integer getStockAnt() {
		return stockAnt;
	}
	public Date getFRegistro() {
		return FRegistro;
	}
	public void setIDSalDet(Integer iDSalDet) {
		IDSalDet = iDSalDet;
	}
	public void setIdSalida(Integer idSalida) {
		IdSalida = idSalida;
	}
	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
	}
	public void setCantSalida(Integer cantSalida) {
		this.cantSalida = cantSalida;
	}
	public void setStockAnt(Integer stockAnt) {
		this.stockAnt = stockAnt;
	}
	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}
	public Integer getIdProgramacion() {
		return IdProgramacion;
	}
	public String getDesProducto() {
		return desProducto;
	}
	public void setIdProgramacion(Integer idProgramacion) {
		IdProgramacion = idProgramacion;
	}
	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}
	public Integer getCantLLegada() {
		return cantLLegada;
	}
	public void setCantLLegada(Integer cantLLegada) {
		this.cantLLegada = cantLLegada;
	}
	public String getMedida() {
		return Medida;
	}
	public void setMedida(String medida) {
		Medida = medida;
	}
	public String getFlagEntrega() {
		return flagEntrega;
	}
	public void setFlagEntrega(String flagEntrega) {
		this.flagEntrega = flagEntrega;
	}
	public Integer getCantConsumida() {
		return cantConsumida;
	}
	public void setCantConsumida(Integer cantConsumida) {
		this.cantConsumida = cantConsumida;
	}
	public double getPrecioCosto() {
		return precioCosto;
	}
	public double getPrecioUni() {
		return precioUni;
	}
	public double getCostoIndiviual() {
		return costoIndiviual;
	}
	public void setPrecioCosto(double precioCosto) {
		this.precioCosto = precioCosto;
	}
	public void setPrecioUni(double precioUni) {
		this.precioUni = precioUni;
	}
	public void setCostoIndiviual(double costoIndiviual) {
		this.costoIndiviual = costoIndiviual;
	}
	public String getDesgregacion() {
		return desgregacion;
	}
	public void setDesgregacion(String desgregacion) {
		this.desgregacion = desgregacion;
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
	public double getCostoUni() {
		return costoUni;
	}
	public void setCostoUni(double costoUni) {
		this.costoUni = costoUni;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public String getDisgregacion() {
		return disgregacion;
	}
	public void setDisgregacion(String disgregacion) {
		this.disgregacion = disgregacion;
	}
	
}
