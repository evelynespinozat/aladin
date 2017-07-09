package com.certicom.ittsa.domain;

import java.util.Date;

public class ObjEncontrados {
	
	private Integer idObjeto;
	private String descripcion;
	private String observacion;
	private Integer nroAsiento;
	private String dniEntregado;
	private String nomEntregado;
	private String estado;
	private Integer IdProgramacion;
	private Integer IdManifiesto;
	private Date fechaEntrega;
	private Date fRegistro;
	private Date fCreacion;
	private String usuarioEntrega;
	
	//atributos alternos
	private Integer idOrigen;
	private Integer idDestino;
	private String hSalida;
	private Date fSalida;
	private String numero;
	private String desServicio;
	private Date fLlegadaReal;
	
	public String gethSalida() {
		return hSalida;
	}
	public void sethSalida(String hSalida) {
		this.hSalida = hSalida;
	}
	public Date getfSalida() {
		return fSalida;
	}
	public void setfSalida(Date fSalida) {
		this.fSalida = fSalida;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
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
	public Integer getIdObjeto() {
		return idObjeto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public Integer getNroAsiento() {
		return nroAsiento;
	}
	public String getDniEntregado() {
		return dniEntregado;
	}
	public String getNomEntregado() {
		return nomEntregado;
	}
	public String getEstado() {
		return estado;
	}
	public Integer getIdProgramacion() {
		return IdProgramacion;
	}
	public Integer getIdManifiesto() {
		return IdManifiesto;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public Date getfCreacion() {
		return fCreacion;
	}
	public String getUsuarioEntrega() {
		return usuarioEntrega;
	}
	public void setIdObjeto(Integer idObjeto) {
		this.idObjeto = idObjeto;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public void setNroAsiento(Integer nroAsiento) {
		this.nroAsiento = nroAsiento;
	}
	public void setDniEntregado(String dniEntregado) {
		this.dniEntregado = dniEntregado;
	}
	public void setNomEntregado(String nomEntregado) {
		this.nomEntregado = nomEntregado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setIdProgramacion(Integer idProgramacion) {
		IdProgramacion = idProgramacion;
	}
	public void setIdManifiesto(Integer idManifiesto) {
		IdManifiesto = idManifiesto;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public void setfCreacion(Date fCreacion) {
		this.fCreacion = fCreacion;
	}
	public void setUsuarioEntrega(String usuarioEntrega) {
		this.usuarioEntrega = usuarioEntrega;
	}
	public String getDesServicio() {
		return desServicio;
	}
	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
	}
	public Date getfLlegadaReal() {
		return fLlegadaReal;
	}
	public void setfLlegadaReal(Date fLlegadaReal) {
		this.fLlegadaReal = fLlegadaReal;
	}
	
}
