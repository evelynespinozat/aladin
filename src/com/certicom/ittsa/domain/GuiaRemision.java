package com.certicom.ittsa.domain;

import java.util.Date;

public class GuiaRemision {

	private Integer idGuiaRemision;
	private String serieGuia;
	private Integer numeroGuia;
	private Integer idBus;
	private Date fRegistro;
	private Date fEmision;
	private Integer idOrigen;
	private Integer idDestino;
	private Integer idEncomienda;
	private String numeroGuiaCliente;

	public GuiaRemision(){
		
	}

	public Integer getIdGuiaRemision() {
		return idGuiaRemision;
	}

	public void setIdGuiaRemision(Integer idGuiaRemision) {
		this.idGuiaRemision = idGuiaRemision;
	}

	public String getSerieGuia() {
		return serieGuia;
	}

	public void setSerieGuia(String serieGuia) {
		this.serieGuia = serieGuia;
	}

	public Integer getNumeroGuia() {
		return numeroGuia;
	}

	public void setNumeroGuia(Integer numeroGuia) {
		this.numeroGuia = numeroGuia;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public Date getfEmision() {
		return fEmision;
	}

	public void setfEmision(Date fEmision) {
		this.fEmision = fEmision;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public Integer getIdEncomienda() {
		return idEncomienda;
	}

	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}

	public String getNumeroGuiaCliente() {
		return numeroGuiaCliente;
	}

	public void setNumeroGuiaCliente(String numeroGuiaCliente) {
		this.numeroGuiaCliente = numeroGuiaCliente;
	}
	
	
	
}
