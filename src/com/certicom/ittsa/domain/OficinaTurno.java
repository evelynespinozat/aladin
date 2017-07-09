package com.certicom.ittsa.domain;

public class OficinaTurno {
	
	private Integer idAgenciaTurno;
	private Integer idOficina;
	private Integer idTurno;
	private boolean estado;
	
	//datos para mostrar en la grilla
	private String desOficina;
	private String desTurno;
	private Integer idAgencia;
	private String desAgencia;
	private String hInicio;
	private String hFin;
	
	public OficinaTurno(){
	}

	public Integer getIdAgenciaTurno() {
		return idAgenciaTurno;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public Integer getIdTurno() {
		return idTurno;
	}

	public boolean isEstado() {
		return estado;
	}

	public String getDesOficina() {
		return desOficina;
	}

	public String getDesTurno() {
		return desTurno;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public String getDesAgencia() {
		return desAgencia;
	}

	public void setIdAgenciaTurno(Integer idAgenciaTurno) {
		this.idAgenciaTurno = idAgenciaTurno;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina.toUpperCase();
	}

	public void setDesTurno(String desTurno) {
		this.desTurno = desTurno.toUpperCase();
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public void setDesAgencia(String desAgencia) {
		this.desAgencia = desAgencia.toUpperCase();
	}

	public String gethInicio() {
		return hInicio;
	}

	public void sethInicio(String hInicio) {
		this.hInicio = hInicio;
	}

	public String gethFin() {
		return hFin;
	}

	public void sethFin(String hFin) {
		this.hFin = hFin;
	}

		
}
