package com.certicom.ittsa.domain;

import java.util.Date;

public class Turno {
	
	private Integer idTurno;
	private String descripcion;
	private String hInicio;
	private String hFin;
	private boolean estado;
	private Date horaInicio;
	private Date horaFin;
	
	public Turno(){
		
	}

	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}


	public String gethInicio() {
		return hInicio;
	}


	public void sethInicio(String hInicio) {
		this.hInicio = hInicio.toUpperCase();
	}


	public String gethFin() {
		return hFin;
	}


	public void sethFin(String hFin) {
		this.hFin = hFin.toUpperCase();
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	
}
