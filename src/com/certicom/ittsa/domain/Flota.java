package com.certicom.ittsa.domain;

import java.util.Date;

public class Flota {

	private Integer idBus;
	private Integer idClase;
	private Integer asientos;
	private Integer numero;
	private String placa;
	private String marca;
	private String modelo;
	private Integer fabricacion;
	private Integer recorrido;
	private String tPropiedad;
	private String inscritoMTC;
	private Integer piloto;
	private Integer copiloto;
	private String tipoCombustible;
	private boolean estado;
	private Integer idTipoFlota;
	private Integer idCertiSat;
	private String nroChasis;

	/** Otros **/
	private String nombPiloto;
	private String nombCopiloto;
	private String descClase;
	private String desTipoFlota;
	private Integer kmAproximado;
	/*
	 * Valores del campo disponibilidad 1=Listo Programar 2=Programado
	 * 3=Solicitado para Taller 4=En Mantenimiento 5=En Reparacion 6=Reten
	 * 7=Disponible 8=En Ruta
	 */
	private Integer disponibilidad;
	private Integer kmAdherir;
	private Date fecFiltro;
	private Date fecInicio;
	private Date fecFin;
	private String desServicio;
	private Integer idServicio;
	private String hSalida;
	
	private String telefono;

	public Flota() {
	}

	public String gethSalida() {
		return hSalida;
	}

	public void sethSalida(String hSalida) {
		this.hSalida = hSalida;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public String getDesServicio() {
		return desServicio;
	}

	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
	}

	public Date getFecFiltro() {
		return fecFiltro;
	}

	public void setFecFiltro(Date fecFiltro) {
		this.fecFiltro = fecFiltro;
	}

	public Integer getKmAproximado() {
		return kmAproximado;
	}

	public void setKmAproximado(Integer kmAproximado) {
		this.kmAproximado = kmAproximado;
	}

	public Integer getKmAdherir() {
		return kmAdherir;
	}

	public void setKmAdherir(Integer kmAdherir) {
		this.kmAdherir = kmAdherir;
	}

	public String getDesTipoFlota() {
		return desTipoFlota;
	}

	public void setDesTipoFlota(String desTipoFlota) {
		this.desTipoFlota = desTipoFlota;
	}

	public Integer getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Integer disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public Integer getIdClase() {
		return idClase;
	}

	public Integer getAsientos() {
		return asientos;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getPlaca() {
		return placa;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public Integer getFabricacion() {
		return fabricacion;
	}

	public Integer getRecorrido() {
		return recorrido;
	}

	public String gettPropiedad() {
		return tPropiedad;
	}

	public String getInscritoMTC() {
		return inscritoMTC;
	}

	public Integer getPiloto() {
		return piloto;
	}

	public Integer getCopiloto() {
		return copiloto;
	}

	public String getTipoCombustible() {
		return tipoCombustible;
	}

	public boolean isEstado() {
		return estado;
	}

	public Integer getIdTipoFlota() {
		return idTipoFlota;
	}

	public Integer getIdCertiSat() {
		return idCertiSat;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}

	public void setAsientos(Integer asientos) {
		this.asientos = asientos;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setPlaca(String placa) {
		this.placa = placa.toUpperCase();
	}

	public void setMarca(String marca) {
		this.marca = marca.toUpperCase();
	}

	public void setModelo(String modelo) {
		this.modelo = modelo.toUpperCase();
	}

	public void setFabricacion(Integer fabricacion) {
		this.fabricacion = fabricacion;
	}

	public void setRecorrido(Integer recorrido) {
		this.recorrido = recorrido;
	}

	public void settPropiedad(String tPropiedad) {
		this.tPropiedad = tPropiedad.toUpperCase();
	}

	public void setInscritoMTC(String inscritoMTC) {
		this.inscritoMTC = inscritoMTC.toUpperCase();
	}

	public void setPiloto(Integer piloto) {
		this.piloto = piloto;
	}

	public void setCopiloto(Integer copiloto) {
		this.copiloto = copiloto;
	}

	public void setTipoCombustible(String tipoCombustible) {
		this.tipoCombustible = tipoCombustible.toUpperCase();
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setIdTipoFlota(Integer idTipoFlota) {
		this.idTipoFlota = idTipoFlota;
	}

	public void setIdCertiSat(Integer idCertiSat) {
		this.idCertiSat = idCertiSat;
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

	public String getDescClase() {
		return descClase;
	}

	public void setDescClase(String descClase) {
		this.descClase = descClase;
	}

	public String getNroChasis() {
		return nroChasis;
	}

	public void setNroChasis(String nroChasis) {
		this.nroChasis = nroChasis;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
