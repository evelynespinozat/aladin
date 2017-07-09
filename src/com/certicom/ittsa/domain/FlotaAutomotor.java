package com.certicom.ittsa.domain;

import java.util.List;

public class FlotaAutomotor {
	
	private Integer idFlotaAutomotor;
	private Integer idBus;
	private Integer idAutomotor;
	
	//alternos
	private String desAutomotor;
	private List<FlotaAutoparte> listaFlotaAutoparte;
	

	public FlotaAutomotor(){
	}
	
	public List<FlotaAutoparte> getListaFlotaAutoparte() {
		return listaFlotaAutoparte;
	}
	
	public void setListaFlotaAutoparte(List<FlotaAutoparte> listaFlotaAutoparte) {
		this.listaFlotaAutoparte = listaFlotaAutoparte;
	}

	public String getDesAutomotor() {
		return desAutomotor;
	}

	public void setDesAutomotor(String desAutomotor) {
		this.desAutomotor = desAutomotor;
	}

	public Integer getIdFlotaAutomotor() {
		return idFlotaAutomotor;
	}

	public void setIdFlotaAutomotor(Integer idFlotaAutomotor) {
		this.idFlotaAutomotor = idFlotaAutomotor;
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

}
