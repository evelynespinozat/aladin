package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class Detalle_Liquidacion {
	
	private Integer idDetalleLiq;
	private Integer idLiquidacion;
	private String concepto;
	private BigDecimal costo;
	private boolean estado;
	//private Integer idProgramacion;

	
	
	public Detalle_Liquidacion(){
		
	}



	public Integer getIdDetalleLiq() {
		return idDetalleLiq;
	}



	public void setIdDetalleLiq(Integer idDetalleLiq) {
		this.idDetalleLiq = idDetalleLiq;
	}



	public String getConcepto() {
		return concepto;
	}



	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}



	public BigDecimal getCosto() {
		return costo;
	}



	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}



	public boolean isEstado() {
		return estado;
	}



	public void setEstado(boolean estado) {
		this.estado = estado;
	}



	public Integer getIdLiquidacion() {
		return idLiquidacion;
	}



	public void setIdLiquidacion(Integer idLiquidacion) {
		this.idLiquidacion = idLiquidacion;
	}


/*
	public Integer getIdProgramacion() {
		return idProgramacion;
	}



	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	*/

	
	

}
