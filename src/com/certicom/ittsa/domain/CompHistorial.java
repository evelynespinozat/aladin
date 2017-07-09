package com.certicom.ittsa.domain;

import java.util.Date;

public class CompHistorial {
	public int IdHistComp;
	public String Descripcion;
	public String Observacion;
	private String Tecnico;
	private Date Fecha;
	private Date FRegistro;
	private String EstadoHis;
	private int Idcomponente;
	
	public int getIdHistComp() {
		return IdHistComp;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public String getObservacion() {
		return Observacion;
	}
	public String getTecnico() {
		return Tecnico;
	}
	public Date getFecha() {
		return Fecha;
	}
	public Date getFRegistro() {
		return FRegistro;
	}
	public String getEstadoHis() {
		return EstadoHis;
	}
	public int getIdcomponente() {
		return Idcomponente;
	}
	public void setIdHistComp(int idHistComp) {
		IdHistComp = idHistComp;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion.toUpperCase();
	}
	public void setObservacion(String observacion) {
		Observacion = observacion.toUpperCase();
	}
	public void setTecnico(String tecnico) {
		Tecnico = tecnico.toUpperCase();
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}
	public void setEstadoHis(String estadoHis) {
		EstadoHis = estadoHis.toUpperCase();
	}
	public void setIdcomponente(int idcomponente) {
		Idcomponente = idcomponente;
	}

	

}
