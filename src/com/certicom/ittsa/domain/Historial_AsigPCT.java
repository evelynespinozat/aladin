package com.certicom.ittsa.domain;

import java.util.Date;

public class Historial_AsigPCT {
	
	private Integer idHistorial;
	private Integer idBus;
	private Date fecha;
	private Integer idPiloto;
	private Integer idCopiloto;
	private Integer idTerramoza;
	private String estado;
	
	/**Otros**/
	private String nombPiloto;
	private String nombCopiloto;
	private Integer numeroBus;
	private String funcion;
	
	
	public Historial_AsigPCT(){
		
	}



	public Integer getIdHistorial() {
		return idHistorial;
	}



	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}



	public Integer getIdBus() {
		return idBus;
	}



	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public Integer getIdPiloto() {
		return idPiloto;
	}



	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}



	public Integer getIdCopiloto() {
		return idCopiloto;
	}



	public void setIdCopiloto(Integer idCopiloto) {
		this.idCopiloto = idCopiloto;
	}



	public Integer getIdTerramoza() {
		return idTerramoza;
	}



	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getNombPiloto() {
		return nombPiloto;
	}



	public void setNombPiloto(String nombPiloto) {
		this.nombPiloto = nombPiloto;
	}



	public String getNombCopiloto() {
		return nombCopiloto;
	}



	public void setNombCopiloto(String nombCopiloto) {
		this.nombCopiloto = nombCopiloto;
	}



	public Integer getNumeroBus() {
		return numeroBus;
	}



	public void setNumeroBus(Integer numeroBus) {
		this.numeroBus = numeroBus;
	}



	public String getFuncion() {
		return funcion;
	}



	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}


	
}
