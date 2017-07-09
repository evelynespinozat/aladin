package com.certicom.ittsa.domain;

import java.util.Date;

public class ListaNegra {
	private Integer idListaNegra;
	private String DNI;
	private String nombreCompleto;
	private String motivo;
	private Date fRegistro;
	private Integer usuarioAutorizante;
	private Integer flagAutorizacion;
	private Date fActualizacion;
	private boolean estado;
	private Integer usuarioRegistro;
	//INICIO PISCOYA
	private Date fAproxIncidencia;
	private String rutaOrigen;
	private String rutaDestino;	
	//INICIO PISCOYA
	private String nomUsuAutoizante;
	
	public Integer getIdListaNegra() {
		return idListaNegra;
	}
	public void setIdListaNegra(Integer idListaNegra) {
		this.idListaNegra = idListaNegra;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public Integer getUsuarioAutorizante() {
		return usuarioAutorizante;
	}
	public void setUsuarioAutorizante(Integer usuarioAutorizante) {
		this.usuarioAutorizante = usuarioAutorizante;
	}
	public Integer getFlagAutorizacion() {
		return flagAutorizacion;
	}
	public void setFlagAutorizacion(Integer flagAutorizacion) {
		this.flagAutorizacion = flagAutorizacion;
	}
	public Date getfActualizacion() {
		return fActualizacion;
	}
	public void setfActualizacion(Date fActualizacion) {
		this.fActualizacion = fActualizacion;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}
	public void setUsuarioRegistro(Integer usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	public String getNomUsuAutoizante() {
		return nomUsuAutoizante;
	}
	public void setNomUsuAutoizante(String nomUsuAutoizante) {
		this.nomUsuAutoizante = nomUsuAutoizante;
	}
	public Date getfAproxIncidencia() {
		return fAproxIncidencia;
	}
	public void setfAproxIncidencia(Date fAproxIncidencia) {
		this.fAproxIncidencia = fAproxIncidencia;
	}
	public String getRutaOrigen() {
		return rutaOrigen;
	}
	public void setRutaOrigen(String rutaOrigen) {
		this.rutaOrigen = rutaOrigen;
	}
	public String getRutaDestino() {
		return rutaDestino;
	}
	public void setRutaDestino(String rutaDestino) {
		this.rutaDestino = rutaDestino;
	}

	
	
	
	
	
}
