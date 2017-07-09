package com.certicom.ittsa.domain;

import java.util.Date;

public class Transbordo {
	
	private Integer idTransbordo;
	private String tipo;
	private Date fRegistro;
	private Integer idProgramacion;
	private Integer idBusOrigen;
	private Integer idBusDestino;
	private Integer idClaseOrigen;
	private Integer idClaseDestino;
	private String observaciones;
	
	//datos secundarios necesarios
	private Integer origen;
	private Integer destino;
	private Date fecha;
	private String hora;
	private String numBus;
	
	//para la grilla
	private Integer busNumOrigen;
	private Integer busNumDestino;
	private String claseOrigen;
	private String claseDestino;

	public Transbordo(){
		
	}
	
	public Integer getBusNumOrigen() {
		return busNumOrigen;
	}

	public void setBusNumOrigen(Integer busNumOrigen) {
		this.busNumOrigen = busNumOrigen;
	}

	public Integer getBusNumDestino() {
		return busNumDestino;
	}

	public void setBusNumDestino(Integer busNumDestino) {
		this.busNumDestino = busNumDestino;
	}

	public String getClaseOrigen() {
		return claseOrigen;
	}

	public void setClaseOrigen(String claseOrigen) {
		this.claseOrigen = claseOrigen.toUpperCase();
	}

	public String getClaseDestino() {
		return claseDestino;
	}

	public void setClaseDestino(String claseDestino) {
		this.claseDestino = claseDestino.toUpperCase();
	}

	public String getNumBus() {
		return numBus;
	}

	public void setNumBus(String numBus) {
		this.numBus = numBus.toUpperCase();
	}

	public Integer getDestino() {
		return destino;
	}
	
	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora.toUpperCase();
	}

	public Integer getOrigen() {
		return origen;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdTransbordo() {
		return idTransbordo;
	}

	public void setIdTransbordo(Integer idTransbordo) {
		this.idTransbordo = idTransbordo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo.toUpperCase();
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public Integer getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public Integer getIdBusOrigen() {
		return idBusOrigen;
	}

	public void setIdBusOrigen(Integer idBusOrigen) {
		this.idBusOrigen = idBusOrigen;
	}

	public Integer getIdBusDestino() {
		return idBusDestino;
	}

	public void setIdBusDestino(Integer idBusDestino) {
		this.idBusDestino = idBusDestino;
	}

	public Integer getIdClaseOrigen() {
		return idClaseOrigen;
	}

	public void setIdClaseOrigen(Integer idClaseOrigen) {
		this.idClaseOrigen = idClaseOrigen;
	}

	public Integer getIdClaseDestino() {
		return idClaseDestino;
	}

	public void setIdClaseDestino(Integer idClaseDestino) {
		this.idClaseDestino = idClaseDestino;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones.toUpperCase();
	}


}
