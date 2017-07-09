package com.certicom.ittsa.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Asignacion {

	private Integer idasignacion;
	private BigDecimal monto;
	private Integer programacionId;
	private Integer idPiloto;
	private Date fecha;
	private Integer idUsuario;
	private Integer agenciaOrigen;
	
	
	public Asignacion(){}


	public Integer getIdasignacion() {
		return idasignacion;
	}


	public BigDecimal getMonto() {
		return monto;
	}


	public Integer getProgramacionId() {
		return programacionId;
	}


	public Integer getIdPiloto() {
		return idPiloto;
	}


	public Date getFecha() {
		return fecha;
	}


	public Integer getIdUsuario() {
		return idUsuario;
	}


	public Integer getAgenciaOrigen() {
		return agenciaOrigen;
	}


	public void setIdasignacion(Integer idasignacion) {
		this.idasignacion = idasignacion;
	}


	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}


	public void setProgramacionId(Integer programacionId) {
		this.programacionId = programacionId;
	}


	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}


	public void setAgenciaOrigen(Integer agenciaOrigen) {
		this.agenciaOrigen = agenciaOrigen;
	}
	
	
}
