package com.certicom.ittsa.domain;

import java.util.Date;

public class FlotaVacia {
	
	private Integer idFlotaVacia;
	private Integer idBus;
	private  Integer idOrigen;
	private Integer idDestino;
	private Date fechaSalida;
	private Date fRegistro;	
	private String motivo;
	private String observacion;
	
	private Integer numeroBus;
	private String desOrigen;
	private String desDestino;
	
	public String namePiloto;
	public String nameCopiloto;
	public Integer idpiloto;
	public Integer idcopiloto;
	public String dniPiloto;
	public String dniCopiloto;
	public String nroBus;
	public String placa;
	public String telefono;
	
	
	private Boolean pilotCopilotReasignado;

	
	
	// filtros
	private Date fecIni;
	private Date fecFin;
	
	public Integer getIdBus() {
		return idBus;
	}
	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
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
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Integer getNumeroBus() {
		return numeroBus;
	}
	public void setNumeroBus(Integer numeroBus) {
		this.numeroBus = numeroBus;
	}
	public String getDesOrigen() {
		return desOrigen;
	}
	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
	}
	public String getDesDestino() {
		return desDestino;
	}
	public void setDesDestino(String desDestino) {
		this.desDestino = desDestino;
	}
	public Integer getIdFlotaVacia() {
		return idFlotaVacia;
	}
	public void setIdFlotaVacia(Integer idFlotaVacia) {
		this.idFlotaVacia = idFlotaVacia;
	}
	public Date getFecIni() {
		return fecIni;
	}
	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}
	public Date getFecFin() {
		return fecFin;
	}
	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}
	public String getNamePiloto() {
		return namePiloto;
	}
	public String getNameCopiloto() {
		return nameCopiloto;
	}
	public void setNamePiloto(String namePiloto) {
		this.namePiloto = namePiloto;
	}
	public void setNameCopiloto(String nameCopiloto) {
		this.nameCopiloto = nameCopiloto;
	}
	public String getDniPiloto() {
		return dniPiloto;
	}
	public String getDniCopiloto() {
		return dniCopiloto;
	}
	public void setDniPiloto(String dniPiloto) {
		this.dniPiloto = dniPiloto;
	}
	public void setDniCopiloto(String dniCopiloto) {
		this.dniCopiloto = dniCopiloto;
	}
	public String getNroBus() {
		return nroBus;
	}
	public void setNroBus(String nroBus) {
		this.nroBus = nroBus;
	}
	public String getPlaca() {
		return placa;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Integer getIdpiloto() {
		return idpiloto;
	}
	public Integer getIdcopiloto() {
		return idcopiloto;
	}
	public Boolean getPilotCopilotReasignado() {
		return pilotCopilotReasignado;
	}
	public void setIdpiloto(Integer idpiloto) {
		this.idpiloto = idpiloto;
	}
	public void setIdcopiloto(Integer idcopiloto) {
		this.idcopiloto = idcopiloto;
	}
	public void setPilotCopilotReasignado(Boolean pilotCopilotReasignado) {
		this.pilotCopilotReasignado = pilotCopilotReasignado;
	}
	

}
