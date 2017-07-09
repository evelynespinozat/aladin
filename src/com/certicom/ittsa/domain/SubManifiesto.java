package com.certicom.ittsa.domain;

import java.util.Date;

public class SubManifiesto {
	
	private Integer idSubmanifiesto;
	private Integer idProgramacion;
	private Integer idEncomienda;
	private Date fRegistro;
	private Integer usucrea;
	
	public Integer getIdSubmanifiesto() {
		return idSubmanifiesto;
	}
	public void setIdSubmanifiesto(Integer idSubmanifiesto) {
		this.idSubmanifiesto = idSubmanifiesto;
	}
	public Integer getIdProgramacion() {
		return idProgramacion;
	}
	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}
	public Integer getIdEncomienda() {
		return idEncomienda;
	}
	public void setIdEncomienda(Integer idEncomienda) {
		this.idEncomienda = idEncomienda;
	}
	public Date getfRegistro() {
		return fRegistro;
	}
	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}
	public Integer getUsucrea() {
		return usucrea;
	}
	public void setUsucrea(Integer usucrea) {
		this.usucrea = usucrea;
	}

	
	
}
