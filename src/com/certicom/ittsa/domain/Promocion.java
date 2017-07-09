package com.certicom.ittsa.domain;

import java.util.Date;

public class Promocion {

	private int idPromocion;
	private String descripcion;
	private Double valorBoleto;
	private Date fRegistro;
	private String tipo;
	private boolean estado;
	private Integer idTipoPromocion;
	private Integer idOrigen;
	private Integer idDestino;
	private Date fInicio;
	private Date fFin;
	private Integer idAutorizante;
	private Integer tipoValorAplicado;
	private Double valorAplicado;
	private String tipoViaje;
	private Integer isTodos;
	private Integer idservicioida;
	private Integer idserviciovuelta;
	
	
	public Promocion() {

	}
	
	public String getTipoViaje() {
		return tipoViaje;
	}

	public void setTipoViaje(String tipoViaje) {
		this.tipoViaje = tipoViaje;
	}

	public Integer getTipoValorAplicado() {
		return tipoValorAplicado;
	}

	public void setTipoValorAplicado(Integer tipoValorAplicado) {
		this.tipoValorAplicado = tipoValorAplicado;
	}

	public Double getValorAplicado() {
		return valorAplicado;
	}

	public void setValorAplicado(Double valorAplicado) {
		this.valorAplicado = valorAplicado;
	}

	public int getIdPromocion() {
		return idPromocion;
	}
	public void setIdPromocion(int idPromocion) {
		this.idPromocion = idPromocion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getValorBoleto() {
		return valorBoleto;
	}
	public void setValorBoleto(Double valorBoleto) {
		this.valorBoleto = valorBoleto;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public Integer getIdTipoPromocion() {
		return idTipoPromocion;
	}
	public void setIdTipoPromocion(Integer idTipoPromocion) {
		this.idTipoPromocion = idTipoPromocion;
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
	public Integer getIdAutorizante() {
		return idAutorizante;
	}
	public void setIdAutorizante(Integer idAutorizante) {
		this.idAutorizante = idAutorizante;
	}
	public Date getfInicio() {
		return fInicio;
	}
	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}
	public Date getfFin() {
		return fFin;
	}
	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}

	public Integer getIsTodos() {
		return isTodos;
	}

	public void setIsTodos(Integer isTodos) {
		this.isTodos = isTodos;
	}


	public Integer getIdservicioida() {
		return idservicioida;
	}

	public void setIdservicioida(Integer idservicioida) {
		this.idservicioida = idservicioida;
	}

	public Integer getIdserviciovuelta() {
		return idserviciovuelta;
	}

	public void setIdserviciovuelta(Integer idserviciovuelta) {
		this.idserviciovuelta = idserviciovuelta;
	}
	
	
	
}
