package com.pe.certicom.ittsa.commons;

import java.util.Date;


public class BoletoFilter {
	
	private Integer origen;
	private Integer destino;
	private Date finicio;
	private Date ffin;
	private Date fechaEntregaDelivery;
	private String estadoDelivery;
	private Integer idagencia;
	private Integer idoficina;
	private String turno_entrega;

	
	public Integer getOrigen() {
		return origen;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public Integer getDestino() {
		return destino;
	}

	public void setDestino(Integer destino) {
		this.destino = destino;
	}


	public String getEstadoDelivery() {
		return estadoDelivery;
	}

	public void setEstadoDelivery(String estadoDelivery) {
		this.estadoDelivery = estadoDelivery;
	}

	public Date getFinicio() {
		return finicio;
	}

	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	public Date getFfin() {
		return ffin;
	}

	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	public Integer getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Integer getIdoficina() {
		return idoficina;
	}

	public void setIdoficina(Integer idoficina) {
		this.idoficina = idoficina;
	}

	public String getTurno_entrega() {
		return turno_entrega;
	}

	public void setTurno_entrega(String turno_entrega) {
		this.turno_entrega = turno_entrega;
	}

	public Date getFechaEntregaDelivery() {
		return fechaEntregaDelivery;
	}

	public void setFechaEntregaDelivery(Date fechaEntregaDelivery) {
		this.fechaEntregaDelivery = fechaEntregaDelivery;
	}
	
	
	
	

}
