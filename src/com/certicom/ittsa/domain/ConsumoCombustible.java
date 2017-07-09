package com.certicom.ittsa.domain;

import java.util.Date;

public class ConsumoCombustible {
	
	private Integer idConCombus;
	private Integer idProgramacion;
	private Date fecRegKilome;
	private Date fecRegCombus;
	private Integer kmInicial;
	private Integer kmFinal;
	private Integer kmRecorridos;
	private double consumo;
	private double kmGalon;
	private Integer idagencia;
	private double precioComb; //precio Unit.
	private double costoTotal;
	private double costoKm;
	private Integer idPiloto;
	private Integer idCopiloto;
	private Integer idBus;
	
	//parametro para listar 
	private String desServicio;
	private String piloto1;
	private String piloto2;
	private Integer numBus;
	private String desAgen; 
	private Integer idOrigenComb;
	
	//bolleanos para la grilla
	private boolean activar;
	private String desAgenRecarga;
	
	public ConsumoCombustible(){	
	}
	
	public String getDesAgenRecarga() {
		return desAgenRecarga;
	}

	public void setDesAgenRecarga(String desAgenRecarga) {
		this.desAgenRecarga = desAgenRecarga;
	}

	public Integer getIdOrigenComb() {
		return idOrigenComb;
	}

	public void setIdOrigenComb(Integer idOrigenComb) {
		this.idOrigenComb = idOrigenComb;
	}

	public boolean isActivar() {
		return activar;
	}

	public void setActivar(boolean activar) {
		this.activar = activar;
	}

	public String getDesServicio() {
		return desServicio;
	}

	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
	}

	public String getPiloto1() {
		return piloto1;
	}

	public void setPiloto1(String piloto1) {
		this.piloto1 = piloto1;
	}

	public String getPiloto2() {
		return piloto2;
	}

	public void setPiloto2(String piloto2) {
		this.piloto2 = piloto2;
	}

	public Integer getNumBus() {
		return numBus;
	}

	public void setNumBus(Integer numBus) {
		this.numBus = numBus;
	}

	public String getDesAgen() {
		return desAgen;
	}

	public void setDesAgen(String desAgen) {
		this.desAgen = desAgen;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Integer getIdConCombus() {
		return idConCombus;
	}

	public void setIdConCombus(Integer idConCombus) {
		this.idConCombus = idConCombus;
	}

	public Integer getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Integer idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public Date getFecRegKilome() {
		return fecRegKilome;
	}

	public void setFecRegKilome(Date fecRegKilome) {
		this.fecRegKilome = fecRegKilome;
	}

	public Date getFecRegCombus() {
		return fecRegCombus;
	}

	public void setFecRegCombus(Date fecRegCombus) {
		this.fecRegCombus = fecRegCombus;
	}

	public Integer getKmInicial() {
		return kmInicial;
	}

	public void setKmInicial(Integer kmInicial) {
		this.kmInicial = kmInicial;
	}

	public Integer getKmFinal() {
		return kmFinal;
	}

	public void setKmFinal(Integer kmFinal) {
		this.kmFinal = kmFinal;
	}

	public Integer getKmRecorridos() {
		return kmRecorridos;
	}

	public void setKmRecorridos(Integer kmRecorridos) {
		this.kmRecorridos = kmRecorridos;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public double getKmGalon() {
		return kmGalon;
	}

	public void setKmGalon(double kmGalon) {
		this.kmGalon = kmGalon;
	}

	public Integer getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public double getPrecioComb() {
		return precioComb;
	}

	public void setPrecioComb(double precioComb) {
		this.precioComb = precioComb;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public double getCostoKm() {
		return costoKm;
	}

	public void setCostoKm(double costoKm) {
		this.costoKm = costoKm;
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


}
