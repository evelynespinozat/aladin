package com.certicom.ittsa.domain;

import java.math.BigDecimal;

public class Liquidacion {
	
	private Integer idLiquidacion;
	private Integer idProgramacion;
	private BigDecimal total;
	private boolean estado;

	
	
	public Liquidacion(){
		
	}



	public Integer getIdLiquidacion() {
		return idLiquidacion;
	}



	public void setIdLiquidacion(Integer idLiquidacion) {
		this.idLiquidacion = idLiquidacion;
	}



	public Integer getIdProgramacion() {
		return idProgramacion;
	}



	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}



	public BigDecimal getTotal() {
		return total;
	}



	public void setTotal(BigDecimal total) {
		this.total = total;
	}



	public boolean isEstado() {
		return estado;
	}



	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	

}
