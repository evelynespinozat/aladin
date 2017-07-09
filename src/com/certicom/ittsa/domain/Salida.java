package com.certicom.ittsa.domain;

import java.util.Date;

public class Salida {
	
	private Integer IdSalida;
	private Integer IdProgramacion;
	private Integer Idagencia;
	private Integer IdOficina;
	private Integer IdOrigen;
	private Integer IdDestino;
	private Date FSalida;
	private Date FRegistro;
	public String flagLlegada;
	
	public Integer getIdSalida() {
		return IdSalida;
	}
	public Integer getIdProgramacion() {
		return IdProgramacion;
	}
	public Integer getIdagencia() {
		return Idagencia;
	}
	public Integer getIdOficina() {
		return IdOficina;
	}
	public Integer getIdOrigen() {
		return IdOrigen;
	}
	public Integer getIdDestino() {
		return IdDestino;
	}
	public Date getFSalida() {
		return FSalida;
	}
	public Date getFRegistro() {
		return FRegistro;
	}
	public void setIdSalida(Integer idSalida) {
		IdSalida = idSalida;
	}
	public void setIdProgramacion(Integer idProgramacion) {
		IdProgramacion = idProgramacion;
	}
	public void setIdagencia(Integer idagencia) {
		Idagencia = idagencia;
	}
	public void setIdOficina(Integer idOficina) {
		IdOficina = idOficina;
	}
	public void setIdOrigen(Integer idOrigen) {
		IdOrigen = idOrigen;
	}
	public void setIdDestino(Integer idDestino) {
		IdDestino = idDestino;
	}
	public void setFSalida(Date fSalida) {
		FSalida = fSalida;
	}
	public void setFRegistro(Date fRegistro) {
		FRegistro = fRegistro;
	}
	public String getFlagLlegada() {
		return flagLlegada;
	}
	public void setFlagLlegada(String flagLlegada) {
		this.flagLlegada = flagLlegada;
	}
}
