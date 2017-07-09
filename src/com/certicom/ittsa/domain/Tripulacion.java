package com.certicom.ittsa.domain;

import java.util.Date;

public class Tripulacion {
	private Integer idTripulacion;
	private Integer idProgramacion;
	private Integer idBus;
	private Integer idPiloto;
	private Integer idCopiloto;
	private Integer idTerramoza;
	private Date fRegistro;
	private Integer tipo;
	private Integer estado;
	private Integer idTerramoza2;
	private Boolean flagTempPiloto;
	private Boolean flagTempCopiloto;
	
	// datos aleatorios
	private String piloto;
	private String dnipiloto;
	private String licPiloto;
	private String copiloto;
	private String dnicopiloto;
	private String licCopiloto;
	private String terramoza1;
	private String dniterramoza1;
	private String terramoza2;
	private String dniterramoza2;
	private Integer numeroBus;
	private String placabus;
	private String modeloBus;
	private String marcaBus;
	
	
	public Tripulacion(){}

	public Integer getIdTripulacion() {
		return idTripulacion;
	}

	public void setIdTripulacion(Integer idTripulacion) {
		this.idTripulacion = idTripulacion;
	}

	public Integer getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public Integer getIdCopiloto() {
		return idCopiloto;
	}

	public void setIdCopiloto(Integer idCopiloto) {
		this.idCopiloto = idCopiloto;
	}

	public Integer getIdTerramoza() {
		return idTerramoza;
	}

	public void setIdTerramoza(Integer idTerramoza) {
		this.idTerramoza = idTerramoza;
	}

	public Date getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(Date fRegistro) {
		this.fRegistro = fRegistro;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdTerramoza2() {
		return idTerramoza2;
	}

	public void setIdTerramoza2(Integer idTerramoza2) {
		this.idTerramoza2 = idTerramoza2;
	}

	public String getPiloto() {
		return piloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}

	public String getCopiloto() {
		return copiloto;
	}

	public void setCopiloto(String copiloto) {
		this.copiloto = copiloto;
	}

	public String getTerramoza1() {
		return terramoza1;
	}

	public void setTerramoza1(String terramoza1) {
		this.terramoza1 = terramoza1;
	}

	public String getTerramoza2() {
		return terramoza2;
	}

	public void setTerramoza2(String terramoza2) {
		this.terramoza2 = terramoza2;
	}

	public String getDnipiloto() {
		return dnipiloto;
	}

	public void setDnipiloto(String dnipiloto) {
		this.dnipiloto = dnipiloto;
	}

	public String getDnicopiloto() {
		return dnicopiloto;
	}

	public void setDnicopiloto(String dnicopiloto) {
		this.dnicopiloto = dnicopiloto;
	}

	public String getDniterramoza1() {
		return dniterramoza1;
	}

	public void setDniterramoza1(String dniterramoza1) {
		this.dniterramoza1 = dniterramoza1;
	}

	public String getDniterramoza2() {
		return dniterramoza2;
	}

	public void setDniterramoza2(String dniterramoza2) {
		this.dniterramoza2 = dniterramoza2;
	}

	public Integer getNumeroBus() {
		return numeroBus;
	}

	public void setNumeroBus(Integer numeroBus) {
		this.numeroBus = numeroBus;
	}

	public String getPlacabus() {
		return placabus;
	}

	public void setPlacabus(String placabus) {
		this.placabus = placabus;
	}

	public String getModeloBus() {
		return modeloBus;
	}

	public void setModeloBus(String modeloBus) {
		this.modeloBus = modeloBus;
	}

	public String getLicPiloto() {
		return licPiloto;
	}

	public void setLicPiloto(String licPiloto) {
		this.licPiloto = licPiloto;
	}

	public String getLicCopiloto() {
		return licCopiloto;
	}

	public void setLicCopiloto(String licCopiloto) {
		this.licCopiloto = licCopiloto;
	}

	public String getMarcaBus() {
		return marcaBus;
	}

	public void setMarcaBus(String marcaBus) {
		this.marcaBus = marcaBus;
	}

	public Boolean getFlagTempPiloto() {
		return flagTempPiloto;
	}

	public void setFlagTempPiloto(Boolean flagTempPiloto) {
		this.flagTempPiloto = flagTempPiloto;
	}

	public Boolean getFlagTempCopiloto() {
		return flagTempCopiloto;
	}

	public void setFlagTempCopiloto(Boolean flagTempCopiloto) {
		this.flagTempCopiloto = flagTempCopiloto;
	}
	
	
	

}
