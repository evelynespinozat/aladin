package com.certicom.ittsa.domain;

public class AutoparteAutomotor {
	
	private Integer idParteAutomotor;
	private Integer idAutomotor;
	private Integer idAutoparte;
	
	//altermos
	private String desPartes;
	
	//temporales
	private String desKm;
	
	public String getDesKm() {
		return desKm;
	}
	public void setDesKm(String desKm) {
		this.desKm = desKm;
	}
	//fin temporales
	public AutoparteAutomotor(){
		
	}

	public Integer getIdParteAutomotor() {
		return idParteAutomotor;
	}

	public String getDesPartes() {
		return desPartes;
	}

	public void setDesPartes(String desPartes) {
		this.desPartes = desPartes;
	}

	public void setIdParteAutomotor(Integer idParteAutomotor) {
		this.idParteAutomotor = idParteAutomotor;
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

	

}
