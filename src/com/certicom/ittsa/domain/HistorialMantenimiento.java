package com.certicom.ittsa.domain;

import java.util.Date;

public class HistorialMantenimiento {

	private Integer idHistoMant;
	private Integer idMecanico;
	private Date fecInicio;
	private Date fecFin;
	private String tipMantenimiento;
	private String tipMantEfectuado;
	private Integer cantidad;
	private String observacion;
	private Integer idFlotaAuto;
	private Integer estadoMecanico;
	private Date fecAsignaMecanico;
	private Date fecMantenimiento;
	private Integer kmRecibido;
	private String tipoAccion;
	private String tipoServicio;

	/* opcionales */
	private String nombreMeca;
	private Integer idAutomotor;
	private String desAutomotor;
	private Integer idAutoparte;
	private String desAutoparte;
	private Integer kmCambio;
	private Integer kmActual;
	private Integer numBus;

	public HistorialMantenimiento() {
	}

	public Integer getNumBus() {
		return numBus;
	}

	public void setNumBus(Integer numBus) {
		this.numBus = numBus;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public Date getFecAsignaMecanico() {
		return fecAsignaMecanico;
	}

	public void setFecAsignaMecanico(Date fecAsignaMecanico) {
		this.fecAsignaMecanico = fecAsignaMecanico;
	}

	public Date getFecMantenimiento() {
		return fecMantenimiento;
	}

	public void setFecMantenimiento(Date fecMantenimiento) {
		this.fecMantenimiento = fecMantenimiento;
	}

	public Integer getKmRecibido() {
		return kmRecibido;
	}

	public void setKmRecibido(Integer kmRecibido) {
		this.kmRecibido = kmRecibido;
	}

	public Integer getIdAutomotor() {
		return idAutomotor;
	}

	public void setIdAutomotor(Integer idAutomotor) {
		this.idAutomotor = idAutomotor;
	}

	public Integer getIdAutoparte() {
		return idAutoparte;
	}

	public void setIdAutoparte(Integer idAutoparte) {
		this.idAutoparte = idAutoparte;
	}

	public String getNombreMeca() {
		return nombreMeca;
	}

	public void setNombreMeca(String nombreMeca) {
		this.nombreMeca = nombreMeca;
	}

	public String getDesAutomotor() {
		return desAutomotor;
	}

	public void setDesAutomotor(String desAutomotor) {
		this.desAutomotor = desAutomotor;
	}

	public String getDesAutoparte() {
		return desAutoparte;
	}

	public void setDesAutoparte(String desAutoparte) {
		this.desAutoparte = desAutoparte;
	}

	public Integer getKmCambio() {
		return kmCambio;
	}

	public void setKmCambio(Integer kmCambio) {
		this.kmCambio = kmCambio;
	}

	public Integer getKmActual() {
		return kmActual;
	}

	public void setKmActual(Integer kmActual) {
		this.kmActual = kmActual;
	}

	public Integer getEstadoMecanico() {
		return estadoMecanico;
	}

	public void setEstadoMecanico(Integer estadoMecanico) {
		this.estadoMecanico = estadoMecanico;
	}

	public Integer getIdHistoMant() {
		return idHistoMant;
	}

	public void setIdHistoMant(Integer idHistoMant) {
		this.idHistoMant = idHistoMant;
	}

	public Integer getIdMecanico() {
		return idMecanico;
	}

	public void setIdMecanico(Integer idMecanico) {
		this.idMecanico = idMecanico;
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

	public String getTipMantenimiento() {
		return tipMantenimiento;
	}

	public void setTipMantenimiento(String tipMantenimiento) {
		this.tipMantenimiento = tipMantenimiento;
	}

	public String getTipMantEfectuado() {
		return tipMantEfectuado;
	}

	public void setTipMantEfectuado(String tipMantEfectuado) {
		this.tipMantEfectuado = tipMantEfectuado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getIdFlotaAuto() {
		return idFlotaAuto;
	}

	public void setIdFlotaAuto(Integer idFlotaAuto) {
		this.idFlotaAuto = idFlotaAuto;
	}

}
