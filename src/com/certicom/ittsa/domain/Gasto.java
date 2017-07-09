package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Gasto {

	private Integer idGasto;
	private Integer programacionId;
	private BigDecimal monto;
	private String concepto;
	private Date fecha;
	private String observaciones;
	
	
	public Gasto(){
		
	}


	public Integer getIdGasto() {
		return idGasto;
	}


	public Integer getProgramacionId() {
		return programacionId;
	}


	public BigDecimal getMonto() {
		return monto;
	}


	public String getConcepto() {
		return concepto;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setIdGasto(Integer idGasto) {
		this.idGasto = idGasto;
	}


	public void setProgramacionId(Integer programacionId) {
		this.programacionId = programacionId;
	}


	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}


	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}
