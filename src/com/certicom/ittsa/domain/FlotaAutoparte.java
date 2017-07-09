package com.certicom.ittsa.domain;

import java.util.Date;

public class FlotaAutoparte {

	private Integer idFlotaAuto;
	private Integer idBus;
	private Integer idAutomotor;
	private Integer idAutoparte;
	private Integer kmActual;
	private Date fecCambio;
	private Date fecKmIncremento;
	private Date fecKmIncrementoAprox;
	private Integer kmAproximado;
	private Integer kmUltimoCambio;

	/* campos visuales */
	private String desAutoparte;
	private Integer kmCambio;
	private Integer kmAdherir;

	// reporte
	private Integer numBus;
	private String desAutomotor;
	private String tipoKilometraje;

	public Integer getKmUltimoCambio() {
		return kmUltimoCambio;
	}

	public void setKmUltimoCambio(Integer kmUltimoCambio) {
		this.kmUltimoCambio = kmUltimoCambio;
	}

	public Date getFecKmIncrementoAprox() {
		return fecKmIncrementoAprox;
	}

	public String getTipoKilometraje() {
		return tipoKilometraje;
	}

	public void setTipoKilometraje(String tipoKilometraje) {
		this.tipoKilometraje = tipoKilometraje;
	}

	public void setFecKmIncrementoAprox(Date fecKmIncrementoAprox) {
		this.fecKmIncrementoAprox = fecKmIncrementoAprox;
	}

	public FlotaAutoparte() {
	}

	public Integer getKmAproximado() {
		return kmAproximado;
	}

	public void setKmAproximado(Integer kmAproximado) {
		this.kmAproximado = kmAproximado;
	}

	public Integer getNumBus() {
		return numBus;
	}

	public void setNumBus(Integer numBus) {
		this.numBus = numBus;
	}

	public String getDesAutomotor() {
		return desAutomotor;
	}

	public void setDesAutomotor(String desAutomotor) {
		this.desAutomotor = desAutomotor;
	}

	public Date getFecKmIncremento() {
		return fecKmIncremento;
	}

	public void setFecKmIncremento(Date fecKmIncremento) {
		this.fecKmIncremento = fecKmIncremento;
	}

	public Integer getKmAdherir() {
		return kmAdherir;
	}

	public void setKmAdherir(Integer kmAdherir) {
		this.kmAdherir = kmAdherir;
	}

	public Integer getIdFlotaAuto() {
		return idFlotaAuto;
	}

	public void setIdFlotaAuto(Integer idFlotaAuto) {
		this.idFlotaAuto = idFlotaAuto;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
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

	public Integer getKmActual() {
		return kmActual;
	}

	public void setKmActual(Integer kmActual) {
		this.kmActual = kmActual;
	}

	public Date getFecCambio() {
		return fecCambio;
	}

	public void setFecCambio(Date fecCambio) {
		this.fecCambio = fecCambio;
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

}
